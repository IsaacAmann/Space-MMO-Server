package com.SpaceMMO.RESTControllers;

import com.SpaceMMO.Services.AuthorizationService;
import com.SpaceMMO.UserManagement.UserAccount;
import com.SpaceMMO.UserManagement.UserAccountRepository;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class LoginController
{
    @Autowired
    private Environment env;
    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private UserAccountRepository userAccountRepository;

    private final String awsAuthOUrl = "https://space-mmo-sso.auth.us-east-2.amazoncognito.com/oauth2/token";
    @PostMapping("/public/login")
    public Map<String, Object> login(@RequestBody Map<String, Object> payload, HttpServletRequest request)
    {
        HashMap<String, Object> output = new HashMap<String, Object>();
        //Retrieve JWT from amazon
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        header.setBasicAuth(env.getProperty("spring.security.oauth2.client.registration.cognito.client-id"), env.getProperty("spring.security.oauth2.client.registration.cognito.client-secret"));
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("code", (String)payload.get("code"));
        body.add("client_id", env.getProperty("spring.security.oauth2.client.registration.cognito.client-id"));
        body.add("client_secret", env.getProperty("spring.security.oauth2.client.registration.cognito.client-secret"));
        //System.out.println(env.getProperty("spring.security.oauth2.client.registration.cognito.client-id"));
        body.add("grant_type", "authorization_code");
        //body.add("redirect_uri", "https://winapimonitoring.com:443");
        body.add("redirect_uri", "https://localhost:3000");

        HttpEntity<MultiValueMap<String,String>> httpRequest = new HttpEntity<MultiValueMap<String,String>>(body, header);

        LinkedHashMap<String,String> response = restTemplate.postForObject(awsAuthOUrl, httpRequest, LinkedHashMap.class);
        //System.out.println("code: " + (String)payload.get("code"));

        //Check if user has an account created yet, if not create it on the database
        UserAccount account = authorizationService.getAccountFromToken((String)response.get("access_token"));
       if(account == null)
        {
            System.out.println("username: "+ (String)authorizationService.getToken((String)response.get("access_token")).get("username"));
            account = new UserAccount((String)authorizationService.getToken((String)response.get("access_token")).get("username"), UserAccount.UserRole.USER);
            userAccountRepository.save(account);
        }

        //Return JWT to client
        System.out.println("user2: " + account.username);
        output.put("response", response);
        HashMap<String, Object> accountObject = new HashMap<String, Object>();
        accountObject.put("username", account.username);
        accountObject.put("userRole", account.userRole);
        output.put("account", accountObject);
        return output;
    }

    @PostMapping("/api/authTest")
    public Map<String, Object> authTest(@RequestBody Map<String, Object> payload, HttpServletRequest request)
    {
        HashMap<String, Object> output = new HashMap<String, Object>();


        authorizationService.printToken(request);
        Map<String, Object> token = authorizationService.getToken(request);
        //System.out.println(authHeader);
        output.put("Message", "Authed");
        output.put("tokenUsername", token.get("username"));
        output.put("account", authorizationService.getAccountFromToken(request));
        return output;
    }
}
