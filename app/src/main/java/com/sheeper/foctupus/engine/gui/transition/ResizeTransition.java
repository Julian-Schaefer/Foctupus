package com.sheeper.foctupus.engine.gui.transition;

import com.sheeper.foctupus.engine.renderer.util.Vector;

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

    public Vector getEndSize(Transition.Direction direction)
    {
        if(direction == Transition.Direction.IN)
            return endSize;
        else
            return startSize;
    }
}
