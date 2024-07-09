package com.SpaceMMO.GameManagement.WebSocketServer.GameNetworkingProtocol;

import com.SpaceMMO.GameManagement.EntitySystem.GameEntity;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class EntitySystemHandlers
{
    public void sendErrorMessage(WebSocketSession session, short errorCode, String message) throws Exception
    {
        String encodedMessage = Base64.getEncoder().encodeToString(message.getBytes());

        ByteBuffer payload = ByteBuffer.allocate(encodedMessage.getBytes(StandardCharsets.US_ASCII).length + 3);

        payload.put(ProtocolConstants.ERROR);
        payload.putShort(errorCode);
        payload.put(encodedMessage.getBytes(StandardCharsets.US_ASCII));

        BinaryMessage response = new BinaryMessage(payload.array());

        session.sendMessage(response);
    }

    public void sendEntityUpdate(WebSocketSession session, GameEntity entity) throws Exception
    {
        //MessageType(1) + EntityID(4) + X(4) + Y(4) + VelocityX(4) + VelocityY(4) + Health(4) + rotation(4) + rotationalVelocity(4)= 33
        ByteBuffer payload = ByteBuffer.allocate(33);

        payload.put(ProtocolConstants.ENTITY_UPDATE);
        payload.putInt(entity.entityID);
        payload.putFloat(entity.x);
        payload.putFloat(entity.y);
        payload.putFloat((float)entity.velocityVector.getEntry(0));
        payload.putFloat((float)entity.velocityVector.getEntry(1));
        payload.putInt(entity.health);
        payload.putFloat(entity.rotation);
        payload.putFloat(entity.rotationalVelocity);
        System.out.println(entity.rotation);

        BinaryMessage response = new BinaryMessage(payload.array());

        session.sendMessage(response);
    }

    public void sendNewEntityNotification(WebSocketSession session, GameEntity entity) throws Exception
    {
        String json = entity.getEntityDataJSON();
        json = Base64.getEncoder().encodeToString(json.getBytes());

        ByteBuffer payload = ByteBuffer.allocate(json.getBytes(StandardCharsets.US_ASCII).length + 1 + 1 + 4);
        payload.put(ProtocolConstants.NEW_ENTITY_NOTIFICATION);

        payload.putInt(entity.entityID);
        payload.put((byte)0);
        payload.put(json.getBytes(StandardCharsets.US_ASCII));

        BinaryMessage response = new BinaryMessage(payload.array());
        System.out.println("new entity message");
        System.out.println("JSON: " + json);
        session.sendMessage(response);
    }
}
