package com.SpaceMMO.GameManagement.EntitySystem;

import com.SpaceMMO.GameManagement.SectorSystem.Player;
import com.SpaceMMO.GameManagement.SectorSystem.Sector;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealVector;

import java.util.HashMap;

public class PlayerEntity extends GameEntity
{
    //Thrust force values
    public float starboardThrust = 4;
    public float portsideThrust = 4;
    public float forwardThrust = 8;
    public float reverseThrust = 4;

    public float maxSpeed = 1000;
    //Rotational speed
    public float rotationalAcceleration = (float)0.0101;
    public float maxRotationalVelocity = (float)5;
    //Rotation in radians

    //Float desired rotation in radians that ship should attempt to orient to
    public float desiredRotation;


    //Friction value to slow down, may be 0
    public float friction = (float)2;
    //Reference to the Player object that owns the entity
    public Player player;

    public PlayerEntity(Player player)
    {
        super();
        this.player = player;
        width = 211;
        height = 80;
        rotation = 0;
    }
    public String getEntityDataJSON()
    {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> entityData = new HashMap<String, Object>();
        entityData.put("test", "helloworld");
        String output = null;
        try
        {
            output = objectMapper.writeValueAsString(entityData);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return output;
    }

    @Override
    public void update()
    {
        RealVector impulse = new ArrayRealVector();
        impulse = impulse.append(0).append(0);

         System.out.println("ID: " + entityID + " rot: " + rotation);
        if(player.inputW)
        {
            impulse.addToEntry(0, forwardThrust);
        }
        if(player.inputS)
        {
            impulse.addToEntry(0, reverseThrust*-1);
        }
        if(player.inputA)
        {
            impulse.addToEntry(1, portsideThrust*-1);
        }
        if(player.inputD)
        {
            impulse.addToEntry(1, starboardThrust);
        }
        if(player.inputQ)
        {
            if(rotationalVelocity - rotationalAcceleration > -1 * maxRotationalVelocity)
                rotationalVelocity -= rotationalAcceleration;
            else
                rotationalVelocity = maxRotationalVelocity * -1;
        }
        if (player.inputE)
        {
            if(rotationalVelocity + rotationalAcceleration < maxRotationalVelocity)
                this.rotationalVelocity += rotationalAcceleration;
            else
                rotationalVelocity = maxRotationalVelocity;
        }

        //Handle rotation
        this.desiredRotation = player.desiredRotation;
        //springRotation();
        this.rotation += rotationalVelocity;

        //Set direction of velocity vector
        float impulseX = (float)impulse.getEntry(0);
        float impulseY = (float)impulse.getEntry(1);

        if(impulseX != 0 || impulseY != 0) {
            //See https://stackoverflow.com/questions/620745/c-rotating-a-vector-around-a-certain-point
            double finalImpulseX = ((impulseX - 0.0) * Math.cos(rotation)) - ((impulseY) * Math.sin(rotation));
            double finalImpulseY = 0.0 - ((0.0 - impulseY) * Math.cos(rotation)) + ((impulseX - 0.0) * Math.sin(rotation));

            impulse.setEntry(0, finalImpulseX);
            impulse.setEntry(1, finalImpulseY);
        }
        /*
        if(x < 0)
        {
            //impulse.setEntry(0, impulse.getEntry(0)*-1);
            impulse.setEntry(1, impulse.getEntry(1)*-1);
        }
        */
        velocityVector = velocityVector.add(impulse);

        //Check that magnitude does not exceed max speed



        this.x += velocityVector.getEntry(0) / Sector.TICKS_PER_SECOND;
        this.y += velocityVector.getEntry(1) / Sector.TICKS_PER_SECOND;
        //this.rotation += rotationalVelocity / Sector.TICKS_PER_SECOND;
    }

    //See https://stackoverflow.com/questions/5100811/algorithm-to-control-acceleration-until-a-position-is-reached
    public void springRotation()
    {
        float target = desiredRotation;
        float current = rotation;
        float timeStep = ((float)1)/Sector.TICKS_PER_SECOND;
        //float springConstant = rotationalAcceleration;
        float springConstant = 50;

        float currentToTarget = target - current;
        float springForce = currentToTarget * springConstant;
        float dampingForce = -1*rotationalVelocity * 2 * (float)Math.sqrt(springConstant);
        float force = springForce + dampingForce;

        rotationalVelocity += force * timeStep;
        System.out.println("force: " + timeStep);

    }
}
