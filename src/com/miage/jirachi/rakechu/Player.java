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
        
        Packet reply = PacketMaker.makeMovePacket(direction, mNetworkId);
        mGameInstance.sendPacket(reply, this);
    }
    
    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        
        Packet reply = PacketMaker.makeSyncPositionPacket(mNetworkId, x,y);
        mGameInstance.sendPacketUnreliable(reply, this);
    }
    
    @Override
    public void jump() {
        super.jump();
        
        Packet reply = PacketMaker.makeJumpPacket(mNetworkId);
        mGameInstance.sendPacket(reply, this);
    }
    
    /**
     * Envoie un packet de facon sure au joueur
     * @param packet
     */
    public void sendPacket(Packet packet) {
    	if (packet == null) {
    		ServerController.LOG.error("Packet is NULL !");
    		return;
    	}
    	if (packet.data == null) {
    		ServerController.LOG.error("Packet data is NULL! Opcode is " + packet.opcode);
    		return;
    	}
    	
    	mNetworkConn.sendTCP(packet);
    }
    
    /**
     * Envoie un packet rapidement, mais n'est pas sur d'arriver
     * @param packet
     */
    public void sendPacketUnreliable(Packet packet) {
        if (packet == null) {
            ServerController.LOG.error("Packet is NULL !");
            return;
        }
        if (packet.data == null) {
            ServerController.LOG.error("Packet data is NULL! Opcode is " + packet.opcode);
            return;
        }
        
        mNetworkConn.sendUDP(packet);
    }
}
