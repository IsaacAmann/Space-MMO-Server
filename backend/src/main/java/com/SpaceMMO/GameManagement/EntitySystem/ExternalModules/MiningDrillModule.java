package com.SpaceMMO.GameManagement.EntitySystem.ExternalModules;

import com.SpaceMMO.GameManagement.EntitySystem.ShipExternalModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dyn4j.geometry.Vector2;

import java.util.HashMap;

public class MiningDrillModule extends ShipExternalModule
{
    public MiningDrillModule(double x, double y)
    {
        this.moduleName = "Mining Drill";
        this.scenePath = "res://Ships/Modules/ExternalModules/Tools/MiningDrill/MiningDrill.tscn";

        this.offsetVector = new Vector2(x, y);
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
