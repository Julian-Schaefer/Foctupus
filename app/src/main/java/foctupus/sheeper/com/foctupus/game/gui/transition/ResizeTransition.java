package foctupus.sheeper.com.foctupus.game.gui.transition;

import android.util.Log;

import foctupus.sheeper.com.foctupus.game.renderer.util.Vector;

/**
 * Created by schae on 12.02.2016.
 */
public class ResizeTransition {

    private Vector startSize;
    private Vector endSize;

    public ResizeTransition(Vector startSize, Vector endSize)
    {
        this.startSize = startSize;
        this.endSize = endSize;
    }

    public Vector update(double ratio, Transition.Direction direction)
    {
        Vector diff = new Vector(endSize.getX() - startSize.getX(), endSize.getY() - startSize.getY());

        if(direction == Transition.Direction.IN)
            return new Vector(startSize.getX() + (float) (ratio * diff.getX()), startSize.getY() + (float) (ratio * diff.getY()));
        else
            return new Vector(endSize.getX() + (float) (ratio * diff.getX() * -1), endSize.getY() + (float) (ratio * diff.getY() * -1));
    }
}
