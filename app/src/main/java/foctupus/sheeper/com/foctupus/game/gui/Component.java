package foctupus.sheeper.com.foctupus.game.gui;

import java.util.LinkedList;

import foctupus.sheeper.com.foctupus.game.gui.transition.Transition;
import foctupus.sheeper.com.foctupus.game.renderer.Sprite;
import foctupus.sheeper.com.foctupus.game.renderer.Texture;
import foctupus.sheeper.com.foctupus.game.renderer.util.RelativeVector;
import foctupus.sheeper.com.foctupus.game.renderer.util.Vector;

/**
 * Created by schae on 12.02.2016.
 */
public class Component implements IUpdateble {

    public static final int USE_RATIO = -1;
    public static final int USE_SAME = -2;

    private Transition current;
    private volatile LinkedList<Transition> transitions;
    protected Sprite sprite;

    private Vector relativePosition;
    private Vector relativeSize;

    private int priority;


    public Component()
    {
        this(null);
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

        if(current != null)
        {
            current.update();
        }
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
    }

    public Vector getRelativeSize()
    {
        return relativeSize;
    }

    public void setRelativeSize(Vector relativeSize)
    {
        this.relativeSize = relativeSize;
    }

    public void addTransition(Transition transition)
    {
        transitions.add(transition);
    }

    public void startTransition(String name)
    {
        for(Transition transition : transitions)
        {
            if(transition.getName().equals(name))
            {
                current = transition;
                current.start();
            }
        }

    }

}
