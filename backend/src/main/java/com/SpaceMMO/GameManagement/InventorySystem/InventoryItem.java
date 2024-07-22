package com.SpaceMMO.GameManagement.InventorySystem;

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
}
