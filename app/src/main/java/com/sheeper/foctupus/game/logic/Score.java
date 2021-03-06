package com.sheeper.foctupus.game.logic;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.sheeper.foctupus.engine.gui.Component;
import com.sheeper.foctupus.engine.renderer.Loader;
import com.sheeper.foctupus.engine.renderer.Sprite;
import com.sheeper.foctupus.engine.renderer.Texture;
import com.sheeper.foctupus.engine.renderer.util.Vector;

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

        setRelativeSize(new Vector(0, 9));
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
        if(background.getSprite().getXSize() > 0 && background.getSprite().getYSize() > 0)
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
}
