package com.SpaceMMO.GameManagement.EntitySystem.Projectiles;

import com.SpaceMMO.GameManagement.EntitySystem.GameEntity;
import com.SpaceMMO.GameManagement.EntitySystem.PlayerEntity;
import com.SpaceMMO.GameManagement.EntitySystem.Ship;
import org.dyn4j.geometry.Vector2;

public abstract class Projectile extends GameEntity
{
    public PlayerEntity owner;
    //Number of ticks that projectile will exist for
    int lifeTime;
    int ticksAlive;
    int damage;

    public Projectile(double x, double y, double width, double height, double rotation, double velocity, PlayerEntity owner)
    {
        super(x, y, width, height, rotation);
        this.owner = owner;
        //default to 240 ticks or ~8 seconds
        this.lifeTime = 240;
        ticksAlive = 0;
        this.damage = 2;

        this.velocityVector = new Vector2(0, velocity);
        velocityVector.rotate(rotation);
    }

    @Override
    public void handleCollision(GameEntity otherEntity)
    {
        if(otherEntity instanceof Ship && otherEntity != owner)
        {
            //Apply damage and set remove flag
            otherEntity.health -= damage;
            this.removeFlag = true;
        }
    }

    @Override
    public String getEntityDataJSON()
    {
        return null;
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

            this.body.translate(position.difference(oldPosition));
        }
    }
}
