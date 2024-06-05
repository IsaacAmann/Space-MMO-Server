package com.SpaceMMO.UserManagement;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.security.core.parameters.P;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

@Entity
public class UserAccount
{
    public enum UserRole
    {
        USER,
        ADMIN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    public String username;

    public UserRole userRole;

    //Indicates whether or not player is currently connected to game session
    public boolean inGame;

    //Current game session token used to establish websocket connection
    private byte[] gameSessionToken;

    public UserAccount(String username, UserRole userRole)
    {
        this.username = username;
        this.userRole = userRole;
        inGame = false;
        gameSessionToken = new byte[16];

    }

    public UserAccount()
    {
        userRole = UserRole.USER;
        username = null;
        inGame = false;
        gameSessionToken = new byte[16];
    }

    //Return encoded session token
    public String getEncodedGameToken()
    {
        Base64.Encoder encoder = Base64.getUrlEncoder();

        return (encoder.encodeToString(gameSessionToken));
    }

    //Check if a given Game session Token is valid
    public boolean isValidGameToken(String token)
    {
        boolean output = false;
        //Decode token
        Base64.Decoder decoder = Base64.getUrlDecoder();
        byte[] tokenBytes = decoder.decode(token);
        //Compare passed token to stored token
        if(Arrays.equals(tokenBytes, this.gameSessionToken))
        {
            output = true;
        }

        return output;
    }

    public void generateGameToken()
    {
        SecureRandom tokenGen = new SecureRandom();
        tokenGen.nextBytes(this.gameSessionToken);
    }
}
