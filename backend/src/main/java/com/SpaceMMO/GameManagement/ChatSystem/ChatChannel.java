package com.SpaceMMO.GameManagement.ChatSystem;

import com.SpaceMMO.GameManagement.SectorSystem.Player;
import com.SpaceMMO.GameManagement.SectorSystem.Sector;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ChatChannel
{
    ArrayList<Player> subscriberList;
    public boolean broadcastSector;
    public boolean broadcastGlobal;
    public Sector localSector;
    int channel;

    //Temporary thread safe storage for messages
    //Emptied and copied to arraylist for long term storage after being sent to clients
    public ConcurrentLinkedQueue<ChatMessage> messageQueue;

    //Stores all chat messages that have been sent to clients
    private ArrayList<ChatMessage> messages;

    public ChatChannel(int channel)
    {
        this.channel = channel;
        messages = new ArrayList<ChatMessage>();
        messageQueue = new ConcurrentLinkedQueue<ChatMessage>();
    }

    //Empties message queue and sends messages to subscriber list
    //Should only be called within the game loop thread, not thread safe
    public void sendMessages()
    {
        int currentMessages = messageQueue.size();
        //Case for global chat
        if(broadcastGlobal)
        {
            //Send to every connected client
        }
        //Case for sector chat
        else if(broadcastSector)
        {
            //Send to every client in the sector
            if(localSector != null)
            {
                for(int i = 0; i < currentMessages; i++)
                {
                    ChatMessage message = messageQueue.remove();
                    for(Player player : localSector.players)
                    {
                        localSector.serviceContainer.chatMessageHandlers.sendChatMessage(player, message);
                    }
                    messages.add(message);
                }
            }
            else
            {
                System.out.println("Chat channel " + channel + " local sector is null, that should not happen");
            }
        }
        //Default case to send only to players in the subscriber list
        else
        {
            //Send to every player in the subscriber list
            for(Player player : subscriberList)
            {
                for(int i = 0; i < currentMessages; i++)
                {
                    ChatMessage currentMessage = messageQueue.remove();

                    player.currentSector.serviceContainer.chatMessageHandlers.sendChatMessage(player, currentMessage);

                    messages.add(currentMessage);
                }
            }
        }
    }
}
