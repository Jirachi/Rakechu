package com.miage.jirachi.rakechu;

public class Character {
    // == Constantes
    public final static short DIRECTION_STOP = 0;
    public final static short DIRECTION_LEFT = 1;
    public final static short DIRECTION_RIGHT = 2;
    
    // == Attributs
    private static long mFreeNetworkId = 0;
    
    protected short mMoveDirection = DIRECTION_STOP;
    protected long mNetworkId;
    protected GameInstance mGameInstance;
    
    // == Constructeur
    public Character() {
        mNetworkId = mFreeNetworkId++;
    }
    
    // == Methodes
    public void setGameInstance(GameInstance instance) {
        mGameInstance = instance;
    }
    
    
    public void setMoveDirection(short direction) {
        mMoveDirection = direction;
    }
}
