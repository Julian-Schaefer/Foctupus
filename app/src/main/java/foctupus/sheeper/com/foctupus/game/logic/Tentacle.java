package foctupus.sheeper.com.foctupus.game.logic;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.Log;

import java.util.HashMap;
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

    public enum Position {
        LEFT, RIGHT, BOTTOM, TOP
    }

    private static LinkedList<TentacleWay> leftTentacles = new LinkedList<>();
    private static LinkedList<TentacleWay> rightTentacles = new LinkedList<>();
    private static LinkedList<TentacleWay> bottomTentacles = new LinkedList<>();
    private static LinkedList<TentacleWay> topTentacles = new LinkedList<>();

    private static final int ANIMATE_OUT_TIME = 700;
    private static final float END_SIZE = 2.5f;
    private static final float SHRINK_FACTOR = 0.98f;

    private TentacleListener listener;

    private int startX, startY;
    private float dirX, dirY;
    private float divX, divY;

    private Treasure treasure;

    private TentacleWay way;
    private LinkedList<Vector> positions;
    private float distance;

    private float endSize;
    private float currentSize;

    private boolean finished = false;
    private boolean isOut = false;
    private boolean isCut = false;

    private int animateOutPos = 0;
    private int currentPos = 0;

    private int animationTime;
    private long startTime;

    private Position position;

    public Tentacle(Treasure treasure, int animationTime, Position position)
    {
        super();

        this.treasure = treasure;
        this.animationTime = animationTime;
        this.position = position;

        switch (position)
        {
            case LEFT: way = leftTentacles.get(Maths.randInt(0, leftTentacles.size()-1)); break;
            case RIGHT: way = rightTentacles.get(Maths.randInt(0, rightTentacles.size()-1)); break;
            case BOTTOM: way = bottomTentacles.get(Maths.randInt(0, bottomTentacles.size()-1)); break;
            case TOP: way = topTentacles.get(Maths.randInt(0, topTentacles.size()-1)); break;
        }

        distance = way.getDistance();
        positions = way.getPositions();

        endSize = (float) Maths.toPercent(END_SIZE, Renderer.getHeight());

        float diff = endSize - (endSize * SHRINK_FACTOR);
        currentSize = endSize + positions.size() * diff;

        createTexture();

        add(getNewPart(0));

        startTime = System.currentTimeMillis();
    }



    public void update()
    {
        long diff = System.currentTimeMillis() - startTime;

        if(!finished)
        {
            if(diff <= animationTime)
            {
                int toPosition = (int) (positions.size() * diff/ (double) animationTime);
                int positionDif = toPosition - currentPos;

                for(int i = 0; i < positionDif; i++)
                {
                    if(currentPos < positions.size())
                        addFirst(getNewPart(currentPos++));
                }

                if(treasure.intersects((getFirst()).getPosition()))
                    finish();
            }
            else
            {
                finish();
            }
        }
        else
        {
            if(!isOut && isCut)
            {
                int toPosition = (int) ((currentPos) * diff / (double) ANIMATE_OUT_TIME);
                int toRemove = toPosition - animateOutPos;

                for (int i = 0; i < toRemove; i++) {
                    if(!isEmpty())
                    {
                        removeFirst();
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
    }

    private void finish()
    {
        finished = true;
        listener.hasFinished(this);
    }

    private void cut()
    {
        finished = true;
        isCut = true;
        startTime = System.currentTimeMillis();
        listener.isCut(this);
    }

    public boolean isOutside()
    {
        return isOut;
    }

    public void checkPoints(Vector first, Vector second)
    {
        if(!finished && first != null && second != null)
        {
            Function fFirst = Maths.createFunction(first, second);
            BoundingBox bounds = new BoundingBox(first, second);

            ListIterator<Sprite> iterator = (ListIterator) iterator();
            while(iterator.hasNext())
            {
                Sprite tFirst = iterator.next();
                if(iterator.hasNext())
                {
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

                    if (bounds.isPointInside(intercept) && cBounds.isPointInside(intercept))
                    {
                        cut();
                        break;
                    }
                    else if ((Maths.lengthOf(intercept, first) <= padding && cBounds.isPointInside(first)) ||
                            (Maths.lengthOf(intercept, second) <= padding) && cBounds.isPointInside(second))
                    {
                        cut();
                        break;
                    }

                    iterator.previous();
                }
            }
        }
    }

    public void createTexture()
    {
        Bitmap bitmap = Bitmap.createBitmap((int) (distance + currentSize), (int) currentSize, Bitmap.Config.ARGB_8888);

        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        paint.setAntiAlias(true);
        paint.setShader(new LinearGradient(0, 0, 0, bitmap.getHeight()*3, Color.RED, Color.BLACK, Shader.TileMode.MIRROR));

        Canvas canvas = new Canvas(bitmap);

        canvas.drawCircle(bitmap.getHeight() / 2, bitmap.getHeight() / 2, bitmap.getHeight() / 2, paint);
        canvas.drawCircle(bitmap.getWidth() - bitmap.getHeight() / 2, bitmap.getHeight() / 2, (bitmap.getHeight() / 2) * SHRINK_FACTOR, paint);


        float shrink = (bitmap.getHeight() - (bitmap.getHeight() * SHRINK_FACTOR)) / 2;

        Path path = new Path();
        path.moveTo(bitmap.getHeight() / 2, 0);
        path.lineTo(bitmap.getHeight() / 2, bitmap.getHeight());
        path.lineTo(bitmap.getWidth() - bitmap.getHeight() / 2, bitmap.getHeight() - shrink);
        path.lineTo(bitmap.getWidth() - bitmap.getHeight() / 2, shrink);
        path.lineTo(bitmap.getHeight() / 2, 0);
        path.close();

        canvas.drawPath(path, paint);

        setTexture(new Texture("tentacle", Loader.loadTexture(bitmap)));
    }


    public static TentacleWay calculateTentacle(Position position)
    {
        int size = (int) Maths.toPercent(END_SIZE, Renderer.getHeight());

        int startX, startY;

        switch (position)
        {
            case LEFT:
                startX = -2 * size;
                startY = Maths.randInt(-size, Renderer.getHeight() + size);
                break;
            case RIGHT:
                startX = Renderer.getWidth() + (2 * size);
                startY = Maths.randInt(-size, Renderer.getHeight() + size);
                break;
            case BOTTOM:
                startX = Maths.randInt(-size, Renderer.getWidth() + size);
                startY = -2 * size;
                break;
            case TOP:
                startX = Maths.randInt(-size, Renderer.getWidth() + size);
                startY = Renderer.getHeight() + (2 * size);
                break;
            default:
                startX = Maths.randInt(-size, Renderer.getWidth() + size);
                startY = -2 * size;
        }

        float dirX = Renderer.getWidth() / 2 - startX;
        float dirY = Renderer.getHeight() / 2 - startY;


        int divisions = Maths.randInt(3, 5);
        float divX = dirX / divisions;
        float divY = dirY / divisions;

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

        float distance = calcDistance(maxDeflection, length / divisions, position);

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

                Vector point = getPointFromDeflection(new Vector(currentX, currentY), new Vector(-divY, divX), (int) func.valueAt((current % width) - left, 2), isLeft);
                wayPositions.add(point);

                last = current;
                lastFunc = functions[last / width];
            }

            current++;
            currentX += dirX;
            currentY += dirY;
        }

        wayPositions.add(new Vector(Renderer.getWidth() / 2, Renderer.getHeight() / 2));

        return new TentacleWay(wayPositions, distance);
    }


    private Sprite getNewPart(int pos)
    {
        Vector pos1 = positions.get(pos);
        Vector pos2 = positions.get(pos + 1);

        float xPos = pos1.getX() + ((pos2.getX() - pos1.getX())/2);
        float yPos = pos1.getY() + ((pos2.getY() - pos1.getY())/2);

        float angle = (float) Math.toDegrees(Math.atan((pos2.getY() - pos1.getY()) / (pos2.getX() - pos1.getX())));

        if(pos2.getX() < pos1.getX())
            angle += 180;

        int distance = Maths.lengthOf(pos1, pos2);

        Sprite tPart = new Sprite();
        tPart.setSize(distance + currentSize, (int) currentSize);
        currentSize *= SHRINK_FACTOR;
        tPart.setPosition(xPos, yPos);
        tPart.setAngle(angle);
        tPart.setVisible(true);

        return tPart;
    }

    private static float calcDistance(int maxDeflection, double pDistance, Position position)
    {
        double percentDistance;

        if(position == Position.LEFT || position == Position.RIGHT)
            percentDistance = Maths.toPercent(2.4, Renderer.getWidth());
        else
            percentDistance = Maths.toPercent(1.2, Renderer.getHeight());

        double x = maxDeflection / percentDistance;
        return (float) ((pDistance/2) / x);
    }

    private static Vector getPointFromDeflection(Vector point, Vector normal, double deflection, boolean left)
    {
        float eX = point.getX();
        float eY = point.getY();

        double normalSum = Math.pow(normal.getX(), 2) + Math.pow(normal.getY(), 2);
        deflection = Math.pow(deflection, 2);

        float multiplicator = (float) Math.sqrt(deflection / normalSum);

        if(left)
            return new Vector(eX - (normal.getX() * multiplicator), eY - (normal.getY() * multiplicator));
        else
            return new Vector(eX + (normal.getX() * multiplicator), eY + (normal.getY() * multiplicator));
    }

    public static void generateTentacles(int count)
    {
        leftTentacles.clear();
        rightTentacles.clear();
        bottomTentacles.clear();
        topTentacles.clear();

        for(int i = 0; i < count; i++)
        {
            leftTentacles.add(calculateTentacle(Position.LEFT));
            rightTentacles.add(calculateTentacle(Position.RIGHT));
            bottomTentacles.add(calculateTentacle(Position.BOTTOM));
            topTentacles.add(calculateTentacle(Position.TOP));
        }
    }



    public void setListener(TentacleListener l)
    {
        listener = l;
    }

    public TentacleWay getWay()
    {
        return way;
    }

    public Position getPosition()
    {
        return position;
    }

    public interface TentacleListener
    {
        void isCut(Tentacle t);
        void hasFinished(Tentacle t);
    }

    public static class TentacleWay {

        private LinkedList<Vector> positions;
        private float distance;

        public TentacleWay(LinkedList<Vector> positions, float distance)
        {
            this.positions = positions;
            this.distance = distance;
        }

        public LinkedList<Vector> getPositions() {
            return positions;
        }

        public float getDistance()
        {
            return distance;
        }
    }
}
