package foctupus.sheeper.com.foctupus.game.renderer;

import foctupus.sheeper.com.foctupus.game.tools.RelativeVector;
import foctupus.sheeper.com.foctupus.game.tools.Vector;

/**
 * Created by schae on 05.02.2016.
 */
public abstract class StaticContainer extends Sprite implements Container  {

    private GenericSpriteList childs;
    private Renderer renderer;

    protected ContainerListener containerListener;

    public StaticContainer(String textureName, int priority)
    {
        super(textureName);
        load(priority);
    }

    public StaticContainer(int textureID, String textureName, int priority)
    {
        super(textureID, textureName);
        load(priority);
    }

    public StaticContainer(int priority)
    {
        super();
        load(priority);
    }

    private void load(int priority)
    {
        childs = new GenericSpriteList(priority);
        renderer = Environment.renderer;
    }

    public void setContainerListener(ContainerListener containerListener)
    {
        this.containerListener = containerListener;
    }


    @Override
    public void render() {
        renderer.addSprite(this, childs.getPriority());
        renderer.addSpriteList(childs);
    }

    @Override
    public void update() {
        for(Sprite sprite : childs)
        {
            if(sprite != this)
                updateChild(sprite, sprite.getRelativePosition(), sprite.getRelativeSize());
        }
    }

    @Override
    public final void revalidate() {
        if(containerListener != null)
            containerListener.onRevalidate(this);

        for(Sprite child : childs)
        {
                if (child instanceof AnimatedSprite) {
                }

                updateChild(child, child.getRelativePosition(), child.getRelativeSize());
        }
    }


    @Override
    public final void addChild(Sprite child, RelativeVector relativePosition, RelativeVector relativeSize) {
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
        child.setRelativePosition(relativePosition);
        child.setRelativeSize(relativeSize);

        Vector bottomLeft = new Vector(xPos - xSize/2, yPos - ySize/2);
        Vector rPos = new RelativeVector(relativePosition.getRelativeX(), relativePosition.getRelativeY(), getSize());
        child.setPosition(bottomLeft.getX() + rPos.getX(), bottomLeft.getY() + rPos.getY());

        child.setSize(new RelativeVector(relativeSize.getRelativeX(), relativeSize.getRelativeY(), getSize()));
    }


}
