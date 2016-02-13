package foctupus.sheeper.com.foctupus.game.gui;

import foctupus.sheeper.com.foctupus.game.renderer.Sprite;
import foctupus.sheeper.com.foctupus.game.renderer.Texture;
import foctupus.sheeper.com.foctupus.game.renderer.util.RelativeVector;
import foctupus.sheeper.com.foctupus.game.renderer.util.Vector;

/**
 * Created by schae on 12.02.2016.
 */
public class Component extends Sprite {

    private Vector relativePosition;
    private Vector relativeSize;


    public Component()
    {
        this(null, null);
    }

    public Component(Texture texture)
    {
        this(texture, null, null);
    }

    public Component(Vector relativePosition, Vector relativeSize)
    {
        this(null, null, null);
    }

    public Component(Texture texture, Vector relativePosition, Vector relativeSize)
    {
        super(texture);
        this.relativePosition = relativePosition;
        this.relativeSize = relativeSize;
    }

    public void setRelativePosition(Vector relativePosition)
    {
        this.relativePosition = relativePosition;
    }

    public Vector getRelativePosition()
    {
        return relativePosition;
    }

    public void setRelativeSize(Vector relativeSize)
    {
        this.relativeSize = relativeSize;
    }

    public Vector getRelativeSize()
    {
        return relativeSize;
    }

}
