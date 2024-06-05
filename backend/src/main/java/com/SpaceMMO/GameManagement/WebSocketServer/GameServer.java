package com.SpaceMMO.GameManagement.WebSocketServer;


import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

public class GameServer extends BinaryWebSocketHandler
{
    @Override
    public void handleBinaryMessage(WebSocketSession session, BinaryMessage message)
    {
        
    }
}

