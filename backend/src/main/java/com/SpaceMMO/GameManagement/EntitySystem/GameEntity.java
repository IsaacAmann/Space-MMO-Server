package com.SpaceMMO.GameManagement.EntitySystem;

public abstract class GameEntity
{

    public void handleCollision(GameEntity otherEntity)
    {
        System.out.println("Collided with: " + otherEntity);
    }
}
