package com.SpaceMMO.GameManagement.WebSocketServer;


import com.SpaceMMO.UserManagement.UserAccount;
import com.SpaceMMO.UserManagement.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.io.IOException;

public class GameServer extends BinaryWebSocketHandler
{
    @Autowired
    UserAccountRepository userAccountRepository;

    @Override
    public void handleBinaryMessage(WebSocketSession session, BinaryMessage message)
    {
        System.out.println(message.toString());

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException
    {
        boolean acceptConnection = false;
        //Check header for token
        System.out.println(session.getHandshakeHeaders().get("username").get(0));
        System.out.println(session.getHandshakeHeaders().get("token").get(0));

        String username = session.getHandshakeHeaders().get("username").get(0);
        String encodedToken = session.getHandshakeHeaders().get("token").get(0);

        //Verify token
        UserAccount account = userAccountRepository.findByUsername(username);
        if(account != null)
        {
            if(account.isValidGameToken(encodedToken) == true && account.inGame == false)
            {
                acceptConnection = true;
            }
        }

        if(acceptConnection == false)
        {
            session.close(CloseStatus.BAD_DATA);
        }
        else
        {
            //Begin user game session
        }
    }
}

