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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.orm.hibernate5.SpringSessionContext;
import org.springframework.security.core.parameters.P;
import org.springframework.web.socket.WebSocketSession;

import java.security.Provider;
import java.util.ArrayList;

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
    public GenericObjectPool<QuadTreeNode> quadTreePool;
    public QuadTreeNode entityTree;
    public ArrayList<GameEntity> entities;
    public ArrayList<Player> players;
    public int nextEntityID = 0;

    public static int nextID = 0;
    public int sectorID;

    public ServiceContainer serviceContainer;

    private SectorUpdateThread sectorUpdateThread;

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

        sectorUpdateThread = new SectorUpdateThread();
        sectorUpdateThread.running = true;
        sectorUpdateThread.start();


    }

    public void addEntity(GameEntity entity)
    {
        entity.entityID = nextEntityID++;
        entities.add(entity);
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
        //Handle input

        //Handle entity updates
        for(GameEntity entity : entities)
        {
            entity.update();
        }
        //Handle Collisions

        //Rebuild quad tree

        //Clear tree
        entityTree.set(0, 0, SECTOR_WIDTH, SECTOR_HEIGHT, 0);

        //Add entities to tree
        for(GameEntity entity : entities)
        {
            entityTree.add(entity);
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
