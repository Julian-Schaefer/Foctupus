package foctupus.sheeper.com.foctupus.game.renderer;

import foctupus.sheeper.com.foctupus.game.MyGLRenderer;
import foctupus.sheeper.com.foctupus.game.tools.RelativeVector;
import foctupus.sheeper.com.foctupus.game.tools.Vector;

/**
 * Created by schae on 04.12.2015.
 */
public class Rectangle {

    protected float xPos = Float.MIN_VALUE;
    protected float yPos = Float.MIN_VALUE;
    protected float angle;
    protected float xSize = Float.MIN_VALUE;
    protected float ySize = Float.MIN_VALUE;
    private RelativeVector relativePosition;
    private RelativeVector relativeSize;

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
        return xPos;
    }

    public float getYPos()
    {
        return yPos;
    }

    public Vector getPosition()
    {
        return new Vector(xPos, yPos);
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

    public void setRelativePosition(RelativeVector relativePosition)
    {
        this.relativePosition = relativePosition;
    }

    public RelativeVector getRelativePosition()
    {
        return relativePosition;
    }

    public void setRelativeSize(RelativeVector relativeSize)
    {
        this.relativeSize = relativeSize;
    }

    public RelativeVector getRelativeSize()
    {
        return relativeSize;
    }

    public void setBottomLeftAligned(boolean bool)
    {
        bottomLeftAligned = bool;
    }

    public boolean isBottomLeftAligned()
    {
        return bottomLeftAligned;
    }


}
