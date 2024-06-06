package com.SpaceMMO.GameManagement.WebSocketServer;


import com.SpaceMMO.GameManagement.WebSocketServer.GameNetworkingProtocol.BasicMessageHandlers;
import com.SpaceMMO.GameManagement.WebSocketServer.GameNetworkingProtocol.ProtocolConstants;
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
    @Autowired
    GameSessionService gameSessionService;
    @Autowired
    BasicMessageHandlers basicMessageHandlers;

    @Override
    public void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception
    {
        System.out.println(message.toString());
        //Get first byte that contains the message type
        Byte messageType = message.getPayload().array()[0];
        switch(messageType.byteValue())
        {
            case ProtocolConstants.PLAYER_INFO:
                basicMessageHandlers.handlePlayerInfo(session, message);
                break;

            default:

                break;
        }
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
            System.out.println("Game Connection refused: username: " + username + " validToke: " +account.isValidGameToken(encodedToken) + "sessionStarted: " + account.inGame);
        }
        else
        {
            //Begin user game session
            account.inGame = true;
            System.out.println(username + " has connected to the game");
            gameSessionService.userSocketSessions.put(session.getId(), account);
            gameSessionService.usernameToSessionMap.put(account.username, session);
            userAccountRepository.save(account);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)
    {
        UserAccount account = gameSessionService.userSocketSessions.get(session.getId());
        if(account != null)
        {
            account.inGame = false;
            System.out.println(account.username + " has disconnected from the game");
            userAccountRepository.save(account);
            //remove from userSocketSessions map
            gameSessionService.userSocketSessions.remove(session.getId());
            //remove from usernameToSession map
            gameSessionService.usernameToSessionMap.remove(account.username);

        }
    }
}


