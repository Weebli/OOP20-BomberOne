package bomberone.model.enemy.navigation;

import java.util.List;
import bomberone.model.common.P2d;
import bomberone.model.gameboard.BoardPoint;
import bomberone.model.common.Direction;

/**
 * This interface defines an object that gives a route to the player. This
 * object uses a Breadth-First Search (BFS) to search the player analyzing the
 * game map.
 */
public interface Navigation {

    /* Methods. */

    /**
     * This method checks if the BoardPoint object passed as argument was already
     * explored.
     * 
     * @param poitnToCheck The point to check.
     * @return true if the point was already explored, otherwise false.
     */
    boolean explored(BoardPoint pointToCheck);

    /**
     * This method adds new positions to check in the search algorithm. In
     * particular, this method builds the tree or graph, where the BFS has to be
     * performed.
     * 
     * @param currentNode  The current node to set as a parent of the nodes that
     *                     will be created.
     * @param currentPoint The currentPosition of the node.
     */
    void addTargets(Node currentNode);

    /**
     * This method search the shortest path to reach the player. The searching
     * algorithm is a Breadth-First Search algorithm.
     * 
     * @param initialPosition The enemy position.
     * @param destination     The player position.
     * @return A list of directions that defines the shortest path.
     */
    List<Direction> searchShortestPath(P2d enemyLocation);
}
