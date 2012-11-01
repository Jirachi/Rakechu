package com.miage.jirachi.rakechu;

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
	
	
	/**
	 * Default constructor
	 * @param id Instance unique ID
	 */
	public GameInstance(long id) {
		mIdentifier = id;
	}
	
	public long getIdentifier() {
	    return mIdentifier;
	}
	
	public GameState getCurrentState() {
	    return mStatus;
	}
}
