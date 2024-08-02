package com.SpaceMMO.GameManagement.EntitySystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

import java.util.HashMap;

public class BasicEntity extends GameEntity
{
    public BasicEntity(double x, double y, double width, double height)
    {
        super(x,y, width, height, 0);
        this.health = 1000;

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
    public void update(float delta)
    {

    }
}
