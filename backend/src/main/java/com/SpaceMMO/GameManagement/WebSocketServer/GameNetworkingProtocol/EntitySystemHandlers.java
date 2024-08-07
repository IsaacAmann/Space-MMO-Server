package com.SpaceMMO.GameManagement.WebSocketServer.GameNetworkingProtocol;

import com.SpaceMMO.GameManagement.EntitySystem.GameEntity;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class EntitySystemHandlers
{

    public void sendEntityUpdate(WebSocketSession session, GameEntity entity) throws Exception
    {
        //MessageType(1) + EntityID(4) + X(4) + Y(4) + VelocityX(4) + VelocityY(4) + Health(4) + rotation(4) + rotationalVelocity(4)= 33
        ByteBuffer payload = ByteBuffer.allocate(33);
        payload.order(ByteOrder.LITTLE_ENDIAN);

        payload.put(ProtocolConstants.ENTITY_UPDATE);
        payload.putInt(entity.entityID);
        payload.putFloat((float)entity.position.x);
        payload.putFloat((float)entity.position.y);

        payload.putFloat((float)entity.velocityVector.x);
        payload.putFloat((float)entity.velocityVector.y);
        payload.putInt(entity.health);
        payload.putFloat((float)entity.rotation);
        payload.putFloat((float)entity.rotationalVelocity);
        //System.out.println(entity.rotation);

        BinaryMessage response = new BinaryMessage(payload.array());

        session.sendMessage(response);
    }

    public void sendNewEntityNotification(WebSocketSession session, GameEntity entity) throws Exception
    {
        String json = entity.getEntityDataJSON();
        json = Base64.getEncoder().encodeToString(json.getBytes());

        //
        ByteBuffer payload = ByteBuffer.allocate(json.getBytes(StandardCharsets.US_ASCII).length + 1 + 1 + 4 + 4 + 4 + 4 + 4 + 4);
        payload.order(ByteOrder.LITTLE_ENDIAN);

        payload.put(ProtocolConstants.NEW_ENTITY_NOTIFICATION);

        payload.putInt(entity.entityID);
        payload.put((byte)0);

        payload.putFloat((float)entity.position.x);
        payload.putFloat((float)entity.position.y);
        payload.putFloat((float)entity.rotation);
        payload.putFloat((float)entity.velocityVector.x);
        payload.putFloat((float)entity.velocityVector.y);
        payload.put(json.getBytes(StandardCharsets.US_ASCII));




        BinaryMessage response = new BinaryMessage(payload.array());
        System.out.println("new entity message");
        System.out.println("JSON: " + json);
        session.sendMessage(response);
    }

    public BinaryMessage sendEntityDeleteNotification(WebSocketSession session, GameEntity entity) throws Exception
    {
        //Message type (1) + EntityID(4) = 5
        ByteBuffer payload = ByteBuffer.allocate(5);
        payload.order(ByteOrder.LITTLE_ENDIAN);

        payload.put(ProtocolConstants.ENTITY_DELETE);
        payload.putInt(entity.entityID);

        BinaryMessage message = new BinaryMessage(payload.array());
        //session.sendMessage(message);
        return message;
    }
}
