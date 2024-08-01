package com.SpaceMMO.GameManagement.WebSocketServer.GameNetworkingProtocol;

public class ProtocolConstants
{
    public static final byte PLAYER_INFO = 0x0;
    public static final byte SECTOR_JOIN = 0x1;
    public static final byte ERROR = 0x2;
    public static final byte JOIN_DEBUG = 0x3;
    public static final byte ENTITY_UPDATE = 0x4;
    public static final byte NEW_ENTITY_NOTIFICATION = 0x5;
    public static final byte PLAYER_SHIP_INPUT = 0x6;
    public static final byte LASER_AFFECT = 0x7;
    public static final byte ENTITY_DELETE = 0x8;
}
