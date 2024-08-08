package com.SpaceMMO.GameManagement.EntitySystem.Projectiles;

import com.SpaceMMO.GameManagement.EntitySystem.GameEntity;
import com.SpaceMMO.GameManagement.EntitySystem.PlayerEntity;
import com.SpaceMMO.GameManagement.EntitySystem.Ship;
import org.dyn4j.geometry.Vector2;

import java.util.HashMap;

public abstract class Projectile extends GameEntity
{
    public PlayerEntity owner;
    //Number of ticks that projectile will exist for
    int lifeTime;
    int ticksAlive;
    public int damage;

    public Projectile(double x, double y, double width, double height, double rotation, double velocity, PlayerEntity owner)
    {
        super(x, y, width, height, rotation);
        this.owner = owner;
        //default to 240 ticks or ~8 seconds
        this.lifeTime = 240;
        ticksAlive = 0;
        this.damage = 2;
        linearProjectile = true;
        notifyDeletion = false;


        this.velocityVector = new Vector2(velocity, 0);
        velocityVector = velocityVector.setDirection(rotation);
    }

    @Override
    public void handleCollision(GameEntity otherEntity)
    {
        if(otherEntity instanceof Ship && otherEntity != owner)
        {
            //Apply damage and set remove flag
            otherEntity.health -= damage;
            this.removeFlag = true;
            //Projectile being removed early, notify clients that it no longer exists
            notifyDeletion = true;
            System.out.println("projectile Collision: " + otherEntity);
        }
    }

    @Override
    public String getEntityDataJSON()
    {
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("godotScenePath", godotScenePath);

        return basicGetEntityJSON(data);
    }

    @Override
    public void update(float delta)
    {
        ticksAlive++;
        if(ticksAlive >= lifeTime)
        {
            this.removeFlag = true;

        }
        else
        {
            Vector2 oldPosition = new Vector2(position);

            this.position.x += velocityVector.x * delta;
            this.position.y += velocityVector.y * delta;

            this.body.translate(yFlipPosition(position.difference(oldPosition)));
        }
    }
}
