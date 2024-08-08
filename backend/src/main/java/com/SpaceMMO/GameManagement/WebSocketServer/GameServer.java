package com.SpaceMMO.GameManagement.WebSocketServer;


import com.SpaceMMO.GameManagement.EntitySystem.SectorObjects.AsteroidSpawner;
import com.SpaceMMO.GameManagement.SectorSystem.Player;
import com.SpaceMMO.GameManagement.SectorSystem.Sector;
import com.SpaceMMO.GameManagement.ServiceContainer;
import com.SpaceMMO.GameManagement.WebSocketServer.GameNetworkingProtocol.*;
import com.SpaceMMO.UserManagement.UserAccount;
import com.SpaceMMO.UserManagement.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;
import org.springframework.web.socket.handler.ConcurrentWebSocketSessionDecorator;

import java.io.IOException;

public class GameServer extends BinaryWebSocketHandler
{
    @Autowired
    UserAccountRepository userAccountRepository;
    @Autowired
    GameSessionService gameSessionService;
    @Autowired
    BasicMessageHandlers basicMessageHandlers;
    @Autowired
    SectorMessages sectorMessages;
    @Autowired
    ServiceContainer serviceContainer;
    @Autowired
    TestingMessageHandlers testingMessageHandlers;
    @Autowired
    UserInputHandlers userInputHandlers;
    @Autowired
    ChatMessageHandlers chatMessageHandlers;

    public static Sector testSector;
    public static float testX = 1;
    public static float testY = 1;

    public GameServer()
    {
    }
    @Override
    public void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception
    {
        //System.out.println(message.toString());
        //Get first byte that contains the message type
        Byte messageType = message.getPayload().array()[0];
        switch(messageType.byteValue())
        {
            case ProtocolConstants.PLAYER_INFO:
                basicMessageHandlers.handlePlayerInfo(session, message);
                break;
            case ProtocolConstants.SECTOR_JOIN:
                System.out.println("sector join");
                break;
            case ProtocolConstants.JOIN_DEBUG:
                testingMessageHandlers.joinDebugSector(session,message);
                break;
            case ProtocolConstants.PLAYER_SHIP_INPUT:
                userInputHandlers.handlePlayerShipControl(session, message);
                break;
            case ProtocolConstants.CHAT_MESSAGE:
                chatMessageHandlers.receiveChatMessage(session, message);
                break;
            default:

                break;
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws IOException
    {
        boolean acceptConnection = false;
        if(testSector == null)
        {
            testSector = new Sector(serviceContainer);
            //add asteroid spawner to test sector
            AsteroidSpawner testSpawner = new AsteroidSpawner(0,0, testSector, "gold");
            testSector.entityAddQueue.add(testSpawner);
        }
        //Check header for token
       // System.out.println(session.getHandshakeHeaders().get("username").get(0));
        //System.out.println(session.getHandshakeHeaders().get("token").get(0));

      //  String username = session.getHandshakeHeaders().get("username").get(0);
      //  String encodedToken = session.getHandshakeHeaders().get("token").get(0);
        String username = (String)session.getAttributes().get("username");
        String encodedToken = (String)session.getAttributes().get("token");

       // System.out.println("NEW: " + session.getAttributes().get("username"));
       // System.out.println("NEW: " + session.getAttributes().get("token"));
        //Verify token
        UserAccount account = userAccountRepository.findByUsername(username);
        if(account != null)
        {
            if(account.isValidGameToken(encodedToken) == true /*&& account.inGame == false*/)
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
            gameSessionService.usernameToSessionMap.put(account.username, new ConcurrentWebSocketSessionDecorator(session, 100, 30000));
            Player newPlayer = new Player(gameSessionService.usernameToSessionMap.get(account.username), account);
            gameSessionService.playerList.put(account.username, newPlayer);
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

            //remove player from player map and save data
            Player player = gameSessionService.playerList.get(account.username);
            if(player != null)
            {
                if(player.currentSector != null)
                {
                    //Remove player from current sector
                    player.currentSector.removePlayer(player);
                }
                //Updates fields on players account

                //Remove from player list
                gameSessionService.playerList.remove(account.username);
            }

        }
    }
}


