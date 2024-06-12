package com.SpaceMMO.GameManagement.EntitySystem;

public abstract class GameEntity
{
    public float x;
    public float y;
    public float width;
    public float height;
    public float velocityX;
    public float velocityY;
    public int health;
    public int entityID;

    public void handleCollision(GameEntity otherEntity)
    {
        System.out.println("Collided with: " + otherEntity);
    }

    public abstract String getEntityDataJSON();
}
