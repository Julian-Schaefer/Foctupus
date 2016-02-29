package foctupus.sheeper.com.foctupus.game.screen;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.AsyncTask;

import foctupus.sheeper.com.foctupus.game.gui.IContainer;
import foctupus.sheeper.com.foctupus.game.renderer.Environment;
import foctupus.sheeper.com.foctupus.game.renderer.Loader;
import foctupus.sheeper.com.foctupus.game.renderer.Renderer;
import foctupus.sheeper.com.foctupus.game.renderer.Sprite;
import foctupus.sheeper.com.foctupus.game.renderer.Texture;
import foctupus.sheeper.com.foctupus.game.renderer.Textures;
import foctupus.sheeper.com.foctupus.game.renderer.util.RelativeVector;

/**
 * Created by schae on 05.02.2016.
 */
public class LoadScreen extends Screen {

    private static final int[] BACKGROUND_COLOR = { 255, 246, 213 };
    private static final int[] PROGRESS_BACKGROUND_COLOR = { 160, 90, 44 };
    private static final int[] PROGRESS_COLOR = { 171, 0, 0 };

    private boolean loaded;
    private int loadedCount;

    private Sprite loadScreenImage;
    private Sprite progressBackground;
    private Sprite progress;

    public LoadScreen(IContainer.Listener containerListener) {
        super(STD_PRIORITY);


        this.containerListener = containerListener;

        setup();
        init();
    }

    @Override
    public void setup() {
    }

    @Override
    public void init() {
        if(!Textures.areDecoded())
        {
            if(Textures.bitmaps != null)
                Textures.bitmaps.clear();

            if(Textures.textures != null)
                Textures.textures.clear();

            Bitmap texBackground = Bitmap.createBitmap(Renderer.getWidth(), Renderer.getHeight(), Bitmap.Config.ARGB_8888);
            String texBackgroundName = "loadscreen_background";

            Canvas canvas = new Canvas(texBackground);
            canvas.drawRGB(BACKGROUND_COLOR[0], BACKGROUND_COLOR[1], BACKGROUND_COLOR[2]);

            int texBackgroundID = Loader.loadTexture(texBackground);

            setTexture(new Texture(texBackgroundName, texBackgroundID));
            setVisible(true);


            Bitmap texLoadScreenImage = Loader.decodeTexture(Textures.LOADSCREEN);
            int loadScreenImageID = Loader.loadTexture(texLoadScreenImage);
            loadScreenImage = new Sprite();
            loadScreenImage.setTexture(new Texture(Textures.LOADSCREEN, loadScreenImageID));
            addChild(loadScreenImage, new RelativeVector(50, 50), new RelativeVector(50, RelativeVector.RATIO));



            Bitmap texProgressBackground = Bitmap.createBitmap(Renderer.getWidth(), Renderer.getHeight(), Bitmap.Config.ARGB_8888);
            String texProgressBackgroundName = "progress_background";

            canvas = new Canvas(texProgressBackground);
            canvas.drawRGB(PROGRESS_BACKGROUND_COLOR[0], PROGRESS_BACKGROUND_COLOR[1], PROGRESS_BACKGROUND_COLOR[2]);

            int texProgressBackgroundID= Loader.loadTexture(texProgressBackground);

            progressBackground = new Sprite();
            progressBackground.setTexture(new Texture(texProgressBackgroundName, texProgressBackgroundID));
            addChild(progressBackground, new RelativeVector(10, 50), new RelativeVector(50, 50));


            Bitmap texProgress = Bitmap.createBitmap(Renderer.getWidth(), Renderer.getHeight(), Bitmap.Config.ARGB_8888);
            String texProgressName = "progress";

            canvas = new Canvas(texProgress);
            canvas.drawRGB(PROGRESS_COLOR[0], PROGRESS_COLOR[1], PROGRESS_COLOR[2]);

            int tex_progress_id = Loader.loadTexture(texProgress);
            progress = new Sprite();
            progress.setTexture(new Texture(texProgressName, tex_progress_id));
            progress.setBottomLeftAligned(true);
            addChild(progress, new RelativeVector(50, 10), new RelativeVector(0, 20));


            new LoadTask().execute();
        }
        else
        {
            loaded = true;
        }
    }

    @Override
    public void update() {
        super.update();

        if(progress != null)
            progress.setSize(new RelativeVector(progressBackground.getXSize()/Textures.pictureNames.length*loadedCount,
                progress.getYSize()));

        if(loaded)
        {
            Loader.registerTextures();

            if(containerListener != null)
                containerListener.onFinished(this);
        }
    }


    private class LoadTask extends AsyncTask<Void, Object, Void>
    {
        @Override
        protected Void doInBackground(Void... params) {

            for (String name : Textures.pictureNames) {

                Bitmap texture = Loader.decodeTexture(name);
                Textures.bitmaps.put(name, texture);
                //Textures.ratios.put(name, Loader.getRatio(texture));

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
            loadedCount++;

            if(loadedCount == Textures.pictureNames.length)
                loaded = true;
        }
    }

}
