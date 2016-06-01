package foctupus.sheeper.com.foctupus.engine.renderer;

import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import foctupus.sheeper.com.foctupus.engine.renderer.util.Vector;

/**
 * Created by schae on 25.01.2016.
 */
public class Sprite extends Rectangle {

    private static HashMap<String, Vector> sizes = new HashMap<>();

    private static final float TEX_SQUARE[] = {
            -0.5f, -0.5f,   // 0 bottom left
            0.5f, -0.5f,   // 1 bottom right
            -0.5f,  0.5f,   // 2 top left
            0.5f, 0.5f,   // 3 top right
    };

    private static final float TEX_COORDS[] = {
            0.0f,   1.0f,   // bottom left
            1.0f,   1.0f,   // bottom right
            0.0f,   0.0f,   // top left
            1.0f,   0.0f,   // top right
    };

    public static final int COORDS_PER_VERTEX = 2;

    public static final int VERTEX_COUNT = TEX_SQUARE.length/COORDS_PER_VERTEX;
    public static final int VERTEX_STRIDE = COORDS_PER_VERTEX * 4;

    public static final int TEX_COORDS_PER_VERTEX = 2;
    public static final int TEX_VERTEX_STRIDE = TEX_COORDS_PER_VERTEX * 4;

    private static FloatBuffer mTexBuffer = createVertexArray(TEX_COORDS);
    private static FloatBuffer mVertexBuffer = createVertexArray(TEX_SQUARE);

    static
    {
        ByteBuffer vertexByteBuffer = ByteBuffer.allocateDirect(TEX_SQUARE.length * 4);
        vertexByteBuffer.order(ByteOrder.nativeOrder());
        mVertexBuffer = vertexByteBuffer.asFloatBuffer();
        mVertexBuffer.put(TEX_SQUARE);
        mVertexBuffer.position(0);
    }

    private boolean isVisible;

    private Texture texture;

    public Sprite(Texture texture)
    {
        this();
        this.texture = texture;
    }

    public Sprite()
    {
    }

    public void setTexture(Texture texture)
    {
        this.texture = texture;
    }

    public Texture getTexture()
    {
        return texture;
    }

    public static FloatBuffer loadTexArray() {
        return mTexBuffer;
    }

    public static FloatBuffer createVertexArray(float[] coords) {
        ByteBuffer bb = ByteBuffer.allocateDirect(coords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer fb = bb.asFloatBuffer();
        fb.put(coords);
        fb.position(0);
        return fb;
    }

    public static FloatBuffer loadVertexBuffer() {
        return mVertexBuffer;
    }

    public void setVisible(boolean isVisible)
    {
        this.isVisible = isVisible;
    }

    public boolean isVisible()
    {
        return isVisible;
    }

    @Override
    public void setSize(float x, float y) {
        super.setSize(x, y);

        if(getTexture() != null)
        {
            addSprite(getTexture().getName(), x, y);
        }
    }

    private void logSpriteSize(String name, double width, double height)
    {
        width = height * (9f/16f);

        double xRatio = getXSize() / (Renderer.getHeight() * (9f/16f));
        double yRatio = getYSize() / Renderer.getHeight();

        double x = xRatio * width;
        double y = yRatio * height;
        Log.d("DEBUGTEXES", getTexture().getName() + ", "  + name + ": x=" + x + " von " + width + " y=" + y + " von " + height);
    }
    
    public static void addSprite(String name, float x, float y)
    {
        if(sizes.containsKey(name))
        {

            if((sizes.get(name).getX() < x || sizes.get(name).getY() < y) || name.equals(Textures.TITLE))
            {
                Vector v = new Vector(x, y);
                sizes.put(name, v);
            }
        }
        else
        {
            sizes.put(name, new Vector(x, y));
        }
    }

    public static void writeSizes()
    {

        Log.d("DEBUGTEXES", "------NEUE AUSGABE------");

        writeSize("beach");
        writeSize("background");
        writeSize("cliffs");
        writeSize("bubble");
        writeSize("scoreboard");
        writeSize("treasure");
        writeSize("title");
        writeSize("loadscreen");
        writeSize("help_instruction");
        writeSize("btn_back");
        writeSize("btn_start");
        writeSize("btn_best");
        writeSize("btn_home");
        writeSize("btn_retry");
        writeSize("btn_muted");
        writeSize("btn_unmuted");
        writeSize("btn_help");
        writeSize("btn_ok");
        writeSize("lbl_best");
        writeSize("lbl_gameover");
        writeSize("lbl_yourbest");
        writeSize("lbl_score");
        writeSize("lbl_newbest");
        writeSize("lbl_gamestart");
        writeSize("char_one");
        writeSize("char_two");
        writeSize("char_three");
        writeSize("char_four");
        writeSize("char_five");
        writeSize("char_six");
        writeSize("char_seven");
        writeSize("char_eight");
        writeSize("char_nine");
        writeSize("char_zero");

        /*for(Map.Entry<String, Vector> entry : sizes.entrySet())
        {
            Log.d("DEBUGTEXES", entry.getKey() + ": x=" + entry.getValue().getX()
                    + " von " + Renderer.getWidth() + " /----/ y=" + entry.getValue().getY() + " von " + Renderer.getHeight());
        }*/
    }

    private static void writeSize(String name)
    {
        if(!sizes.containsKey(name))
            return;

        Vector size = sizes.get(name);

        String nameString = name + ": ";
        String widthString = "x= " + (int) size.getX() + " von " + Renderer.getWidth();
        String heightString = "// y=" + (int) size.getY() + " von " + Renderer.getHeight();


        final int width = 30;
        StringBuffer b = new StringBuffer();

        b.append(nameString);

        for(int i = 0; i < width - nameString.length(); i++)
        {
            b.append(" ");
        }

        b.append(widthString);
        for(int i = 0; i < width - widthString.length(); i++)
            b.append(" ");

        b.append(heightString);
        for(int i = 0; i < width - heightString.length(); i++)
            b.append(" ");




        Log.d("DEBUGTEXES", b.toString());

        Log.d("DEBUGTEXES", "- ");
    }
}
