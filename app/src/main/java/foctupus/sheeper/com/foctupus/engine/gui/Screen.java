package foctupus.sheeper.com.foctupus.engine.gui;

import foctupus.sheeper.com.foctupus.engine.gui.transition.Transition;
import foctupus.sheeper.com.foctupus.engine.renderer.Renderer;
import foctupus.sheeper.com.foctupus.engine.renderer.util.Vector;

/**
 * Created by JSchaefer on 05.03.2016.
 */
public abstract class Screen extends Container implements Component.ComponentListener, Transition.TransitionListener {

    private Component popUp;

    public ScreenListener screenListener;

    public Screen(Renderer renderer)
    {
        this(renderer, false);
    }


    public Screen(Renderer renderer, boolean fullScreen)
    {
        super(renderer);

        setRelativePosition(new Vector(50, 50));

        if(fullScreen)
            setRelativeSize(new Vector(100, 100));
        else
            setRelativeSize(new Vector(calcWidth(), 100));

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

    @Override
    public void onFinished(Component component) {
        removePopUp();
    }

    private float calcWidth()
    {
        float width = Renderer.getWidth();
        float height = Renderer.getHeight();

        float innerWidth = height * (9f/16f);

        return 100f * (innerWidth / width);
    }

    public void finishScreen(Screen nextScreen)
    {
        if(screenListener != null)
            screenListener.OnScreenFinished(nextScreen);
    }

    public void setScreenListener(ScreenListener screenListener)
    {
        this.screenListener = screenListener;
    }

    public interface ScreenListener
    {
        void OnScreenFinished(Screen nextScreen);
    }
}
