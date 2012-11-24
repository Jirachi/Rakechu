package com.miage.jirachi.rakechu;

public class Character {
    // == Constantes
    public final static short DIRECTION_STOP = 0;
    public final static short DIRECTION_LEFT = 1;
    public final static short DIRECTION_RIGHT = 2;
    
    // == Attributs
    private static long mFreeNetworkId = 1;
    
    protected short mMoveDirection = DIRECTION_STOP;
    protected long mNetworkId;
    protected GameInstance mGameInstance;
    protected Vector2 mPosition;
    
    // == Constructeur
    public Character() {
        mNetworkId = mFreeNetworkId++;
        mPosition = new Vector2();
    }
    
    // == Methodes
    public void setGameInstance(GameInstance instance) {
        mGameInstance = instance;
    }
    
    
    public void setMoveDirection(short direction) {
        mMoveDirection = direction;
    }
    
    public long getNetworkId() {
    	return mNetworkId;
    }
    
    public Vector2 getPosition() {
    	return mPosition;
    }
    
    public void setPosition(float x, float y) {
        mPosition.x = x;
        mPosition.y = y;
    }
}
