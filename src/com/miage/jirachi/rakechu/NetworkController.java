package com.miage.jirachi.rakechu;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;


public class NetworkController {
    private static NetworkController mSingleton = null;
    
    //=============================================
    // INNER CLASS
    //=============================================
   
    
    //=============================================
    // MEMBERS
    //=============================================
    
    // There is a code behind this number. Will you find it?
    public final static int SERVER_PORT = 37153;
    private Map<Connection, Player> mPlayers = null;

    //=============================================
    // CONSTRUCTORS
    //=============================================
    /**
     * Renvoie l'instance unique de cette classe
     * @return Instance
     * @throws IOException 
     */
    public static NetworkController getInstance() throws IOException {
        if (mSingleton == null) {
            mSingleton = new NetworkController();
        }
       
        return mSingleton;
    }
    
    /**
     * Constructeur par dŽfaut
     */
    public NetworkController() throws IOException {
        ServerController.LOG.debug("Starting NetworkController...");
        
        mPlayers = new HashMap<Connection, Player>();
        
        
        final GameInstance testInstance = new GameInstance(1);
        
        Server server = new Server() {
        	protected Connection newConnection() {
        		Connection c = super.newConnection();
        		Player p = new Player(c);
        		
        		mPlayers.put(c, p);
        		return c;
        	}
        };
       
        
        server.start();
        server.bind(37153, 35173);
        
        Kryo kryo = server.getKryo();
        kryo.register(Packet.class);
        kryo.register(byte[].class);

        server.addListener(new Listener() {
        	public void received (Connection connection, Object object) {
    			Player p = mPlayers.get(connection);
    			
        		if (object instanceof Packet) {
        			Packet request = (Packet)object;

        			ServerController.LOG.debug("Received opcode: " + request.opcode);
        			
        			switch (request.opcode)
        			{
        			case Opcodes.CMSG_BOOTME:
        				testInstance.addPlayer(p);
        				PacketHandler.getInstance().handleBootMe(p);
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
        	}
        });

        
        ServerController.LOG.debug("Server socket open on ports 37153 (TCP) and 35173 (UDP)");
    }
    
    //=============================================
    // METHODS
    //=============================================
    /**
     * DŽmarre le thread gŽrant la boucle rŽseau
     */
    public void startLoop() {
        while (true) { try { Thread.sleep(1000); } catch (Exception e) { } }
    }
    
    /**
     * Arr�te le thread gŽrant la boucle rŽseau
     */
    public void stopLoop() {
        
    }
}
