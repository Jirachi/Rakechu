package com.miage.jirachi.rakechu;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class NetworkController {
    private static NetworkController mSingleton = null;
    
    //=============================================
    // INNER CLASS
    //=============================================
    public class NetworkThread extends Thread {
        @Override
        public void run() {
            ServerController.LOG.info("Started network loop in thread");
            GameInstance testInstance = new GameInstance(0);
            
            // Run the network loop as long as the thread is not interrupted
            while (!isInterrupted()) {
                // Check if we have new incoming connections
                try {
                    // Try to accept a new TCP client
                    Socket client = mSocket.accept();
                    client.setTcpNoDelay(true);
                    
                    // Create a new player structure for this new friend
                    Player player = new Player(client);
                    mPlayers.add(player);
                    
                    ServerController.LOG.debug("New player connected: " + client.getInetAddress().getHostAddress());
                }
                catch (SocketTimeoutException ex) {
                    // If we don't have a new client, check if they have messages for us
                    for (int i = 0; i < mPlayers.size(); i++) {
                        Player p = mPlayers.get(i);
                        InputStream in = p.getNetworkInput();
                        
                        // Try to read data from player inputstream
                        try {
                            if (in == null) continue;
                            int bytesToRead = in.available();
                            
                            // The player has data for us!
                            if (bytesToRead > 0) {
                                byte[] data = new byte[bytesToRead];
                                in.read(data);
                                
                                ServerController.LOG.debug("Data received: " + new String(data));
                                
                                // Create a BitStream, read the opcode, do things
                                BitStream datastream = new BitStream(data);
                                short opcode = datastream.readShort();
                                
                                // Process the packet
                                switch (opcode) {
                                case Opcodes.CMSG_BOOTME:
                                    p.setGameInstance(testInstance);
                                    break;
                                    
                                case Opcodes.CMSG_MOVE_LEFT:
                                    PacketHandler.getInstance().handleMovePacket(p, Character.DIRECTION_LEFT);
                                    break;
                                    
                                case Opcodes.CMSG_MOVE_RIGHT:
                                    PacketHandler.getInstance().handleMovePacket(p, Character.DIRECTION_RIGHT);
                                    break;
                                    
                                case Opcodes.CMSG_MOVE_STOP:
                                    PacketHandler.getInstance().handleMovePacket(p, Character.DIRECTION_STOP);
                                    break;
                                }
                            }
                        } catch (IOException e) {
                            ServerController.LOG.error("IOException during player processing: " + e.getMessage());
                        }
                    }
                } catch (IOException e) {
                    ServerController.LOG.error("Error while getting a new network client (ignored): " + e.getMessage());
                }
            }
            
            ServerController.LOG.info("NetworkThread interrupted");
        }
    }
    
    
    //=============================================
    // MEMBERS
    //=============================================
    
    // There is a code behind this number. Will you find it?
    public final static int SERVER_PORT = 37153;
    private ServerSocket mSocket = null;
    private List<Player> mPlayers = null;
    private NetworkThread mThread = null;
    
    //=============================================
    // CONSTRUCTORS
    //=============================================
    /**
     * Renvoie l'instance unique de cette classe
     * @return Instance
     */
    public static NetworkController getInstance() {
        if (mSingleton == null) {
            mSingleton = new NetworkController();
        }
        
        return mSingleton;
    }
    
    /**
     * Constructeur par défaut
     */
    public NetworkController() {
        ServerController.LOG.debug("Starting NetworkController...");
        
        // Create the server socket
        try {
            mSocket = new ServerSocket(SERVER_PORT);
            mSocket.setSoTimeout(1);
            mSocket.setPerformancePreferences(1, 1, 1000);
        } catch (IOException e) {
            ServerController.LOG.fatal("Cannot start server socket! Is port already in use?");
            ServerController.LOG.fatal(e.getMessage());
            System.exit(-1);
        }
        
        ServerController.LOG.debug("Server socket open on port " + SERVER_PORT);
        
        // Create an empty players list
        mPlayers = new ArrayList<Player>();
    }
    
    //=============================================
    // METHODS
    //=============================================
    /**
     * Démarre le thread gérant la boucle réseau
     */
    public void startLoop() {
        mThread = new NetworkThread();
        mThread.start();
    }
    
    /**
     * Arrête le thread gérant la boucle réseau
     */
    public void stopLoop() {
        if (mThread == null) {
            ServerController.LOG.error("Tried to stop network loop, but thread is null!");
            return;
        }
        
        mThread.interrupt();
    }
}
