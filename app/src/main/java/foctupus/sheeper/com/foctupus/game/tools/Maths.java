package foctupus.sheeper.com.foctupus.game.tools;

import java.util.Random;

import foctupus.sheeper.com.foctupus.engine.renderer.util.Vector;

/**
 * Created by schae on 11.11.2015.
 */
public final class Maths {

    private static Random random = new Random();

    private Maths()
    {

    }

    public static int randInt(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    public static Function createFunction(Vector first, Vector second)
    {
        float b, m;
        if(first.getX() == second.getX())
        {
            return new Function(first.getX(), 0);

        }
        else if(first.getY() == second.getY())
        {
            return new Function(0, first.getY());
        }
        else {
            if (first.getX() == 0) {
                if (second.getX() != 0) {
                    b = first.getY();
                    m = ((second.getY() - b) / second.getX());

                    return new Function(m, b);
                }
            } else if (second.getX() == 0) {
                if (first.getX() != 0) {
                    b = second.getY();
                    m = ((first.getY() - b) / first.getX());

                    return new Function(m, b);
                }
            } else {
                float leftSum = second.getY() - first.getY();
                float rightM = second.getX() + (0 - first.getX());

                m = leftSum / rightM;
                b = first.getY() - m * first.getX();
                return new Function(m, b);
            }
        }

        return null;
    }

    public static Vector interception(Function first, Function second)
    {
        if(first != null && second != null) {

            if(first.getA() == 0 || second.getA() == 0)
            {
                float y = (first.getA() == 0) ? first.getB() : second.getB();
                Function f = (first.getA() == 0) ? second : first;

                float x = (y - f.getB()) / f.getA();

                return new Vector(x, y);
            }
            else if(first.getB() == 0 || second.getB() == 0)
            {

                float x = (first.getB() == 0) ? first.getA() : second.getA();
                Function f = (first.getB() == 0) ? second : first;

                float y = (float) f.valueAt(x, 1);

                return new Vector(x, y);
            }
            else
            {
                float left, right;
                right = second.getA() - first.getA();
                left = first.getB() - second.getB();


                float x = left / right;
                float y = first.getA() * x + first.getB();

                return new Vector(x, y);
            }

        }

        return null;
    }

    public static Function createFunction1f(Vector first, Vector second)
    {
        if(first != null && second != null) {
            float x1 = first.getX();
            float y1 = first.getY();
            float x2 = second.getX();
            float y2 = second.getY();

            float right = x1 - x2;
            float left = y1 - y2;

            float a = left / right;


            float b = y1 - (x1 * a);

            return new Function(a, b);
        }
        return null;
    }

    public static float percentToPixel(float percent, float relation)
    {
        return (float) (relation / (double) 100 * percent);
    }

    public static int lengthOf(double x, double y)
    {
        return (int) Math.sqrt(x*x+y*y);
    }


    public static int lengthOf(Vector x, Vector y)
    {
        return (int) Math.sqrt(Math.pow(x.getX() - y.getX(), 2)+ Math.pow(x.getY() - y.getY(), 2));
    }

    public static double toPercent(double p, double all)
    {
        return all *  p / 100.0;
    }


    public static double getNextOfGraph(Function graph, float xFrom, float yFrom, float margin)
    {
        float a = graph.getA();
        float b = graph.getB();

        float bMinusyFrom = b - yFrom;

        float normalNumberSum = (float) (Math.pow((b-yFrom), 2) + Math.pow(xFrom, 2));
        float normalXSum = -2*xFrom;
        float normalXPow2Sum = (2*a*bMinusyFrom)+1;
        float normalXPow4Sum = (float) Math.pow(a, 2);


        return 0;
    }
}
