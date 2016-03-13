package foctupus.sheeper.com.foctupus.game.gui.transition;

import foctupus.sheeper.com.foctupus.game.renderer.util.Vector;

/**
 * Created by schae on 12.02.2016.
 */
public class PositionTransition {

    private Vector startPosition;
    private Vector endPosition;


    public PositionTransition(Vector startPosition, Vector endPosition)
    {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    public Vector update(double ratio, Transition.Direction direction)
    {
        Vector diff = new Vector(endPosition.getX() - startPosition.getX(), endPosition.getY() - startPosition.getY());

        if(direction == Transition.Direction.IN)
            return new Vector(startPosition.getX() + (float) (ratio * diff.getX()), startPosition.getY() + (float) (ratio * diff.getY()));
        else
            return new Vector(endPosition.getX() + (float) (ratio * diff.getX() * -1),
                              endPosition.getY() + (float) (ratio * diff.getY() * -1));
    }

    public Vector getEndPosition(Transition.Direction direction)
    {
        if(direction == Transition.Direction.IN)
            return endPosition;
        else
            return startPosition;
    }

}
