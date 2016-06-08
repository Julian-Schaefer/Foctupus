package com.sheeper.foctupus.engine.renderer;

import java.util.LinkedList;

/**
 * Created by schae on 09.02.2016.
 */
public class AnimatedSprite extends Sprite {

    private static final int STD_DURATION = 1000;

    private LinkedList<Texture> textures;
    private Texture current;

    private int duration = STD_DURATION;
    private long lastChange;

    public AnimatedSprite()
    {
        this(null);
    }

    public AnimatedSprite(LinkedList<Texture> textures)
    {
        super();
        if(textures != null)
            this.textures = textures;
        else
            this.textures = new LinkedList<>();
    }

    @Override
    public Texture getTexture()
    {
        if(System.currentTimeMillis() - lastChange > duration/textures.size() || current == null)
            getNext();

        return current;
    }

    private void getNext()
    {
        if(textures.size() > 0)
        {
            if(current == null)
                current = textures.getFirst();
            else
            {
                int index = textures.indexOf(current);

                if(index < textures.size()-1)
                    current = textures.get(index+1);
                else
                    current = textures.getFirst();
            }
        }
        lastChange = System.currentTimeMillis();
    }

    public void setDuration(int duration)
    {
        this.duration = duration;
    }

    public int getDuration()
    {
        return duration;
    }

    public void clearTextures()
    {
        textures.clear();
    }

    public void addTexture(Texture texture)
    {
        textures.add(texture);
    }

    public void removeTexture(Texture texture)
    {
        textures.remove(texture);
    }

    public void removeTexture(int i)
    {
        if(i > 0 && i < textures.size())
            textures.remove(i);
    }
}
