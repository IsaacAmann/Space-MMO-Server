package com.SpaceMMO.GameManagement.EntitySystem;

import org.apache.commons.pool2.ObjectPool;

public class QuadTreeNode
{
    public ObjectPool<QuadTreeNode> pool;

    int x,y;
    int width, height;
    int level;


}
