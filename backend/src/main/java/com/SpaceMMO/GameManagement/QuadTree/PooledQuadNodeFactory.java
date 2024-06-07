package com.SpaceMMO.GameManagement.QuadTree;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class PooledQuadNodeFactory extends BasePooledObjectFactory<QuadTreeNode>
{
    @Override
    public QuadTreeNode create()
    {
        return new QuadTreeNode();
    }

    @Override
    public PooledObject<QuadTreeNode> wrap(QuadTreeNode obj)
    {
        return new DefaultPooledObject<QuadTreeNode>(obj);
    }
}
