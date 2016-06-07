package foctupus.sheeper.com.foctupus.engine.gui;

import android.util.Log;

import java.util.LinkedList;

import foctupus.sheeper.com.foctupus.engine.renderer.Renderer;
import foctupus.sheeper.com.foctupus.engine.renderer.Sprite;
import foctupus.sheeper.com.foctupus.engine.renderer.util.Vector;
import foctupus.sheeper.com.foctupus.game.tools.Maths;

/**
 * Created by schae on 12.02.2016.
 */
public class Container extends Component implements IDrawable {

    public static final int STD_PRIORITY = 10;

    protected Renderer renderer;
    protected volatile LinkedList<Component> childs;

    private Container parent;

    public Container(Renderer renderer)
    {
        this(renderer, new Sprite());
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

        if(gotUpdated())
            calculateSprite();

        for(Component child : childs)
        {
            child.update();
            if(child.gotUpdated() || child instanceof Container)
                updateChild(child);
        }
    }

    @Override
    public void draw()
    {
        renderer.addSprite(getSprite(), getPriority());

        for(Component child : childs)
        {
            if(child instanceof Container)
                ((Container) child).draw();
            else
                renderer.addSprite(child.getSprite(), child.getPriority());
        }
    }

    @Override
    public void onTouch(float x, float y, int mode) {
        for(int i = 0; i < childs.size(); i++)
        {
            Component child = childs.get(i);
            if(child.getSprite().isVisible())
                child.onTouch(x, y, mode);
        }
    }

    public void revalidate() {

        calculateSprite();

        if (getSprite().getTexture() != null)
            getSprite().getTexture().revalidate();

        for (Component child : childs) {
            if (child.getSprite().getTexture() != null)
                child.getSprite().getTexture().revalidate();

            updateChild(child);

            if (child instanceof Container) {
                ((Container) child).revalidate();
            }
        }
    }

    public void clearChilds()
    {
        childs.clear();
    }

    public void removeChild(Component component)
    {
        if(childs.contains(component))
            childs.remove(component);
    }

    public void addChild(Component child)
    {
        addChild(child, childs.size(), -1);
    }

    public void addChild(Component child, int priority)
    {
        addChild(child, childs.size(), priority);
    }

    public void addChild(Component child, int index, int priority)
    {
        if(child != null)
        {
            childs.add(index, child);

            child.getSprite().setVisible(true);

            if(priority <= 0)
            {
                if (child instanceof Container)
                {
                    ((Container) child).setParent(this);
                    child.setPriority(getPriority() + 1);
                } else
                {
                    child.setPriority(getPriority());
                }
            }
            else
                child.setPriority(priority);
        }
    }

    private void updateChild(Component child) {

        Vector relativePosition = child.getRelativePosition();
        Vector relativeSize = child.getRelativeSize();
        Sprite childSprite = child.getSprite();

        Sprite sprite = getSprite();

        if(relativePosition != null && relativeSize != null) {
            Vector bottomLeft = new Vector(sprite.getActualXPos() - sprite.getXSize() / 2, sprite.getActualYPos() - sprite.getYSize() / 2);

            float width, height;

            if(relativeSize.getX() == USE_SAME)
            {
                height = sprite.getYSize() / 100f * relativeSize.getY();
                width = height;
            }
            else if(relativeSize.getY() == USE_SAME)
            {
                width = sprite.getXSize() / 100f * relativeSize.getX();
                height = width;
            }
            else if(relativeSize.getX() == USE_RATIO)
            {
                height = sprite.getYSize() / 100f * relativeSize.getY();
                width = height / childSprite.getTexture().getRatio();
            }
            else if(relativeSize.getY() == USE_RATIO)
            {
                width = sprite.getXSize() / 100f * relativeSize.getX();
                height = width * childSprite.getTexture().getRatio();
            }
            else
            {
                width = sprite.getXSize() / 100f * relativeSize.getX();
                height = sprite.getYSize() / 100f * relativeSize.getY();
            }



            if(width != childSprite.getXSize() || height != childSprite.getYSize())
                childSprite.setSize(width, height);

            childSprite.setPosition(bottomLeft.getX() + (sprite.getXSize() / 100f * relativePosition.getX()),
                    bottomLeft.getY() + (sprite.getYSize() / 100f * relativePosition.getY()));
        }

        if(child instanceof Container)
        {
            ((Container) child).updateChilds();
        }
    }

    public void updateChilds()
    {
        for(Component child : childs)
            updateChild(child);
    }

    public void setParent(Container parent)
    {
        this.parent = parent;
    }

    public Container getParent()
    {
        return parent;
    }

    protected void calculateSprite()
    {
        if(parent == null)
        {

            if(getRelativePosition() != null)
                getSprite().setPosition(Renderer.getWidth() / 100f * getRelativePosition().getX(),
                        Renderer.getHeight() / 100f * getRelativePosition().getY());

            if(getRelativeSize() != null)
                getSprite().setSize(Renderer.getWidth() / 100f * getRelativeSize().getX(),
                        Renderer.getHeight() / 100f * getRelativeSize().getY());

            updateChilds();
        }
    }

    @Override
    public void setSprite(Sprite sprite) {
        super.setSprite(sprite);
        calculateSprite();
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
}
