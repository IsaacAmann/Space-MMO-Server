package com.SpaceMMO.GameManagement.InventorySystem;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;

public class Inventory
{
    public HashMap<String, InventoryItem> items;
    //Inventory ID will likely match the entity ID that it is attached to
    public int inventoryID;

    public Inventory(int inventoryID)
    {
        items = new HashMap<String, InventoryItem>();
        this.inventoryID = inventoryID;
    }

    public boolean addItem(InventoryItem item)
    {
        InventoryItem existingItem = items.get(item.itemName);
        if(existingItem != null)
        {
            existingItem.itemQuantity += item.itemQuantity;
        }
        else
        {
            items.put(item.itemName, item);
        }
        return true;
    }

    public InventoryItem removeItem(String itemName)
    {
        InventoryItem item = items.remove(itemName);
        return item;
    }

    public int removeItemQuantity(String itemName, int amount)
    {
        int amountRemoved = 0;
        InventoryItem item = items.get(itemName);

        if(item != null)
        {
            if(item.itemQuantity <= amount)
            {
                amountRemoved = item.itemQuantity;
                removeItem(itemName);
            }
            else
            {
                item.itemQuantity -= amount;
                amountRemoved = amount;
            }
        }

        return amountRemoved;
    }

    public String getInventoryJSON()
    {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> entityData = new HashMap<String, Object>();
        String output = null;

        //Add items
        HashMap<String, Object>[] itemData = new HashMap[items.size()];
        int i = 0;
        for(String key : items.keySet())
        {
            itemData[i] = items.get(key).getDataMap();
            i++;
        }

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


}
