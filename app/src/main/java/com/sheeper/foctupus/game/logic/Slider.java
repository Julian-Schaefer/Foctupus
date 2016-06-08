package com.sheeper.foctupus.game.logic;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import com.sheeper.foctupus.engine.renderer.Loader;
import com.sheeper.foctupus.engine.renderer.Renderer;
import com.sheeper.foctupus.engine.renderer.Sprite;
import com.sheeper.foctupus.engine.renderer.StaticSpriteList;
import com.sheeper.foctupus.engine.renderer.Texture;
import com.sheeper.foctupus.engine.renderer.util.Vector;
import com.sheeper.foctupus.game.tools.Maths;

import java.util.Iterator;

/**
 * Created by julianschafer on 23.04.16.
 */
public class Slider {

    private StaticSpriteList circles;
    private StaticSpriteList squares;

    private int initSize = (int) Maths.toPercent(1.2, Renderer.getHeight());
    private float minSize = initSize/2f;

    private int squareID;
    private int circleID;

    public Slider()
    {
        loadTextures();

        circles = new StaticSpriteList(new Texture("slider_circle", circleID));
        squares = new StaticSpriteList(new Texture("slider_square", squareID));
    }

    private void loadTextures()
    {
        Bitmap bitmap = Bitmap.createBitmap(initSize, initSize, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);

        squareID = Loader.loadTexture(bitmap);
        circleID = Loader.loadTexture(getCircleBitmap(bitmap));

        bitmap.recycle();
    }

    public void revalidate()
    {
        loadTextures();

        circles = new StaticSpriteList(new Texture("slider_circle", circleID));
        squares = new StaticSpriteList(new Texture("slider_square", squareID));
    }

    public void reset()
    {
        circles.clear();
        squares.clear();
    }

    public void addPoint(float x, float y)
    {
        if(circles.size() >  0)
        {
            Sprite last = circles.getFirst();
            calculatePoint(last.getActualXPos() + (x - last.getActualXPos()) / 2, last.getActualYPos() + (y - last.getActualYPos()) / 2);
            resizeSprites();
        }

        calculatePoint(x, y);
        resizeSprites();
    }

    private void calculatePoint(float x, float y)
    {
        Sprite newCircle = new Sprite();
        newCircle.setTexture(new Texture("sliderCircle", circleID));
        newCircle.setPosition(x, y);
        newCircle.setSize(initSize, initSize);
        newCircle.setVisible(true);

        if (circles.size() > 0) {
            Sprite newSquare = calculateSquare(newCircle, circles.getFirst());
            newSquare.setTexture(new Texture("sliderSquare", squareID));
            squares.addFirst(newSquare);
            circles.addFirst(newCircle);
        }
        else
            circles.addFirst(newCircle);
    }

    private void resizeSprites()
    {
        if(circles.size() > 0)
        {
            Iterator<Sprite> circleIterator = circles.iterator();
            Iterator<Sprite> squareIterator = squares.iterator();

            Sprite previous = circleIterator.next();

            while (circleIterator.hasNext() && squareIterator.hasNext())
            {
                float size = (float) Maths.toPercent(95, previous.getYSize());

                Sprite circle = circleIterator.next();
                Sprite square = squareIterator.next();

                previous = circle;

                if (size >= minSize)
                {
                    circle.setSize(size, size);
                    square.setSize(square.getXSize(), size);
                } else
                {
                    squareIterator.remove();
                    circleIterator.remove();
                }
            }
        }
    }

    private static Sprite calculateSquare(Sprite c1, Sprite c2)
    {

        Sprite square = new Sprite();

        double x = c1.getXPos() - c2.getXPos();
        double y = c1.getYPos() - c2.getYPos();

        Vector middle = new Vector(0.5f * (c1.getXPos() + c2.getXPos()), 0.5f * (c1.getYPos() + c2.getYPos()));
        int length = Maths.lengthOf(x, y);

        int angle = (int) Math.toDegrees(Math.atan(y / x));


        square.setSize(length, c1.getYSize());
        square.setAngle(angle);
        square.setPosition(middle.getX(), middle.getY());
        square.setVisible(true);

        return square;
    }

    public void draw(Renderer renderer)
    {
        renderer.addSpriteList(circles, Game.SLIDER_PRIO);
        renderer.addSpriteList(squares, Game.SLIDER_PRIO);
    }

    private static Bitmap getCircleBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);


        return output;
    }
}


