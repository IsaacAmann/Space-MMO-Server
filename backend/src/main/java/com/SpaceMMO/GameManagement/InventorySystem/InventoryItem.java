package com.SpaceMMO.GameManagement.InventorySystem;

import java.util.HashMap;

public class InventoryItem
{
    public String itemName;

    public int itemQuantity;

    public int volumeRatio;

    public int volume;


    public InventoryItem(String itemName, int itemQuantity, int volumeRatio)
    {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.volumeRatio = volumeRatio;

        this.volume = itemQuantity * volumeRatio;
    }
    //Copy constructor
    public InventoryItem(InventoryItem item)
    {
        itemName = item.itemName;
        itemQuantity = item.itemQuantity;
    }

    public HashMap<String, Object> getDataMap()
    {
        HashMap<String, Object> output = new HashMap<String, Object>();

        output.put("itemName", itemName);
        output.put("itemQuantity", itemQuantity);


        return output;
    }
}
