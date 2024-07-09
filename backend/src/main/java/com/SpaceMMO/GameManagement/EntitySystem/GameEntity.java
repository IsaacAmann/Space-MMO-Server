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
    //Alter x and y position to handle rotation around center point
    public void rotateAboutCenter(float angle)
    {
        //double finalImpulseX = ((impulseX - 0.0) * Math.cos(rotation)) - ((impulseY) * Math.sin(rotation));
        //double finalImpulseY = 0.0 - ((0.0 - impulseY) * Math.cos(rotation)) + ((impulseX - 0.0) * Math.sin(rotation));

        //Calculate center
        float centerX = getCenterX();
        float centerY = getCenterY();

        //Calculate new x and y
        double newX = ((x - centerX) * Math.cos(angle)) - ((centerY - y) * Math.sin(angle)) + centerX;
        double newY = centerY - ((centerY - y) * Math.cos(angle)) + ((x - centerX) * Math.sin(angle));


        //Set x and y
        this.x = (float)newX;
        this.y = (float)newY;

    }

    public float getCenterX()
    {
        double centerX = (this.x + this.width/2) * Math.cos(rotation) - ((this.y+this.height/2)) * Math.sin(rotation);

        return (float)centerX;
    }

    public float getCenterY()
    {
        double centerY = (this.x + this.width/2) * Math.sin(rotation) + ((this.y + this.height/2)) * Math.cos(rotation);
        return (float)centerY;
    }
}

