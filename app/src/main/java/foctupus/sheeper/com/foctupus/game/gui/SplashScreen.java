package foctupus.sheeper.com.foctupus.game.gui;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import foctupus.sheeper.com.foctupus.game.renderer.Loader;
import foctupus.sheeper.com.foctupus.game.renderer.Renderer;
import foctupus.sheeper.com.foctupus.game.renderer.Textures;

/**
 * Created by JSchaefer on 05.03.2016.
 */
public class SplashScreen extends Screen {

    private int loadedCount;
    private boolean loaded;

    public SplashScreen(Renderer renderer) {
        super(renderer);
    }

    @Override
    protected void init() {

    }

    public void load(String[] textureNames)
    {

    }

    public void setBackgroundColor(int r, int g, int b)
    {

    }

    public void setProgressBackgroundColor(int r, int g, int b)
    {

    }

    public void setProgressBackground(int r, int g, int b)
    {

    }

    @Override
    public void update() {
        super.update();

        if(loaded)
        {

        }
    }

    private class LoadTask extends AsyncTask<Void, Object, Void>
    {
        @Override
        protected Void doInBackground(Void... params) {

            for (String name : Textures.pictureNames) {

                Bitmap texture = Loader.decodeTexture(name);
                Textures.bitmaps.put(name, texture);

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
