package com.SpaceMMO.GameManagement.EntitySystem.CollisionSystem;

import com.SpaceMMO.GameManagement.EntitySystem.GameEntity;

import java.util.ArrayList;
import java.util.Comparator;

//Special entity class to handle ray casting.
//Should exist for one game tick and produce a list of all entities that collided with the ray
//Uses the entities rectangle to determine collisions
//Maybe not the best solution, but allows it to integrate into the existing quad tree setup with little extra effort
public class RayCast extends GameEntity
{
    public ArrayList<GameEntity> colliders;
    public CollisionCallBack callBack;
    public GameEntity source;
    //Default to only handle first collision and discard the rest
    public boolean resolveFirstHit;


    public RayCast(double x,double y, double distance, double angle, GameEntity source)
    {
        super(x, y, 1, distance, angle);
        colliders = new ArrayList<GameEntity>();
        this.source = source;
        resolveFirstHit = true;
    }

    public String getEntityDataJSON()
    {
        return null;
    }

    public void update()
    {
        //Case for only handling the closest hit
        if(resolveFirstHit)
        {
            //Sort the list by distance
            colliders.sort(new DistanceComparator());

            //Run the call back function for the closest entity
            if(colliders.size() >= 1)
                callBack.call(source, colliders.get(1));
        }
        //Case to handle all collisions along the rays path
        else
        {
            //run the call back function on all colliders
            for(GameEntity entity : colliders)
            {
                callBack.call(source, entity);
            }
        }
    }

    @Override
    public void handleCollision(GameEntity otherEntity)
    {
        //Add collider to list
        this.colliders.add(otherEntity);
    }

    //Comparator for sorting by distance to ray caster
    private class DistanceComparator implements Comparator<GameEntity>
    {
        public int compare(GameEntity entity1, GameEntity entity2)
        {
            //Get distance to source entity for both comparing entities
            double distance1 = entity1.position.distance(source.position);

            double distance2 = entity2.position.distance(source.position);

            if(distance1 > distance2)
            {
                return 1;
            }
            else if(distance1 < distance2)
            {
                return -1;
            }
            else
            {
                return 0;
            }
        }
    }
}
