package foctupus.sheeper.com.foctupus.game.gui;

import java.util.LinkedList;

import foctupus.sheeper.com.foctupus.game.gui.transition.IDrawable;
import foctupus.sheeper.com.foctupus.game.renderer.Renderer;
import foctupus.sheeper.com.foctupus.game.renderer.Sprite;
import foctupus.sheeper.com.foctupus.game.renderer.util.Vector;

/**
 * Created by schae on 12.02.2016.
 */
public class Container extends Component implements IDrawable {

    private static final int STD_PRIORITY = 10;

    private Renderer renderer;
    private LinkedList<Component> childs;

    private Container parent;

    public Container(Renderer renderer)
    {
        this(renderer, null);
    }

    public Container(Renderer renderer, Sprite sprite)
    {
        super(sprite);
        this.renderer = renderer;
        childs = new LinkedList<>();

        setPriority(STD_PRIORITY);
        sprite.setVisible(true);
    }

    @Override
    public void update()
    {
        super.update();

        for(Component child : childs)
        {
            child.update();
            updateChild(child);
        }
    }

    @Override
    public void draw()
    {
        renderer.addSprite(sprite, getPriority());

        for(Component child : childs)
        {
            if(child instanceof Container)
                ((Container) child).draw();
            else
                renderer.addSprite(child.getSprite(), child.getPriority());
        }

    }

    public void revalidate() {

        calculateSprite();

        sprite.getTexture().revalidate();

        for(Component child : childs)
        {

            child.getSprite().getTexture().revalidate();

            if(child instanceof Container)
            {
                ((Container) child).revalidate();
            }
            else
            {
                updateChild(child);
            }
            updateChild(child);
        }
    }

    public void clearChilds()
    {
        childs.clear();
    }

    public void removeChild(Component component)
    {
        if(childs.contains(component))
        {
            childs.remove(component);
        }
    }
    public void addChild(Component child)
    {
            if(child != null)
            {
                child.getSprite().setVisible(true);

                childs.add(child);
                updateChild(child);

                if(child instanceof Container)
                    child.setPriority(getPriority() + 1);
                else
                    child.setPriority(getPriority());
        }
    }

    private void updateChild(Component child) {

        Vector relativePosition = child.getRelativePosition();
        Vector relativeSize = child.getRelativeSize();
        Sprite childSprite = child.getSprite();

        if(relativePosition != null && relativeSize != null) {
            Vector bottomLeft = new Vector(sprite.getActualXPos() - sprite.getXSize() / 2, sprite.getActualYPos() - sprite.getYSize() / 2);

            float width, height;

            if(relativeSize.getX() == Component.USE_SAME)
            {
                height = sprite.getYSize() / 100f * relativeSize.getY();
                width = height;
            }
            else if(relativeSize.getY() == Component.USE_SAME)
            {
                width = sprite.getXSize() / 100f * relativeSize.getX();
                height = width;
            }
            else if(relativeSize.getX() == Component.USE_RATIO)
            {
                height = sprite.getYSize() / 100f * relativeSize.getY();
                width = height / childSprite.getTexture().getRatio();
            }
            else if(relativeSize.getY() == Component.USE_RATIO)
            {
                width = sprite.getXSize() / 100f * relativeSize.getX();
                height = width * childSprite.getTexture().getRatio();
            }
            else {
                width = sprite.getXSize() / 100f * relativeSize.getX();
                height = sprite.getYSize() / 100f * relativeSize.getY();
            }

            childSprite.setSize(width, height);

            childSprite.setPosition(bottomLeft.getX() + (sprite.getXSize() / 100f * relativePosition.getX()),
                    bottomLeft.getY() + (sprite.getYSize() / 100f * relativePosition.getY()));
        }
    }

    private void calculateSprite()
    {
        if(parent == null && getRelativePosition() != null)
            sprite.setPosition(Renderer.getWidth() / 100f * getRelativePosition().getX(),
                    Renderer.getHeight() / 100f * getRelativePosition().getY());

        if(parent == null && getRelativeSize() != null)
            sprite.setSize(Renderer.getWidth() / 100f * getRelativeSize().getX(),
                    Renderer.getHeight() / 100f * getRelativeSize().getY());
    }

    @Override
    public void setPriority(int priority) {
        super.setPriority(priority);

        for(Component child : childs)
        {
            if(child instanceof Container)
                child.setPriority(priority+1);
            else
                child.setPriority(priority);
        }
    }

    @Override
    public void setRelativePosition(Vector relativePosition) {
        super.setRelativePosition(relativePosition);
        calculateSprite();
    }

    @Override
    public void setRelativeSize(Vector relativeSize) {
        super.setRelativeSize(relativeSize);
        calculateSprite();
    }
}
