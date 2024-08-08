package com.SpaceMMO.GameManagement.EntitySystem;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.dyn4j.collision.narrowphase.Penetration;
import org.dyn4j.collision.narrowphase.Sat;
import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Rotation;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;

import java.util.HashMap;

public abstract class GameEntity
{
    public Vector2 position;
    public Vector2 velocityVector;
    //Indicates that the entity should be removed on the next game loop iteration
    public boolean removeFlag;
    //Flag for whether or not a packet should be sent on deletion (mostly relevant to projectiles)
    public boolean notifyDeletion;
    //Do not update client every tick, velocity is constant
    public boolean linearProjectile;

    //public Rectangle rectangle;
    public Body body;

    public int health;
    public int entityID;

    public double rotation;
    public double rotationalVelocity;

    public String godotScenePath;

    public static Sat collisionDetector = new Sat();

    public static Vector2 yFlip = new Vector2(1, -1);

    public GameEntity(double x, double y, double width, double height, double rotation)
    {
        this.velocityVector = new Vector2(0, 0);
        this.position = new Vector2(x, y);
        this.rotation = rotation;
        Rectangle rectangle = new Rectangle(width, height);
        this.body = new Body();
        body.addFixture(rectangle);

        body.translate(yFlipPosition(position));
        body.rotateAboutCenter(rotation);
        body.setUserData(this);
        notifyDeletion = true;
        linearProjectile = false;
    }

    public Vector2 yFlipPosition(Vector2 position)
    {
        Vector2 tempPosition = new Vector2(position);
        tempPosition.y = tempPosition.y * -1;
        return tempPosition;
    }

    public void handleCollision(GameEntity otherEntity)
    {
        //System.out.println("Collided with: " + otherEntity);
    }

    public String basicGetEntityJSON(HashMap<String, Object> data)
    {
        ObjectMapper objectMapper = new ObjectMapper();

        String output = null;
        try
        {
            output = objectMapper.writeValueAsString(data);
            System.out.println("JSON: : : : " + output);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return output;
    }

    public abstract String getEntityDataJSON();

    public abstract void update(float delta);

}

