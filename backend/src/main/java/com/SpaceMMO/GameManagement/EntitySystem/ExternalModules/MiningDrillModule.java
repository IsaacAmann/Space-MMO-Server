package com.SpaceMMO.GameManagement.EntitySystem.ExternalModules;

import com.SpaceMMO.GameManagement.EntitySystem.CollisionSystem.CollisionCallBack;
import com.SpaceMMO.GameManagement.EntitySystem.CollisionSystem.RayCast;
import com.SpaceMMO.GameManagement.EntitySystem.GameEntity;
import com.SpaceMMO.GameManagement.EntitySystem.PlayerEntity;
import com.SpaceMMO.GameManagement.EntitySystem.SectorObjects.ResourceAsteroid;
import com.SpaceMMO.GameManagement.EntitySystem.ShipExternalModule;
import com.SpaceMMO.GameManagement.EntitySystem.Weapon;
import com.SpaceMMO.GameManagement.SectorSystem.Player;
import com.SpaceMMO.GameManagement.SectorSystem.Sector;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dyn4j.geometry.Vector2;

import java.util.HashMap;

public class MiningDrillModule extends ShipExternalModule implements Weapon
{
    public MiningDrillModule(double x, double y, PlayerEntity playerShip)
    {
        this.moduleName = "Mining Drill";
        this.scenePath = "res://Ships/Modules/ExternalModules/Tools/MiningDrill/MiningDrill.tscn";
        this.parentShip = playerShip;
        this.offsetVector = new Vector2(x, y);
    }
    //Implementing fire from weapon interface
    @Override
    public void fire()
    {
        Sector sector = parentShip.player.currentSector;
        //Signal client to draw laser affect
        for(Player player : sector.players)
        {
            try
            {
                sector.serviceContainer.clientAffectsHandler.sendLaserAffect(player, parentShip.position, new Vector2(parentShip.player.mouseX, parentShip.player.mouseY), (float)0.2, (float)0.9, 0, (float)0.2);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        //Ray cast and search for hits
        Vector2 mousePositionVector = new Vector2(parentShip.player.mouseX, parentShip.player.mouseY);
        //System.out.println("distance: " + parentShip.position.distance(mousePositionVector));
        RayCast rayCast = new RayCast(parentShip.position.x, parentShip.position.y, parentShip.position.distance(mousePositionVector), mousePositionVector.getDirection(), parentShip, new AsteroidLaserCallback());
        parentShip.player.currentSector.rayCastQueue.add(rayCast);
    }
    private class AsteroidLaserCallback implements CollisionCallBack
    {
        public void call(GameEntity rayCaster, GameEntity collision)
        {
            System.out.println("raycast hit");
            if(collision instanceof ResourceAsteroid)
            {
                System.out.println(rayCaster + " mining laser hit on : " + collision);
                //If asteroid, reduce asteroid health and add resources to inventory

            }
        }
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
