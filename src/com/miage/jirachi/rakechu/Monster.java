package com.miage.jirachi.rakechu;

import java.util.ArrayList;
import java.util.List;

public class Monster extends Character {
    // ==
    // Members
    protected List<Character> mThreateningChars;   
    protected float mAttackCooldown;
    protected float mAIBoundaryLeft;
    protected float mAIBoundaryRight;
    protected Vector2 mOriginalPosition;
    protected Character mChasedCharacter;
    protected float mPositionSyncCooldown;
    protected float mWaitLastTarget;
    
    // ==
    // Constants
    public final static float COOLDOWN_MELEE = 1.4f;
    public final static int MELEE_DAMAGES = 12;
    public final static float POSITION_SYNC_COOLDOWN = 0.3f;
    public final static float TARGET_FORGET_TIME = 3.0f;
    
    // ==
    // Methods
    public Monster(String _texture) {
        super(_texture);
        
        mOriginalPosition = new Vector2();
        mThreateningChars = new ArrayList<Character>();
        mAttackCooldown = 0;
        mPositionSyncCooldown = 0;
        mWaitLastTarget = TARGET_FORGET_TIME;
    }

    @Override
    public void setMoveDirection(short direction) {
        super.setMoveDirection(direction);
    }
    
    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        mOriginalPosition.x = x;
        mOriginalPosition.y = y;
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
     * We are totally lazy and we haven't put yet Box2D simulation
     * on the server. We will hack this in the levels by only putting
     * monsters on flat surfaces, and defining the left most and right most
     * position the monster can get before falling off the surface or going
     * off-range. The AI will then only move left/right the monster.
     * @param minX The left-most position the monster can get
     * @param maxX The right-most position the monster can get
     */
    public void setMovementBoundaries(float minX, float maxX) {
        mAIBoundaryLeft = minX;
        mAIBoundaryRight = maxX;
    }
    
    /**
     * Met a jour le monstre
     * @param timeDelta Temps depuis le dernier update
     */
    public void update(float timeDelta) {
        if (mHealth <= 0)
            return;
        
        if (mAttackCooldown > 0)
            mAttackCooldown -= timeDelta;
        
        if (mPositionSyncCooldown > 0)
            mPositionSyncCooldown -= timeDelta;

        if (mWaitLastTarget > 0)
            mWaitLastTarget -= timeDelta;

        boolean hitSomeone = false;

        // If someone is attacking us, ripost!
        if (mThreateningChars.size() > 0) {
            // Lookup the closest one
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
                // Ready or not, we are close to the guy, so
                // we're not going to forget about him
                hitSomeone = true;
                
                // If the cooldown is up...
                if (mAttackCooldown <= 0) {
                    closest.hit(this, MELEE_DAMAGES);
                    mAttackCooldown = COOLDOWN_MELEE;
                }

                if (closest.getHealth() <= 0) {
                    // Player is dead, no more a threat
                    mThreateningChars.remove(closest);
                }
            } else {
                // Move the NPC towards the player, if he is inside boundaries
                if (closest.getPosition().x <= mAIBoundaryRight && closest.getPosition().x >= mAIBoundaryLeft) {
                    mChasedCharacter = closest;
                } else if (mThreateningChars.size() > 1) {
                    // He's too far, let him alone and move to the next
                    mThreateningChars.remove(closest);
                } else {
                    // Only one guy left, we wait, and get back home
                    mWaitLastTarget = TARGET_FORGET_TIME;
                }
            }
        }

        // If we are chasing someone, go towards him!
        if (mChasedCharacter != null) {
            // Check that we need to chase him actually
            if (mChasedCharacter.getPosition().squaredDistance(mPosition) < 30*30) {
                mChasedCharacter = null;
                mMoveDirection = DIRECTION_STOP;
                
                // notify the network
                Packet p = PacketMaker.makeMovePacket(DIRECTION_STOP, mNetworkId);
                mGameInstance.sendPacket(p, null);
            }
            // Otherwise, make sure we don't go off-limits and chase the guy
            else if (mChasedCharacter.getPosition().x <= mAIBoundaryRight 
                    && mChasedCharacter.getPosition().x >= mAIBoundaryLeft) {
                if (mChasedCharacter.getPosition().x < mPosition.x) {
                    if (mMoveDirection != DIRECTION_LEFT) {
                        mMoveDirection = DIRECTION_LEFT;
                        
                        // notify the network
                        Packet p = PacketMaker.makeMovePacket(DIRECTION_LEFT, mNetworkId);
                        mGameInstance.sendPacket(p, null);
                    }
                } else {
                    if (mMoveDirection != DIRECTION_RIGHT) {
                        mMoveDirection = DIRECTION_RIGHT;
                        
                        // notify the network
                        Packet p = PacketMaker.makeMovePacket(DIRECTION_RIGHT, mNetworkId);
                        mGameInstance.sendPacket(p, null);
                    }
                }
            } else {
                // The guy is off limits
                if (mMoveDirection != DIRECTION_STOP) {
                    mMoveDirection = DIRECTION_STOP;
                    
                    // notify the network
                    Packet p = PacketMaker.makeMovePacket(DIRECTION_STOP, mNetworkId);
                    mGameInstance.sendPacket(p, null);
                    
                    p = PacketMaker.makeSyncPositionPacket(mNetworkId, mPosition.x, mPosition.y);
                    mGameInstance.sendPacket(p, null);
                }
            }
        }
        
        // Move if we need to move
        if (mMoveDirection != DIRECTION_STOP) {
            float moveDelta = 200.0f * timeDelta;
            if (mMoveDirection == DIRECTION_LEFT)
                moveDelta = -moveDelta;
            
            mPosition.x += moveDelta;
            
            // notify the network every once in a few
            if (mPositionSyncCooldown <= 0) {
                Packet p = PacketMaker.makeSyncPositionPacket(mNetworkId, mPosition.x, mPosition.y);
                mGameInstance.sendPacket(p, null);
                
                mPositionSyncCooldown = POSITION_SYNC_COOLDOWN;
            }
        }
        else if (mThreateningChars.size() == 1 && mWaitLastTarget <= 0 && !hitSomeone) {
            // We were chasing someone, but now he's gone for too long, 
            // we forget about him and go back to spawn point
            setPosition(mOriginalPosition.x, mOriginalPosition.y);
            Packet p = PacketMaker.makeSyncPositionPacket(mNetworkId, mPosition.x, mPosition.y);
            mGameInstance.sendPacket(p, null);
            
            mThreateningChars.clear();
        }
    }
}

