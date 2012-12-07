package com.miage.jirachi.rakechu;

import java.util.Iterator;
import java.util.List;

public class GameObject {
    public final static int PHYSICS_TYPE_STATIC = 1;
    public final static int PHYSICS_TYPE_DYNAMIC = 2;
    public final static int PHYSICS_TYPE_NO_COLLISION = 3;
    
    protected Vector2 mPosition;
    protected Vector2 mTargetPosition;
    protected String mResourceName;
    protected long mNetworkId;
    protected GameInstance mGameInstance;
    protected List<GameObjectAction> mUseActions;
    protected int mPhysicsType;
    
    /**
     * @param netId
     */
    public GameObject(long netId, String resName, int physicsType, float x, float y) {
        mPosition = new Vector2(x,y);
        mResourceName = resName;
        mNetworkId = netId;
        mPhysicsType = physicsType;
    }
    
    /**
     * Definit l'instance de jeu auquel appartient l'objet
     * @param instance
     */
    public void setGameInstance(GameInstance instance) {
        mGameInstance = instance;
    }
    
    /**
     * @param type
     */
    public void setPhysicsType(int type) {
        mPhysicsType = type;
    }
    
    public int getPhysicsType() {
        return mPhysicsType;
    }
    
    /**
     * @return Nom de la resource a afficher pour cet objet
     */
    public String getResourceName() {
        return mResourceName;
    }
    
    /**
     * @return l'ID reseau de l'objet
     */
    public long getNetworkId() {
        return mNetworkId;
    }
    
    /**
     * @return la position de l'objet
     */
    public Vector2 getPosition() {
        return mPosition;
    }
    
    /**
     * Deplace l'objet a l'emplacement specifie en un temps donne
     * @param x
     * @param y
     * @param time
     */
    public void moveTo(float x, float y, float time) {
        mTargetPosition = new Vector2(x,y);
        mGameInstance.sendPacket(PacketMaker.makeGameObjectMove(mNetworkId, x, y, time), null);
    }
    
    /**
     * Joue une animation
     * @param animation Nom de l'animation presente sur le client
     */
    public void playAnimation(String animation) {
        mGameInstance.sendPacket(PacketMaker.makeGameObjectAnimate(mNetworkId, animation), null);
    }
    
    /**
     * Met a jour l'objet
     * @param timeDelta Temps depuis le dernier update
     */
    public void update(float timeDelta) {
        if (mTargetPosition != null) {
            if (!mTargetPosition.equals(mPosition)) {
                // On veut aller quelque part et on y est pas, on y va :3
                // TODO
            }
        }
    }
    
    /**
     * Ajoute une action lorsque l'objet est utilise
     * @param act L'action
     */
    public void addUseAction(GameObjectAction act) {
        mUseActions.add(act);
    }
    
    /**
     * Lance les actions ajoutees par addUseAction
     * @param user
     */
    public void use(Character user) {
        Iterator<GameObjectAction> iter = mUseActions.iterator();
        
        while (iter.hasNext()) {
            GameObjectAction act = iter.next();
            act.useObject(user);
        }
    }
}
