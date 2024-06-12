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
import java.nio.MappedByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class TestingMessageHandlers
{
    @Autowired
    UserAccountRepository userAccountRepository;
    @Autowired
    GameSessionService gameSessionService;
    @Autowired
    SectorMessages sectorMessages;

    public void joinDebugSector(WebSocketSession session, BinaryMessage message) throws Exception
    {
        //Get account of user
        UserAccount account = GameSessionService.userSocketSessions.get(session.getId());
        if(account != null)
        {
            GameServer.testSector.addPlayer(gameSessionService.playerList.get(account.username));
            sectorMessages.sectorJoinNotification(session, GameServer.testSector);
        }
        else
        {

        }
    }


}
