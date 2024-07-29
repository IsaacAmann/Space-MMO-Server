package com.SpaceMMO.GameManagement.EntitySystem.CollisionSystem;

import com.SpaceMMO.GameManagement.EntitySystem.GameEntity;

public interface CollisionCallBack
{
    public void call(GameEntity rayCaster, GameEntity collision);
}
