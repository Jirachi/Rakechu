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
	
	/**
	 * Renvoie la distance exacte entre ce vecteur et le vecteur passe en parametre
	 * @note La racine carree est couteuse en temps CPU, utiliser squaredDistance partout
	 * ou c'est possible.
	 * @param pos
	 * @return
	 */
	public float distance(Vector2 pos) {
	    return (float)Math.sqrt(squaredDistance(pos));
	}
	
	/**
	 * Renvoie la longueur du vecteur
	 */
	public float length() {
	    return (float)Math.sqrt(x * x + y * y);
	}
	
	/**
	 * Renvoie une copie normalisee de ce vecteur (= ramene a l'unite)
	 * @return
	 */
	public Vector2 normalizedCopy() {
	    Vector2 normal = new Vector2();
	    float length = length();
	    normal.x = x / length;
	    normal.y = y / length;
	    
	    return normal;
	}
	
	/**
	 * Soustrait ce vecteur au vecteur en parametre
	 * @param pos
	 * @return
	 */
	public Vector2 sub(Vector2 pos) {
	    Vector2 subbed = new Vector2();
	    subbed.x = x - pos.x;
	    subbed.y = y - pos.y;
	    return subbed;
	}
	
	/**
	 * Multiplie les membres du vecteur par le facteur
	 */
	public void mulSelf(float scale) {
	    x *= scale;
	    y *= scale;
	}
	
	/**
	 * Ajoute des coordonnees a ce vector
	 */
	public void addSelf(float x, float y) {
	    this.x += x;
	    this.y += y;
	}
}
