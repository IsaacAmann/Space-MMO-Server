package com.SpaceMMO.GameManagement.WebSocketServer.GameNetworkingProtocol;

import com.SpaceMMO.GameManagement.SectorSystem.Player;
import com.SpaceMMO.GameManagement.WebSocketServer.GameSessionService;
import com.SpaceMMO.UserManagement.UserAccount;
import com.SpaceMMO.UserManagement.UserAccountRepository;
import jdk.incubator.vector.VectorOperators;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;

import java.nio.ByteBuffer;

public class UserInputHandlers
{
    @Autowired
    UserAccountRepository userAccountRepository;
    @Autowired
    GameSessionService gameSessionService;

    public void handlePlayerShipControl(WebSocketSession session, BinaryMessage message)
    {
        UserAccount playerAccount = gameSessionService.userSocketSessions.get(session.getId());
        Player player = gameSessionService.playerList.get(playerAccount.username);
        if(player != null)
        {
            //Parse message
            ByteBuffer messageBuffer = message.getPayload();
            byte inputBitMap = messageBuffer.get(1);


        }

    }
}

