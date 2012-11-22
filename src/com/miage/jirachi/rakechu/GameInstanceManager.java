package com.miage.jirachi.rakechu;


public class GameInstanceManager {
	private static GameInstanceManager mSingleton = null;
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static GameInstanceManager getInstance() {
		if (mSingleton == null) {
			mSingleton = new GameInstanceManager();
		}
		
		return mSingleton;
	}

}
