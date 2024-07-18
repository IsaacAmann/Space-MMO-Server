package com.SpaceMMO.GameManagement.WebSocketServer.GameNetworkingProtocol;

import com.SpaceMMO.GameManagement.EntitySystem.GameEntity;
import com.SpaceMMO.GameManagement.SectorSystem.Player;
import org.dyn4j.geometry.Vector2;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;

import java.nio.ByteBuffer;

//Contains socket handlers for displaying affects client side that do not need to be a synchronized entity
@Service
public class ClientAffectsHandler
{
    public void sendLaserAffect(Player player, Vector2 startPosition, Vector2 endPosition, float duration, float red, float green, float blue) throws Exception
    {
        //byte messageType(1) + Float startx (4) + float starty(4) + float endx(4) + float endy(4) + float duration(4) + float red(4) + float green(4) + float blue(4) = 33
        ByteBuffer payload = ByteBuffer.allocate(33);

        payload.put(ProtocolConstants.LASER_AFFECT);

        payload.putFloat((float)startPosition.x);
        payload.putFloat((float)startPosition.y);

        payload.putFloat((float)endPosition.x);
        payload.putFloat((float)endPosition.y);

        payload.putFloat(duration);

        payload.putFloat(red);
        payload.putFloat(green);
        payload.putFloat(blue);

        //session.sendMessage(new BinaryMessage(payload));
        player.messageQueue.add(new BinaryMessage(payload.array()));
    }

}
