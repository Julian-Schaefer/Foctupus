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

    private LinkedList<ButtonListener> listeners;
    private float normalWidth;

    public Button(Sprite sprite)
    {
        this(sprite, null, null);
    }

    private Button(Sprite sprite, Vector relativePosition, Vector relativeSize)
    {
        super(sprite, relativePosition, relativeSize);
        listeners = new LinkedList<>();
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
            pressed = true;
            setRelativeSize(new Vector(getRelativeSize().getX() * 0.85f, USE_SAME));
        }
    }

    private void release()
    {
        if(pressed) {
            pressed = false;
            setRelativeSize(new Vector(getRelativeSize().getX() / 0.85f, USE_SAME));
        }
    }

    @Override
    public boolean isIntersected(float x, float y) {
        if(Maths.lengthOf(getSprite().getActualPosition(), new Vector(x, y)) <= (normalWidth > 0 ? normalWidth/2 : getSprite().getXSize()/2))
            return true;

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
