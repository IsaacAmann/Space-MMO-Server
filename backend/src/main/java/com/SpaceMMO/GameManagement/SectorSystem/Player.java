package com.SpaceMMO.GameManagement.SectorSystem;

import com.SpaceMMO.GameManagement.EntitySystem.GameEntity;
import com.SpaceMMO.UserManagement.UserAccount;
import org.springframework.web.socket.WebSocketSession;

public class Player
{
    WebSocketSession session;
    GameEntity currentEntity;
    Sector currentSector;
    UserAccount account;

    public Player(WebSocketSession session, UserAccount account)
    {
        this.session = session;
        this.account = account;

    }


}
