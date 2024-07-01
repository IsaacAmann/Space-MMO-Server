package com.SpaceMMO.GameManagement.WebSocketServer.GameNetworkingProtocol;

import com.SpaceMMO.GameManagement.SectorSystem.Player;
import com.SpaceMMO.GameManagement.WebSocketServer.GameSessionService;
import com.SpaceMMO.UserManagement.UserAccount;
import com.SpaceMMO.UserManagement.UserAccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;

import java.nio.ByteBuffer;

@Service
public class UserInputHandlers
{
    @Autowired
    UserAccountRepository userAccountRepository;
    @Autowired
    GameSessionService gameSessionService;

    private final int  W_BITMASK = 0b00000001;
    private final int A_BITMASK = 0b00000010;
    private final int S_BITMASK = 0b00000100;
    private final int D_BITMASK = 0b00001000;

    private final int SPACE_BITMASK = 0b00010000;
    private final int Q_BITMASK = 0b00100000;
    private final int E_BITMASK = 0b01000000;
    private final int F_BITMASK = 0b10000000;


    public void handlePlayerShipControl(WebSocketSession session, BinaryMessage message)
    {
        UserAccount playerAccount = gameSessionService.userSocketSessions.get(session.getId());
        Player player = gameSessionService.playerList.get(playerAccount.username);
        if(player != null)
        {
            //Parse message
            ByteBuffer messageBuffer = message.getPayload();
            byte inputBitMap = messageBuffer.get(1);
            System.out.println("Input byte: " + inputBitMap);

            if((inputBitMap & W_BITMASK) == W_BITMASK)
            {
                System.out.println("W pressed by " + playerAccount.username);
            }

            if((inputBitMap & A_BITMASK) == A_BITMASK)
            {
                System.out.println("A pressed by " + playerAccount.username);
            }

            if((inputBitMap & S_BITMASK) == S_BITMASK)
            {
                System.out.println("S pressed by " + playerAccount.username);
            }

            if((inputBitMap & D_BITMASK) == D_BITMASK)
            {
                System.out.println("D pressed by " + playerAccount.username);
            }

            if((inputBitMap & SPACE_BITMASK) == SPACE_BITMASK)
            {
                System.out.println("SPACE pressed by " + playerAccount.username);
            }

            if((inputBitMap & Q_BITMASK) == Q_BITMASK)
            {
                System.out.println("Q pressed by " + playerAccount.username);
            }

            if((inputBitMap & E_BITMASK) == E_BITMASK)
            {
                System.out.println("E pressed by " + playerAccount.username);
            }

            if((inputBitMap & F_BITMASK) == F_BITMASK)
            {
                System.out.println("F pressed by " + playerAccount.username);
            }



        }

    }
}

