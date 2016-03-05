package foctupus.sheeper.com.foctupus.game.gui;

import foctupus.sheeper.com.foctupus.game.renderer.Renderer;
import foctupus.sheeper.com.foctupus.game.renderer.Sprite;
import foctupus.sheeper.com.foctupus.game.renderer.components.Container;
import foctupus.sheeper.com.foctupus.game.renderer.util.Vector;

/**
 * Created by JSchaefer on 05.03.2016.
 */
public abstract class Screen extends Container {

    protected ScreenListener listener;

    public Screen(Renderer renderer) {
        this(renderer, null);
    }

    public Screen(Renderer renderer, Sprite sprite)
    {
        super(renderer, sprite);
        setRelativePosition(new Vector(50, 50));
        setRelativeSize(new Vector(100, 100));
    }

    protected abstract void init();

    public void setListener(ScreenListener listener)
    {
        this.listener = listener;
    }

    public interface ScreenListener
    {
        void onFinished(Screen screen);
    }
}
