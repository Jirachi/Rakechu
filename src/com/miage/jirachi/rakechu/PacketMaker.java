package com.miage.jirachi.rakechu;

public class PacketMaker {
    public static BitStream makeMovePacket(short direction, long characterID) {
        BitStream packet = new BitStream();
        
        switch (direction) {
        case Character.DIRECTION_STOP:
            packet.write(Opcodes.SMSG_MOVE_STOP);
            break;
            
        case Character.DIRECTION_RIGHT:
            packet.write(Opcodes.SMSG_MOVE_RIGHT);
            break;
            
        case Character.DIRECTION_LEFT:
            packet.write(Opcodes.SMSG_MOVE_LEFT);
            break;
        }
        
        packet.write(characterID);
        return packet;
    }
}
