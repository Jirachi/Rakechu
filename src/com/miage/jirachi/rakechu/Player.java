package com.miage.jirachi.rakechu;

import com.esotericsoftware.kryonet.Connection;

public class Player extends Character {
    private Connection mNetworkConn;

    public Player(Connection _socket) {
    	mNetworkConn = _socket;
        
    }

    @Override
    public void setMoveDirection(short direction) {
        super.setMoveDirection(direction);
        
        System.out.println("The player id " + mNetworkId + " moved");
        
        Packet reply = PacketMaker.makeMovePacket(direction, mNetworkId);
        mGameInstance.sendPacket(reply, this);
    }
    
    public void sendPacket(Packet packet) {
    	if (packet == null) {
    		ServerController.LOG.error("PACKET IS NULL!!!!!!");
    		return;
    	}
    	if (packet.data == null) {
    		ServerController.LOG.error("PACKET DATA IS NULL!!!!!!");
    		return;
    	}
    	mNetworkConn.sendTCP(packet);
    }
}
