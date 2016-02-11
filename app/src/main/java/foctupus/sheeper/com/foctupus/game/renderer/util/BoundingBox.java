package foctupus.sheeper.com.foctupus.game.renderer.util;

/**
 * Created by schae on 04.12.2015.
 */
public class BoundingBox {

    private float left, right, bottom, top;
    public BoundingBox(float left, float right, float bottom, float top)
    {
        this.left = left;
        this.right = right;
        this.bottom = bottom;
        this.top = top;
    }

    public BoundingBox(Vector first, Vector second)
    {
        if(first.getX() > second.getX())
        {
            this.left = second.getX();
            this.right = first.getX();
        }
        else
        {
            this.left = first.getX();
            this.right = second.getX();
        }

        if(first.getY() > second.getY())
        {
            this.top = first.getY();
            this.bottom = second.getY();
        }
        else
        {
            this.top = second.getY();
            this.bottom = first.getY();
        }


    }

    public boolean isPointInside(Vector point)
    {
        if(point != null) {
            if (point.getX() >= left && point.getX() <= right && point.getY() >= bottom && point.getY() <= top) {
                return true;
            }
        }
        return false;
    }


}
