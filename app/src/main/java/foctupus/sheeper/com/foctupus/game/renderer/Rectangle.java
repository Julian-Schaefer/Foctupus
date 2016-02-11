package foctupus.sheeper.com.foctupus.game.renderer;

import foctupus.sheeper.com.foctupus.game.renderer.util.Vector;

/**
 * Created by schae on 04.12.2015.
 */
public class Rectangle {

    protected float xPos = Float.MIN_VALUE;
    protected float yPos = Float.MIN_VALUE;
    protected float angle;
    protected float xSize = Float.MIN_VALUE;
    protected float ySize = Float.MIN_VALUE;

    private boolean bottomLeftAligned;

    public Rectangle()
    {

    }

    public void setAngle(float angle)
    {
        this.angle = angle;
    }

    public float getAngle()
    {
        return angle;
    }

    public float getXPos()
    {
        if(bottomLeftAligned)
            return xPos - getXSize()/2;
        else
            return xPos;
    }

    public float getYPos()
    {
        if(bottomLeftAligned)
            return yPos - getYSize()/2;
        else
            return yPos;
    }

    public float getActualXPos()
    {
        return xPos;
    }

    public float getActualYPos()
    {
        return yPos;
    }

    public Vector getActualPosition()
    {
        return new Vector(xPos, yPos);
    }

    public Vector getPosition()
    {
        return new Vector(getXPos(), getYPos());
    }

    public void setPosition(Vector vec)
    {
        setPosition(vec.getX(), vec.getY());
    }

    public void setPosition(float x, float y) {
        if(!bottomLeftAligned) {
            xPos = x;
            yPos = y;
        }
        else
        {
            xPos = (int) (x + (getXSize() / 2));
            yPos = (int) (y + (getYSize() / 2));
        }
    }

    public float getXSize()
    {
        return xSize;
    }

    public float getYSize()
    {
        return ySize;
    }

    public Vector getSize()
    {
        return new Vector(xSize, ySize);
    }

    public void setSize(float x, float y) {

        float oldX = getXSize();
        float oldY = getYSize();

        xSize = x;
        ySize = y;

        if(bottomLeftAligned)
            setPosition(getXPos()-oldX/2, getYPos()-oldY/2);
    }

    public void setSize(Vector vec)
    {
        setSize((int) vec.getX(), (int) vec.getY());
    }

    public void setBottomLeftAligned(boolean bool)
    {
        bottomLeftAligned = bool;
    }

    public boolean isBottomLeftAligned()
    {
        return bottomLeftAligned;
    }

    public boolean intersects(float x, float y)
    {
        float left = xPos - xSize/2;
        float right = xPos + xSize/2;
        float bottom = yPos - ySize/2;
        float top = yPos + ySize/2;

        return x >= left && x <= right && y >= bottom && y <= top;
    }

    public boolean intersects(Vector pos)
    {
        return intersects(pos.getX(), pos.getY());
    }
}
