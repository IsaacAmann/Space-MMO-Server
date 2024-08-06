package com.SpaceMMO.GameManagement.EntitySystem;

import com.SpaceMMO.GameManagement.EntitySystem.ExternalModules.MiningDrillModule;
import com.SpaceMMO.GameManagement.SectorSystem.Player;
import com.SpaceMMO.GameManagement.SectorSystem.Sector;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.dyn4j.geometry.Vector2;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerEntity extends Ship
{

    //Reference to the Player object that owns the entity
    public Player player;

    //Internal Modules
    //int maxInternalModules = 1;
   // public ArrayList<ShipInternalModule> internalModules;

    //External Modules
    //int maxExternalModules = 1;
    //public ArrayList<ShipExternalModule> externalModules;

    //Simple constructor for debugging
    public PlayerEntity(Player player)
    {
        super(0, 0, 240, 80, 0);
        internalModules = new ArrayList<ShipInternalModule>();
        externalModules = new ArrayList<ShipExternalModule>();
        externalModules.add(new MiningDrillModule(0,0, this));
        godotScenePath = "res://Ships/pipeDutch/pipeDutch.tscn";
        this.health = 500;
        this.player = player;
    }

    @Override
    public String getEntityDataJSON()
    {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> entityData = new HashMap<String, Object>();

        //Put external modules
        HashMap<String,Object>[] externalModuleJSON = new HashMap[externalModules.size()];
        for(int i = 0; i < externalModules.size(); i++)
        {
            externalModuleJSON[i] = externalModules.get(i).getValueMap();
        }
        entityData.put("externalModules", externalModuleJSON);
        entityData.put("test", 4040);
        entityData.put("username", player.account.username);
        entityData.put("godotScenePath", godotScenePath);
        String output = null;
        try
        {
            output = objectMapper.writeValueAsString(entityData);
            System.out.println("JSON: : : : " + output);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return output;
    }

    public void handleCollision(GameEntity otherEntity)
    {

        if(otherEntity != this)
        {
            //System.out.println("PLAYER COLLISION: " + (otherEntity));
           // System.out.println(body.getTransform());
        }
    }


    @Override
    public void update(float delta)
    {
        Vector2 impulse = new Vector2();

         //System.out.println("ID: " + entityID + " rot: " + rotation);
        if(player.inputW)
        {
            impulse.x += forwardThrust;
        }
        if(player.inputS)
        {
            impulse.x -= reverseThrust;
        }
        if(player.inputA)
        {
            impulse.y -=  portsideThrust;
        }
        if(player.inputD)
        {
            impulse.y += starboardThrust;
        }
        //Attempt to fire weapons
        if(player.inputSpace)
        {
            for(ShipExternalModule module : externalModules)
            {
                if(module instanceof Weapon)
                {
                    ((Weapon)module).fire();
                }
            }
        }

        if(player.inputQ)
        {
            rotationalVelocity = maxRotationalVelocity * -1;
        }
        else if (player.inputE)
        {
            rotationalVelocity = maxRotationalVelocity;
        }
        else
        {
            rotationalVelocity = 0;
        }

        //Handle rotation
        this.desiredRotation = player.desiredRotation;

        this.body.rotateAboutCenter(rotationalVelocity);
        this.rotation += rotationalVelocity;

        impulse.rotate(rotation);
        velocityVector = velocityVector.add(impulse);
        super.update(delta);

    }


}
