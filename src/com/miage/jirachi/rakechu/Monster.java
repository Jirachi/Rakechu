package com.miage.jirachi.rakechu;

import java.util.ArrayList;
import java.util.List;

public class Monster extends Character {
    protected List<Character> mThreateningChars;   
    protected float mAttackCooldown;
    
    public final static float COOLDOWN_MELEE = 1.4f;
    public final static int MELEE_DAMAGES = 12;
    
    public Monster(String _texture) {
        super(_texture);
        
        mThreateningChars = new ArrayList<Character>();
        mAttackCooldown = 0;
    }

    @Override
    public void setMoveDirection(short direction) {
        super.setMoveDirection(direction);
    }
    
    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
    }
    
    @Override
    public void jump() {
        super.jump();
    }
    
    @Override
    public void hit(Character src, int amount) {
        super.hit(src, amount);
        
        if (!mThreateningChars.contains(src)) {
            System.out.println("A player is hitting me! Grrr");
            mThreateningChars.add(src);
        }
    }
    
    /**
     * Met a jour le monstre
     * @param timeDelta Temps depuis le dernier update
     */
    public void update(float timeDelta) {
        if (mAttackCooldown > 0)
            mAttackCooldown -= timeDelta;
       
        
        // If someone is attacking us, ripost!
        if (mThreateningChars.size() > 0) {
            // If the cooldown is up...
            if (mAttackCooldown <= 0) {
                // Hit the closest one
                Character closest = null;
                float closest_distance = Float.MAX_VALUE;
                
                for (int i = 0; i < mThreateningChars.size(); i++) {
                    Character c = mThreateningChars.get(i);
                    float distance = c.getPosition().squaredDistance(getPosition());
                    
                    if (distance < closest_distance || closest == null) {
                        closest_distance = distance;
                        closest = c;
                    }
                }
                
                // Hit him if we are close enough, and reset cooldown
                if (closest_distance < 30*30) {
                    closest.hit(this, MELEE_DAMAGES);
                    mAttackCooldown = COOLDOWN_MELEE;
                    
                    if (closest.getHealth() <= 0) {
                        // Player is dead, no more a threat
                        mThreateningChars.remove(closest);
                    }
                } else {
                    // TODO: Move the NPC towards the player
                }
            }
        }
    }
}

