package com.SpaceMMO.GameManagement.SectorSystem;

import com.SpaceMMO.GameManagement.EntitySystem.GameEntity;
import com.SpaceMMO.UserManagement.UserAccount;
import org.springframework.web.socket.WebSocketSession;

public class Player
{
    public WebSocketSession session;
    public GameEntity currentEntity;
    public Sector currentSector;
    public UserAccount account;

    boolean inputUp;
    boolean inputDown;
    boolean inputLeft;
    boolean inputRight;
    boolean inputSpace;

    public Player(WebSocketSession session, UserAccount account)
    {
        this.session = session;
        this.account = account;

        inputUp = false;
        inputDown = false;
        inputLeft = false;
        inputRight = false;
        inputSpace = false;


    }


}
