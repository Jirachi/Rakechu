package com.miage.jirachi.rakechu;

public class Vector2 {
	public float x;
	public float y;
	
	public Vector2() {
	    
	}
	
	public Vector2(float x, float y) {
	    this.x = x;
	    this.y = y;
	}
	
	/**
	 * Renvoie la distance au carre entre ce vecteur et le vecteur passé en paramètre
	 * @param pos
	 * @return Distance
	 */
	public float squaredDistance(Vector2 pos) {
	    return (float) ( Math.pow(pos.x - x, 2.0) + Math.pow(pos.y - y, 2.0) );
	}
}
