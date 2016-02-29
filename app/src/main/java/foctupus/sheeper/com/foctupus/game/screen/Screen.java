package foctupus.sheeper.com.foctupus.game.screen;

import android.util.Log;

import foctupus.sheeper.com.foctupus.BuildConfig;
import foctupus.sheeper.com.foctupus.game.renderer.Environment;
import foctupus.sheeper.com.foctupus.game.gui.StaticContainer;
import foctupus.sheeper.com.foctupus.game.renderer.Renderer;
import foctupus.sheeper.com.foctupus.game.renderer.Texture;

/**
 * Created by schae on 09.02.2016.
 */
public abstract class Screen extends StaticContainer {


    public Screen(Texture texture, int priority) {
        super(texture, priority);
        initScreen();
    }

    public Screen(int priority)
    {
        super(priority);
        initScreen();
    }

    private void initScreen()
    {
        if (BuildConfig.DEBUG)
            Log.d("sadsas", toString() + " Screen Constructor called.");

        setBottomLeftAligned(true);
        setSize(Renderer.getWidth(), Renderer.getHeight());
        setPosition(0, 0);
    }
}
