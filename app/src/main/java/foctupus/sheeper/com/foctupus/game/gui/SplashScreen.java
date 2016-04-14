package foctupus.sheeper.com.foctupus.game.gui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Debug;
import android.util.Log;

import foctupus.sheeper.com.foctupus.game.gui.transition.Transition;
import foctupus.sheeper.com.foctupus.game.renderer.Loader;
import foctupus.sheeper.com.foctupus.game.renderer.Renderer;
import foctupus.sheeper.com.foctupus.game.renderer.Texture;
import foctupus.sheeper.com.foctupus.game.renderer.Textures;
import foctupus.sheeper.com.foctupus.game.renderer.util.Vector;

/**
 * Created by JSchaefer on 05.03.2016.
 */
public class SplashScreen extends Screen {

    private int loadedCount;
    private boolean loaded;
    private int toLoad;
    private boolean finished;


    private Component screenImage;
    private Component progressBackground;
    private Component progress;

    public SplashScreen(Renderer renderer)
    {
        super(renderer);

        init();
    }

    @Override
    protected void init()
    {
        getSprite().setVisible(true);

        screenImage = new Component();
        progressBackground = new Component();
        progress = new Component();

        addChild(progressBackground);
        addChild(progress);
        addChild(screenImage);
    }

    public void load(String[] textureNames)
    {
        toLoad = textureNames.length;
        new LoadTask().execute(textureNames);
    }

    public void setBackgroundColor(int[] rgb)
    {
        loadSprite(this, "loadscreen_background", rgb);
    }

    public void setProgressBackgroundColor(int[] rgb)
    {
        loadSprite(progressBackground, "progress_background", rgb);
        align();
    }

    public void setProgressColor(int[] rgb)
    {
        loadSprite(progress, "progress", rgb);
        align();
    }

    public void setScreenImage(String name) {
        loadScreenImage(name);

        screenImage.setRelativePosition(new Vector(50, 50));
        screenImage.setRelativeSize(new Vector(80, USE_RATIO));

        align();
    }

    private void loadScreenImage(String name)
    {
        Bitmap bitmap = Loader.decodeTexture(name);
        int screenImageId = Loader.loadTexture(bitmap);

        Renderer.updateTextureBitmap(name, bitmap);
        Renderer.updateTextureID(name, screenImageId);

        screenImage.getSprite().setTexture(new Texture(name, screenImageId));
    }
    
    private void loadSprite(Component component, String name, int[] rgb)
    {
        Bitmap bitmap = Bitmap.createBitmap(12, 12, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawRGB(rgb[0], rgb[1], rgb[2]);

        int id = Loader.loadTexture(bitmap);

        Renderer.updateTextureBitmap(name, bitmap);
        Renderer.updateTextureID(name, id);

        component.getSprite().setTexture(new Texture(name, id));
    }

    @Override
    public void update() {
        super.update();

        if(progress != null && toLoad != 0)
            progress.setRelativeSize(new Vector(progressBackground.getRelativeSize().getX() * loadedCount / toLoad, progress.getRelativeSize().getY()));

        if(loaded && listener != null && !finished) {
            Renderer.registerTextures();
            finished = true;
            listener.onFinished(this);
        }
    }

    private void align()
    {
        if(screenImage.getRelativePosition() != null && screenImage.getRelativeSize() != null)
        {
            progressBackground.setRelativePosition(new Vector(screenImage.getRelativePosition().getX(), screenImage.getRelativePosition().getY() - 8));
            progressBackground.setRelativeSize(new Vector(screenImage.getRelativeSize().getX() - 4, 8));
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
    public void onFinished(Component component) {

    }

    @Override
    public void onTransitionFinished(Transition transition) {

    }

    private class LoadTask extends AsyncTask<String, Object, Void>
    {

        @Override
        protected Void doInBackground(String[] textureNames) {

            Renderer.resetTextures();
            for (String name : textureNames) {

                Bitmap bitmap = Loader.decodeTexture(name);
                Renderer.updateTextureBitmap(name, bitmap);

                publishProgress();

                try {

                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Object... objects) {
            loadedCount++;

            if(loadedCount == Textures.pictureNames.length)
                loaded = true;
        }
    }


}
