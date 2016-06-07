package foctupus.sheeper.com.foctupus.game.logic;

import android.util.Log;

import foctupus.sheeper.com.foctupus.BuildConfig;
import foctupus.sheeper.com.foctupus.MainActivity;
import foctupus.sheeper.com.foctupus.engine.gui.Screen;
import foctupus.sheeper.com.foctupus.engine.gui.SplashScreen;
import foctupus.sheeper.com.foctupus.engine.renderer.Renderer;
import foctupus.sheeper.com.foctupus.engine.renderer.Textures;

/**
 * Created by schae on 04.02.2016.
 */
public class GameManager implements Screen.ScreenListener {

    private Renderer renderer;
    private Screen screen;
    private static Background background;

    private static GameManager instance;

    private boolean adVisible;

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
        if(background != null)
            background.updateAndDraw();

        screen.draw();

        renderer.draw();
    }

    public void revalidate(boolean created)
    {
        renderer = Renderer.getInstance();

        if(created)
        {
            if (screen == null)
                startSplashScreen();
            else if(!(screen instanceof SplashScreen))
                Renderer.registerTextures();
        }

        if(background != null)
            background.revalidate();

        if(screen != null)
            screen.revalidate();
    }

    /*public void showAd()
    {
        MainActivity.showAd();
        adVisible = true;
    }

    public void hideAd()
    {
        MainActivity.hideAd();
        adVisible = false;
    }

    public void toggleAd()
    {
        if(adVisible)
        {
            MainActivity.hideAd();
            adVisible = false;
        }
        else
        {
            MainActivity.showAd();
            adVisible = true;
        }
    }*/

    private void startSplashScreen()
    {
        SplashScreen splashScreen = new SplashScreen(renderer);
        splashScreen.setScreenListener(this);
        splashScreen.setScreenImage(Textures.LOADSCREEN);
        splashScreen.setBackgroundColor(new int[]{255, 225, 166});
        splashScreen.setProgressBackgroundColor(new int[]{81, 40, 0});
        splashScreen.setProgressColor(new int[]{159, 0, 0});
        splashScreen.setLoadTask(new SplashScreen.SplashLoadTask() {
            @Override
            public void executeTask()
            {
            }
        });


        splashScreen.load(Textures.pictureNames);

        screen = splashScreen;
    }

    private void setScreen(Screen screen)
    {
        this.screen = screen;
        screen.setScreenListener(this);

        if(!(screen instanceof SplashScreen) && background == null)
            background = new Background();
    }

    public static Background getBackground()
    {
        return background;
    }


    public void onTouch(float x, float y, int mode)
    {
        screen.onTouch(x, y, mode);
    }

    @Override
    public void OnScreenFinished(Screen nextScreen) {
        setScreen(nextScreen);
    }
}
