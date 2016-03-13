package foctupus.sheeper.com.foctupus.game.gui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.AsyncTask;
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


    private Component loadScreenImage;
    private Component progressBackground;
    private Component progress;

    public SplashScreen(Renderer renderer) {
        super(renderer);

        loadScreenImage = new Component();
        progressBackground = new Component();
        progress = new Component();

        init();

        addChild(loadScreenImage);
        addChild(progressBackground);
        addChild(progress);
    }

    @Override
    protected void init() {
        //loadScreenImage.setRelativePosition(new Vector(50, 50));
        //loadScreenImage.setRelativeSize(new Vector(50, Component.USE_RATIO));

        progressBackground.setRelativePosition(new Vector(10, 50));
        progressBackground.setRelativeSize(new Vector(50, 50));

        progress.setRelativePosition(new Vector(10, 50));
        progress.setRelativeSize(new Vector(0, 40));

    }

    public void load(String[] textureNames)
    {
        Log.i("musslade,", "geladnse");
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
    }

    public void setProgressColor(int[] rgb)
    {
        loadSprite(progress, "progress", rgb);
    }


    private void loadSprite(Component component, String name, int[] rgb)
    {
        Bitmap bitmap = Bitmap.createBitmap(12, 12, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawRGB(rgb[0], rgb[1], rgb[2]);

        int id = Loader.loadTexture(bitmap);
        component.getSprite().setTexture(new Texture(name, id));

        Renderer.updateTextureBitmap(name, bitmap);
        Renderer.updateTextureID(name, id);
    }

    @Override
    public void update() {
        super.update();

        if(progress != null && toLoad != 0)
            progress.setRelativeSize(new Vector((float) 50 * loadedCount / toLoad, progress.getRelativeSize().getY()));

        if(loaded && listener != null && !finished) {
            Renderer.registerTextures();
            finished = true;
            listener.onFinished(this);
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

            if(loadedCount == Textures.pictureNames.length)
                loaded = true;
        }
    }


}
