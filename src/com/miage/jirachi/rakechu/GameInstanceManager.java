package com.miage.jirachi.rakechu;


public class GameInstanceManager {
	private static GameInstanceManager mSingleton = null;
	
	public static GameInstanceManager getInstance() {
		if (mSingleton == null) {
			mSingleton = new GameInstanceManager();
		}
		
		return mSingleton;
	}

}
