package foctupus.sheeper.com.foctupus.game.screen;

import android.util.Log;

import foctupus.sheeper.com.foctupus.BuildConfig;
import foctupus.sheeper.com.foctupus.game.renderer.Environment;
import foctupus.sheeper.com.foctupus.game.renderer.StaticContainer;

/**
 * Created by schae on 09.02.2016.
 */
public abstract class Screen extends StaticContainer {


    public Screen(String textureName, int priority) {
        super(textureName, priority);
        initScreen();
    }

    public Screen(int priority)
    {
        super(priority);
        initScreen();
    }

    public Screen(int textureID, String textureName, int priority)
    {
        super(textureID, textureName, priority);
        initScreen();
    }

    private void initScreen()
    {
        if (BuildConfig.DEBUG)
            Log.d(Environment.TAG, toString() + " Screen Constructor called.");

        setBottomLeftAligned(true);
        setSize(Environment.width, Environment.height);
        setPosition(0, 0);
    }
}
