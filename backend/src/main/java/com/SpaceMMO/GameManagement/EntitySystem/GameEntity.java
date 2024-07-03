package com.SpaceMMO.GameManagement.EntitySystem;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

public abstract class GameEntity
{
    public float x;
    public float y;
    public float width;
    public float height;
    public float velocityX;
    public float velocityY;
    public RealVector velocityVector;
    public int health;
    public int entityID;

    public float rotation;
    public float rotationalVelocity;

    public GameEntity()
    {
        this.velocityVector = new ArrayRealVector();
        velocityVector = velocityVector.append(0).append(0);
    }

    public void handleCollision(GameEntity otherEntity)
    {
        System.out.println("Collided with: " + otherEntity);
        //bounce off
        /*
        velocityX = -1 * velocityX;
        x += velocityX;
        velocityY = -1 * velocityY;
        y += velocityY;
        */
    }

    public abstract String getEntityDataJSON();

    public abstract void update();

    public boolean isColliding(GameEntity otherEntity)
    {
        boolean output = false;

       if(otherEntity.x < (x + width) && (otherEntity.x + otherEntity.width) > x && otherEntity.y < (y + height) && (otherEntity.y + otherEntity.height) > y)
       {
           output = true;
       }

        return output;
    }
}

