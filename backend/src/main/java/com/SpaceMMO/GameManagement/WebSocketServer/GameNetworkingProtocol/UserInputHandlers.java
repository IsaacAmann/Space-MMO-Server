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
            float desiredRotation = messageBuffer.getFloat(2);
            //desiredRotation = (byte)Integer.reverse(Float.floatToIntBits(desiredRotation));
            System.out.println("Input byte: " + inputBitMap);

            if((inputBitMap & W_BITMASK) == W_BITMASK)
            {
                System.out.println("W pressed by " + playerAccount.username);
                player.inputW = true;
            }
            else
            {
                player.inputW = false;
            }

            if((inputBitMap & A_BITMASK) == A_BITMASK)
            {
                System.out.println("A pressed by " + playerAccount.username);
                player.inputA = true;
            }
            else
            {
                player.inputA = false;
            }

            if((inputBitMap & S_BITMASK) == S_BITMASK)
            {
                System.out.println("S pressed by " + playerAccount.username);
                player.inputS = true;
            }
            else
            {
                player.inputS = false;
            }

            if((inputBitMap & D_BITMASK) == D_BITMASK)
            {
                System.out.println("D pressed by " + playerAccount.username);
                player.inputD = true;
            }
            else
            {
                player.inputD = false;
            }

            if((inputBitMap & SPACE_BITMASK) == SPACE_BITMASK)
            {
                System.out.println("SPACE pressed by " + playerAccount.username);
                player.inputSpace = true;
            }
            else
            {
                player.inputSpace = false;
            }

            if((inputBitMap & Q_BITMASK) == Q_BITMASK)
            {
                System.out.println("Q pressed by " + playerAccount.username);
                player.inputQ = true;
            }
            else
            {
                player.inputQ = false;
            }

            if((inputBitMap & E_BITMASK) == E_BITMASK)
            {
                System.out.println("E pressed by " + playerAccount.username);
                player.inputE = true;
            }
            else
            {
                player.inputE = false;
            }

            if((inputBitMap & F_BITMASK) == F_BITMASK)
            {
                System.out.println("F pressed by " + playerAccount.username);
                player.inputF = true;
            }
            else
            {
                player.inputF = false;
            }

            System.out.println(playerAccount.username + " desired angle: " + desiredRotation);
            player.desiredRotation = desiredRotation;
        }

    }
}

