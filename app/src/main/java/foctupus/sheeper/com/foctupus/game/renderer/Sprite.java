package foctupus.sheeper.com.foctupus.game.renderer;

import android.opengl.Matrix;

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


    private boolean isVisible;
    private float[] mModelMatrix = new float[16];

    private int textureID = -1;
    private String textureName;

    private short priority = 1;


    public Sprite(String textureName)
    {
        this();
        setTexture(textureName);
    }

    public Sprite(int textureID, String textureName)
    {
        this();
        setTextureID(textureID, textureName);
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

    public void setTexture(String name)
    {
        textureID = Textures.getTextureID(name);
        textureName = name;
    }

    public void setTextureID(int id, String name)
    {
        textureID = id;
        textureName = name;
    }

    public String getTextureName()
    {
        return textureName;
    }

    public static FloatBuffer loadTexArray() {
        return mTexBuffer;
    }

    public static FloatBuffer loadVertexBuffer()
    {
        FloatBuffer mVertexBuffer;

        ByteBuffer vertexByteBuffer = ByteBuffer.allocateDirect(TEX_SQUARE.length * 4);
        vertexByteBuffer.order(ByteOrder.nativeOrder());
        mVertexBuffer = vertexByteBuffer.asFloatBuffer();
        mVertexBuffer.put(TEX_SQUARE);
        mVertexBuffer.position(0);
        return mVertexBuffer;
    }

    public float[] getTransformationMatrix()
    {
        Matrix.setIdentityM(mModelMatrix, 0);

        Matrix.translateM(mModelMatrix, 0, (int) xPos, (int) yPos, 0f);

        Matrix.rotateM(mModelMatrix, 0, angle, 0.0f, 0.0f, 1.0f);

        Matrix.scaleM(mModelMatrix, 0, xSize, ySize, 1);

        return mModelMatrix;
    }

    public static FloatBuffer createVertexArray(float[] coords) {
        // Allocate a direct ByteBuffer, using 4 bytes per float, and copy coords into it.
        ByteBuffer bb = ByteBuffer.allocateDirect(coords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer fb = bb.asFloatBuffer();
        fb.put(coords);
        fb.position(0);
        return fb;
    }

    public void revalidate()
    {
        setTexture(getTextureName());
    }

    public static FloatBuffer loadVertexArray() {
        return mVertexBuffer;
    }

    public int getTextureID()
    {
        return textureID;
    }


    public void setVisible(boolean isVisible)
    {
        this.isVisible = isVisible;
    }

    public boolean isVisible()
    {
        return isVisible;
    }



    public void setPriority(short priority)
    {
        this.priority = priority;
    }

    public short getPriority()
    {
        return priority;
    }


}
