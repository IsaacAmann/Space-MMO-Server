package com.SpaceMMO.GameManagement.SectorSystem;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.SpaceMMO.GameManagement.EntitySystem.GameEntity;

import com.SpaceMMO.GameManagement.QuadTree.PooledQuadNodeFactory;
import com.SpaceMMO.GameManagement.QuadTree.QuadTreeNode;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.ArrayList;

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

    private SectorUpdateThread sectorUpdateThread;

    public Sector()
    {
        name = "Unamed Sector";
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

    //Statements to be run each simulation frame
    public void gameTick() throws Exception
    {
        //Handle input

        //Handle entity updates

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
    public void postGameStateUpdate()
    {
        for(Player player : this.players)
        {

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
