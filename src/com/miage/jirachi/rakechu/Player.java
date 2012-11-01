package com.miage.jirachi.rakechu;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Player extends Character {
    private Socket mNetworkSocket;
    private InputStream mNetworkInput;
    private OutputStream mNetworkOutput;
    
    public Player(Socket _socket) {
        mNetworkSocket = _socket;
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
}
