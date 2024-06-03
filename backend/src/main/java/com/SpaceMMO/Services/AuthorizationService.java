package com.SpaceMMO.Services;

import com.SpaceMMO.UserManagement.UserAccount;
import com.SpaceMMO.UserManagement.UserAccountRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthorizationService
{
    @Autowired
    private UserAccountRepository userAccountRepository;


    public UserAccount getAccountFromToken(HttpServletRequest request)
    {
        return userAccountRepository.findByUsername((String)getToken(request).get("username"));
    }

    public UserAccount getAccountFromToken(String token)
    {
        String[] decoded = decodeToken(token);
        return userAccountRepository.findByUsername((String)jsonToMap(decoded[1]).get("username"));
    }


    public Map<String, Object> getToken(HttpServletRequest request)
    {
        String authHeader = request.getHeader("Authorization");
        authHeader = authHeader.replace("Bearer ", "");

        //System.out.println(decodeToken(authHeader));

        String[] decoded = decodeToken(authHeader);
        return jsonToMap(decoded[1]);
    }

    public Map<String, Object> getToken(String token)
    {
        String[] decoded = decodeToken(token);
        return jsonToMap(decoded[1]);
    }

    public void printToken(HttpServletRequest request)
    {

        String authHeader = request.getHeader("Authorization");
        authHeader = authHeader.replace("Bearer ", "");

        //System.out.println(decodeToken(authHeader));

        String[] decoded = decodeToken(authHeader);
        for(int i = 0; i < decoded.length; i++)
        {
            System.out.println(decoded[i]);
        }

        System.out.println(jsonToMap(decoded[1]).get("username"));
    }

    public String[] decodeToken(String token)
    {
        //byte[] decodedToken = Base64.getDecoder().decode(token);
        String[] tokenParts = token.split("\\.", 0);

        String[] decodedToken = new String[2];
        for(int i = 0; i < 2; i++)
        {
            decodedToken[i] = new String(Base64.getDecoder().decode(tokenParts[i]));
        }

        return decodedToken;
    }

    public Map<String, Object> jsonToMap(String json)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            TypeReference<HashMap<String, Object>> typeReference = new TypeReference<HashMap<String, Object>>() {
            };
            return mapper.readValue(json, typeReference);
        }
        catch(Exception e)
        {
            throw new RuntimeException("Couldnt parse Json:" + json, e);
        }
    }
}


