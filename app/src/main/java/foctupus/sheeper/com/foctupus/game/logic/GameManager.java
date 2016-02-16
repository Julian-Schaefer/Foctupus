package foctupus.sheeper.com.foctupus.game.logic;

import android.util.Log;

import foctupus.sheeper.com.foctupus.BuildConfig;
import foctupus.sheeper.com.foctupus.game.gui.Component;
import foctupus.sheeper.com.foctupus.game.gui.Container;
import foctupus.sheeper.com.foctupus.game.gui.IContainer;
import foctupus.sheeper.com.foctupus.game.renderer.Environment;
import foctupus.sheeper.com.foctupus.game.renderer.Loader;
import foctupus.sheeper.com.foctupus.game.renderer.Renderer;
import foctupus.sheeper.com.foctupus.game.gui.StaticContainer;
import foctupus.sheeper.com.foctupus.game.renderer.Texture;
import foctupus.sheeper.com.foctupus.game.renderer.Textures;
import foctupus.sheeper.com.foctupus.game.renderer.util.RelativeVector;
import foctupus.sheeper.com.foctupus.game.renderer.util.Vector;
import foctupus.sheeper.com.foctupus.game.screen.LoadScreen;
import foctupus.sheeper.com.foctupus.game.screen.TestScreen;
import foctupus.sheeper.com.foctupus.game.tools.Maths;

/**
 * Created by schae on 04.02.2016.
 */
public class GameManager implements IContainer.Listener {

    private Renderer renderer;
    private StaticContainer screen;
    private Container container;

    public GameManager()
    {
        if (BuildConfig.DEBUG)
            Log.d(Environment.TAG, "GameManager Constructor called.");


        renderer = Environment.renderer;
        screen = new LoadScreen(this);


    }

    public void update()
    {
        if(container != null)
            container.update();
        else
            screen.update();
    }

    public void draw()
    {
        if(container != null)
            container.draw();
        else
            screen.render();

        renderer.draw();
    }

    public void revalidate(boolean created, float oldX, float oldY)
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

        if(container != null)
            container.revalidate(new Vector(oldX, oldY), new Vector(Environment.width, Environment.height));

        screen.revalidate(oldX, oldY);
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
    public void onRevalidate(IContainer container) {

    }

    @Override
    public void onLoaded(IContainer container) {

    }

    @Override
    public synchronized void onFinished(IContainer container) {

        if (BuildConfig.DEBUG)
            Log.d(Environment.TAG, toString() + " Screen onFinished() Event called");

        this.container = new Container(renderer, 10, new Texture(Textures.BACKGROUND));
        this.container.setBottomLeftAligned(true);
        this.container.setSize(Environment.width, Environment.height);
        this.container.setPosition(0, 0);

        Component c = new Component(new Texture(Textures.BEACH));
        //c.setBottomLeftAligned(true);
        c.setSize(new RelativeVector(100, RelativeVector.RATIO, Textures.ratios.get(Textures.BEACH), this.container.getSize()));
        c.setPosition(Environment.width / 2, c.getYSize() / 2);
        this.container.addChild(c);

        Container con = new Container(renderer, 12, new Texture(Textures.SCORE_BACKGROUND));
        con.setSize(Maths.percentToPixel(85, Environment.width), Maths.percentToPixel(85, Environment.height));
        con.setPosition(Environment.width/2, Environment.height/2);

        Component c2 = new Component(new Texture(Textures.BTN_START));
        //c.setBottomLeftAligned(true);
        c2.setSize(new RelativeVector(80, RelativeVector.RATIO, Textures.ratios.get(Textures.BTN_START), con.getSize()));
        c2.setPosition(con.getXSize()/2, con.getYSize() / 2);
        this.container.addChild(c2);

        this.container.addChild(con);

        if (BuildConfig.DEBUG)
            Log.d(Environment.TAG, screen.toString() + " New Screen");
    }
}
