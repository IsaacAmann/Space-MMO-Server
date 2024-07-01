package com.SpaceMMO.GameManagement.EntitySystem;

import com.SpaceMMO.GameManagement.SectorSystem.Player;
import com.SpaceMMO.GameManagement.SectorSystem.Sector;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

public class PlayerEntity extends GameEntity
{
    //Value added to velocity from thrust
    public float acceleration = 4;
    public float maxSpeed = 1000;
    //Rotational speed
    public float rotationalAcceleration = (float)0.0101;
    public float maxRotationalVelocity = (float)20;
    //Rotation in radians


    //Friction value to slow down, may be 0
    public float friction = (float)2;
    //Reference to the Player object that owns the entity
    public Player player;

    public PlayerEntity(Player player)
    {
        super();
        this.player = player;
        width = 211;
        height = 80;
        rotation = 0;
    }
    public String getEntityDataJSON()
    {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> entityData = new HashMap<String, Object>();
        entityData.put("test", "helloworld");
        String output = null;
        try
        {
            output = objectMapper.writeValueAsString(entityData);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return output;
    }

    @Override
    public void update()
    {
       //this.rotation += 0.002;
        // System.out.println("ID: " + entityID + " rot: " + rotation);
        if(player.inputW)
        {
            //Check that adding acceleration would not exceed max speed
            if(velocityY - acceleration > -1 * maxSpeed)
            {
                //Check later if acceleration should also be divided by ticks per second
                velocityY -= acceleration;
            }
            else
            {
                velocityY = -1 * maxSpeed;
            }
        }
        if(player.inputS)
        {
            if(velocityY + acceleration < maxSpeed )
            {
                velocityY += acceleration;
            }
            else
            {
                velocityY = maxSpeed;
            }
        }
        if(player.inputA)
        {
            if(velocityX - acceleration > -1 * maxSpeed)
            {
                velocityX -= acceleration;
            }
            else
            {
                velocityX = -1 * maxSpeed;
            }
        }
        if(player.inputD)
        {
            if(velocityX + acceleration < maxSpeed)
            {
                velocityX += acceleration;
            }
            else
            {
                velocityX = maxSpeed;
            }
        }
        if(player.inputQ)
        {
            if(rotationalVelocity - rotationalAcceleration > -1 * maxRotationalVelocity)
                rotationalVelocity -= rotationalAcceleration;
            else
                rotationalVelocity = maxRotationalVelocity * -1;
        }
        if (player.inputE)
        {
            if(rotationalVelocity + rotationalAcceleration < maxRotationalVelocity)
                this.rotationalVelocity += rotationalAcceleration;
            else
                rotationalVelocity = maxRotationalVelocity;
        }

        this.x += this.velocityX / Sector.TICKS_PER_SECOND;
        this.y += this.velocityY / Sector.TICKS_PER_SECOND;
        this.rotation += rotationalVelocity / Sector.TICKS_PER_SECOND;
    }
}
