package foctupus.sheeper.com.foctupus.game.gui.transition;

import foctupus.sheeper.com.foctupus.game.gui.Component;

/**
 * Created by schae on 12.02.2016.
 */
public class Transition {

    private static final int STD_ANIM_TIME = 700;

    public enum Direction
    {
        IN, OUT
    }

    private String name;
    private PositionTransition positionTransition;
    private ResizeTransition resizeTransition;
    private RotateTransition rotateTransition;

    private Component component;
    private Direction direction;
    private TransitionListener listener;

    private boolean autoRevert;
    private boolean autoRepeat;

    private long startTime;
    private int animationTime = STD_ANIM_TIME;

    private boolean finished;

    public Transition(String name, Component component)
    {
        this.name = name;
        this.component = component;

        direction = Direction.IN;
    }

    public void start()
    {
        startTime = System.currentTimeMillis();
    }

    public void update()
    {
        double time = System.currentTimeMillis() - startTime;

        if(time < animationTime) {

            double ratio = time / animationTime;

            if (resizeTransition != null)
                component.setRelativeSize(resizeTransition.update(ratio, direction));

            if(positionTransition != null)
                component.setRelativePosition(positionTransition.update(ratio, direction));

            if(rotateTransition != null)
                component.getSprite().setAngle(rotateTransition.update(ratio, direction));
        }
        else if(autoRepeat)
        {
            setEndPosition();

            if(autoRevert)
                revert();

            start();
        }
        else if(listener != null) {
            setEndPosition();
            finished = true;
            listener.onTransitionFinished(this);
        }
    }

    private void setEndPosition()
    {
        if (resizeTransition != null)
            component.setRelativeSize(resizeTransition.getEndSize(direction));

        if(positionTransition != null)
            component.setRelativePosition(positionTransition.getEndPosition(direction));

        if(rotateTransition != null)
            component.getSprite().setAngle(rotateTransition.getEndAngle(direction));
    }

    public void revert()
    {
        direction = direction == Direction.IN ? Direction.OUT : Direction.IN;
    }

    public void setResizeTransition(ResizeTransition resizeTransition)
    {
        this.resizeTransition = resizeTransition;
    }

    public void setPositionTransition(PositionTransition positionTransition)
    {
        this.positionTransition = positionTransition;
    }

    public void setRotateTransition(RotateTransition rotateTransition)
    {
        this.rotateTransition = rotateTransition;
    }

    public Component getComponent()
    {
        return component;
    }

    public void setListener(TransitionListener listener)
    {
        this.listener = listener;
    }

    public void setAutoRepeating(boolean autoRepeat)
    {
        this.autoRepeat = autoRepeat;
    }

    public boolean isAutoRepeating()
    {
        return autoRepeat;
    }

    public void setAutoReverting(boolean autoRevert)
    {
        this.autoRevert = autoRevert;
    }

    public boolean isAutoReverting()
    {
        return autoRevert;
    }

    public boolean isFinished()
    {
        return finished;
    }

    public void setAnimationTime(int animationTime)
    {
        this.animationTime = animationTime;
    }

    public String getName()
    {
        return name;
    }

    public interface TransitionListener
    {
        void onTransitionFinished(Transition transition);
    }
}
