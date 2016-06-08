package com.sheeper.foctupus.engine.gui;

import com.sheeper.foctupus.engine.gui.transition.Transition;
import com.sheeper.foctupus.engine.renderer.Sprite;
import com.sheeper.foctupus.engine.renderer.util.Vector;

import java.util.LinkedList;

/**
 * Created by schae on 12.02.2016.
 */
public class Component implements IUpdateble {

    public static final int USE_RATIO = -1;
    public static final int USE_SAME = -2;

    protected ComponentListener listener;

    private Transition current;
    private volatile LinkedList<Transition> transitions;
    private Sprite sprite;

    private Vector relativePosition;
    private Vector relativeSize;

    private int priority;
    private boolean updated;

    public Component()
    {
        this(new Sprite());
    }

    public Component(Sprite sprite)
    {
        this(sprite, null, null);
    }

    public Component(Sprite sprite, Vector relativePosition, Vector relativeSize)
    {
        this.sprite = sprite;
        this.relativePosition = relativePosition;
        this.relativeSize = relativeSize;
        transitions = new LinkedList<>();
    }

    @Override
    public void update() {

        if(current != null && !current.isFinished())
            current.update();
        else
            startTransition();

    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public int getPriority()
    {
        return priority;
    }

    public void setPriority(int priority)
    {
        this.priority = priority;
    }

    public Vector getRelativePosition()
    {
        return relativePosition;
    }

    public void setRelativePosition(Vector relativePosition)
    {
        this.relativePosition = relativePosition;
        updated = true;
    }

    public Vector getRelativeSize()
    {
        return relativeSize;
    }

    public void setRelativeSize(Vector relativeSize)
    {
        this.relativeSize = relativeSize;
        updated = true;
    }

    public boolean gotUpdated()
    {
        boolean temp = updated;
        updated = false;
        return temp;
    }

    public void addTransition(Transition transition)
    {
        transitions.add(transition);
    }

    public void startTransition(String name)
    {
        stopTransition();

        for(Transition transition : transitions)
        {
            if(transition.getName().equals(name))
            {
                current = transition;
                current.start();
            }
        }

    }

    public void startTransition()
    {
        if(transitions.size() > 0)
        {
            stopTransition();

            current = transitions.removeFirst();
            current.start();
        }
    }

    public void startTransition(Transition transition)
    {
        if(transition != null)
        {
            current = transition;
            current.start();
        }
    }

    public void onTouch(float x, float y, int mode)
    {

    }

    public void finish()
    {
        if(listener != null)
            listener.onFinished(this);
    }

    public boolean isIntersected(float x, float y) {
        Sprite sprite = getSprite();
        float left = sprite.getActualXPos() - sprite.getXSize()/2;
        float right = sprite.getActualXPos() + sprite.getXSize()/2;
        float bottom = sprite.getActualYPos() - sprite.getYSize()/2;
        float top = sprite.getActualYPos() + sprite.getYSize()/2;

        return x >= left && x <= right && y >= bottom && y <= top;
    }

    public void stopTransition()
    {
        current = null;
    }

    public void clearTransitions()
    {
        transitions.clear();
    }

    public void setListener(ComponentListener listener)
    {
        this.listener = listener;
    }

    public interface ComponentListener
    {
        void onFinished(Component component);
    }
}
