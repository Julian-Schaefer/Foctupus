package foctupus.sheeper.com.foctupus.game.gui;

import foctupus.sheeper.com.foctupus.game.renderer.AnimatedSprite;
import foctupus.sheeper.com.foctupus.game.renderer.Environment;
import foctupus.sheeper.com.foctupus.game.renderer.GenericSpriteList;
import foctupus.sheeper.com.foctupus.game.renderer.Renderer;
import foctupus.sheeper.com.foctupus.game.renderer.Sprite;
import foctupus.sheeper.com.foctupus.game.renderer.Texture;
import foctupus.sheeper.com.foctupus.game.renderer.util.RelativeVector;
import foctupus.sheeper.com.foctupus.game.renderer.util.Vector;

/**
 * Created by schae on 05.02.2016.
 */
public abstract class StaticContainer extends Sprite implements IContainer {

    private GenericSpriteList childs;
    private Renderer renderer;

    protected IContainer.Listener containerListener;

    public StaticContainer(Texture texture, int priority)
    {
        super(texture);
        load(priority);
    }

    public StaticContainer(int priority)
    {
        super();
        load(priority);
    }

    private void load(int priority)
    {
        childs = new GenericSpriteList();
        renderer = Renderer.getInstance();
    }

    public void setContainerListener(IContainer.Listener containerListener)
    {
        this.containerListener = containerListener;
    }


    @Override
    public void render() {
        renderer.addSprite(this,2);
    }

    public void update() {
        for(Sprite sprite : childs)
        {
        }
    }

    public final void revalidate(float oldX, float oldY) {


        if(containerListener != null)
            containerListener.onRevalidate(this);

        for(Sprite child : childs)
        {
            if (child instanceof AnimatedSprite) {
            }
        }
    }

    @Override
    public void setPosition(float x, float y) {

        float difX = getXPos() - x;
        float difY = getYPos() - y;

        super.setPosition(x, y);

        for(Sprite child : childs)
        {
            child.setPosition(child.getXPos() + difX, child.getYSize() + difY);
        }
    }

    @Override
    public void addChild(Sprite child) {
        addChild(child, null, null);
    }

    @Override
    public final void addChild(Sprite child, RelativeVector relativePosition, RelativeVector relativeSize) {
        if(relativePosition != null && relativeSize != null)
            updateChild(child, relativePosition, relativeSize);

        child.setVisible(true);
        childs.add(child);
    }


    @Override
    public final void removeChild(Sprite child) {
        childs.remove(child);
    }

    private void updateChild(Sprite child, RelativeVector relativePosition, RelativeVector relativeSize)
    {
        Vector bottomLeft;
        if(!isBottomLeftAligned())
            bottomLeft = new Vector(getXPos() - getYSize()/2, getYPos() - getYSize()/2);
        else
            bottomLeft = new Vector(getXPos(), getYPos());

        Vector rPos = new RelativeVector(relativePosition.getRelativeX(), relativePosition.getRelativeY(), getSize());
        child.setPosition(bottomLeft.getX() + rPos.getX(), bottomLeft.getY() + rPos.getY());

        child.setSize(new RelativeVector(relativeSize.getRelativeX(), relativeSize.getRelativeY(), getSize()));
    }

}
