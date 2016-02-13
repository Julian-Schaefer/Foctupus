package foctupus.sheeper.com.foctupus.game.renderer;

import android.opengl.Matrix;

import foctupus.sheeper.com.foctupus.game.renderer.util.Vector;

/**
 * Created by schae on 04.12.2015.
 */
public class Rectangle {

    private float xPos = Float.MIN_VALUE;
    private float yPos = Float.MIN_VALUE;
    private float angle;
    private float xSize = Float.MIN_VALUE;
    private float ySize = Float.MIN_VALUE;

    private boolean bottomLeftAligned;

    private float[] mModelMatrix = new float[16];

    public Rectangle()
    {

    }

    public float[] getTransformationMatrix()
    {
        Matrix.setIdentityM(mModelMatrix, 0);

        Matrix.translateM(mModelMatrix, 0, xPos, yPos, 0f);

        Matrix.rotateM(mModelMatrix, 0, angle, 0.0f, 0.0f, 1.0f);

        Matrix.scaleM(mModelMatrix, 0, xSize, ySize, 1);

        return mModelMatrix;
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
            return xPos - xSize/2;
        else
            return xPos;
    }

    public float getYPos()
    {
        if(bottomLeftAligned)
            return yPos - ySize/2;
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
            xPos = x + xSize / 2;
            yPos = y + ySize / 2;
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
