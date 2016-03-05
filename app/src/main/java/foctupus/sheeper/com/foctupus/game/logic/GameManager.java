package foctupus.sheeper.com.foctupus.game.logic;

import android.util.Log;

import foctupus.sheeper.com.foctupus.BuildConfig;
import foctupus.sheeper.com.foctupus.game.renderer.components.Component;
import foctupus.sheeper.com.foctupus.game.renderer.components.Container;
import foctupus.sheeper.com.foctupus.game.renderer.components.asd;
import foctupus.sheeper.com.foctupus.game.renderer.components.transition.PositionTransition;
import foctupus.sheeper.com.foctupus.game.renderer.components.transition.ResizeTransition;
import foctupus.sheeper.com.foctupus.game.renderer.components.transition.Transition;
import foctupus.sheeper.com.foctupus.game.renderer.Loader;
import foctupus.sheeper.com.foctupus.game.renderer.Renderer;
import foctupus.sheeper.com.foctupus.game.renderer.components.StaticContainer;
import foctupus.sheeper.com.foctupus.game.renderer.Sprite;
import foctupus.sheeper.com.foctupus.game.renderer.Texture;
import foctupus.sheeper.com.foctupus.game.renderer.Textures;
import foctupus.sheeper.com.foctupus.game.renderer.util.Vector;
import foctupus.sheeper.com.foctupus.game.screen.LoadScreen;

/**
 * Created by schae on 04.02.2016.
 */
public class GameManager {

    private Renderer renderer;
    private Container container;

    private static GameManager instance;

    public static GameManager getInstance()
    {
        if(instance == null)
            instance = new GameManager();

        return instance;
    }

    private GameManager()
    {

    }

    public void update()
    {
        if(container != null)
            container.update();
        else
           ;
    }

    public void draw()
    {
        if(container != null)
            container.draw();
        else
           ;

        renderer.draw();
    }

    public void revalidate(boolean created, float oldX, float oldY)
    {
        if (BuildConfig.DEBUG)
            Log.d("asd", "GameManager.revalidate(" +  created + ") called.");

        renderer = Renderer.getInstance();

        if(created)
        {
            if(Textures.areDecoded())
                Loader.registerTextures();
            else
                ;
        }

        if(container != null)
            container.revalidate();


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
    public synchronized void onFinished(asd container) {

        if (BuildConfig.DEBUG)
            Log.d("asds", toString() + " Screen onFinished() Event called");

        Sprite back = new Sprite(new Texture(Textures.BACKGROUND));
        this.container = new Container(renderer, back);
        this.container.setRelativeSize(new Vector(100, 100));
        this.container.setRelativePosition(new Vector(50, 50));

        Component c = new Component(new Sprite(new Texture(Textures.BEACH)));
        c.getSprite().setBottomLeftAligned(true);
        c.setRelativeSize(new Vector(100, Component.USE_RATIO));
        c.setRelativePosition(new Vector(0, 0));
        this.container.addChild(c);

        Container con = new Container(renderer, new Sprite(new Texture(Textures.SCORE_BACKGROUND)));
        con.setRelativeSize(new Vector(60, 50));

        Transition t2 = new Transition("test2", con);
        t2.setPositionTransition(new PositionTransition(new Vector(50, 50), new Vector(50, 50)));
        t2.setAutoRepeating(true);
        t2.setAutoReverting(true);

        Component c2 = new Component(new Sprite(new Texture(Textures.BTN_START)));
        //c.setBottomLeftAligned(true);

        c2.setRelativePosition(new Vector(50, 50));
        con.addChild(c2);

        Transition t = new Transition("test", c2);
        t.setResizeTransition(new ResizeTransition(new Vector(10, Component.USE_SAME), new Vector(100, Component.USE_SAME)));
        t.setPositionTransition(new PositionTransition(new Vector(50, 0), new Vector(50, 100)));
        t.setAutoRepeating(true);
        t.setAutoReverting(true);

        c2.addTransition(t);
        c2.startTransition("test");

        con.addTransition(t2);
        con.startTransition("test2");

        this.container.addChild(con);

        if (BuildConfig.DEBUG)
            Log.d("asdas", screen.toString() + " New Screen");
    }
}
