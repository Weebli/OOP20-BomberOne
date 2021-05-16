package bomberone.controllers.game.input.commands;

import bomberone.model.GameMatch;
import bomberone.model.common.Direction;

public class MoveDown implements Command {

    Direction dir;
    
    public MoveDown() {
        this.dir = Direction.DOWN;
    }
    
    @Override
    public Direction dir() {
        return this.dir;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final GameMatch gameModel) {
        gameModel.getWorld().getBomber().moveDown();  
        gameModel.getWorld().getBomber().setStatic(false);
    }

    

}