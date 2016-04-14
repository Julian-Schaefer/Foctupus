package foctupus.sheeper.com.foctupus.game.gui;

import foctupus.sheeper.com.foctupus.game.renderer.Renderer;
import foctupus.sheeper.com.foctupus.game.renderer.Sprite;
import foctupus.sheeper.com.foctupus.game.renderer.Texture;
import foctupus.sheeper.com.foctupus.game.renderer.Textures;
import foctupus.sheeper.com.foctupus.game.renderer.util.Vector;

/**
 * Created by schae on 12.03.2016.
 */
public abstract class Popup extends Container {

    public Popup(Renderer renderer) {
        super(renderer);
    }

    protected abstract void init();

    private void close()
    {
        if(listener != null)
            listener.onFinished(this);
    }

}
