package com.sheeper.foctupus.engine.gui;


import com.sheeper.foctupus.engine.renderer.Renderer;
import com.sheeper.foctupus.engine.renderer.Sprite;

/**
 * Created by schae on 12.03.2016.
 */
public abstract class Popup extends Container {

    public Popup(Renderer renderer) {
        super(renderer);
    }

    public Popup(Renderer renderer, Sprite sprite)
    {
        super(renderer, sprite);

        getSprite().setVisible(true);
    }

    public void close()
    {
        if(listener != null)
            listener.onFinished(this);
    }

}
