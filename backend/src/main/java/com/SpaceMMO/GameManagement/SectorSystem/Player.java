package com.SpaceMMO.GameManagement.SectorSystem;

import com.SpaceMMO.GameManagement.EntitySystem.GameEntity;
import com.SpaceMMO.UserManagement.UserAccount;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ConcurrentLinkedQueue;

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
    public float desiredRotation;
    public float mouseX;
    public float mouseY;

    //Allows messages to be queued to sent without running into concurrency issues
    public ConcurrentLinkedQueue<BinaryMessage> messageQueue;


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
        mouseX = 0;
        mouseY = 0;
        messageQueue = new ConcurrentLinkedQueue<BinaryMessage>();
    }


}
