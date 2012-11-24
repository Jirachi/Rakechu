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
    public void handleBootMe(Player source) {
    	source.sendPacket(PacketMaker.makeBootMe(source.getNetworkId()));
    }
    
    public void handleMovePacket(Player source, short direction) {
        source.setMoveDirection(direction);
    }
    
    public void handleSyncPosition(Player source, BitStream data) {
        source.setPosition(data.readFloat(),data.readFloat());
    }
}
