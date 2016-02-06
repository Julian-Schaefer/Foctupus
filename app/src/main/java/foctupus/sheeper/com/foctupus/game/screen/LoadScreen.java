package foctupus.sheeper.com.foctupus.game.screen;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.AsyncTask;

import java.util.Map;

import foctupus.sheeper.com.foctupus.game.MyGLRenderer;
import foctupus.sheeper.com.foctupus.game.renderer.Container;
import foctupus.sheeper.com.foctupus.game.renderer.ContainerListener;
import foctupus.sheeper.com.foctupus.game.renderer.Environment;
import foctupus.sheeper.com.foctupus.game.renderer.Loader;
import foctupus.sheeper.com.foctupus.game.renderer.Sprite;
import foctupus.sheeper.com.foctupus.game.renderer.StaticContainer;
import foctupus.sheeper.com.foctupus.game.renderer.Textures;
import foctupus.sheeper.com.foctupus.game.tools.RelativeVector;
import foctupus.sheeper.com.foctupus.game.tools.Vector;

/**
 * Created by schae on 05.02.2016.
 */
public class LoadScreen extends StaticContainer {

    private static final int[] BACKGROUND_COLOR = { 255, 246, 213 };
    private static final int[] PROGRESS_BACKGROUND_COLOR = { 160, 90, 44 };
    private static final int[] PROGRESS_COLOR = { 171, 0, 0 };

    private int loaded;

    private Sprite loadScreenImage;
    private Sprite progressBackground;
    private Sprite progress;

    public LoadScreen(ContainerListener containerListener) {
        super(STD_PRIORITY);

        this.containerListener = containerListener;

        setup();
        init();
    }

    @Override
    public void setup() {
        setBottomLeftAligned(true);
        setPosition(0, 0);
        setSize(Environment.width, Environment.height);
    }

    @Override
    public void init() {
        if(Textures.ratios == null || Textures.ratios.size() != Textures.pictureNames.length ||
                Textures.bitmaps == null || Textures.bitmaps.size() != Textures.pictureNames.length)
        {

            Bitmap texBackground = Bitmap.createBitmap(Environment.width, Environment.height, Bitmap.Config.ARGB_8888);
            String texBackgroundName = "loadscreen_background";

            Canvas canvas = new Canvas(texBackground);
            canvas.drawRGB(BACKGROUND_COLOR[0], BACKGROUND_COLOR[1], BACKGROUND_COLOR[2]);

            int texBackgroundID = Loader.loadTexture(texBackground);

            setTextureID(texBackgroundID, texBackgroundName);
            setVisible(true);


            Bitmap texLoadScreenImage = Loader.decodeTexture(Textures.LOADSCREEN);
            int loadScreenImageID = Loader.loadTexture(texLoadScreenImage);
            loadScreenImage = new Sprite();
            loadScreenImage.setTextureID(loadScreenImageID, Textures.LOADSCREEN);
            addChild(loadScreenImage, new RelativeVector(50, 50), new RelativeVector(50, RelativeVector.RATIO));



            Bitmap texProgressBackground = Bitmap.createBitmap(Environment.width, Environment.height, Bitmap.Config.ARGB_8888);
            String texProgressBackgroundName = "progress_background";

            canvas = new Canvas(texProgressBackground);
            canvas.drawRGB(PROGRESS_BACKGROUND_COLOR[0], PROGRESS_BACKGROUND_COLOR[1], PROGRESS_BACKGROUND_COLOR[2]);

            int texProgressBackgroundID= Loader.loadTexture(texProgressBackground);

            progressBackground = new Sprite();
            progressBackground.setTextureID(texProgressBackgroundID, texProgressBackgroundName);
            addChild(progressBackground, new RelativeVector(10, 50), new RelativeVector(50, 50));


            Bitmap texProgress = Bitmap.createBitmap(Environment.width, Environment.height, Bitmap.Config.ARGB_8888);
            String texProgressName = "progress";

            canvas = new Canvas(texProgress);
            canvas.drawRGB(PROGRESS_COLOR[0], PROGRESS_COLOR[1], PROGRESS_COLOR[2]);

            int tex_progress_id = Loader.loadTexture(texProgress);
            progress = new Sprite();
            progress.setTextureID(tex_progress_id, texProgressName);
            progress.setBottomLeftAligned(true);
            addChild(progress, new RelativeVector(50, 10), new RelativeVector(0, 20));


            new LoadTask().execute();
        }
        else
        {
            registerTextures();
        }
    }

    @Override
    public void update() {
        super.update();

        if(progress != null)
            progress.setRelativeSize(new RelativeVector(progressBackground.getRelativeSize().getRelativeX()/Textures.pictureNames.length*loaded,
                progress.getRelativeSize().getRelativeY()));

        if(loaded == Textures.pictureNames.length)
        {
            registerTextures();
        }
    }

    private void registerTextures()
    {
        for(Map.Entry<String, Bitmap> entry : Textures.bitmaps.entrySet())
        {
            Textures.textures.put(entry.getKey(), Loader.loadTexture(entry.getValue()));
        }

        if(containerListener != null)
            containerListener.onFinished(this);
    }


    protected class LoadTask extends AsyncTask<Void, Object, Void>
    {
        @Override
        protected Void doInBackground(Void... params) {

            for (String name : Textures.pictureNames) {

                Bitmap texture = Loader.decodeTexture(name);
                Textures.bitmaps.put(name, texture);
                Textures.ratios.put(name, Loader.getRatio(texture));

                publishProgress();

                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Object... objects) {
            loaded++;
        }
    }

}
