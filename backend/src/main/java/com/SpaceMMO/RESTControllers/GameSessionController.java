package com.SpaceMMO.RESTControllers;

import com.SpaceMMO.Services.AuthorizationService;
import com.SpaceMMO.UserManagement.UserAccount;
import com.SpaceMMO.UserManagement.UserAccountRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class GameSessionController
{
    @Autowired
    AuthorizationService authorizationService;
    @Autowired
    UserAccountRepository userAccountRepository;

    @PostMapping("/api/getGameToken")
    public Map<String, Object> getGameToken(@RequestBody Map<String, Object> payload, HttpServletRequest request)
    {
        HashMap<String, Object> output = new HashMap<String, Object>();

        UserAccount account = authorizationService.getAccountFromToken(request);

        if(account.inGame == false)
        {
            //Generate new session token
            account.generateGameToken();
            //Save account
            userAccountRepository.save(account);
            //Pass token to user
            output.put("gameSessionToken", account.getEncodedGameToken());
        }
        else
        {
            //Return error
            output.put("error", "User signed in on another session");
        }

        return output;
    }
}

