package com.SpaceMMO.GameManagement.WebSocketServer;

import com.SpaceMMO.GameManagement.SectorSystem.Player;
import com.SpaceMMO.GameManagement.SectorSystem.Sector;
import com.SpaceMMO.UserManagement.UserAccount;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameSessionService
{
    //Maps Socket SessionIDs to user accounts
    public static ConcurrentHashMap<String, UserAccount> userSocketSessions = new ConcurrentHashMap<String, UserAccount>();
    //Maps usernames to Sessions
    public static ConcurrentHashMap<String, WebSocketSession>  usernameToSessionMap = new ConcurrentHashMap<String, WebSocketSession>();

    //Player map
    public static ConcurrentHashMap<String, Player> playerList = new ConcurrentHashMap<String, Player>();
    //Sector map
    public static ConcurrentHashMap<Integer, Sector> sectorMap = new ConcurrentHashMap<Integer, Sector>();

    //Get user account from session
    public UserAccount getAccountFromSession(WebSocketSession session)
    {
        return userSocketSessions.get(session.getId());
    }
    //Get player from session
    public Player getPlayerFromSession(WebSocketSession session)
    {
        return playerList.get(getAccountFromSession(session).username);
    }
}
