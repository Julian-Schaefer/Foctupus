package foctupus.sheeper.com.foctupus.game.renderer;

import java.util.LinkedList;

/**
 * Created by schae on 25.01.2016.
 */
public class StaticSpriteList extends SpriteList {

    private int id;
    private boolean visible;

    public StaticSpriteList(String textureName)
    {
        this(textureName, 0);
    }

    public StaticSpriteList(int textureID)
    {
        this(textureID, 0);
    }

    public StaticSpriteList(String textureName, int priority)
    {
        super(priority);
        id = Textures.getTextureID(textureName);
    }

    public StaticSpriteList(int textureID, int priority)
    {
        super(priority);
        id = textureID;
    }

    public int getTextureID()
    {
        return id;
    }

    public boolean isVisible()
    {
        return visible;
    }

    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }
}
