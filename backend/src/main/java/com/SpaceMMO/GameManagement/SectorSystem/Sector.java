package com.SpaceMMO.GameManagement.SectorSystem;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.SpaceMMO.GameManagement.EntitySystem.GameEntity;

import com.SpaceMMO.GameManagement.EntitySystem.PlayerEntity;
import com.SpaceMMO.GameManagement.QuadTree.PooledQuadNodeFactory;
import com.SpaceMMO.GameManagement.QuadTree.QuadTreeNode;
import com.SpaceMMO.GameManagement.ServiceContainer;
import com.SpaceMMO.GameManagement.WebSocketServer.GameNetworkingProtocol.BasicMessageHandlers;
import com.SpaceMMO.UserManagement.UserAccount;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.dyn4j.collision.CollisionPair;
import org.dyn4j.collision.broadphase.*;
import org.dyn4j.dynamics.Body;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.orm.hibernate5.SpringSessionContext;
import org.springframework.security.core.parameters.P;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;

import java.security.Provider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

@Configurable
public class Sector
{
    public static final float SECTOR_WIDTH = 1000000000;
    public static final float SECTOR_HEIGHT = 1000000000;

    public static final int MAX_QUAD_NODES = 10000;

    public static final int TICKS_PER_PACKET = 6;
    public static final int TICKS_PER_SECOND = 30;
    public static final long OPTIMAL_TIME = 1000000000 / TICKS_PER_SECOND;

    public String name;

    //Quad tree for collision handling
    public GenericObjectPool<QuadTreeNode> quadTreePool;
    public QuadTreeNode entityTree;

    //Entity containers
    private ArrayList<GameEntity> entities;
    public ArrayList<Player> players;
    public int nextEntityID = 0;

    public static int nextID = 0;
    public int sectorID;

    //Entity queue, allows outside classes to push entities into the sector
    //Needed since the add entity function makes a non-thread safe operation
    public ConcurrentLinkedQueue<GameEntity> entityAddQueue;

    public ServiceContainer serviceContainer;

    private SectorUpdateThread sectorUpdateThread;

    //public Sap<Body> sapDetector;
    public DynamicAABBTree<Body> collisionDetector;

    //Sector's message queue
    public ConcurrentLinkedQueue<BinaryMessage> sectorMessageQueue;

    public Sector(ServiceContainer serviceContainer)
    {
        name = "Unamed Sector";
        this.serviceContainer = serviceContainer;
        sectorID = nextID++;
        GenericObjectPoolConfig<QuadTreeNode> quadPoolConfig = new GenericObjectPoolConfig<QuadTreeNode>();
        quadPoolConfig.setMaxTotal(MAX_QUAD_NODES);

        quadTreePool = new GenericObjectPool(new PooledQuadNodeFactory(), quadPoolConfig);
        System.out.println("MAX OBJECT POOL: " + quadTreePool.getMaxTotal());
        entities = new ArrayList<GameEntity>();
        entityTree = new QuadTreeNode(0,0,SECTOR_WIDTH, SECTOR_HEIGHT, 0, quadTreePool);
        players = new ArrayList<Player>();
        entityAddQueue = new ConcurrentLinkedQueue<GameEntity>();

        sectorUpdateThread = new SectorUpdateThread();
        sectorUpdateThread.running = true;
        sectorUpdateThread.start();

        //sapDetector = new Sap<Body>(new filter(), new CollisionBodyAABBProducer<Body>(),new NullAABBExpansionMethod());
        collisionDetector = new DynamicAABBTree<Body>(new filter(), new CollisionBodyAABBProducer<Body>(),new NullAABBExpansionMethod());

        sectorMessageQueue = new ConcurrentLinkedQueue<BinaryMessage>() ;

    }

    private class filter implements BroadphaseFilter<Body>
    {
        public boolean isAllowed(Body b1, Body b2)
        {
            return true;
        }
    }

    private void addEntity(GameEntity entity)
    {
        entity.entityID = nextEntityID++;
        entities.add(entity);
        collisionDetector.add(entity.body);
        for(Player player : players)
        {
            try
            {
                serviceContainer.entitySystemHandlers.sendNewEntityNotification(player.session, entity);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public void addPlayer(Player player)
    {
        players.add(player);
        player.currentSector = this;

        //Send new entity notifications for pre existing entities
        for(GameEntity entity : entities)
        {
            try {
                serviceContainer.entitySystemHandlers.sendNewEntityNotification(player.session, entity);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        //Create player entity
        PlayerEntity newPlayerEntity = new PlayerEntity(player);
        addEntity(newPlayerEntity);
    }

    public void removePlayer(Player player)
    {
        player.currentSector = null;
        this.players.remove(player);
    }

    //Statements to be run each simulation frame
    public void gameTick() throws Exception
    {
        //Handle entity updates
        Iterator<GameEntity> iterator = entities.iterator();
        while(iterator.hasNext())
        {
            GameEntity entity = iterator.next();
            if(entity.removeFlag == true)
            {
                for(Player player : players)
                {
                    serviceContainer.entitySystemHandlers.sendEntityDeleteNotification(player.session, entity);
                }
                collisionDetector.remove(entity.body);
                iterator.remove();
            }
            else
            {
                entity.update();
            }
        }


        //Handle Collisions
        collisionDetector.update();
        Iterator<CollisionPair<Body>> pairs = collisionDetector.detectIterator(false);
        while(pairs.hasNext())
        {
            CollisionPair<Body> pair = pairs.next();
            GameEntity e1 = (GameEntity)pair.getFirst().getUserData();
            GameEntity e2 = (GameEntity)pair.getSecond().getUserData();

            e1.handleCollision(e2);
            e2.handleCollision(e1);
        }

        //Add items off the entity add queue
        int amountToAdd = entityAddQueue.size();
        for(int i = 0; i < amountToAdd; i++)
        {
            addEntity(entityAddQueue.remove());
        }
    }

    //Prepare a game state update message for each player and send it
    public void postGameStateUpdate() throws Exception
    {
        for(Player player : this.players)
        {
            //System.out.println("Posting gamestate");
            //serviceContainer.basicMessageHandlers.sendErrorMessage(player.session, (short)1, "test");
            for(GameEntity entity : this.entities)
            {
                serviceContainer.entitySystemHandlers.sendEntityUpdate(player.session, entity);
            }
        }
        //Broadcast messages on the sector message queue to all players
        //grabbing current size since the size may change while broadcasting
        int numberMessages = sectorMessageQueue.size();
        for(int i = 0; i < numberMessages; i++)
        {
            BinaryMessage message = sectorMessageQueue.remove();
            for(Player player : players)
            {
                try
                {
                    player.session.sendMessage(message);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public class SectorUpdateThread extends Thread
    {
        public boolean running;

        //When this hits the max value, it should be reset and game state should be broadcasted to connected players
        private int packetSendCounter;

        public SectorUpdateThread()
        {
            running = true;
            packetSendCounter = 0;
        }

        @Override
        public void run()
        {
            while(running)
            {
                try
                {
                    long startTime = System.nanoTime();

                    gameTick();
                    if(packetSendCounter == TICKS_PER_PACKET)
                    {
                        postGameStateUpdate();
                        packetSendCounter = 0;
                    }

                    long updateTime = System.nanoTime() - startTime;
                    //System.out.println(updateTime);
                    long wait = (OPTIMAL_TIME - updateTime) / 1000000;

                    packetSendCounter++;
                    Thread.sleep(wait);

                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
