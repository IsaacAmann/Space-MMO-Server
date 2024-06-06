package com.SpaceMMO.GameManagement.WebSocketServer.GameNetworkingProtocol;

import com.SpaceMMO.GameManagement.WebSocketServer.GameSessionService;
import com.SpaceMMO.UserManagement.UserAccount;
import com.SpaceMMO.UserManagement.UserAccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;

import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

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

            //Send response
            session.sendMessage(response);

        }
        //Handle case where account is null (this should not happen)
        else
        {

        }
    }


}
