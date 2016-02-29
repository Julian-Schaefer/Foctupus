package foctupus.sheeper.com.foctupus.game.renderer;

import java.util.LinkedList;

/**
 * Created by schae on 25.01.2016.
 */
public class StaticSpriteList extends SpriteList {

    private Texture texture;

    public StaticSpriteList(Texture texture)
    {
        this.texture = texture;
    }

    public void setTexture(Texture texture)
    {
        this.texture = texture;
    }

    public Texture getTexture()
    {
        return texture;
    }
}
