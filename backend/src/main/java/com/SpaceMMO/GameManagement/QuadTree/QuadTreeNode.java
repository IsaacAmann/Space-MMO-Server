package com.SpaceMMO.GameManagement.QuadTree;

import com.SpaceMMO.GameManagement.EntitySystem.GameEntity;
import org.apache.commons.pool2.ObjectPool;

import java.util.ArrayList;

//Quadtree implementation adapted from https://medium.com/@aprithul/implementing-a-quadtree-baac7acf3ad0
public class QuadTreeNode
{

    public static final int MAX_LEVEL = 5000;

    float x,y;
    float width, height;
    float level;
    ObjectPool<QuadTreeNode> quadTreePool;

    public QuadTreeNode(float x, float y, float width, float height, float level, ObjectPool<QuadTreeNode> quadTreePool)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.level = level;
        this.quadTreePool = quadTreePool;
        this.entities = new ArrayList<GameEntity>();
        this.children = new ArrayList<QuadTreeNode>();
    }

    public QuadTreeNode()
    {
        x = 0;
        y = 0;
        width = 0;
        height = 0;
        level = 0;
        this.entities = new ArrayList<GameEntity>();
        this.children = new ArrayList<QuadTreeNode>();
    }

    public ArrayList<QuadTreeNode> children;
    public ArrayList<GameEntity> entities;

    public void add(GameEntity entity) throws Exception
    {
        if(entities.size() < 4 || level == MAX_LEVEL)
        {
            entities.add(entity);
            System.out.println("Entity added, level: " + level);
        }
        else
        {
            if(children.size() == 0)
            {
                //exceeding 10000 nodes quickly, check this
                System.out.println("Splitting node into 4");
                System.out.println("Current Nodes: " + quadTreePool.getNumActive());
                QuadTreeNode n1 = quadTreePool.borrowObject();
                QuadTreeNode n2 = quadTreePool.borrowObject();
                QuadTreeNode n3 = quadTreePool.borrowObject();
                QuadTreeNode n4 = quadTreePool.borrowObject();
                System.out.println("Point");
                n1.set(x, y, width/2, height/2, level + 1);
                n1.quadTreePool = this.quadTreePool;
                children.add(n1);

                n2.set(x+width/2, y, width/2, height/2, level + 1);
                children.add(n2);
                n2.quadTreePool = this.quadTreePool;

                n3.set(x, y+height/2, width/2, height/2, level + 1);
                children.add(n3);
                n3.quadTreePool = this.quadTreePool;

                n4.set(x+width/2, y+height/2, width/2, height/2, level + 1);
                children.add(n4);
                n4.quadTreePool = this.quadTreePool;

                for(GameEntity currentEntity : entities)
                {
                    for(QuadTreeNode node : children)
                    {
                        if(node.inside(currentEntity))
                        {
                            node.add(currentEntity);
                        }
                    }
                }

            }

            for(QuadTreeNode node : children)
            {
                if(node.inside(entity))
                {
                    node.add(entity);
                }
            }
        }
    }

    public boolean inside(GameEntity entity)
    {
        System.out.println("Comparing entity: x: " + entity.x + " y: " + entity.y + " width: " + entity.width + " height: " + entity.height);
        System.out.println("Bounding box: x: " + x + " y: " + y + " width: " + width + " height: " + height);
        if (entity.x < (x + width) && (entity.x + entity.width) > x &&
            entity.y < (y + height) && (entity.y + entity.height) > y)
        {

            return true;
        }
        else
        {
            System.out.println("not intersecting");
            return false;
        }

      /*
        if (entity.x > x + width && entity.x + entity.width < x &&
                entity.y < y + height && entity.y + entity.height > y)
            return true;
        else
            return false;

       */
    }

    public void set(float x, float y, float width, float height, float level) throws Exception
    {
        System.out.println("Setting");
        clearChildren();

        children.clear();
        entities.clear();

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.level = level;
    }

    //Recursively delete tree
    public void clearChildren() throws Exception
    {
        for(QuadTreeNode node : children)
        {
            node.clearChildren();
        }

        for(QuadTreeNode node : children)
        {
            quadTreePool.invalidateObject(node);
        }

    }
}