package com.SpaceMMO.GameManagement.WebSocketServer.GameNetworkingProtocol;

import com.SpaceMMO.GameManagement.EntitySystem.GameEntity;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

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
        //MessageType(1) + EntityID(4) + X(4) + Y(4) + VelocityX(4) + VelocityY(4) + Health(4) =
        ByteBuffer payload = ByteBuffer.allocate(25);

        payload.put(ProtocolConstants.ENTITY_UPDATE);
        payload.putInt(entity.entityID);
        payload.putFloat(entity.x);
        payload.putFloat(entity.y);
        payload.putFloat(entity.velocityX);
        payload.putFloat(entity.velocityY);
        payload.putInt(entity.health);

        BinaryMessage response = new BinaryMessage(payload.array());

        session.sendMessage(response);
    }
}
