package com.sheeper.foctupus.game.tools;

/**
 * Created by schae on 11.11.2015.
 */
public class Function {

    private float a, b, nullpoint;
    private boolean left = false;

    public Function(float a, float b)
    {
        this.a = a;
        this.b = b;


        double left = 0 - b;
        left /= a;
        nullpoint = (float) Math.sqrt(left);
    }

    public float getA(){ return a; }
    public float getB() { return b; }

    public double valueAt(double x, int pow)
    {
        return (a*Math.pow(x, pow))+ b;
    }

    public void setLeft(boolean b)
    {
        left = b;
    }

    public boolean getLeft()
    {
        return left;
    }

    public float getNullPoint()
    {
        return nullpoint;
    }
}
