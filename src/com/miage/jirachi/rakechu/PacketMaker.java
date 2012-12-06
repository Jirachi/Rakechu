package com.miage.jirachi.rakechu;

public class PacketMaker {
	// SMSG_BOOTME
	public static Packet makeBootMe(long networkId) {
		Packet packet = new Packet();
		packet.opcode = Opcodes.SMSG_BOOTME;
		
		BitStream data = new BitStream();
		data.write(networkId);
		
		packet.data = data.getBytesP();
		
		return packet;
	}
	
	// SMSG_PLAYER_CONNECT
	public static Packet makePlayerConnect(long networkId) {
		Packet packet = new Packet();	
		packet.opcode = Opcodes.SMSG_PLAYER_CONNECT;
		
		BitStream data = new BitStream();
		data.write(networkId);
		
		packet.data = data.getBytesP();
		
		return packet;
	}
	
	// SMSG_PLAYER_EXISTING
	public static Packet makePlayerExisting(long networkId, int posX, int posY) {
		Packet packet = new Packet();
		packet.opcode = Opcodes.SMSG_PLAYER_EXISTING;
		
		BitStream data = new BitStream();
		data.write(networkId);
		data.write(posX);
		data.write(posY);
		
		packet.data = data.getBytesP();
		
		return packet;
	}
	
	// SMSG_MOVE_...
    public static Packet makeMovePacket(short direction, long characterID) {
    	Packet packet = new Packet();
        
        switch (direction) {
        case Character.DIRECTION_STOP:
            packet.opcode = Opcodes.SMSG_MOVE_STOP;
            break;
            
        case Character.DIRECTION_RIGHT:
        	packet.opcode = Opcodes.SMSG_MOVE_RIGHT;
            break;
            
        case Character.DIRECTION_LEFT:
        	packet.opcode = Opcodes.SMSG_MOVE_LEFT;
            break;
        }
        
        BitStream data = new BitStream();
        data.write(characterID);
        packet.data = data.getBytesP();
        
        return packet;
    }
    
    public static Packet setHealthPacket(long networkId, int health){
    	Packet packet = new Packet();
    	packet.opcode = Opcodes.SMSG_SET_HEALTH;
    	BitStream data = new BitStream();
    	data.write(networkId);
    	data.write(health);
    	
    	packet.data = data.getBytesP();
    	
    	return packet;
    }

}
