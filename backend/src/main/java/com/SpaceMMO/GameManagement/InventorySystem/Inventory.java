package com.SpaceMMO.GameManagement.InventorySystem;

import java.util.ArrayList;
import java.util.HashMap;

public class Inventory
{
    public HashMap<String, InventoryItem> items;

    public Inventory()
    {
        items = new HashMap<String, InventoryItem>();
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


}
