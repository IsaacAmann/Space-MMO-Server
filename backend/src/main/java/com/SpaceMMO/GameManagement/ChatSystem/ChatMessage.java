package com.SpaceMMO.GameManagement.ChatSystem;

import com.SpaceMMO.GameManagement.SectorSystem.Player;

import java.util.Date;

public class ChatMessage
{
    public final static int MAX_MESSAGE_LENGTH = 500;

    public Player sender;
    public int channel;
    public Date timeStamp;
    public String message;

    public ChatMessage(Player sender, int channel, String message)
    {
        timeStamp = new Date();
        this.channel = channel;
        this.sender = sender;
        this.message = message;
    }

    //Return a string formated like: Player <TIMESTAMP>: <message>
    public String getFormattedString()
    {
        String output = "";

        //Append username
        if(sender != null)
        {
            output += sender.account.username;
        }
        else
        {
            output += "Console";
        }

        //Append time stamp
        output += " " + timeStamp.toString();

        //Append message
        output += ": " + message;

        return output;
    }
}


