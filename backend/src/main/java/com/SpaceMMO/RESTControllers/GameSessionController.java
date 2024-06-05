package com.SpaceMMO.RESTControllers;

import com.SpaceMMO.GameManagement.WebSocketServer.GameSessionService;
import com.SpaceMMO.Services.AuthorizationService;
import com.SpaceMMO.UserManagement.UserAccount;
import com.SpaceMMO.UserManagement.UserAccountRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class GameSessionController
{
    @Autowired
    AuthorizationService authorizationService;
    @Autowired
    UserAccountRepository userAccountRepository;
    @Autowired
    GameSessionService gameSessionService;

    @PostMapping("/api/getGameToken")
    public Map<String, Object> getGameToken(@RequestBody Map<String, Object> payload, HttpServletRequest request) throws IOException
    {
        HashMap<String, Object> output = new HashMap<String, Object>();

        UserAccount account = authorizationService.getAccountFromToken(request);


        //Disconnect player from any active session
        WebSocketSession session = gameSessionService.usernameToSessionMap.get(account.username);
        if(session != null)
        {
            session.close(new CloseStatus(1000, "Closing connection, new session token requested "));
        }
        //Save account
        //Generate new session token
        account.generateGameToken();
        account.inGame = false;
        userAccountRepository.save(account);
        //Pass token to user
        output.put("gameSessionToken", account.getEncodedGameToken());


        return output;
    }
}

