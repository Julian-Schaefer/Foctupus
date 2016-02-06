package foctupus.sheeper.com.foctupus.game.tools;

import foctupus.sheeper.com.foctupus.game.MyGLRenderer;
import foctupus.sheeper.com.foctupus.game.renderer.Environment;

/**
 * Created by schae on 28.11.2015.
 */
public class RelativeVector extends Vector {

    public static final int RATIO = -1;
    public static final int SAME = -2;

    private double ratio = 1;
    private int width;
    private int height;

    private boolean centered = false;

    private float rX;
    private float rY;


    public RelativeVector(float x, float y) {
        this(x, y, 1);
    }

    public RelativeVector(float x, float y, boolean centered) {
        this(x, y, 1, centered);
    }

    public RelativeVector(float x, float y, double ratio) {
        this.ratio = ratio;
        calculatePosition(x, y);
    }

    public RelativeVector(float x, float y, double ratio, boolean centered) {
        this.ratio = ratio;
        this.centered = centered;
        calculatePosition(x, y);
    }

    public RelativeVector(float x, float y, Vector relations) {
        this(x, y, 1, relations);
    }

    public RelativeVector(float x, float y, boolean centered, Vector relations) {
        this(x, y, 1, centered, relations);
    }


    public RelativeVector(float x, float y, double ratio, Vector relations) {

        width = (int) relations.getX();
        height = (int) relations.getY();
        this.ratio = ratio;

        calculatePosition(x, y);
    }

    public RelativeVector(float x, float y, double ratio, boolean centered, Vector relations) {

        width = (int) relations.getX();
        height = (int) relations.getY();
        this.ratio = ratio;
        this.centered = centered;

        calculatePosition(x, y);
    }


    private void calculatePosition(float x, float y)
    {
        rX = x;
        rY = y;

        if(width <= 0 && height <= 0)
        {
            height = Environment.height;
            width = (int) (height / (16.0f/9.0f));
        }

        Vector position = percentToPixel(new Vector(rX, rY), ratio, width, height);
        if(centered)
        {
            position.setX(position.getX() + (Environment.width - width)/2);
            position.setY(position.getY() + (Environment.height - height)/2);
        }
        setVector(position);
    }



    private static Vector percentToPixel(Vector vec, double ratio, int width, int height)
    {
        if(vec != null)
        {


            int x = (int) ((double) width * vec.getX() / 100.0);
            int y = (int) ((double) height * vec.getY() / 100.0);

            if(vec.getX() == SAME)
                x = y;
            else if(vec.getX() == RATIO)
                x = (int) (ratio * y);
            else if(vec.getY() == SAME)
                y = x;
            else if(vec.getY() == RATIO)
                y = (int) (x / ratio);

            return new Vector(x, y);
        }
        return null;
    }

    public float getRelativeX()
    {
        return rX;
    }

    public float getRelativeY()
    {
        return rY;
    }

}
