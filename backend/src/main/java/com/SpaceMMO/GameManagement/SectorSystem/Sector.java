package com.SpaceMMO.GameManagement.SectorSystem;

import com.SpaceMMO.GameManagement.EntitySystem.GameEntity;
import com.SpaceMMO.GameManagement.QuadTree.QuadRectangle;
import com.SpaceMMO.GameManagement.QuadTree.QuadTree;

public class Sector
{
    public static final float SECTOR_WIDTH = 1000000000;
    public static final float SECTOR_HEIGHT = 1000000000;

    String name;
    QuadTree<GameEntity> quadTree;

    public Sector()
    {
        name = "Unamed Sector";
        quadTree = new QuadTree<GameEntity>(new QuadRectangle(0,0, SECTOR_WIDTH, SECTOR_HEIGHT), 0);


    }

    //Statements to be run each simulation frame
    public void gameTick()
    {


        //Rebuild quad tree

    }
}
