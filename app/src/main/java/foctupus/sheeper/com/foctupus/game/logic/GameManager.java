package foctupus.sheeper.com.foctupus.game.logic;

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

    public void revalidate()
    {
        screen = new LoadScreen(this);
    }

    public void onClick(float x, float y, int mode)
    {

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
    public void onFinished(Container container) {
        if(container instanceof LoadScreen)
            screen = new TestScreen(10);

    }
}
