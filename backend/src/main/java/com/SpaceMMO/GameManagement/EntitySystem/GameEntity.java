package com.SpaceMMO.GameManagement.EntitySystem;

public abstract class GameEntity
{
    public float x;
    public float y;
    public float width;
    public float height;

    public void handleCollision(GameEntity otherEntity)
    {
        System.out.println("Collided with: " + otherEntity);
    }
}
