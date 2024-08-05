package com.SpaceMMO.GameManagement.WebSocketServer.GameNetworkingProtocol;

import com.SpaceMMO.GameManagement.ChatSystem.ChatMessage;
import com.SpaceMMO.GameManagement.EntitySystem.GameEntity;
import com.SpaceMMO.GameManagement.SectorSystem.Player;
import org.dyn4j.geometry.Vector2;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class ChatMessageHandlers
{
    public void sendChatMessage(Player player, ChatMessage message)
    {
        String encodedMessage = Base64.getEncoder().encodeToString(message.getFormattedString().getBytes(StandardCharsets.US_ASCII));
        //Message type (1) + channel (4) + message length (variable)
        ByteBuffer payload = ByteBuffer.allocate(5 + encodedMessage.getBytes(StandardCharsets.US_ASCII).length);

        payload.put(ProtocolConstants.CHAT_MESSAGE);
        payload.putInt(message.channel);
        payload.put(encodedMessage.getBytes(StandardCharsets.US_ASCII));

        try
        {
            BinaryMessage response = new BinaryMessage(payload.array());
            player.session.sendMessage(response);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    //Go back and change message handlers to use the order(ByteOrder) function so that
    //bytes do not have to be flipped on the client
    public void receiveChatMessage(WebSocketSession session, BinaryMessage message)
    {
        ByteBuffer messageBuffer = message.getPayload();
        int channel = messageBuffer.getInt(1);

        //ByteBuffer stringBytes = ByteBuffer.allocate(messageBuffer.capacity() - 5);
        byte[] buffer = new byte[messageBuffer.capacity() - 5];
        messageBuffer.position(5);
        ByteBuffer stringBytes = messageBuffer.get(buffer, 0, messageBuffer.capacity() - 5);

        //String decodedMessage = new String(Base64.getDecoder().decode(stringBytes).array());
        String decodedMessage = new String(Base64.getDecoder().decode(buffer));

        System.out.println(decodedMessage);
    }
}
