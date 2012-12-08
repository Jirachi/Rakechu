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
	public static Packet makePlayerExisting(long networkId, float posX, float posY) {
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
    
    // SMSG_SET_HEALTH
    public static Packet makeSetHealthPacket(long networkId, int health){
    	Packet packet = new Packet();
    	packet.opcode = Opcodes.SMSG_SET_HEALTH;
    	BitStream data = new BitStream();
    	data.write(networkId);
    	data.write(health);
    	
    	packet.data = data.getBytesP();
    	
    	return packet;
    }

    // SMSG_SYNC_POSITION
    public static Packet makeSyncPositionPacket(long characterId, float x, float y) {
        Packet packet = new Packet();
        BitStream data = new BitStream();
        
        packet.opcode = Opcodes.SMSG_SYNC_POSITION;
        data.write(characterId);
        data.write(x);
        data.write(y);
        
        packet.data = data.getBytesP();
        return packet;
    }
    
    // SMSG_JUMP
    public static Packet makeJumpPacket(long characterId) {
        Packet packet = new Packet();
        BitStream data = new BitStream();
        
        packet.opcode = Opcodes.SMSG_JUMP;
        data.write(characterId);
        
        packet.data = data.getBytesP();
        return packet;
    }
    
    // SMSG_GAMEOBJECT_ANIMATE
    public static Packet makeGameObjectAnimate(long objectId, String animationName) {
        Packet packet = new Packet();
        BitStream data = new BitStream();
        
        packet.opcode = Opcodes.SMSG_GAMEOBJECT_ANIMATE;
        data.write(objectId);
        data.write(animationName);
        
        packet.data = data.getBytesP();
        return packet;
    }
    
    // SMSG_GAMEOBJECT_MOVE
    public static Packet makeGameObjectMove(long objectId, float targetX, float targetY, float time) {
        Packet packet = new Packet();
        BitStream data = new BitStream();
        
        packet.opcode = Opcodes.SMSG_GAMEOBJECT_MOVE;
        data.write(objectId);
        data.write(targetX);
        data.write(targetY);
        data.write(time);
        
        packet.data = data.getBytesP();
        return packet;
    }
    
    // SMSG_GAMEOBJECT_FORCE_POSITION
    public static Packet makeGameObjectForcePosition(long objectId, float x, float y) {
        Packet packet = new Packet();
        BitStream data = new BitStream();
        
        packet.opcode = Opcodes.SMSG_GAMEOBJECT_FORCE_POSITION;
        data.write(objectId);
        data.write(x);
        data.write(y);
        
        packet.data = data.getBytesP();
        return packet;
    }
    
    // SMSG_SPAWN_GAMEOBJECT
    public static Packet makeSpawnGameObject(long objectId, float x, float y, String resource, int physicsType) {
        Packet packet = new Packet();
        BitStream data = new BitStream();
        
        packet.opcode = Opcodes.SMSG_SPAWN_GAMEOBJECT;
        data.write(objectId);
        data.write(x);
        data.write(y);
        data.write(resource);
        data.write(physicsType);
        
        packet.data = data.getBytesP();
        return packet;
    }
}
