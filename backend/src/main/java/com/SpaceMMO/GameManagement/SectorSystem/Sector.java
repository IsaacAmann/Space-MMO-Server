package com.SpaceMMO.GameManagement.SectorSystem;

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

    public String name;
    public GenericObjectPool<QuadTreeNode> quadTreePool;
    public QuadTreeNode entityTree;
    public ArrayList<GameEntity> entities;

    public Sector()
    {
        name = "Unamed Sector";
        GenericObjectPoolConfig<QuadTreeNode> quadPoolConfig = new GenericObjectPoolConfig<QuadTreeNode>();
        quadPoolConfig.setMaxTotal(MAX_QUAD_NODES);

        quadTreePool = new GenericObjectPool(new PooledQuadNodeFactory(), quadPoolConfig);
        System.out.println("MAX OBJECT POOL: " + quadTreePool.getMaxTotal());
        entities = new ArrayList<GameEntity>();
        entityTree = new QuadTreeNode(0,0,SECTOR_WIDTH, SECTOR_HEIGHT, 0, quadTreePool);
    }

    //Statements to be run each simulation frame
    public void gameTick() throws Exception
    {


        //Rebuild quad tree

        //Clear tree
        entityTree.set(0, 0, SECTOR_WIDTH, SECTOR_HEIGHT, 0);

        //Add entities to tree
        for(GameEntity entity : entities)
        {
            entityTree.add(entity);
        }
    }

    public class SectorUpdateThread extends Thread
    {
        public boolean running;
        
        public SectorUpdateThread()
        {
            running = true;
        }

        @Override
        public void run()
        {
            while(running)
            {

            }
        }
    }
}
