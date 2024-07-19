package com.SpaceMMO.GameManagement.EntitySystem;

import com.SpaceMMO.GameManagement.SectorSystem.Sector;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dyn4j.geometry.Vector2;

import java.util.ArrayList;
import java.util.HashMap;

public class Ship extends GameEntity
{
    //Thrust force values
    public double starboardThrust = 4;
    public double portsideThrust = 4;
    public double forwardThrust = 8;
    public double reverseThrust = 4;

    public double maxSpeed = 500;
    public double rotationalAcceleration = (float)0.0101;
    public double maxRotationalVelocity = (float).035;

    public double desiredRotation;

    int maxInternalModules = 1;
    public ArrayList<ShipInternalModule> internalModules;

    int maxExternalModules = 1;
    public ArrayList<ShipExternalModule> externalModules;

    public Ship(double x, double y, double width, double height, double rotation)
    {
        super(x, y, width, height, rotation);
    }

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
        entityData.put("godotScenePath", godotScenePath);
        //entityData.put("username", player.account.username);
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

    @Override
    public void update()
    {
        //Check that magnitude does not exceed max speed
        if(velocityVector.getMagnitude() > maxSpeed)
        {
            velocityVector.setMagnitude(maxSpeed);
        }

        Vector2 oldPosition = position;
        this.position.x += velocityVector.x / Sector.TICKS_PER_SECOND;
        this.position.y += velocityVector.y / Sector.TICKS_PER_SECOND;

        this.rectangle.translate(position.difference(oldPosition));
    }
}
