package foctupus.sheeper.com.foctupus.game.renderer;

import android.graphics.Bitmap;

/**
 * Created by schae on 11.02.2016.
 */
public class Texture {

    private int id;
    private String name;
    private Bitmap bitmap;
    private float ratio;

    public Texture(String name, int id)
    {
        this.name = name;
        this.id = id;
        bitmap = Textures.getBitmap(name);

        if(bitmap != null)
            ratio = (float) bitmap.getHeight() / (float) bitmap.getWidth();
    }

    public Texture(String name)
    {
        this(name, Textures.getTextureID(name));
    }

    public void revalidate()
    {
        id = Textures.getTextureID(name);
    }

    public void setID(int id)
    {
        this.id = id;
    }

    public int getID()
    {
        return id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public float getRatio()
    {
        return ratio;
    }
}
