package foctupus.sheeper.com.foctupus.game.renderer;

/**
 * Created by schae on 11.02.2016.
 */
public class Texture {

    private int id;
    private String name;

    public Texture(String name, int id)
    {
        this.name = name;
        this.id = id;
    }


    public Texture(String name)
    {
        this.name = name;
        this.id = Textures.getTextureID(name);
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
}
