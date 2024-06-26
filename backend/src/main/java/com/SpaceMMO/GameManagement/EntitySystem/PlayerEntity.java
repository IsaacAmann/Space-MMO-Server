package com.SpaceMMO.GameManagement.EntitySystem;

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
}
