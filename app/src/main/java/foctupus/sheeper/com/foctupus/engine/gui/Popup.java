package foctupus.sheeper.com.foctupus.engine.gui;

import foctupus.sheeper.com.foctupus.engine.renderer.Renderer;

/**
 * Created by schae on 12.03.2016.
 */
public abstract class Popup extends Container {

    public Popup(Renderer renderer) {
        super(renderer);
    }

    protected abstract void init();

    public void close()
    {
        if(listener != null)
            listener.onFinished(this);
    }

}
