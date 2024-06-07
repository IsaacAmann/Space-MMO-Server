package com.SpaceMMO.GameManagement.SectorSystem;

import com.SpaceMMO.GameManagement.EntitySystem.GameEntity;

import com.SpaceMMO.GameManagement.QuadTree.PooledQuadNodeFactory;
import com.SpaceMMO.GameManagement.QuadTree.QuadTreeNode;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;

import java.util.ArrayList;

public class Sector
{
    public static final float SECTOR_WIDTH = 1000000000;
    public static final float SECTOR_HEIGHT = 1000000000;

    public String name;
    public ObjectPool<QuadTreeNode> quadTreePool;
    public QuadTreeNode entityTree;
    public ArrayList<GameEntity> entities;

    public Sector()
    {
        name = "Unamed Sector";
        quadTreePool = new GenericObjectPool(new PooledQuadNodeFactory());
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
}
