package com.SpaceMMO.GameManagement.EntitySystem.ExternalModules;

import com.SpaceMMO.GameManagement.EntitySystem.PlayerEntity;
import com.SpaceMMO.GameManagement.EntitySystem.Projectiles.CannonRound;
import com.SpaceMMO.GameManagement.EntitySystem.ShipExternalModule;
import com.SpaceMMO.GameManagement.EntitySystem.Weapon;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dyn4j.geometry.Vector2;

import java.util.HashMap;


public class RapidCannon extends ShipExternalModule implements Weapon
{
    public int damage;
    public double aimCone;
    public double projectileVelocity;


    public RapidCannon(double x, double y, PlayerEntity playerShip)
    {
        this.parentShip = playerShip;
        this.scenePath = "res://Ships/Modules/ExternalModules/Weapons/RapidCannon/RapidCannon.tscn";
        this.offsetVector = new Vector2(x, y);

        damage = 20;
        aimCone = 0.2;
        projectileVelocity = 1000;

    }

    @Override
    public void fire()
    {
        //Spawn a projectile heading towards mouse position
        CannonRound currentRound = new CannonRound(parentShip.position.x + this.offsetVector.x, parentShip.position.y + this.offsetVector.y, 6, 13, parentShip.rotation, projectileVelocity, parentShip);
        currentRound.velocityVector = currentRound.velocityVector.setDirection(parentShip.rotation);
        currentRound.damage = damage;
        //System.out.println("Projecilebody: " + currentRound.body.getTransform());
       // System.out.println("ParentP: " + parentShip.body.getTransform());
        parentShip.player.currentSector.entityAddQueue.add(currentRound);


    }

    //Implementing reload from weapon interface
    @Override
    public void reload()
    {
        //Does nothing currently, may add special action later
    }

    @Override
    public String getJSON()
    {
        String output = null;
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> entityData = new HashMap<String, Object>();

        entityData.put("moduleName", moduleName);
        entityData.put("scenePath", scenePath);
        entityData.put("offsetVectorX", offsetVector.x);
        entityData.put("offsetVectorY", offsetVector.y);

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
    public HashMap<String, Object> getValueMap()
    {
        HashMap<String, Object> entityData = new HashMap<String, Object>();
        entityData.put("moduleName", moduleName);
        entityData.put("scenePath", scenePath);
        entityData.put("offsetVectorX", offsetVector.x);
        entityData.put("offsetVectorY", offsetVector.y);
        return entityData;
    }
}
