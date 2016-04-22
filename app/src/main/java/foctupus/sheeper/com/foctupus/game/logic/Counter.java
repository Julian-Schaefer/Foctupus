package foctupus.sheeper.com.foctupus.game.logic;

import java.util.LinkedList;

import foctupus.sheeper.com.foctupus.game.gui.Component;
import foctupus.sheeper.com.foctupus.game.gui.Container;
import foctupus.sheeper.com.foctupus.game.renderer.Renderer;
import foctupus.sheeper.com.foctupus.game.renderer.Sprite;
import foctupus.sheeper.com.foctupus.game.renderer.Texture;
import foctupus.sheeper.com.foctupus.game.renderer.Textures;
import foctupus.sheeper.com.foctupus.game.renderer.util.Vector;

/**
 * Created by schae on 04.12.2015.
 */
public class Counter extends Container {

    private int count = 0;

    public Counter()
    {
        this(0);
    }

    public Counter(int count)
    {
        super(Renderer.getInstance());
        getSprite().setVisible(false);
        this.count = count;
    }

    protected void validate()
    {
        if(getRelativePosition() != null && getRelativeSize() != null) {
            clearChilds();

            float zeroRatio = (float) Texture.calcRatio(Renderer.getBitmap(Textures.CHAR_ZERO));

            String numberStr = String.valueOf(count);
            int length = numberStr.length();

            float numberHeight = 100f;
            float numberWidth = numberHeight / zeroRatio;

            float cellWidth = (numberWidth * 1.2f);

            if (cellWidth * length > 100f) {
                cellWidth = 100f / length;
                numberWidth = cellWidth * 0.8f;
                numberHeight = numberWidth * zeroRatio;
            }

            int cell = 0;
            float startPos = 50f - (cellWidth * length / 2f);

            for (char c : numberStr.toCharArray()) {
                Component number = getNumber(c);
                number.setRelativeSize(new Vector(numberWidth, numberHeight));
                number.setRelativePosition(new Vector(startPos + (cell * cellWidth) + (cellWidth / 2f), 50f));
                addChild(number);

                cell++;
            }
        }
    }

    @Override
    public void setRelativePosition(Vector relativePosition) {
        super.setRelativePosition(relativePosition);
        validate();
    }

    @Override
    public void setRelativeSize(Vector relativeSize) {
        super.setRelativeSize(relativeSize);
        validate();
    }

    private Component getNumber(char number)
    {
        Component num = new Component();
        switch (number)
        {
            case '0': num.setSprite(new Sprite(new Texture(Textures.CHAR_ZERO))); break;
            case '1': num.setSprite(new Sprite(new Texture(Textures.CHAR_ONE))); break;
            case '2': num.setSprite(new Sprite(new Texture(Textures.CHAR_TWO))); break;
            case '3': num.setSprite(new Sprite(new Texture(Textures.CHAR_THREE))); break;
            case '4': num.setSprite(new Sprite(new Texture(Textures.CHAR_FOUR))); break;
            case '5': num.setSprite(new Sprite(new Texture(Textures.CHAR_FIVE))); break;
            case '6': num.setSprite(new Sprite(new Texture(Textures.CHAR_SIX))); break;
            case '7': num.setSprite(new Sprite(new Texture(Textures.CHAR_SEVEN))); break;
            case '8': num.setSprite(new Sprite(new Texture(Textures.CHAR_EIGHT))); break;
            case '9': num.setSprite(new Sprite(new Texture(Textures.CHAR_NINE))); break;
            default: num.setSprite(new Sprite(new Texture(Textures.CHAR_ZERO))); break;
        }
        if(getSprite().isBottomLeftAligned())
            num.getSprite().setBottomLeftAligned(true);

        return num;
    }

    public void increaseCount()
    {
        count++;
        validate();
    }

    public void setCount(int count)
    {
        this.count = count;
        validate();
    }

    public void reset()
    {
        count = 0;
        validate();
    }

    public int getCount()
    {
        return count;
    }

}
