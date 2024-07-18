package com.SpaceMMO.GameManagement;

import com.SpaceMMO.GameManagement.WebSocketServer.GameNetworkingProtocol.*;
import com.SpaceMMO.GameManagement.WebSocketServer.GameSessionService;
import com.SpaceMMO.UserManagement.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Class to contain services for instantiated objects. Would usually be accessed using autowired
@Service
public class ServiceContainer
{
    @Autowired
    public UserAccountRepository userAccountRepository;
    @Autowired
    public GameSessionService gameSessionService;
    @Autowired
    public BasicMessageHandlers basicMessageHandlers;
    @Autowired
    public SectorMessages sectorMessages;
    @Autowired
    public TestingMessageHandlers testingMessageHandlers;
    @Autowired
    public EntitySystemHandlers entitySystemHandlers;
    @Autowired
    public UserInputHandlers userInputHandlers;
    @Autowired
    public ClientAffectsHandler clientAffectsHandler;
}
