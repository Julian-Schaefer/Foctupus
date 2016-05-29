package foctupus.sheeper.com.foctupus.engine.renderer;

import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by schae on 25.01.2016.
 */
public class Sprite extends Rectangle {


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
        FloatBuffer defaultCoords = loadTexArray();

        ByteBuffer bb = ByteBuffer.allocateDirect(VERTEX_COUNT * TEX_VERTEX_STRIDE);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer fb = bb.asFloatBuffer();
        defaultCoords.position(0);
        fb.put(defaultCoords);
        fb.position(0);
        mTexBuffer = fb;
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

            logSpriteSize("normal-hdpi", 480, 854);
            logSpriteSize("large-hdpi", 720, 1280);


        }
    }

    private void logSpriteSize(String name, int x, int y)
    {
        double xRatio = getXSize() / Renderer.getWidth();
        double yRatio = getYSize() / Renderer.getHeight();

        double x_normal_hdpi = xRatio * x;
        double y_normal_hdpi = yRatio * y;
        Log.d("DEBUGTEXES", getTexture().getName() + ", "  + name + ": x=" + x_normal_hdpi + " y=" + y_normal_hdpi);
    }
}
