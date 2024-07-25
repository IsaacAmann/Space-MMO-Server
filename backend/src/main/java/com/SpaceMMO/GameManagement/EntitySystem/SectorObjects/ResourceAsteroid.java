package com.SpaceMMO.GameManagement.EntitySystem.SectorObjects;

import com.SpaceMMO.GameManagement.EntitySystem.GameEntity;
import com.SpaceMMO.GameManagement.InventorySystem.InventoryItem;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

public class ResourceAsteroid extends GameEntity
{
    public InventoryItem itemToGive;
    //Amount of health needed to be removed per item to give an item
    public int healthPerItem;

    public ResourceAsteroid(double x, double y, double width, double height, InventoryItem itemToGive)
    {
        super(x, y, width, height, 0);
        this.itemToGive = itemToGive;
        godotScenePath = "res://Entities/Asteroids/goldAsteroid.tscn";
        health = 2000;
    }

    public String getEntityDataJSON()
    {
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("health", health);
        data.put("godotScenePath", godotScenePath);

        return basicGetEntityJSON(data);
    }

    public void update()
    {

        //If 0, asteroid depleted and should be removed
        if(health <= 0)
        {
            removeFlag = true;
        }
    }

    //Apply damage to asteroid and return resource inventory item with a certain quantity
    public InventoryItem mineAsteroid(int damage)
    {
        int amount = 0;
        //Apply damage to asteroid
        //Case where asteroid has enough health to accept full damage amount
        if(this.health >= damage)
        {
            this.health -= damage;
            amount = damage / healthPerItem;
        }
        //Case where damage exceeds current health
        else
        {
            amount = this.health / healthPerItem;
            this.health = 0;
        }
        //Copy the item to give and set quantity
        InventoryItem item = new InventoryItem(itemToGive);
        item.itemQuantity = amount;

        return item;
    }
}
