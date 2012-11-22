package com.miage.jirachi.rakechu;

public class PacketHandler {
    private static PacketHandler mSingleton = null;
    
    
    
    // == Constructeur
    public static PacketHandler getInstance() {
        if (mSingleton == null)
            mSingleton = new PacketHandler();
        
        return mSingleton;
    }
    
    
    // == Methodes
    public void handleMovePacket(Player source, short direction) {
        source.setMoveDirection(direction);
    }
}
