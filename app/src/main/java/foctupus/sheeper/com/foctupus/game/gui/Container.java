package foctupus.sheeper.com.foctupus.game.gui;

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
    private GenericSpriteList childs;

    public Container(Renderer renderer, int priority)
    {
        this(renderer, priority, null);
    }

    public Container(Renderer renderer, int priority, Texture texture)
    {
        super(texture);
        this.renderer = renderer;
        childs = new GenericSpriteList();
        childs.setPriority(priority);
    }

    @Override
    public void update()
    {
        for(Sprite sprite : childs)
        {
            Component child = (Component) sprite;
            updateChild(child, child.getRelativePosition(), child.getRelativeSize());

            if(child instanceof IUpdateble)
            {
                ((IUpdateble) child).update();
            }

            if(child instanceof IDrawable)
            {
                ((IDrawable) child).draw();
            }
        }
    }

    @Override
    public void draw()
    {
        setVisible(true);
        renderer.addSprite(this, childs.getPriority());
        renderer.addSpriteList(childs);
    }


    public void addChild(Component child)
    {
        if(child != null && child.getRelativePosition() != null && child.getRelativeSize() != null)
        {
            updateChild(child, child.getRelativePosition(), child.getRelativeSize());
            child.setVisible(true);
            childs.add(child);
        }
    }

    private void updateChild(Component child, Vector relativePosition, Vector relativeSize)
    {
        Vector bottomLeft = new Vector(getActualXPos()-getXSize()/2, getActualYPos()-getYSize()/2);

        child.setSize(Maths.percentToPixel(relativeSize.getX(), getXSize()), Maths.percentToPixel(relativeSize.getY(), getYSize()));

        child.setPosition(bottomLeft.getX() + Maths.percentToPixel(relativePosition.getX(), getXSize()),
                bottomLeft.getY() + Maths.percentToPixel(relativePosition.getY(), getYSize()));

    }
}
