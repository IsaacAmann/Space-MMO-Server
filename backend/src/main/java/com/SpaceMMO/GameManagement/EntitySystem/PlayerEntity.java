package com.SpaceMMO.GameManagement.EntitySystem;

import com.SpaceMMO.GameManagement.SectorSystem.Sector;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

public class PlayerEntity extends GameEntity
{
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
        this.x += this.velocityX / Sector.TICKS_PER_SECOND;
        this.x += this.velocityY / Sector.TICKS_PER_SECOND;
        this.rotation += 0.002;
        //System.out.println("ID: " + entityID + " rot: " + rotation);
    }
}
