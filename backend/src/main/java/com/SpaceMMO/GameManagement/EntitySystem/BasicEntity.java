package com.SpaceMMO.GameManagement.EntitySystem;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

public class BasicEntity extends GameEntity
{
    public BasicEntity(float x, float y, float width, float height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public String getEntityDataJSON()
    {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> entityData = new HashMap<String, Object>();
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

    }
}
