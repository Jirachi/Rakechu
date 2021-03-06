package com.miage.jirachi.rakechu;

/**
 * Liste des identifiants de paquets r�seau.
 * 
 * Il y a un identifiant CMSG pour les requ�tes envoy�es par le client,
 * et un identifiant SMSG correspondant (si n�cessaire) pour la r�ponse
 * serveur.
 *
 */
public class Opcodes {
    // Le client demande d'�tre boot� :3 (recevoir les informations g�n�rales)
    public final static short CMSG_BOOTME = 1;
    
    // Le serveur renvoie les informations g�n�rales de la partie
    public final static short SMSG_BOOTME = 2;
    
    // Le serveur signale qu'un joueur s'est connect�
    public final static short SMSG_PLAYER_CONNECT = 3;
    
    // Le serveur signale qu'un joueur �tait d�j� connect� lors du join
    public final static short SMSG_PLAYER_EXISTING = 4;
    
    // Paquets/r�ponses de mouvement
    public final static short CMSG_MOVE_LEFT    = 5;
    public final static short SMSG_MOVE_LEFT    = 6;
    public final static short CMSG_MOVE_RIGHT   = 7;
    public final static short SMSG_MOVE_RIGHT   = 8;
    public final static short CMSG_MOVE_STOP    = 9;
    public final static short SMSG_MOVE_STOP    = 10;
    
    // Paquet/r�ponse de saut
    public final static short CMSG_MOVE_JUMP    = 11;
    public final static short SMSG_MOVE_JUMP    = 12;
    
    // Synchro de position
    public final static short CMSG_SYNC_POSITION = 13;
    public final static short SMSG_SYNC_POSITION = 14;

    public final static short SMSG_SET_HEALTH = 15;
    
    // Saut
    public final static short CMSG_JUMP = 16;
    public final static short SMSG_JUMP = 17;
    
    // GameObjects
    public final static short SMSG_SPAWN_GAMEOBJECT = 18;
    public final static short SMSG_GAMEOBJECT_ANIMATE = 19;
    public final static short SMSG_GAMEOBJECT_MOVE = 20;
    public final static short SMSG_GAMEOBJECT_FORCE_POSITION = 21;
    
    public final static short CMSG_USE_GAMEOBJECT = 22;
    public final static short SMSG_GAMEOBJECT_CHANGE_PHYSICS = 23;
    
    // Combat
    public final static short CMSG_FIGHT = 24;
    public final static short SMSG_FIGHT = 25;
    public final static short SMSG_HIT = 26;
}

