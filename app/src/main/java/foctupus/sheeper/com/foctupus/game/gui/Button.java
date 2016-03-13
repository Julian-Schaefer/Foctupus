package foctupus.sheeper.com.foctupus.game.gui;

import android.view.MotionEvent;

import java.util.Iterator;
import java.util.LinkedList;

import foctupus.sheeper.com.foctupus.game.renderer.Sprite;
import foctupus.sheeper.com.foctupus.game.renderer.util.Vector;

/**
 * Created by schae on 08.03.2016.
 */
public class Button extends Component {

    private boolean pressed;

    private LinkedList<ButtonListener> listeners;


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

        switch (mode)
        {
            case MotionEvent.ACTION_DOWN:
                if(isIntersected(x, y))
                    press();
                else
                    release();
                break;
            case MotionEvent.ACTION_MOVE:
                if(!isIntersected(x, y))
                    release();
                break;
            case MotionEvent.ACTION_UP:
                if(isIntersected(x, y) && pressed) {
                    onButtonClick();
                    release();
                }
                break;
        }
    }

    private void press()
    {
        if(pressed == false) {
            pressed = true;
            setRelativeSize(new Vector(getRelativeSize().getX() * 0.85f, USE_SAME));
        }
    }

    private void release()
    {
        if(pressed == true) {
            pressed = false;
            setRelativeSize(new Vector(getRelativeSize().getX() / 0.85f, USE_SAME));
        }
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

    public void clearListeners()
    {
        listeners.clear();
    }

    public interface ButtonListener
    {
        public void onClick(Button button);
    }

}
