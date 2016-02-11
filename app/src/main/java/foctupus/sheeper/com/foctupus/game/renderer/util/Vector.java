package foctupus.sheeper.com.foctupus.game.renderer.util;

/**
 * Created by schae on 09.11.2015.
 */
public class Vector {

    private float x = Float.MIN_VALUE, y = Float.MIN_VALUE;


    public Vector(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public Vector()
    {
        
    }

    public void normalise()
    {
        float length = (int) Math.sqrt((Math.pow(x, 2) + Math.pow(y, 2)));

        x = (x / length);
        y = (y/length);

    }

    public void setVector(Vector vector)
    {
        x = vector.getX();
        y = vector.getY();
    }

    public void setX(float i)
    {
        x = i;
    }

    public void setY(float i)
    {
        y = i;
    }

    public float getX() {
        return x;
    }

    public float getY()
    {
        return y;
    }

    public Vector clone()
    {
        return new Vector(x, y);
    }

}

