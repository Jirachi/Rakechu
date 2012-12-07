package com.miage.jirachi.rakechu;

public class GameObjectActionMove implements GameObjectAction {
    protected GameObject mObjectToMove;
    protected float mTargetX;
    protected float mTargetY;
    protected float mTime;
    
    /**
     * Cree une action triggerable par utilisation d'un GameObject, qui va
     * deplacer un autre GameObject.
     * @param toMove
     * @param targetX
     * @param targetY
     */
    public GameObjectActionMove(GameObject toMove, float targetX, float targetY, float time) {
        mObjectToMove = toMove;
        mTargetX = targetX;
        mTargetY = targetY;
        mTime = time;
    }
    
    /**
     * Action quand l'objet est utilise
     */
    @Override
    public void useObject(Character user) {
        mObjectToMove.moveTo(mTargetX, mTargetY, mTime);
    }
    
}
