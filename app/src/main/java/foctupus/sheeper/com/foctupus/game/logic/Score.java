package foctupus.sheeper.com.foctupus.game.logic;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;

import foctupus.sheeper.com.foctupus.engine.gui.Component;
import foctupus.sheeper.com.foctupus.engine.gui.Container;
import foctupus.sheeper.com.foctupus.engine.renderer.Loader;
import foctupus.sheeper.com.foctupus.engine.renderer.Renderer;
import foctupus.sheeper.com.foctupus.engine.renderer.Sprite;
import foctupus.sheeper.com.foctupus.engine.renderer.Texture;
import foctupus.sheeper.com.foctupus.engine.renderer.Textures;
import foctupus.sheeper.com.foctupus.engine.renderer.util.Vector;

/**
 * Created by julianschafer on 23.04.16.
 */
public class Score extends Counter {

    private Component background;

    public Score() {
        this(0);
    }

    public Score(int score) {
        super(score);

        setPriority(Game.SCORE_PRIO);
        setAdjustWidth(true);

        background = new Component(new Sprite());
        background.setRelativePosition(new Vector(50, 50));
        background.setRelativeSize(new Vector(125, 125));

        setRelativeSize(new Vector(10, 9));
        setRelativePosition(new Vector(50, 85));

        addChild(background);
    }

    @Override
    public void updateChilds()
    {
        super.updateChilds();
        generateTexture();
    }

    private void generateTexture()
    {
        Bitmap bitmap = Bitmap.createBitmap((int) background.getSprite().getXSize(),
                (int) background.getSprite().getYSize(), Bitmap.Config.ARGB_8888);

        float circleRadius = bitmap.getHeight() / 7;

        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setAlpha(110);

        canvas.drawRoundRect(new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()), circleRadius, circleRadius, paint);

        int id = Loader.loadTexture(bitmap);

        background.getSprite().setTexture(new Texture("score_background", id));

        bitmap.recycle();
    }
}
