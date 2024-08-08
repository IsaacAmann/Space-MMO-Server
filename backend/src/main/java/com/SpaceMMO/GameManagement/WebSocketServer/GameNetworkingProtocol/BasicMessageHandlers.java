package com.SpaceMMO.GameManagement.WebSocketServer.GameNetworkingProtocol;

import com.SpaceMMO.GameManagement.EntitySystem.BasicEntity;
import com.SpaceMMO.GameManagement.EntitySystem.GameEntity;
import com.SpaceMMO.GameManagement.WebSocketServer.GameServer;
import com.SpaceMMO.GameManagement.WebSocketServer.GameSessionService;
import com.SpaceMMO.UserManagement.UserAccount;
import com.SpaceMMO.UserManagement.UserAccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Basic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.ExecutionException;

@Service
public class BasicMessageHandlers
{
    @Autowired
    UserAccountRepository userAccountRepository;
    @Autowired
    GameSessionService gameSessionService;

    public void handlePlayerInfo(WebSocketSession session, BinaryMessage message) throws Exception
    {
        //Get account of user
        UserAccount account = GameSessionService.userSocketSessions.get(session.getId());
        if(account != null)
        {
            ObjectMapper objectMapper = new ObjectMapper();
            String accountJSON = objectMapper.writeValueAsString(account);

            //Encode json string
            String encodedJSON = Base64.getEncoder().encodeToString(accountJSON.getBytes());

            //Create message
            System.out.println(accountJSON);
            System.out.println(encodedJSON);
            System.out.println(encodedJSON.getBytes(StandardCharsets.US_ASCII).length + 1);

            ByteBuffer payload = ByteBuffer.allocate(encodedJSON.getBytes(StandardCharsets.US_ASCII).length + 1);

            System.out.println(payload.array().length);
            payload.put(ProtocolConstants.PLAYER_INFO);
            payload.put(encodedJSON.getBytes(StandardCharsets.US_ASCII));


            BinaryMessage response = new BinaryMessage(payload.array());

            GameServer.testSector.entityTree.add(new BasicEntity(GameServer.testX+=10, GameServer.testY+=10, 5, 5));
            GameServer.testSector.entityTree.add(new BasicEntity(10, 10, 5, 5));

            //Send response
            session.sendMessage(response);

        }
        //Handle case where account is null (this should not happen)
        else
        {

        }
    }

    public void sendErrorMessage(WebSocketSession session, short errorCode, String message) throws Exception
    {
        String encodedMessage = Base64.getEncoder().encodeToString(message.getBytes());

        ByteBuffer payload = ByteBuffer.allocate(encodedMessage.getBytes(StandardCharsets.US_ASCII).length + 3);
        payload.order(ByteOrder.LITTLE_ENDIAN);

        payload.put(ProtocolConstants.ERROR);
        payload.putShort(errorCode);
        payload.put(encodedMessage.getBytes(StandardCharsets.US_ASCII));

        BinaryMessage response = new BinaryMessage(payload.array());

        session.sendMessage(response);
    }


}
