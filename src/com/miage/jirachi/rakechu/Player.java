package com.miage.jirachi.rakechu;

import com.esotericsoftware.kryonet.Connection;

public class Player extends Character {
    private Connection mNetworkConn;

    public Player(Connection _socket, String _texture) {
        super(_texture);
    	mNetworkConn = _socket;
        
    }

    @Override
    public void setMoveDirection(short direction) {
        super.setMoveDirection(direction);
    }
    
    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
    }
    
    @Override
    public void jump() {
        super.jump();
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
        
        mNetworkConn.sendTCP(packet);
    }
}

