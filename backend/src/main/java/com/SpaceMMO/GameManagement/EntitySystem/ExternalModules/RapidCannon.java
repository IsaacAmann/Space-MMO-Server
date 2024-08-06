package com.SpaceMMO.GameManagement.EntitySystem.ExternalModules;

import com.SpaceMMO.GameManagement.EntitySystem.PlayerEntity;
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

        damage = 2;
        aimCone = 0.2;
        projectileVelocity = 1000;
    }

    @Override
    public void fire()
    {

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
