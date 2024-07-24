package com.SpaceMMO.GameManagement.EntitySystem.SectorObjects;

import com.SpaceMMO.GameManagement.EntitySystem.GameEntity;
import com.SpaceMMO.GameManagement.InventorySystem.InventoryItem;
import com.SpaceMMO.GameManagement.SectorSystem.Sector;
import org.dyn4j.geometry.Vector2;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.stream.DoubleStream;
import java.util.Random;

public class AsteroidSpawner extends GameEntity
{
    Sector parentSector;

    int healthPerItem;
    //Maximum number of spawned asteroids
    int maxAsteroids;
    //number of ticks in between asteroid spawns
    int ticksPerAsteroid;
    //Tick counter
    int tickCounter;
    //Spawn radius
    double spawnRadius;

    InventoryItem itemToGive;
    //Maintaining a list of references to spawned asteroids so we are able to limit spawns
    private ArrayList<ResourceAsteroid> asteroids;

    public AsteroidSpawner(double x, double y, Sector parentSector, String itemName)
    {
        super(x, y, 1, 1, 0);
        godotScenePath = "asteroidSpawner";
        this.parentSector = parentSector;

        itemToGive = new InventoryItem(itemName, 1);
        asteroids = new ArrayList<ResourceAsteroid>();
        spawnRadius = 333;
        ticksPerAsteroid = 50;
        tickCounter = ticksPerAsteroid;
        maxAsteroids = 4;
    }

    public void update()
    {
        //remove asteroids that have been removed from local list
        ArrayList<ResourceAsteroid> removeList = new ArrayList<ResourceAsteroid>();
        for(ResourceAsteroid asteroid : asteroids)
        {
            if(asteroid.removeFlag == true)
            {
                removeList.add(asteroid);
            }
        }
        //Remove reference from list
        for(ResourceAsteroid asteroid : removeList)
        {
            asteroids.remove(asteroid);
        }

        //If not at max asteroids, attempt to spawn asteroid
        if(asteroids.size() < maxAsteroids)
        {
            if(tickCounter >= ticksPerAsteroid)
            {
                tickCounter = 0;
                //Spawn asteroid
                System.out.println("Spawning Asteroid");
                //Get random position vector relative to spawner
                Random random = new Random();
                double magnitude = 0 + (spawnRadius - 0) * random.nextDouble();
                System.out.println("Magnitude: " + magnitude);
                //Get random rotation for position vector
                double rotation = 0 + (6.2 - 0) * random.nextDouble();
                System.out.println("Rotation:" + rotation);
                //Create position vector
                Vector2 newPosition = Vector2.create(magnitude, rotation);
                //newPosition.setDirection(rotation);
                //newPosition.setMagnitude(magnitude);
                System.out.println("Asteroid x: " + newPosition.x + " y: " + newPosition.y);

                ResourceAsteroid asteroid = new ResourceAsteroid(this.position.x + newPosition.x, this.position.y + newPosition.y, 64, 64, itemToGive);
                asteroid.healthPerItem = healthPerItem;
                asteroids.add(asteroid);
                parentSector.entityAddQueue.add(asteroid);
            }
        }
        tickCounter ++;
    }

    public String getEntityDataJSON()
    {
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("godotScenePath", godotScenePath);
        return basicGetEntityJSON(data);
    }
}
