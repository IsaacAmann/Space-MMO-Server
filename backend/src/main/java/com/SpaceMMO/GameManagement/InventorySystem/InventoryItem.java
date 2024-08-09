package com.SpaceMMO.GameManagement.InventorySystem;

import java.util.HashMap;

public class InventoryItem
{
    public String itemName;

    public int itemQuantity;

    public InventoryItem(String itemName, int itemQuantity)
    {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
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
