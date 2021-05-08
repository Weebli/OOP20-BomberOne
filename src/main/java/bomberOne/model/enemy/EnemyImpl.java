package bomberOne.model.enemy;

import bomberOne.model.Difficulty;
import bomberOne.model.common.Direction;
import bomberOne.model.common.P2d;
import bomberOne.model.gameObjects.AnimatedEntityImpl;
import java.awt.image.BufferedImage;
import bomberOne.model.enemy.actions.Actions;
import bomberOne.model.enemy.actions.IntermediateBehavior;
import bomberOne.model.enemy.actions.BasicBehavior;

public final class EnemyImpl extends AnimatedEntityImpl implements Enemy {

        /* Fields. */
        private static final int NEXT_MOVE_FRAME_QUANTITY = 1;
        private static final int SECONDS_TO_WAIT = 4;
        private static final int FRAME_PER_SECOND = 60;
        private static final int LOW_SPEED = 500;
        private static final int HIGH_SPEED = 600;
        private Actions behavior;
        private int frameCounter;
        private int nextMoveFrameCounter;
        private int frameCounterAnimation;
        
        /* Constructors. */
        public EnemyImpl(final P2d position, final BufferedImage [][] img, final int lifes, Difficulty mode) {
            super(position, img, lifes, img[0][0]);
            
            //Setting the number of frames that the enemy has to wait before start moving.
            this.frameCounter = SECONDS_TO_WAIT * FRAME_PER_SECOND;
            
            //Setting the frames number that the enemy has to wait to perform the next move.
            this.nextMoveFrameCounter = NEXT_MOVE_FRAME_QUANTITY;
            
            //Creating the enemy behavior on the basis of the mode chosen by the user.
            if (mode.equals(Difficulty.STANDARD)) {
                this.behavior = new BasicBehavior(this);
                this.setSpeed(LOW_SPEED);
            } else if (mode.equals(Difficulty.HARD)){
                this.behavior = new IntermediateBehavior(this);
                this.setSpeed(HIGH_SPEED);
            }
        }

    /* Methods. */

    /**
     * {@inheritDoc}
     */
    public void update(int elapsed) {
        // The enemy before acts has to wait four second that are 240 frames.
        // Checking if the frame counter is greater than zero.
        if (this.frameCounter > 0) {
            this.frameCounter--;
        } else {
            // The enemy has to wait some frames before the next move.
            if (++this.nextMoveFrameCounter >= NEXT_MOVE_FRAME_QUANTITY) {
                this.nextMoveFrameCounter = 0;
                this.behavior.doActions();
                super.update(elapsed);
            }
        }
    }
   
        /**
         * {@inheritDoc}
         */
        @Override
        public BufferedImage getImage() {
            return this.getSprites()[this.getSpriteIndex()][this.getAnimationIndex() % 3];
        }
        
        public int getFrameCounterAnimation() {
            return this.frameCounterAnimation;
        }
        
        public void setFrameCounterAnimation(int value) {
            this.frameCounterAnimation = value;
        }
}