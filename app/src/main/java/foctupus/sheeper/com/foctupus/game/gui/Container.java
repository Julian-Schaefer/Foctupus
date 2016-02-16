package foctupus.sheeper.com.foctupus.game.gui;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.Map;

import foctupus.sheeper.com.foctupus.game.gui.transition.IDrawable;
import foctupus.sheeper.com.foctupus.game.renderer.GenericSpriteList;
import foctupus.sheeper.com.foctupus.game.renderer.Renderer;
import foctupus.sheeper.com.foctupus.game.renderer.Sprite;
import foctupus.sheeper.com.foctupus.game.renderer.Texture;
import foctupus.sheeper.com.foctupus.game.renderer.util.RelativeVector;
import foctupus.sheeper.com.foctupus.game.renderer.util.Vector;
import foctupus.sheeper.com.foctupus.game.tools.Maths;

/**
 * Created by schae on 12.02.2016.
 */
public class Container extends Component implements IDrawable, IUpdateble {

    private Renderer renderer;
    private LinkedHashMap<Component, Vector> childs;

    private GenericSpriteList renderList;
    private int priority;


    public Container(Renderer renderer, int priority)
    {
        this(renderer, priority, null);
    }

    public Container(Renderer renderer, int priority, Texture texture)
    {
        super(texture);
        this.renderer = renderer;
        childs = new LinkedHashMap<>();
        renderList = new GenericSpriteList(priority);

        setVisible(true);
    }

    @Override
    public void update()
    {
        for(Map.Entry<Component, Vector> entry : childs.entrySet())
        {
            Component child = entry.getKey();
            updateChild(child, entry.getValue());

            if(child instanceof IUpdateble)
            {
                ((IUpdateble) child).update();
            }
        }
    }

    @Override
    public void draw()
    {
        renderer.addSprite(this, priority);


        renderer.addSpriteList(renderList);

        for(Map.Entry<Component, Vector> entry : childs.entrySet())
        {
            Component child = entry.getKey();

            if(child instanceof IDrawable)
                ((IDrawable) child).draw();
        }

    }

    public void revalidate(Vector old, Vector updated) {

        Vector oldSize = getSize();
        Vector oldPos = getPosition();

        BigDecimal ratioX = new BigDecimal(updated.getX()).divide(new BigDecimal(old.getX()), 10, BigDecimal.ROUND_HALF_DOWN);
        BigDecimal ratioY = new BigDecimal(updated.getY()).divide(new BigDecimal(old.getY()), 10, BigDecimal.ROUND_HALF_DOWN);

        Vector newSize = new Vector(new BigDecimal(getXSize()).multiply(ratioX).intValue(),
                new BigDecimal(getYSize()).multiply(ratioY).intValue());

        Vector newPos = new Vector(new BigDecimal(getXPos()).multiply(ratioX).intValue(),
                new BigDecimal(getYPos()).multiply(ratioY).intValue());

        setSize(newSize);
        setPosition(newPos);


        getTexture().revalidate();

        for(Map.Entry<Component, Vector> entry : childs.entrySet())
        {
            Component child = entry.getKey();
            Vector relative = entry.getValue();

            child.getTexture().revalidate();

            if(child instanceof Container)
            {
                ((Container) child).revalidate(old, updated);
            }
            else
            {
                newSize = new Vector(new BigDecimal(child.getXSize()).multiply(ratioX).intValue(),
                        new BigDecimal(child.getYSize()).multiply(ratioY).intValue());

                newPos = new Vector(new BigDecimal(relative.getX()).multiply(ratioX).intValue(),
                        new BigDecimal(relative.getY()).multiply(ratioY).intValue());

                child.setSize(newSize);
                childs.put(child, newPos);

                updateChild(child, newPos);
            }
        }
    }

    private Vector calculateNewPosition(Vector component, Vector diff)
    {
        double xRatio = 0, yRatio = 0;

        if(component.getX() > 0)
            xRatio = (double) diff.getX() / component.getX();

        if(component.getY() > 0)
            yRatio = (double) diff.getY() / component.getY();

        return new Vector(component.getX() + (float) (component.getX() * xRatio), component.getY() * (float) (component.getY() * yRatio));

    }




    public void addChild(Component child)
    {
            if(child != null)
            {
                child.setParent(this);
                child.setVisible(true);

                childs.put(child, child.getPosition());
                updateChild(child, child.getPosition());
        }
    }

    private Vector getRelativePos(Component child)
    {
        return new Vector(child.getXPos()/getXSize(), child.getYPos()/getYSize());
    }

    private void updateChild(Component child, Vector rPos) {
        Vector bottomLeft = new Vector(getActualXPos()-getXSize()/2, getActualYPos() - getYSize() / 2);

        child.setPosition(bottomLeft.getX() + rPos.getX(),
                bottomLeft.getY() + rPos.getY());


    }



}
