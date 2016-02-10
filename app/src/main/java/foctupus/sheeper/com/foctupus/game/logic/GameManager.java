package foctupus.sheeper.com.foctupus.game.logic;

import android.util.Log;

import foctupus.sheeper.com.foctupus.BuildConfig;
import foctupus.sheeper.com.foctupus.MainActivity;
import foctupus.sheeper.com.foctupus.game.MyGLRenderer;
import foctupus.sheeper.com.foctupus.game.renderer.Container;
import foctupus.sheeper.com.foctupus.game.renderer.ContainerListener;
import foctupus.sheeper.com.foctupus.game.renderer.Environment;
import foctupus.sheeper.com.foctupus.game.renderer.GenericSpriteList;
import foctupus.sheeper.com.foctupus.game.renderer.Loader;
import foctupus.sheeper.com.foctupus.game.renderer.Renderer;
import foctupus.sheeper.com.foctupus.game.renderer.Sprite;
import foctupus.sheeper.com.foctupus.game.renderer.StaticContainer;
import foctupus.sheeper.com.foctupus.game.renderer.StaticSpriteList;
import foctupus.sheeper.com.foctupus.game.renderer.Textures;
import foctupus.sheeper.com.foctupus.game.screen.LoadScreen;
import foctupus.sheeper.com.foctupus.game.screen.TestScreen;

/**
 * Created by schae on 04.02.2016.
 */
public class GameManager implements ContainerListener {

    private Renderer renderer;
    private StaticContainer screen;

    public GameManager()
    {
        if (BuildConfig.DEBUG)
            Log.d(Environment.TAG, "GameManager Constructor called.");


        renderer = Environment.renderer;
        screen = new LoadScreen(this);


    }

    public void update()
    {
        screen.update();
    }

    public void render()
    {
        screen.render();
        renderer.render();
    }

    public void revalidate(boolean created)
    {
        if (BuildConfig.DEBUG)
            Log.d(Environment.TAG, "GameManager.revalidate(" +  created + ") called.");

        if(created)
        {
            if(Textures.areDecoded())
                Loader.registerTextures();
            else
                screen = new LoadScreen(this);
        }

        screen.revalidate();
    }

    public void onClick(float x, float y, int mode) {

    }

    public void showAd()
    {

    }

    public void hideAd()
    {

    }

    @Override
    public void onRevalidate(Container container) {

    }

    @Override
    public void onLoaded(Container container) {

    }

    @Override
    public synchronized void onFinished(Container container) {
        if (BuildConfig.DEBUG)
            Log.d(Environment.TAG, toString() + " Screen onFinished() Event called");

        screen = new TestScreen(10);

        if (BuildConfig.DEBUG)
            Log.d(Environment.TAG, screen.toString() + " New Screen");
    }
}
