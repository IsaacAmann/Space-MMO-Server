package com.SpaceMMO.GameManagement.WebSocketServer.GameNetworkingProtocol;

import com.SpaceMMO.GameManagement.EntitySystem.PlayerEntity;
import com.SpaceMMO.GameManagement.InventorySystem.HasInventory;
import com.SpaceMMO.GameManagement.SectorSystem.Player;
import com.SpaceMMO.GameManagement.WebSocketServer.GameSessionService;
import com.SpaceMMO.UserManagement.UserAccount;
import com.SpaceMMO.UserManagement.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

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
            //Retrieve entity from current sector

            HasInventory targetEntity = (HasInventory) player.currentSector.getEntityByID(entityID);

            //Return inventory in JSON format (Base64 ASCII string)
            if(player.currentEntity != null)
            {
                String inventoryJSON = ((PlayerEntity)player.currentEntity).getInventory().getInventoryJSON();
                inventoryJSON = Base64.getEncoder().encodeToString(inventoryJSON.getBytes());


                ByteBuffer payload = ByteBuffer.allocate(inventoryJSON.getBytes(StandardCharsets.US_ASCII).length + 1);
                payload.order(ByteOrder.LITTLE_ENDIAN);

                payload.put(ProtocolConstants.REQUEST_INVENTORY);
                payload.putInt(entityID);
                payload.put(inventoryJSON.getBytes(StandardCharsets.US_ASCII));

                BinaryMessage response = new BinaryMessage(payload);
                player.messageQueue.add(response);

            }
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
