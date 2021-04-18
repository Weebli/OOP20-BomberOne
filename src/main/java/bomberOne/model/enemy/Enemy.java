/**
 * This interface define a basic enemy.
 */

package bomberOne.model.enemy;

import bomberOne.model.gameObjects.AnimatedEntity;
import bomberOne.model.common.P2d;

public interface Enemy extends AnimatedEntity {

        /* Methods. */

	/**
	 * This method set the enemy behavior to the basic behavior.
	 * The basic behavior allows the enemy to move the enemy randomly.
	 */
	void setBasicEnemy();
	
	/**
	 * This method set the enemy behavior to the "intermediate" behavior.
	 * The intermediate behavior means that the enemy can follow the player to catch him
	 * but only if the player is seen by it.
	 * @param playerPosition The current position of the player.
	 * (see the keepTrack method documentation to learn more about the following process.)
	 */
	void setHardEnemy(P2d playerPosition);
	
	/**
	 * This method set the destination field of an Observation class or the classes
	 * that extends it.
	 * @param playerPosition The current player position.
	 */
	void keepTrack(P2d playerPosition);
}
