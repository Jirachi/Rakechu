package com.miage.jirachi.rakechu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
	
	private ArrayList<Character> mCharacters;
	private ArrayList<GameObject> mObjects;
	
	
	/**
	 * Default constructor
	 * @param id Instance unique ID
	 */
	public GameInstance(long id) {
		mIdentifier = id;
		mCharacters = new ArrayList<Character>();
		mObjects = new ArrayList<GameObject>();
		
		// ==============
		// TEST =========
		// ==============
		// Add a dummy monster to test AI and server-side characters
		Monster monsta = new Monster("VonFlamby");
		addCharacter(monsta);
		monsta.setPosition(1094.4509f, -60.645046f);
		monsta.setMovementBoundaries(930, 1120);
	}
	
	/**
	 * @return L'identifiant Id de l'instance
	 */
	public long getIdentifier() {
	    return mIdentifier;
	}
	
	/**
	 * 
	 * @return L'etat de jeu de l'instance (voir GameState)
	 */
	public GameState getCurrentState() {
	    return mStatus;
	}
	
	/**
     * Ajoute un personnage a l'instance. Les joueurs seront notifies,
     * et l'instance du personnage sera definie a this.
     * @param p Le personnage a ajouter
     */
	public void addCharacter(Character p) {
	    p.setGameInstance(this);

	    // Notify other players that this player joined the instance
	    sendPacket(PacketMaker.makePlayerConnect(p.getNetworkId(), p.getTexture()), p);

	    // Notify the newborn of other players in the instance :3
	    for (int i = 0; i < mCharacters.size(); i++) {
	        Character cha = mCharacters.get(i);
	        ((Player)p).sendPacket(PacketMaker.makePlayerExisting(cha.getNetworkId(), cha.getPosition().x, cha.getPosition().y, cha.getTexture()));
	    }

	    mCharacters.add(p);
	}
	
	/**
	 * Ajoute un joueur a l'instance. Les autres joueurs seront notifies,
	 * et l'instance du joueur sera definie a this.
	 * @param p Le joueur a ajouter
	 */
	public void addPlayer(Player p) {
	    addCharacter(p);
	}
	
	/**
	 * Supprime un joueur de l'instance
	 * TODO: Signaler la deconnexion
	 * @param p
	 */
	public void removePlayer(Player p) {
	    mCharacters.remove(p);
	}
	
	/**
	 * Ajoute un objet a l'instance. Les joueurs seront notifies,
	 * et l'instance de l'objet sera definie a this.
	 * @param go L'objet a ajouter
	 */
	public void addGameObject(GameObject go) {
	    go.setGameInstance(this);
	    
	    // On signale aux joueurs la creation de cet objet
	    sendPacket(PacketMaker.makeSpawnGameObjectPacket(go.getNetworkId(), 
	            go.getPosition().x, go.getPosition().y, 
	            go.getResourceName(), go.getPhysicsType()), null);
	    
	    mObjects.add(go);
	}
	
	
	/**
	 * Envoie un paquet de facon sure a tous les joueurs de l'instance (excepte
	 * 'avoid' si il est precise)
	 * @param data Le packet a envoyer
	 * @param avoid Le joueur auquel ne pas envoyer le paquet (peut etre null)
	 */
	public void sendPacket(Packet data, Character avoid) {
	    for (int i = 0; i < mCharacters.size(); i++) {
	        Character c = mCharacters.get(i);
	        if (c == avoid)
                continue;
	        
	        if (c instanceof Player) {
	            Player p = (Player)c;
	            p.sendPacket(data);
	        }
	    }
	}
	
	/**
	 * Envoie un paquet de facon incertaine a tous les joueurs de l'instance
	 * (excepte 'avoid' s'il n'est pas null)
	 * @param data Le packet a envoyer
	 * @param avoid Le joueur auquel ne pas envoyer le paquet (peut etre null)
	 */
	public void sendPacketUnreliable(Packet data, Character avoid) {
	    for (int i = 0; i < mCharacters.size(); i++) {
	        Character c = mCharacters.get(i);
            if (c == avoid)
                continue;
            
            if (c instanceof Player) {
                Player p = (Player)c;
                p.sendPacketUnreliable(data);
            }
        }
	}
	
	/**
	 * Met a jour l'instance et les objets qu'elle contient
	 * @param timeDelta
	 */
	public void update(float timeDelta) {
	    // Mise ˆ jour des objets
	    Iterator<GameObject> iter_g = mObjects.iterator();
	    
	    while (iter_g.hasNext()) {
	        GameObject object = iter_g.next();
	        object.update(timeDelta);
	    }
	    
	    // Mise ˆ jour des monstres
	    Iterator<Character> iter_m = mCharacters.iterator();
	    
	    while (iter_m.hasNext()) {
	        Character c = iter_m.next();
	        if (c instanceof Monster) {
	            Monster m = (Monster)c;
	            m.update(timeDelta);
	        }
	    }
	}
	
	/**
	 * Retourne la liste des personnages a portee
	 * @note distance au carre
	 */
	List<Character> getCharactersNear(Character src, float dist) {
	    List<Character> charsList = new ArrayList<Character>();
	    
	    Iterator<Character> iter = mCharacters.iterator();
	    
	    while (iter.hasNext()) {
	        Character c = iter.next();
	        System.out.println("Distance: " + c.getPosition().squaredDistance(src.getPosition()) + " <= " + dist);
	        if (src != c && c.getPosition().squaredDistance(src.getPosition()) <= dist) {
                charsList.add(c);
            }
	    }
	    
	    return charsList;
	}
	
	/**
	 * Retourne la liste des objets a portee 
	 * @note distance au carre
	 */
	List<GameObject> getObjectsNear(Character src, float dist) {
	    List<GameObject> objectsList = new ArrayList<GameObject>();
	    
	    // On cherche parmis les objets
	    Iterator<GameObject> iter = mObjects.iterator();
        
        while (iter.hasNext()) {
            GameObject object = iter.next();
            if (object.getPosition().squaredDistance(src.getPosition()) <= dist) {
                objectsList.add(object);
            }
        }
	    
	    return objectsList;
	}
}
