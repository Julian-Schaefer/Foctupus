package foctupus.sheeper.com.foctupus.game.logic;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;

import java.util.LinkedList;
import java.util.ListIterator;

import foctupus.sheeper.com.foctupus.engine.renderer.Loader;
import foctupus.sheeper.com.foctupus.engine.renderer.Renderer;
import foctupus.sheeper.com.foctupus.engine.renderer.Sprite;
import foctupus.sheeper.com.foctupus.engine.renderer.StaticSpriteList;
import foctupus.sheeper.com.foctupus.engine.renderer.Texture;
import foctupus.sheeper.com.foctupus.engine.renderer.util.BoundingBox;
import foctupus.sheeper.com.foctupus.game.tools.Function;
import foctupus.sheeper.com.foctupus.game.tools.Maths;
import foctupus.sheeper.com.foctupus.engine.renderer.util.Vector;

/**
 * Created by schae on 24.11.2015.
 */
public class Tentacle extends StaticSpriteList {


    private static final int ANIMATE_OUT_TIME = 700;
    private static final float END_SIZE = 2.5f;
    private static final float SHRINK_FACTOR = 0.98f;

    private TentacleListener listener;

    private static float distance = 20;

    private int startX, startY;
    private float dirX, dirY;
    private float divX, divY;

    private Treasure treasure;
    private Vector[] way;


    private double endSize;
    private double currentSize;

    private boolean finished = false;
    private boolean isOut = false;
    private int splitter = -1;
    private int animateOutPos = 0;

    private int currentPos = 0;

    private int animationTime;
    private long startTime;

    private boolean leftOrRight = true;

    public Tentacle(Treasure treasure, int animationTime)
    {
        super();

        this.treasure = treasure;
        this.animationTime = animationTime;

        endSize = Maths.toPercent(END_SIZE, Renderer.getHeight());

        setRandomLocation();
        calc();

        double diff = endSize - (endSize * SHRINK_FACTOR);
        currentSize = endSize + way.length * diff;

        createTexture();

        add(getNewPart(0));

        startTime = System.currentTimeMillis();
    }



    public void update()
    {
        if(!finished) {

            long diff = System.currentTimeMillis() - startTime;

            if(diff <= animationTime)
            {
                int toPosition = (int) (way.length * diff/ (double) animationTime);
                int positionDif = toPosition - currentPos;

                for(int i = 0; i < positionDif; i++)
                {
                    if(currentPos < way.length) {
                        addFirst(getNewPart(currentPos++));
                    }
                }

                if(treasure.intersects(((Sprite) getFirst()).getPosition())) {
                    finished = true;
                    if (listener != null)
                        listener.hasFinished();
                }
            }
            else
            {
                finished = true;
                if (listener != null)
                    listener.hasFinished();
            }


        }
        else {
            animateOut();
        }
    }

    public void animateOut()
    {
        if(!isOut && splitter != -1) {

            long diff = System.currentTimeMillis() - startTime;

            int toPosition = (int) ((currentPos - splitter) * diff / (double) ANIMATE_OUT_TIME);
            int toRemove = toPosition - animateOutPos;

            for (int i = 0; i < toRemove; i++) {
                if (splitter < size()) {
                    remove(splitter);
                    animateOutPos++;
                }
            }

            if(diff > ANIMATE_OUT_TIME)
            {
                clear();
                isOut = true;
            }

        }
    }

    public boolean isOutside()
    {
        return isOut;
    }

    public void checkPoints(Vector first, Vector second)
    {
        if(!finished && first != null && second != null) {
            boolean tFinished = false;
            int tSplitter = -1;

            Function fFirst = Maths.createFunction(first, second);
            BoundingBox bounds = new BoundingBox(first, second);

            int i = 0;

            ListIterator<Sprite> iterator = (ListIterator) iterator();
            while(iterator.hasNext())
            {
                Sprite tFirst = iterator.next();
                if(iterator.hasNext()) {
                    Sprite tSecond = iterator.next();

                    float left, right, top, bottom;
                    float padding = tFirst.getYSize() / 2;

                    if (tFirst.getXPos() > tSecond.getXPos()) {
                        left = tSecond.getXPos() - padding;
                        right = tFirst.getXPos() + padding;
                    } else {
                        left = tFirst.getXPos() - padding;
                        right = tSecond.getXPos() + padding;
                    }

                    if (tFirst.getYPos() > tSecond.getYPos()) {
                        top = tFirst.getYPos() + padding;
                        bottom = tSecond.getYPos() - padding;
                    } else {
                        top = tSecond.getYPos() + padding;
                        bottom = tFirst.getYPos() - padding;
                    }

                    BoundingBox cBounds = new BoundingBox(left, right, bottom, top);


                    Function fSecond = Maths.createFunction(tFirst.getPosition(), tSecond.getPosition());

                    Vector intercept = Maths.interception(fFirst, fSecond);


                    if (bounds.isPointInside(intercept) && cBounds.isPointInside(intercept)) {
                        tSplitter = i;
                        tFinished = true;
                        break;
                    } else if ((Maths.lengthOf(intercept, first) <= padding && cBounds.isPointInside(first)) ||
                            (Maths.lengthOf(intercept, second) <= padding) && cBounds.isPointInside(second)) {
                        tSplitter = i;
                        tFinished = true;
                        break;
                    }

                    iterator.previous();
                    i++;
                }
            }

            if(tSplitter != -1 && tFinished)
            {
                iterator.previous();
                Sprite sizer = iterator.next();

                //if(game.swipeLength() >= sizer.getYSize())
                //{
                finished = true;
                splitter = tSplitter;
                startTime = System.currentTimeMillis();

                listener.isCut(this);
                //}
            }

        }
    }


    public void calc() {
        dirX = Renderer.getWidth() / 2 - startX;
        dirY = Renderer.getHeight() / 2 - startY;


        int divisions = Maths.randInt(3, 5);
        divX = dirX / divisions;
        divY = dirY / divisions;

        double length = Math.sqrt(dirX * dirX + dirY * dirY);
        dirX = (float) (dirX / length);
        dirY = (float) (dirY / length);


        Function[] functions = new Function[divisions];

        boolean isLeft = true;

        int maxDeflection = 0;

        for (int i = 0; i < divisions; i++) {

            int deflection = Maths.randInt(Renderer.getHeight() / 50, Renderer.getHeight() / 35);

            if(deflection > maxDeflection)
                maxDeflection = deflection;

            if (isLeft)
            {
                isLeft = false;
                deflection *= -1;
            }
            else
            {
                isLeft = true;
            }

            float funcB = deflection;
            float funcA = (-funcB) / (float)Math.pow((Maths.lengthOf(divX, divY)) / 2, 2);

            functions[i] = new Function(funcA, funcB);
        }

        calcDistance(maxDeflection, length / divisions);


        LinkedList<Vector> wayPositions = new LinkedList<>();
        wayPositions.add(new Vector(startX, startY));

        int width = Maths.lengthOf(divX, divY);
        int left = width / 2;

        float currentX = startX;
        float currentY = startY;


        int totalLength = width * divisions;
        int current = 1;
        int last = 0;

        Function lastFunc = functions[last / width];

        while(current < totalLength)
        {
            Function func = functions[current / width];

            if (Maths.lengthOf(current - last, func.valueAt((current % width) - left, 2) - lastFunc.valueAt((last % width) - left, 2)) > distance)
            {
                isLeft = true;
                if (func.getA() >= 0)
                    isLeft = false;

                Vector point = getPointFromDeflection(new Vector(currentX, currentY), (int) func.valueAt((current % width) - left, 2), isLeft);
                wayPositions.add(point);

                last = current;
                lastFunc = functions[last / width];
            }

            current++;
            currentX += dirX;
            currentY += dirY;
        }

        wayPositions.add(new Vector(Renderer.getWidth() / 2, Renderer.getHeight() / 2));

        way = wayPositions.toArray(new Vector[wayPositions.size()]);


        if(leftOrRight)
            animationTime *= 2;

    }

    private Sprite getNewPart(int pos)
    {
        Vector pos1 = way[pos];
        Vector pos2 = way[pos + 1];


        float xPos = pos1.getX() + ((pos2.getX() - pos1.getX())/2);
        float yPos = pos1.getY() + ((pos2.getY() - pos1.getY())/2);

        float angle = (float) Math.toDegrees(Math.atan((pos2.getY() - pos1.getY()) / (pos2.getX() - pos1.getX())));

        if(angle < 0)
            angle += 180;

        int distance = Maths.lengthOf(pos1, pos2);


        Sprite tPart = new Sprite();
        tPart.setSize(distance + (int) currentSize, (int) currentSize);
        currentSize *= SHRINK_FACTOR;
        tPart.setPosition(xPos, yPos);
        tPart.setAngle(angle);
        tPart.setVisible(true);

        return tPart;
    }

    private void calcDistance(int maxDeflection, double pDistance)
    {
        /*
        Hier ist die Version mit der Punkt zu Punkt distanz (Pythagoras)

        int percentDistance = (int) Maths.toPercent(2.6, Renderer.norm);


        double x = (double) pDistance/2;
        double y = maxDef;

        double a = Math.pow(x, 2);
        double b = Math.pow(y, 2);
        double c = Math.pow(percentDistance, 2);

        double div = (a + b)/c;

        distance = (int) (x / div);

        */

        double percentDistance;

        if(leftOrRight)
            percentDistance = Maths.toPercent(2.4, Renderer.getWidth());
        else
            percentDistance = Maths.toPercent(1.2, Renderer.getHeight());

        double x = maxDeflection / percentDistance;
        distance = (float) ((pDistance/2) / x);
    }

    private Vector getPointFromDeflection(Vector point, double deflection, boolean left)
    {
        float eX = point.getX();
        float eY = point.getY();

        Vector normal = new Vector(-divY, divX);

        double normalSum = Math.pow(normal.getX(), 2) + Math.pow(normal.getY(), 2);
        deflection = Math.pow(deflection, 2);

        float multiplicator = (float) Math.sqrt(deflection / normalSum);

        if(left)
            return new Vector(eX - (normal.getX() * multiplicator), eY - (normal.getY() * multiplicator));
        else
            return new Vector(eX + (normal.getX() * multiplicator), eY + (normal.getY() * multiplicator));
    }

    private void setRandomLocation()
    {
        int random = Maths.randInt(1, 6);

        int size = (int) endSize;

        int x = 0, y = 0;
        switch (random)
        {
            //Unten
            case 1:
                y = -2 * size;
                x = Maths.randInt(-size, Renderer.getWidth() + size);
                leftOrRight = false;
                break;
            //Oben
            case 2:
                y = Renderer.getHeight() + 2 * size;
                x = Maths.randInt(-size, Renderer.getWidth() + size);
                leftOrRight = false;
                break;
            //UntenRechts
            case 3:
                y = Maths.randInt(-size, Renderer.getHeight()/4 - size);
                x = Renderer.getWidth() + 2 * size;
                break;

            //UntenLinks
            case 4:
                y = Maths.randInt(-size, Renderer.getHeight()/4 - size);
                x = -2 * size;
                break;
            //ObenRechts
            case 5:
                y = Maths.randInt(Renderer.getHeight()/2 + Renderer.getHeight()/4 + size, Renderer.getHeight() + size);
                x = Renderer.getWidth() + 2 * size;
                break;
            //ObenLinks
            case 6:
                y = Maths.randInt(Renderer.getHeight()/2 + Renderer.getHeight()/4 + size, Renderer.getHeight() + size);
                x = -2 * size;
                break;
        }

        startX = x;
        startY = y;
    }

    public void createTexture()
    {
        Bitmap bitmap = Bitmap.createBitmap((int) (distance + endSize), (int) endSize, Bitmap.Config.ARGB_8888);

        int padding = (int) Maths.toPercent(12, bitmap.getHeight());

        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        paint.setAntiAlias(true);
        paint.setShader(new LinearGradient(0, 0, 0, bitmap.getHeight()*3, Color.RED, Color.BLACK, Shader.TileMode.MIRROR));
        //paint.setColor(Color.RED);

        Canvas canvas = new Canvas(bitmap);

        canvas.drawCircle(bitmap.getHeight() / 2, bitmap.getHeight() / 2, bitmap.getHeight() / 2, paint);
        canvas.drawCircle(bitmap.getWidth() - bitmap.getHeight() / 2, bitmap.getHeight() / 2, bitmap.getHeight() / 2, paint);

        canvas.drawRect(bitmap.getHeight() / 2, bitmap.getHeight(), bitmap.getWidth() - bitmap.getHeight() / 2, 0, paint);

        //paint.setColor(Color.RED);

        //canvas.drawCircle(bitmap.getHeight() / 2, bitmap.getHeight() / 2, (bitmap.getHeight() / 2) - padding, paint);
        //canvas.drawRect(bitmap.getHeight() / 2, bitmap.getHeight() - padding, bitmap.getWidth() - bitmap.getHeight() / 2, padding, paint);
        //canvas.drawCircle(bitmap.getWidth() - bitmap.getHeight() / 2, bitmap.getHeight() / 2, (bitmap.getHeight() / 2) - padding, paint);


/*

        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawCircle(bitmap.getWidth()/2, bitmap.getHeight()/2, bitmap.getHeight()/2, paint);


        paint = new Paint();
        paint.setFilterBitmap(true);
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);

        canvas.drawCircle(bitmap.getWidth()/2, bitmap.getHeight()/2, (bitmap.getHeight()/2) - 10, paint);
*/

        setTexture(new Texture("tentacle", Loader.loadTexture(bitmap)));
    }

    public void setListener(TentacleListener l)
    {
        listener = l;
    }

    public Vector[] getWay()
    {
        return way;
    }

    public interface TentacleListener
    {
        void isCut(Tentacle t);
        void hasFinished();
    }
}
