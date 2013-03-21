package com.miage.jirachi.rakechu;

import java.util.List;

public class Character {
    // == Constantes
    public final static short DIRECTION_STOP = 0;
    public final static short DIRECTION_LEFT = 1;
    public final static short DIRECTION_RIGHT = 2;
    
    // == Attributs
    private static long mFreeNetworkId = 1;
    
    protected short mMoveDirection = DIRECTION_STOP;
    protected short mIdleDirection = DIRECTION_RIGHT;
    protected long mNetworkId;
    protected GameInstance mGameInstance;
    protected Vector2 mPosition;
    protected int mHealth;
    protected String mTexture;
    
    // == Constructeur
    public Character(String texture) {
        mNetworkId = mFreeNetworkId++;
        mPosition = new Vector2();
        mTexture = texture;
        mHealth = 100;
    }
    
    // == Methodes
    /**
     * Definit l'instance de jeu dans laquelle le joueur se trouve
     * @param instance
     */
    public void setGameInstance(GameInstance instance) {
        mGameInstance = instance;
    }
    
    /**
     * Retourne l'instance dans laquelle se trouve le joueur
     */
    public GameInstance getGameInstance() {
        return mGameInstance;
    }
    
    /**
     * Definit la direction dans laquelle le personnage se deplace et le signale
     * aux joueurs
     * @param direction
     */
    public void setMoveDirection(short direction) {
        mMoveDirection = direction;
        
        if (direction != DIRECTION_STOP)
            mIdleDirection = direction;
        
        Packet reply = PacketMaker.makeMovePacket(direction, mNetworkId);
        mGameInstance.sendPacket(reply, this);
    }
    
    /**
     * Retourne l'id reseau du personnage
     * @return
     */
    public long getNetworkId() {
    	return mNetworkId;
    }
    
    /**
     * @return La position du personnage
     */
    public Vector2 getPosition() {
    	return mPosition;
    }
    
    /**
     * Definit la position du personnage, et la signale aux joueurs
     * @param x
     * @param y
     */
    public void setPosition(float x, float y) {
        mPosition.x = x;
        mPosition.y = y;
        
        Packet reply = PacketMaker.makeSyncPositionPacket(mNetworkId, x,y);
        mGameInstance.sendPacketUnreliable(reply, this);
    }

    /**
     * Definit la vie du personnage, et la signale aux joueurs
     * @param health
     */
    public void setHealth(int health) {
    	mHealth = health;
    	Packet reply = PacketMaker.makeSetHealthPacket(mNetworkId, mHealth);
    	mGameInstance.sendPacket(reply, null);
    }

    /**
     * Fais sauter le personnage, et le signale aux joueurs
     */
    public void jump() {
        Packet reply = PacketMaker.makeJumpPacket(mNetworkId);
        mGameInstance.sendPacket(reply, this);
    }
    
    /**
     * Retourne la texture utilisŽe par le personnage
     */
    public String getTexture() {
        return mTexture;
    }
    
    /**
     * Recoit un coup du personnage spŽcifiŽ et retire le montant de degats
     */
    public void hit(Character src, int amount) {
        if (amount > mHealth) {
            setHealth(0);
        } else {
            setHealth(mHealth - amount);
        }
        
        Packet notify = PacketMaker.makeHitPacket(mNetworkId, amount);
        mGameInstance.sendPacket(notify, null);
    }
    
    /**
     * Fait frapper les perso les plus proche
     */
    public void fight() {
        List<Character> sightChars = mGameInstance.getCharactersNear(this, 25*25);
        for (int i = 0; i < sightChars.size(); i++) {
            Character c = sightChars.get(i);
            
            // filter direction
            if ((mIdleDirection == DIRECTION_RIGHT && c.getPosition().x > this.getPosition().x)
                    || (mIdleDirection == DIRECTION_LEFT && c.getPosition().x < this.getPosition().x)) {
                c.hit(this, 10);
            }           
        }
        
        Packet playAnimation = PacketMaker.makeFightPacket(mNetworkId);
        mGameInstance.sendPacket(playAnimation, this);
    }
}