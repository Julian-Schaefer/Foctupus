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
    private float relativeWidth;

    private GenericSpriteList numbers;

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
        if(getSprite().getXSize() > 0 && getSprite().getYSize() > 0) {
            numbers.clear();

            float zeroRatio = Texture.calcRatio(Renderer.getBitmap(Textures.CHAR_ONE));

            String numberStr = String.valueOf(count);
            int length = numberStr.length();

            float numberHeight = getSprite().getYSize();
            float numberWidth = numberHeight / zeroRatio;

            float cellWidth = (numberWidth * 1.1f);

            if (cellWidth * length > getSprite().getXSize()) {
                if(!adjustWidth)
                {
                    cellWidth = getSprite().getXSize() / length;
                    numberWidth = cellWidth * 0.9f;
                    numberHeight = numberWidth * zeroRatio;
                }
                else
                    super.setRelativeSize(new Vector(getRelativeSize().getX() * ((cellWidth * length) / getSprite().getXSize()), getRelativeSize().getY()));
            }

            int cell = 0;
            float startPos = getSprite().getActualXPos() - (cellWidth * length / 2f);

            for (char c : numberStr.toCharArray()) {
                Sprite number = getNumber(c);
                number.setSize(numberWidth, numberHeight);
                number.setPosition(startPos + (cell * cellWidth) + (cellWidth / 2f), getSprite().getActualYPos());
                number.setVisible(true);

                numbers.add(number);

                cell++;
            }
        }
    }

    @Override
    public void update()
    {
    }

    @Override
    public void draw() {
        super.draw();
        renderer.addSpriteList(numbers, getPriority());
    }

    private Sprite getNumber(char number)
    {
        Sprite num;
        switch (number)
        {
            case '0': num = new Sprite(new Texture(Textures.CHAR_ZERO)); break;
            case '1': num = new Sprite(new Texture(Textures.CHAR_ONE)); break;
            case '2': num = new Sprite(new Texture(Textures.CHAR_TWO)); break;
            case '3': num = new Sprite(new Texture(Textures.CHAR_THREE)); break;
            case '4': num = new Sprite(new Texture(Textures.CHAR_FOUR)); break;
            case '5': num = new Sprite(new Texture(Textures.CHAR_FIVE)); break;
            case '6': num = new Sprite(new Texture(Textures.CHAR_SIX)); break;
            case '7': num = new Sprite(new Texture(Textures.CHAR_SEVEN)); break;
            case '8': num = new Sprite(new Texture(Textures.CHAR_EIGHT)); break;
            case '9': num = new Sprite(new Texture(Textures.CHAR_NINE)); break;
            default: num = new Sprite(new Texture(Textures.CHAR_ZERO)); break;
        }

        return num;
    }

    public void increaseCount()
    {
        setCount(++count);
    }

    public void setCount(int count)
    {
        this.count = count;
        updated = true;
    }

    public void reset()
    {
        count = 0;
        updated = true;
    }

    @Override
    public void updateChilds()
    {
        super.updateChilds();
        validate();
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
