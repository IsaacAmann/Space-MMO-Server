package com.SpaceMMO.GameManagement.WebSocketServer;

import jakarta.websocket.*;
import jakarta.websocket.OnOpen;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/openGameSession/{username}")
public class GameServer
{
    @OnOpen
    public void open(Session session, @PathParam("username") String username)
    {

    }

    @OnClose
    public void close(Session session)
    {

    }

    @OnError
    public void onError(Throwable error)
    {

    }

    @OnMessage
    public void handleMessage(SocketMessage message, String session)
    {

    }
}

