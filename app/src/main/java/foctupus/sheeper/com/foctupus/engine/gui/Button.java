package foctupus.sheeper.com.foctupus.engine.gui;

import android.view.MotionEvent;

import java.util.Iterator;
import java.util.LinkedList;

import foctupus.sheeper.com.foctupus.engine.renderer.Sprite;
import foctupus.sheeper.com.foctupus.engine.renderer.util.Vector;
import foctupus.sheeper.com.foctupus.game.tools.Maths;

/**
 * Created by schae on 08.03.2016.
 */
public class Button extends Component {

    private boolean pressed;
    private boolean round = true;

    private LinkedList<ButtonListener> listeners;
    private float normalWidth = -1f;
    private float normalHeight = -1f;

    public Button(Sprite sprite)
    {
        this(sprite, null, null);
    }

    private Button(Sprite sprite, Vector relativePosition, Vector relativeSize)
    {
        super(sprite, relativePosition, relativeSize);
        listeners = new LinkedList<>();
    }

    public void setSquared()
    {
        round = false;
    }

    public void setRound()
    {
        round = true;
    }

    @Override
    public void onTouch(float x, float y, int mode) {
        super.onTouch(x, y, mode);

        if(listeners.size() > 0)
        {
            switch (mode)
            {
                case MotionEvent.ACTION_DOWN:
                    if (isIntersected(x, y))
                        press();
                    else
                        release();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (!isIntersected(x, y))
                        release();
                    break;
                case MotionEvent.ACTION_UP:
                    if (isIntersected(x, y) && pressed)
                    {
                        onButtonClick();
                        release();
                    }
                    break;
            }
        }
    }

    private void press()
    {
        if(!pressed) {
            normalWidth = getSprite().getXSize();
            normalHeight = getSprite().getYSize();
            pressed = true;

            float x = getRelativeSize().getX() * 0.85f;
            float y = getRelativeSize().getY() * 0.85f;

            if(getRelativeSize().getX() == USE_RATIO)
                x = USE_RATIO;
            else if(getRelativeSize().getX() == USE_SAME)
                x = USE_SAME;

            if(getRelativeSize().getY() == USE_RATIO)
                y = USE_RATIO;
            else if(getRelativeSize().getY() == USE_SAME)
                y = USE_SAME;

            setRelativeSize(new Vector(x, y));
        }
    }

    private void release()
    {
        if(pressed) {
            pressed = false;

            float x = getRelativeSize().getX() / 0.85f;
            float y = getRelativeSize().getY() / 0.85f;

            if(getRelativeSize().getX() == USE_RATIO)
                x = USE_RATIO;
            else if(getRelativeSize().getX() == USE_SAME)
                x = USE_SAME;

            if(getRelativeSize().getY() == USE_RATIO)
                y = USE_RATIO;
            else if(getRelativeSize().getY() == USE_SAME)
                y = USE_SAME;

            setRelativeSize(new Vector(x, y));
        }
    }

    @Override
    public boolean isIntersected(float x, float y) {

        normalWidth = normalWidth == -1 ? getSprite().getXSize() : normalWidth;
        normalHeight = normalHeight == -1 ? getSprite().getYSize() : normalHeight;

        if(round)
        {
            return Maths.lengthOf(getSprite().getActualPosition(), new Vector(x, y)) <= (normalWidth > 0 ? normalWidth / 2 : getSprite().getXSize() / 2);
        }
        else if(!round)
        {
            float left = getSprite().getActualXPos() - normalWidth/2;
            float right = getSprite().getActualXPos() + normalWidth/2;
            float bottom = getSprite().getActualYPos() - normalHeight/2;
            float top = getSprite().getActualYPos() + normalHeight/2;

            return x > left && x < right && y > bottom && y < top;
        }

        return false;
    }

    private void onButtonClick() {
        Iterator<ButtonListener> iterator = listeners.iterator();
        while (iterator.hasNext())
        {
            ButtonListener listener = iterator.next();
            if (listener != null)
                listener.onClick(this);
            else
                iterator.remove();
        }
    }

    public void addButtonListener(ButtonListener listener)
    {
        listeners.add(listener);
    }

    public void clearButtonListeners()
    {
        listeners.clear();
    }

    public interface ButtonListener
    {
        void onClick(Button button);
    }

}
