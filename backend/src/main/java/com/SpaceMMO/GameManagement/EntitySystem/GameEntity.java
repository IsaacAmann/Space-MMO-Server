package com.SpaceMMO.GameManagement.EntitySystem;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;
import org.dyn4j.collision.narrowphase.Penetration;
import org.dyn4j.collision.narrowphase.Sat;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Rotation;
import org.dyn4j.geometry.Transform;
import org.dyn4j.geometry.Vector2;

public abstract class GameEntity
{
    public Vector2 position;
    public Vector2 velocityVector;

    public Rectangle rectangle;


    public int health;
    public int entityID;

    public double rotation;
    public double rotationalVelocity;

    public String godotScenePath;

    public static Sat collisionDetector = new Sat();

    public GameEntity(double x, double y, double width, double height, double rotation)
    {
        this.velocityVector = new Vector2(0, 0);
        this.position = new Vector2(x, y);
        this.rotation = rotation;
        this.rectangle = new Rectangle(width, height);

        rectangle.translate(position);
        rectangle.rotate(rotation);
    }

    public void handleCollision(GameEntity otherEntity)
    {
        //System.out.println("Collided with: " + otherEntity);
    }

    public abstract String getEntityDataJSON();

    public abstract void update();

    public boolean isColliding(GameEntity otherEntity)
    {
        Transform transform1 = new Transform();
        //transform1.rotate(rotation, rectangle.getCenter());
        transform1.setTranslation(position);
        transform1.setRotation(new Rotation(0.0));

        //System.out.println("local: " + rectangle.getRotation().toRadians());

        Transform transform2 = new Transform();
        //transform2.rotate(otherEntity.rotation, otherEntity.rectangle.getCenter());
        transform2.setTranslation(otherEntity.position);
        transform2.setRotation(new Rotation(0.0));

        //Run collision check
        return collisionDetector.detect(this.rectangle, transform1, otherEntity.rectangle, transform2);
    }

}

