package com.SpaceMMO.GameManagement.WebSocketServer.GameNetworkingProtocol;

import com.SpaceMMO.GameManagement.EntitySystem.BasicEntity;
import com.SpaceMMO.GameManagement.EntitySystem.GameEntity;
import com.SpaceMMO.GameManagement.SectorSystem.Sector;
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
import java.util.HashMap;

@Service
public class SectorMessages
{
    @Autowired
    UserAccountRepository userAccountRepository;
    @Autowired
    GameSessionService gameSessionService;

    public void sectorJoinNotification(WebSocketSession session, Sector sector) throws Exception
    {


        //Sector data to send
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> sectorData = new HashMap<String, Object>();
        sectorData.put("sectorName", sector.name);
        sectorData.put("sectorID", sector.sectorID);

        String dataJSON = objectMapper.writeValueAsString(sectorData);

        //Encode json string
        String encodedJSON = Base64.getEncoder().encodeToString(dataJSON.getBytes());

        //Create message
        ByteBuffer payload = ByteBuffer.allocate(encodedJSON.getBytes(StandardCharsets.US_ASCII).length + 1);

        System.out.println(payload.array().length);
        payload.put(ProtocolConstants.SECTOR_JOIN);
        payload.put(encodedJSON.getBytes(StandardCharsets.US_ASCII));

        BinaryMessage message = new BinaryMessage(payload.array());

        //Send response
        session.sendMessage(message);

    }


}
