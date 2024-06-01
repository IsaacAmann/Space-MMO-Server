package com.SpaceMMO.RESTControllers;

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

    private final String awsAuthOUrl = "https://space-mmo-sso.auth.us-east-2.amazoncognito.com/oauth2/token";
    @PostMapping("/public/login")
    public Map<String, Object> login(@RequestBody Map<String, Object> payload, HttpServletRequest request)
    {
        HashMap<String, Object> output = new HashMap<String, Object>();
        System.out.println("yuh");
        System.out.println(env.getProperty("spring.security.oauth2.client.registration.cognito.client-secret"));

        //Retrieve JWT from amazon
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        header.setBasicAuth(env.getProperty("spring.security.oauth2.client.registration.cognito.client-id"), env.getProperty("spring.security.oauth2.client.registration.cognito.client-secret"));
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("code", (String)payload.get("code"));
        body.add("client_id", env.getProperty("spring.security.oauth2.client.registration.cognito.client-id"));
        body.add("client_secret", env.getProperty("spring.security.oauth2.client.registration.cognito.client-secret"));
        body.add("grant_type", "authorization_code");
        body.add("redirect_uri", "http://localhost:5173");

        HttpEntity<MultiValueMap<String,String>> httpRequest = new HttpEntity<MultiValueMap<String,String>>(body, header);

        LinkedHashMap<String,String> response = restTemplate.postForObject(awsAuthOUrl, httpRequest, LinkedHashMap.class);
        //Return JWT to client
        output.put("response", response);
        return output;
    }

    @PostMapping("/api/authTest")
    public Map<String, Object> authTest(@RequestBody Map<String, Object> payload, HttpServletRequest request)
    {
        HashMap<String, Object> output = new HashMap<String, Object>();

        output.put("Message", "Authed");

        return output;
    }
}
