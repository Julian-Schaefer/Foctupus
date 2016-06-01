package foctupus.sheeper.com.foctupus.engine.gui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.util.Log;

import foctupus.sheeper.com.foctupus.engine.gui.transition.Transition;
import foctupus.sheeper.com.foctupus.engine.renderer.Loader;
import foctupus.sheeper.com.foctupus.engine.renderer.Renderer;
import foctupus.sheeper.com.foctupus.engine.renderer.Sprite;
import foctupus.sheeper.com.foctupus.engine.renderer.Texture;
import foctupus.sheeper.com.foctupus.engine.renderer.Textures;
import foctupus.sheeper.com.foctupus.engine.renderer.util.Vector;
import foctupus.sheeper.com.foctupus.game.screen.StartScreen;

/**
 * Created by JSchaefer on 05.03.2016.
 */
public class SplashScreen extends Screen {

    private int loadedCount;
    private boolean loaded;
    private int toLoad;
    private boolean finished;

    private int[] rgbProgressBackground;
    private int[] rgbProgess;
    private int[] rgbBackground;
    private String screenImageName;

    private Sprite background;
    private Container screenImage;
    private Component progressBackground;
    private Component progress;

    private SplashLoadTask loadTask;

    public SplashScreen(Renderer renderer)
    {
        super(renderer);

        init();
    }

    @Override
    protected void init()
    {
        background = new Sprite();
        background.setSize(Renderer.getWidth(), Renderer.getHeight());
        background.setPosition(Renderer.getWidth()/2, Renderer.getHeight()/2);
        background.setVisible(true);

        screenImage = new Container(Renderer.getInstance());
        progressBackground = new Component();
        progress = new Component();

        addChild(screenImage);

        screenImage.addChild(progressBackground, screenImage.getPriority()-1);
        screenImage.addChild(progress, screenImage.getPriority()-1);
    }

    public void load(String[] textureNames)
    {
        toLoad = textureNames.length;
        new LoadTask().execute(textureNames);
    }

    public void setBackgroundColor(int[] rgb)
    {
        rgbBackground = rgb;
        loadSprite(background, "loadscreen_background", rgb);

        align();
    }

    public void setProgressBackgroundColor(int[] rgb)
    {
        rgbProgressBackground = rgb;
        loadSprite(progressBackground.getSprite(), "progress_background", rgb);
        align();
    }

    public void setProgressColor(int[] rgb)
    {
        rgbProgess = rgb;
        loadSprite(progress.getSprite(), "progress", rgb);
        align();
    }

    public void setScreenImage(String name) {
        screenImageName = name;
        loadScreenImage(name);

        screenImage.setRelativePosition(new Vector(50, 50));
        screenImage.setRelativeSize(new Vector(85, USE_RATIO));

        align();
    }

    private void loadScreenImage(String name)
    {
        Bitmap bitmap = Loader.decodeTexture(name);
        int screenImageId = Loader.loadTexture(bitmap);

        screenImage.getSprite().setTexture(new Texture(name, screenImageId, bitmap));
    }

    private void loadSprite(Sprite sprite, String name, int[] rgb)
    {
        Bitmap bitmap = Bitmap.createBitmap(12, 12, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawRGB(rgb[0], rgb[1], rgb[2]);

        int id = Loader.loadTexture(bitmap);

        bitmap.recycle();

        sprite.setTexture(new Texture(name, id));
    }

    private void align()
    {
        if(screenImage.getRelativePosition() != null && screenImage.getRelativeSize() != null)
        {
            progressBackground.setRelativePosition(new Vector(50, 27));

            progressBackground.setRelativeSize(new Vector(94, 19));
        }

        if(progressBackground.getRelativePosition() != null && progressBackground.getRelativeSize() != null) {
            progress.getSprite().setBottomLeftAligned(true);

            Vector backSize = progressBackground.getRelativeSize();
            Vector backPos = progressBackground.getRelativePosition();

            progress.setRelativeSize(new Vector(0, backSize.getY()));
            progress.setRelativePosition(new Vector(backPos.getX() - backSize.getX() / 2, backPos.getY() - backSize.getY() / 2));
        }

    }

    @Override
    public void revalidate()
    {
        super.revalidate();
        loadScreenImage(screenImageName);
        setBackgroundColor(rgbBackground);
        setProgressBackgroundColor(rgbProgressBackground);
        setProgressColor(rgbProgess);

        background.setPosition(Renderer.getWidth()/2, Renderer.getHeight()/2);
        background.setSize(Renderer.getWidth(), Renderer.getHeight());
    }

    @Override
    public void update() {

        super.update();

        if(progress != null && toLoad != 0)
            progress.setRelativeSize(new Vector(progressBackground.getRelativeSize().getX() * loadedCount / toLoad, progress.getRelativeSize().getY()));

        if(loaded && !finished) {
            Renderer.registerTextures();
            finished = true;
            finishScreen(new StartScreen(renderer));
        }
    }

    @Override
    public void draw()
    {
        renderer.addSprite(background, getPriority());
        super.draw();
    }

    @Override
    public void onFinished(Component component) {

    }

    @Override
    public void onTransitionFinished(Transition transition) {

    }

    public void setLoadTask(SplashLoadTask loadTask)
    {
        this.loadTask = loadTask;
    }

    private class LoadTask extends AsyncTask<String, Object, Void>
    {

        @Override
        protected Void doInBackground(String[] textureNames) {

            if(loadTask != null)
            {
                toLoad++;
                loadedCount++;

                loadTask.executeTask();
            }

            Renderer.resetTextures();
            for (String name : textureNames) {

                Bitmap bitmap = Loader.decodeTexture(name);
                Renderer.addTextureBitmap(name, bitmap);

                publishProgress();

                try {

                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Object... objects) {
            loadedCount++;

            if(loadedCount == toLoad)
                loaded = true;
        }
    }

    public interface SplashLoadTask
    {
        void executeTask();
    }



}
