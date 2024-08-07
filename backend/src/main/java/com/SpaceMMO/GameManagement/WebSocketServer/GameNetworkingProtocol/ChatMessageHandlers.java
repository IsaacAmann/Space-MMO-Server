package com.SpaceMMO.GameManagement.WebSocketServer.GameNetworkingProtocol;

import com.SpaceMMO.GameManagement.ChatSystem.ChatMessage;
import com.SpaceMMO.GameManagement.EntitySystem.GameEntity;
import com.SpaceMMO.GameManagement.SectorSystem.Player;
import com.SpaceMMO.GameManagement.WebSocketServer.GameSessionService;
import com.SpaceMMO.UserManagement.UserAccount;
import com.SpaceMMO.UserManagement.UserAccountRepository;
import org.dyn4j.geometry.Vector2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class ChatMessageHandlers
{

    @Autowired
    UserAccountRepository userAccountRepository;
    @Autowired
    GameSessionService gameSessionService;

    public void sendChatMessage(Player player, ChatMessage message)
    {
        String encodedMessage = Base64.getEncoder().encodeToString(message.getFormattedString().getBytes(StandardCharsets.US_ASCII));
        //Message type (1) + channel (4) + message length (variable)
        ByteBuffer payload = ByteBuffer.allocate(5 + encodedMessage.getBytes(StandardCharsets.US_ASCII).length);
        payload.order(ByteOrder.LITTLE_ENDIAN);

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
        messageBuffer.order(ByteOrder.LITTLE_ENDIAN);

        int channel = messageBuffer.getInt(1);

        //ByteBuffer stringBytes = ByteBuffer.allocate(messageBuffer.capacity() - 5);
        byte[] buffer = new byte[messageBuffer.capacity() - 5];
        messageBuffer.position(5);
        ByteBuffer stringBytes = messageBuffer.get(buffer, 0, messageBuffer.capacity() - 5);

        //String decodedMessage = new String(Base64.getDecoder().decode(stringBytes).array());
        String decodedMessage = new String(Base64.getDecoder().decode(buffer));

        System.out.println(decodedMessage);
        //Get sender information
        UserAccount user = gameSessionService.userSocketSessions.get(session.getId());

        if(user != null)
        {
            Player player = gameSessionService.playerList.get(user.username);
            ChatMessage newMessage = new ChatMessage(player, channel, decodedMessage);
            if(channel == 0)
            {
                player.currentSector.sectorChat.messageQueue.add(newMessage);
            }
        }
    }
}
