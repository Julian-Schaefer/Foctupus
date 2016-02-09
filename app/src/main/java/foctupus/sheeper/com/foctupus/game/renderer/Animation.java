package foctupus.sheeper.com.foctupus.game.renderer;



import foctupus.sheeper.com.foctupus.game.tools.Vector;

/**
 * Created by schae on 28.11.2015.
 */
public class Animation
{

    private static final int STD_ANIM_TIME = 700;

    public enum Status
    {
        IN, OUT
    }


    private Status status = Status.IN;

    private AnimatedSprite texture;

    private Vector startPos;
    private Vector endPos;

    private float startAngle;
    private float rotation;

    private Vector startSize;
    private Vector endSize;


    private long startTime;

    private long animationTime = STD_ANIM_TIME;


    private long animationTimeOffset = 0;
    private boolean finished;
    private boolean repeating;
    private boolean invert;

    private String name;

    public Animation()
    {
    }

    public Animation(String name)
    {
        this.name = name;
    }

    public void setTexture(AnimatedSprite texture)
    {
        this.texture = texture;
    }

    public void start()
    {
        start(false);
    }

    public void start(boolean invert)
    {
        finished = false;

        if(invert)
            invert();

        long diff = System.currentTimeMillis() + animationTimeOffset - startTime;

        if(diff < animationTime)
            animationTimeOffset = animationTime - diff;
        else
            animationTimeOffset = 0;


        startTime = System.currentTimeMillis();
    }

    public void update()
    {
        if(!finished) {
            long diff = System.currentTimeMillis() + animationTimeOffset - startTime;

            if (diff < animationTime) {
                updatePosition(diff);
                updateSize(diff);
                updateAngle(diff);
            } else if (repeating) {
                updatePosition(animationTime);
                updateSize(animationTime);
                updateAngle(animationTime);
                start(invert);
            }
            else
                finished = true;
        }
    }

    public void stop()
    {
        finished = true;
    }

    private void updatePosition(long diff)
    {
        if(texture != null && startPos != null && endPos != null) {
            Vector pos;
            if(status == Status.IN)
                pos = calculateVector(startPos, endPos, diff);
            else
                pos = calculateVector(endPos, startPos, diff);

            texture.setPosition(pos);
        }
    }

    private void updateSize(long diff)
    {
        if(texture != null && startSize != null && endSize != null) {
            Vector size = calculateVector(startSize, endSize, diff);
            texture.setSize(size);
        }
    }

    private void updateAngle(long diff)
    {
        if(texture != null && startAngle != 0 && rotation != 0) {
            texture.setAngle(startAngle +  (rotation * ((float) diff / animationTime)));
        }
    }

    private Vector calculateVector(Vector startVec, Vector endVec, double diff)
    {

        Vector vec = new Vector(endVec.getX() - startVec.getX(), endVec.getY() - startVec.getY());


        float x = startVec.getX() + (int) (vec.getX() * (diff / animationTime));
        float y = startVec.getY() + (int) (vec.getY() * (diff / animationTime));

        return new Vector(x, y);
    }


    private void invert()
    {
        if(status == Status.IN)
            status = Status.OUT;
        else
            status = Status.IN;


        Vector temp = endSize;
        endSize = startSize;
        startSize = temp;

        startAngle += rotation;
        rotation *= -1;
    }


    public void setRepeating(boolean repeating)
    {
        setRepeating(repeating, false);
    }


    public void setRepeating(boolean repeating, boolean invert)
    {
        this.repeating = repeating;
        this.invert = invert;
    }

    public void setAnimationPositions(Vector startPos, Vector endPos)
    {
        this.startPos = startPos;
        this.endPos = endPos;
    }

    public void setAnimationAngles(float startAngle, float rotation)
    {
        this.startAngle = startAngle;
        this.rotation = rotation;
    }

    public void setAnimationSizes(Vector startSize, Vector endSize)
    {
        this.startSize = startSize;
        this.endSize = endSize;
    }

    public boolean isFinished()
    {
        return finished;
    }

    public void setAnimationTime(int animationTime)
    {
        this.animationTime = animationTime;
    }


    public Vector getStartPos() {
        return startPos;
    }

    public Vector getEndPos() {
        return endPos;
    }

    public float getStartAngle() {
        return startAngle;
    }

    public float getRotation() {
        return rotation;
    }

    public Vector getStartSize() {
        return startSize;
    }

    public Vector getEndSize() {
        return endSize;
    }

    public boolean isRepeating()
    {
        return repeating;
    }

    public String getName()
    {
        return name;
    }
}
