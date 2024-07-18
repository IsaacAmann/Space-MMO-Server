package com.SpaceMMO.GameManagement.EntitySystem;

import org.dyn4j.geometry.Vector2;

import java.util.HashMap;

public abstract class ShipExternalModule
{
    //position of module relative to the center of the ship
    public Vector2 offsetVector;
    //Path of scene file within godot client
    public String scenePath;
    public String moduleName;
    //Reference to parent entity
    public PlayerEntity parentShip;

    abstract public String getJSON();
    abstract public HashMap<String, Object> getValueMap();


}
