package com.miage.jirachi.rakechu;

public class GameObjectManager {
    private static GameObjectManager mSingleton = null;
    
    private long mFreeNetworkId = 1;
    
    public static GameObjectManager getInstance() {
        if (mSingleton == null) {
            mSingleton = new GameObjectManager();
        }
        
        return mSingleton;
    }
    
    public GameObject createObject(String resName, float x, float y, int physicsType) {
        GameObject object = new GameObject(mFreeNetworkId++, resName, physicsType, x, y);
        return object;
    }
}
