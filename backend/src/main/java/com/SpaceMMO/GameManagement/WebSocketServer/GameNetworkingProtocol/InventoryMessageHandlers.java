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
import java.nio.ByteOrder;


@Service
public class InventoryMessageHandlers
{
    @Autowired
    UserAccountRepository userAccountRepository;
    @Autowired
    GameSessionService gameSessionService;

    public void handleInventoryRequest(WebSocketSession session, BinaryMessage message)
    {
        ByteBuffer messageBuffer = message.getPayload();
        messageBuffer.order(ByteOrder.LITTLE_ENDIAN);



        //Retrieve requested inventory
        //Get entity ID from message
        int entityID = messageBuffer.getInt(1);

        //Retrieve entity from current sector
        Player player = gameSessionService.getPlayerFromSession(session);

        //Verify that requester is authorized to view inventory
        //Defaulting to authorize all requests for now
        boolean authorized = false;
        if(player != null)
        {
            //Place code for checking authorization here later
            authorized = true;
        }

        if(authorized)
        {

            //Return inventory in JSON format (Base64 ASCII string)
        }
        //If unauthorized return error
        else
        {
            //Add error message to players message queue
        }

    }

    public void handleTradeRequest(WebSocketSession session, BinaryMessage message)
    {

    }
}
