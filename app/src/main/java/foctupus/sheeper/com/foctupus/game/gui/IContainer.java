package foctupus.sheeper.com.foctupus.game.gui;

import foctupus.sheeper.com.foctupus.game.renderer.Sprite;
import foctupus.sheeper.com.foctupus.game.renderer.util.RelativeVector;

/**
 * Created by schae on 11.02.2016.
 */
public interface IContainer extends Updatable, Drawable {

    public static final int STD_PRIORITY = 1;

    public abstract void setup();
    public abstract void init();
    public abstract void addChild(Sprite child, RelativeVector relativePosition, RelativeVector relativeSize);
    public abstract void addChild(Sprite child);
    public abstract void removeChild(Sprite child);

    interface Listener
    {
        public abstract void onRevalidate(IContainer container);
        public abstract void onLoaded(IContainer container);
        public abstract void onFinished(IContainer container);
    }

}
