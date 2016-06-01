package foctupus.sheeper.com.foctupus.game.logic;

import foctupus.sheeper.com.foctupus.engine.renderer.Renderer;
import foctupus.sheeper.com.foctupus.engine.renderer.Sprite;
import foctupus.sheeper.com.foctupus.engine.renderer.Texture;
import foctupus.sheeper.com.foctupus.engine.renderer.Textures;
import foctupus.sheeper.com.foctupus.engine.renderer.util.Vector;

/**
 * Created by julianschafer on 23.04.16.
 */
public class Treasure extends Sprite {


    float left, right, top, bottom;

    public Treasure()
    {
        super(new Texture(Textures.TREASURE));

        setPosition(Renderer.getWidth()/2, Renderer.getHeight()/2);
        float height = Renderer.getHeight() * 15f/100;
        setSize(height/getTexture().getRatio(), height);

        left = getXPos() - getXSize()/6;
        right = getXPos() + getXSize()/6;
        bottom = getYPos() - getYSize()/6;
        top = getYPos() + getYSize()/6;

        setVisible(true);
    }

    public boolean intersects(Vector pos)
    {
        if(pos.getX() >= left && pos.getX() <= right && pos.getY() >= bottom && pos.getY() <= top)
            return true;

        return false;
    }
}
