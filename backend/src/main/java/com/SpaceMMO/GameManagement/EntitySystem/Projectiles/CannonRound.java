package com.SpaceMMO.GameManagement.EntitySystem.Projectiles;

import com.SpaceMMO.GameManagement.EntitySystem.PlayerEntity;

public class CannonRound extends Projectile
{
    public CannonRound(double x, double y, double width, double height, double rotation, double velocity, PlayerEntity owner)
    {
        super(x, y, width, height, rotation, velocity, owner);
        this.godotScenePath = "res://Entities/Projectiles/BasicBullet/BasicBullet.tscn";
    }
}
