package foctupus.sheeper.com.foctupus.game.renderer;

import foctupus.sheeper.com.foctupus.game.tools.RelativeVector;
import foctupus.sheeper.com.foctupus.game.tools.Vector;

/**
 * Created by schae on 04.02.2016.
 */
public interface Container extends Drawable, Updatable {

    public static final int STD_PRIORITY = 1;

    public abstract void setup();
    public abstract void init();
    public abstract void addChild(Sprite child, RelativeVector relativePosition, RelativeVector relativeSize);
    public abstract void removeChild(Sprite child);
}
