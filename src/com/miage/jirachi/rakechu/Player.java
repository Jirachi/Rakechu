package com.miage.jirachi.rakechu;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Player extends Character {
    private Socket mNetworkSocket;
    private InputStream mNetworkInput;
    private OutputStream mNetworkOutput;

    public Player(Socket _socket) {
        mNetworkSocket = _socket;
        try {
            mNetworkInput = _socket.getInputStream();
            mNetworkOutput = _socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Socket getNetworkSocket() {
        return mNetworkSocket;
    }

    public InputStream getNetworkInput() {
        return mNetworkInput;
    }

    public OutputStream getNetworkOutput() {
        return mNetworkOutput;
    }

    @Override
    public void setMoveDirection(short direction) {
        super.setMoveDirection(direction);
        BitStream reply = PacketMaker.makeMovePacket(direction, mNetworkId);
        
        try {
            mGameInstance.sendPacket(reply, this);
        } catch (IOException e) {
            ServerController.LOG.error("Unable to transmit MOVE reply package!");
            e.printStackTrace();
        }
    }
}
