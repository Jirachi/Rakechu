package com.miage.jirachi.rakechu;

import java.util.ArrayList;

/**
 * Represents an instance of a game level, with all the entities inside
 * 
 * @author Guillaume Lesniak
 *
 */
public class GameInstance {
	/**
	 * List of states the players can be in, while being in this Game Instance
	 */
	enum GameState {
		// Instance waits on both players to join
		GAME_STATE_PENDING_JOIN,
		
		// Instance waits on both players to finish loading
		GAME_STATE_PENDING_LOADING,
		
		// Instance is handling levels hub
		GAME_STATE_LEVELS_HUB,
		
		// Instance is currently playing a level
		GAME_STATE_INGAME,
		
		// Instance's level is done, leaderboard displaying
		GAME_STATE_LEVEL_END
	}
	
	/**
	 * Unique ID number of this game instance
	 */
	private long mIdentifier;
	
	/**
	 * Current instance's status
	 */
	private GameState mStatus;
	
	private ArrayList<Player> mPlayers;
	
	
	/**
	 * Default constructor
	 * @param id Instance unique ID
	 */
	public GameInstance(long id) {
		mIdentifier = id;
		mPlayers = new ArrayList<Player>();
	}
	
	public long getIdentifier() {
	    return mIdentifier;
	}
	
	public GameState getCurrentState() {
	    return mStatus;
	}
	
	public void addPlayer(Player p) {
	    p.setGameInstance(this);
	    
	    // Notify other players that this player joined the instance
		sendPacket(PacketMaker.makePlayerConnect(p.getNetworkId()), p);
	    
	    // Notify the newborn of other players in the instance :3
	    for (int i = 0; i < mPlayers.size(); i++) {
	    	Player ex = mPlayers.get(i);
	    	p.sendPacket(PacketMaker.makePlayerExisting(ex.getNetworkId(), ex.getPosition().x, ex.getPosition().y));
	    }
	    
	    mPlayers.add(p);
	}
	
	public void removePlayer(Player p) {
		mPlayers.remove(p);
	}
	
	public void sendPacket(Packet data, Player avoid) {
	    for (int i = 0; i < mPlayers.size(); i++) {
	        Player p = mPlayers.get(i);
	        if (p == avoid)
	            continue;
	        
	        p.sendPacket(data);
	    }
	}
}
