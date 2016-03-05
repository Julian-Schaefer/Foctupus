package foctupus.sheeper.com.foctupus.game.renderer.components.transition;

import foctupus.sheeper.com.foctupus.game.renderer.util.Vector;

/**
 * Created by schae on 12.02.2016.
 */
public class RotateTransition {

    private float startAngle;
    private float endAngle;

    public RotateTransition(float startAngle, float endAngle)
    {
        this.startAngle = startAngle;
        this.endAngle = endAngle;
    }

    public float update(double ratio, Transition.Direction direction)
    {
        float diff = endAngle - startAngle;
        if(direction == Transition.Direction.IN)
            return startAngle + (float) (ratio * diff);
        else
            return endAngle + (float) (ratio * diff * -1);

    }
}
