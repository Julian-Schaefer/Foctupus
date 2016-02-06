package foctupus.sheeper.com.foctupus.game.renderer;

import java.util.LinkedList;

/**
 * Created by schae on 29.01.2016.
 */
public abstract class SpriteList extends LinkedList<Sprite> {

    private int priority;

    public SpriteList()
    {

    }

    public SpriteList(int priority)
    {
        this.priority = priority;
    }

    public void setPriority(int priority)
    {
        this.priority = priority;
    }

    public int getPriority()
    {
        return priority;
    }
}
