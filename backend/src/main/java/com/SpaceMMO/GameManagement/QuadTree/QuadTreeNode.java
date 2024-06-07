package com.SpaceMMO.GameManagement.QuadTree;

import com.SpaceMMO.GameManagement.EntitySystem.GameEntity;
import org.apache.commons.pool2.ObjectPool;

import java.util.ArrayList;

//Quadtree implementation adapted from https://medium.com/@aprithul/implementing-a-quadtree-baac7acf3ad0
public class QuadTreeNode
{
    public ObjectPool<QuadTreeNode> pool;

    public static final int MAX_LEVEL = 5000;

    int x,y;
    int width, height;
    int level;

    public QuadTreeNode(int x, int y, int width, int height, int level)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.level = level;

    }

    public ArrayList<QuadTreeNode> children;
    public ArrayList<GameEntity> entities;

    public void add(GameEntity entity)
    {
        if(entities.size() < 4 || level == MAX_LEVEL)
        {
            entities.add(entity);
        }
        else
        {
            if(children.size() == 0)
            {

            }
        }
    }
}