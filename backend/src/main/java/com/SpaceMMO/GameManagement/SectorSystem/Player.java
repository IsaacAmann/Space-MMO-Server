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

    public boolean inputW;
    public boolean inputA;
    public boolean inputS;
    public boolean inputD;
    public boolean inputSpace;
    public boolean inputQ;
    public boolean inputE;
    public boolean inputF;

    public Player(WebSocketSession session, UserAccount account)
    {
        this.session = session;
        this.account = account;

        inputW = false;
        inputA = false;
        inputS = false;
        inputD = false;
        inputSpace = false;
        inputQ = false;
        inputE = false;
        inputF = false;

    }


}
