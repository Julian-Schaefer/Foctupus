package foctupus.sheeper.com.foctupus.game.logic;

import android.util.Log;

import foctupus.sheeper.com.foctupus.BuildConfig;
import foctupus.sheeper.com.foctupus.game.gui.Component;
import foctupus.sheeper.com.foctupus.game.gui.Container;
import foctupus.sheeper.com.foctupus.game.gui.Screen;
import foctupus.sheeper.com.foctupus.game.gui.SplashScreen;
import foctupus.sheeper.com.foctupus.game.renderer.Renderer;
import foctupus.sheeper.com.foctupus.game.renderer.Sprite;
import foctupus.sheeper.com.foctupus.game.renderer.Texture;
import foctupus.sheeper.com.foctupus.game.renderer.Textures;
import foctupus.sheeper.com.foctupus.game.renderer.util.Vector;
import foctupus.sheeper.com.foctupus.screen.StartScreen;

/**
 * Created by schae on 04.02.2016.
 */
public class GameManager implements Component.ComponentListener {

    public enum GameState
    {
        LOADING, STARTSCREEN, SCORESCREEN, PLAYING
    }

    private Renderer renderer;
    private Screen screen;

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
        if(screen != null)
            screen.update();
    }

    public void draw()
    {
        if(screen != null)
            screen.draw();

        renderer.draw();
    }

    public void revalidate(boolean created)
    {
        if (BuildConfig.DEBUG)
            Log.d("asd", "GameManager.revalidate(" +  created + ") called.");

        renderer = Renderer.getInstance();

        if(created)
        {
            if (!Renderer.areTexturesDecoded() || screen == null)
                startSplashScreen();
            else
                Renderer.registerTextures();
        }

        if(screen != null)
            screen.revalidate();
    }

    public void showAd()
    {

    }

    public void hideAd()
    {

    }

    private void startSplashScreen()
    {
        SplashScreen splashScreen = new SplashScreen(renderer);
        splashScreen.setListener(this);
        splashScreen.setScreenImage(Textures.LOADSCREEN);
        splashScreen.setBackgroundColor(new int[]{255, 0, 0});
        splashScreen.setProgressBackgroundColor(new int[]{0, 0, 255});
        splashScreen.setProgressColor(new int[]{0, 255, 0});
        splashScreen.load(Textures.pictureNames);

        screen = splashScreen;
    }

    private void setScreen(Screen screen)
    {
        this.screen = screen;
    }


    public void onTouch(float x, float y, int mode)
    {
        screen.onTouch(x, y, mode);
    }

    @Override
    public void onFinished(Component component)
    {
        if(component instanceof SplashScreen)
        {
            setScreen(new StartScreen(renderer));
        }
    }
}
