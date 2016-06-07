package foctupus.sheeper.com.foctupus.game.logic;

import java.util.LinkedList;

import foctupus.sheeper.com.foctupus.engine.gui.Component;
import foctupus.sheeper.com.foctupus.engine.gui.Container;
import foctupus.sheeper.com.foctupus.engine.renderer.GenericSpriteList;
import foctupus.sheeper.com.foctupus.engine.renderer.Renderer;
import foctupus.sheeper.com.foctupus.engine.renderer.Sprite;
import foctupus.sheeper.com.foctupus.engine.renderer.Texture;
import foctupus.sheeper.com.foctupus.engine.renderer.Textures;
import foctupus.sheeper.com.foctupus.engine.renderer.util.Vector;

/**
 * Created by schae on 04.12.2015.
 */
public class Counter extends Container {

    private int count = 0;
    private boolean adjustWidth;

    private GenericSpriteList numbers;
    private boolean updated;

    public Counter()
    {
        this(0);
    }

    public Counter(int count)
    {
        super(Renderer.getInstance());
        getSprite().setVisible(false);

        numbers = new GenericSpriteList();
        this.count = count;
    }

    protected void validate()
    {
        if(updated)
        {
            numbers.clear();

            float zeroRatio = getDefaultRatio();

            String numberStr = String.valueOf(count);
            int length = numberStr.length();

            float numberHeight = getSprite().getYSize();
            float numberWidth = numberHeight / zeroRatio;

            float cellWidth = (numberWidth * 1.1f);

            if (cellWidth * length > getSprite().getXSize())
            {
                if (!adjustWidth)
                {
                    cellWidth = getSprite().getXSize() / length;
                    numberWidth = cellWidth * 0.9f;
                    numberHeight = numberWidth * zeroRatio;
                }
            }

            if (adjustWidth)
            {
                if (getParent() != null)
                {
                    setRelativeSize(new Vector(100f * cellWidth * length / getParent().getSprite().getXSize(), getRelativeSize().getY()));
                } else
                {
                    setRelativeSize(new Vector(100f * cellWidth * length / Renderer.getWidth(), getRelativeSize().getY()));
                }
            }

            int cell = 0;
            float startPos = getSprite().getActualXPos() - (cellWidth * length / 2f);

            for (char c : numberStr.toCharArray())
            {
                Sprite number = getNumber(c);
                number.setSize(numberWidth, numberHeight);
                number.setPosition(startPos + (cell * cellWidth) + (cellWidth / 2f), getSprite().getActualYPos());
                number.setVisible(true);

                numbers.add(number);

                cell++;
            }
        }
    }

    private float getDefaultRatio()
    {
        return Texture.calcRatio(Renderer.getBitmap(Textures.CHAR_ONE));
    }

    @Override
    public void update()
    {
    }

    @Override
    public void revalidate()
    {
        super.revalidate();

        for(Sprite number : numbers)
            number.getTexture().revalidate();
    }

    @Override
    public void draw() {
        super.draw();
        renderer.addSpriteList(numbers, getPriority());
    }

    private Sprite getNumber(char number)
    {
        switch (number)
        {
            case '0': return new Sprite(new Texture(Textures.CHAR_ZERO));
            case '1': return new Sprite(new Texture(Textures.CHAR_ONE));
            case '2': return new Sprite(new Texture(Textures.CHAR_TWO));
            case '3': return new Sprite(new Texture(Textures.CHAR_THREE));
            case '4': return new Sprite(new Texture(Textures.CHAR_FOUR));
            case '5': return new Sprite(new Texture(Textures.CHAR_FIVE));
            case '6': return new Sprite(new Texture(Textures.CHAR_SIX));
            case '7': return new Sprite(new Texture(Textures.CHAR_SEVEN));
            case '8': return new Sprite(new Texture(Textures.CHAR_EIGHT));
            case '9': return new Sprite(new Texture(Textures.CHAR_NINE));
            default: return new Sprite(new Texture(Textures.CHAR_ZERO));
        }
    }

    public void increaseCount()
    {
        setCount(++count);
    }

    @Override
    public void updateChilds()
    {
        super.updateChilds();
        validate();
    }

    public void setCount(int count)
    {
        if(adjustWidth)
            setRelativeSize(new Vector(0, getRelativeSize().getY()));

        updated = true;
        this.count = count;
        validate();
    }

    public void reset()
    {
        setCount(0);
    }

    @Override
    public void setRelativeSize(Vector relativeSize)
    {
        super.setRelativeSize(relativeSize);
        updated = true;
    }

    @Override
    public void setRelativePosition(Vector relativePosition)
    {
        super.setRelativePosition(relativePosition);
        updated = true;
    }

    public int getCount()
    {
        return count;
    }

    public void setAdjustWidth(boolean adjustWidth)
    {
        this.adjustWidth = adjustWidth;
    }
}
