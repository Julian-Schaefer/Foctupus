package foctupus.sheeper.com.foctupus.game.logic;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.Iterator;
import java.util.LinkedList;

import foctupus.sheeper.com.foctupus.game.renderer.Loader;
import foctupus.sheeper.com.foctupus.game.renderer.Renderer;
import foctupus.sheeper.com.foctupus.game.renderer.Sprite;
import foctupus.sheeper.com.foctupus.game.renderer.Texture;
import foctupus.sheeper.com.foctupus.game.renderer.util.Vector;
import foctupus.sheeper.com.foctupus.game.tools.Maths;

/**
 * Created by julianschafer on 23.04.16.
 */
public class Slider {

    private LinkedList<Sprite> textures;

    private int initSize = (int) Maths.toPercent(1.2, Renderer.getHeight());

    private int squareID;
    private int circleID;
    public Slider()
    {
        textures = new LinkedList<>();

        Bitmap bitmap = Bitmap.createBitmap(initSize, initSize, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);

        squareID = Loader.loadTexture(bitmap);
        circleID = Loader.loadTexture(getCircleBitmap(bitmap));

        bitmap.recycle();
    }

    public void reset()
    {
        textures.clear();
    }

    public void addPoint(float x, float y)
    {

        //if(textures.size() > 0 && Maths.lengthOf(new Vector(x,y), textures.getFirst().getPosition()) < initSize) {
        //    return;
        //}

        Sprite newCircle = new Sprite();
        newCircle.setTexture(new Texture("sliderCircle", circleID));
        newCircle.setPosition(x, y);
        newCircle.setSize(initSize, initSize);
        newCircle.setVisible(true);

        if (textures.size() > 0) {
            Sprite newSquare = calculateSquare(newCircle, textures.getFirst());
            newSquare.setTexture(new Texture("sliderSquare", squareID));
            textures.addFirst(newSquare);
            textures.addFirst(newCircle);
            resizeTextures();
        }
        else
            textures.addFirst(newCircle);


        if(textures.size() > 20)
        {
            textures.removeLast();
        }

    }

    private void resizeTextures()
    {
        Iterator<Sprite> iterator = textures.iterator();
        Sprite previous = iterator.next();
        iterator.next();


        while(iterator.hasNext())
        {
            int size = (int) Maths.toPercent(90, previous.getYSize());
            Sprite circle = iterator.next();
            circle.setSize(size, size);

            previous = circle;

            if(iterator.hasNext())
            {
                Sprite square = iterator.next();
                square.setSize(square.getXSize(), size);
            }
        }
    }

    private static Sprite calculateSquare(Sprite c1, Sprite c2)
    {

        Sprite s = new Sprite();

        double y = c1.getYPos() - c2.getYPos();
        double x = c1.getXPos() - c2.getXPos();

        Vector middle = new Vector(0.5f * (c1.getXPos() + c2.getXPos()), 0.5f * (c1.getYPos() + c2.getYPos()));
        int length = Maths.lengthOf(x, y);

        int angle = (int) Math.toDegrees(Math.atan(y / x));


        s.setSize(length, c1.getYSize());
        s.setAngle(angle);
        s.setPosition(middle.getX(), middle.getY());
        s.setVisible(true);

        return s;

    }

    public void render(Renderer renderer)
    {
        for(Sprite texture : textures)
        {
            renderer.addSprite(texture, Game.SLIDER_PRIO);
        }
    }

    private static Bitmap getCircleBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        int color = Color.RED;
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);


        return output;
    }
}


