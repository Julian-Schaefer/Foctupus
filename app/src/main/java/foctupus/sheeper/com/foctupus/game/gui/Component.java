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
public class Component extends Sprite {


    private Transition current;
    private LinkedList<Transition> transitions;

    private Container parent;

    public Component()
    {
        this(null);
    }

    public Component(Texture texture)
    {
        super(texture);
    }

    public Container getParent()
    {
        return parent;
    }

    public void setParent(Container parent)
    {
        this.parent = parent;
    }

}
