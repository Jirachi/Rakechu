package com.miage.jirachi.rakechu;

import java.util.List;

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
    
    public void handleJump(Player source) {
        source.jump();
    }
    
    public void handleUseGameObject(Player source) {
        // On cherche si on a un objet a portee du joueur
        List<GameObject> objects = source.getGameInstance().getObjectsNear(source, 100);
        
        // On prend le premier objet qu'on trouve
        // TODO: Trier par distance et prendre le plus proche?
        if (objects.size() > 0) {
            objects.get(0).use(source);
        }
    }
}
