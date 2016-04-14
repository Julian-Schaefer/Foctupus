package foctupus.sheeper.com.foctupus.game.gui;

import java.util.LinkedList;

import foctupus.sheeper.com.foctupus.game.gui.transition.Transition;
import foctupus.sheeper.com.foctupus.game.renderer.Renderer;
import foctupus.sheeper.com.foctupus.game.renderer.Sprite;
import foctupus.sheeper.com.foctupus.game.renderer.util.Vector;

/**
 * Created by JSchaefer on 05.03.2016.
 */
public abstract class Screen extends Container implements Component.ComponentListener, Transition.TransitionListener {

    private Component popUp;

    public Screen(Renderer renderer)
    {
        super(renderer);

        setRelativePosition(new Vector(50, 50));
        setRelativeSize(new Vector(100, 100));

        getSprite().setVisible(false);
    }

    protected abstract void init();

    @Override
    public void onTouch(float x, float y, int mode)
    {
        if(popUp != null)
            popUp.onTouch(x, y, mode);
        else
            super.onTouch(x, y, mode);
    }

    public void showPopUp(Component component)
    {
        if(popUp != null)
            removePopUp();

        component.setListener(this);
        addChild(component);
        popUp = component;
    }

    public void removePopUp()
    {
        childs.remove(popUp);
        popUp = null;
    }
}
