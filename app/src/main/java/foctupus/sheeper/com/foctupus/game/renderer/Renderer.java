package foctupus.sheeper.com.foctupus.game.renderer;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.FloatBuffer;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import foctupus.sheeper.com.foctupus.game.renderer.shader.TextureShader;

/**
 * Created by schae on 10.11.2015.
 */
public class Renderer {

    private TextureShader shader;

    private volatile LinkedHashMap<Integer, LinkedList<SpriteList>> spriteLists;
    private volatile LinkedHashMap<Integer, SpriteList> sprites;
    private volatile LinkedList<Integer> priorities;

    private static int width;
    private static int height;

    private float[] projectionMatrix;
    private float[] mvp;
    private float[] transformation;

    private int mPositionHandle;
    private int mTexHandle;

    private FloatBuffer vertices;
    private FloatBuffer textureInformation;

    private static Renderer instance;

    public static Renderer getInstance()
    {
        if(instance == null)
            instance = new Renderer();

        return instance;
    }

    private Renderer()
    {
        spriteLists = new LinkedHashMap<>();
        sprites = new LinkedHashMap<>();
        priorities = new LinkedList<>();
    }



    public void revalidate(float[] projectionMatrix, int width, int height)
    {
        this.projectionMatrix = projectionMatrix;
        this.width = width;
        this.height = height;

        setup();
    }

    private void setup()
    {
        shader = new TextureShader();
        shader.start();

        mPositionHandle = shader.getPositionHandle();
        mTexHandle = shader.getTextureHandle();

        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glEnableVertexAttribArray(mTexHandle);

        vertices = Sprite.loadVertexBuffer();
        textureInformation = Sprite.loadTexArray();

        GLES20.glVertexAttribPointer(mPositionHandle, Sprite.COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false, Sprite.VERTEX_STRIDE
                , vertices);

        GLES20.glVertexAttribPointer(mTexHandle, Sprite.TEX_COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false, Sprite.TEX_VERTEX_STRIDE, textureInformation);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
    }



    public void draw()
    {
        for(int priority : priorities)
        {
            if(sprites.containsKey(priority))
                for(Sprite sprite : sprites.get(priority))
                {
                    prepareSprite(sprite);
                }

            if(spriteLists.containsKey(priority))
                for(SpriteList spriteList : spriteLists.get(priority))
                {
                    drawList(spriteList);
                }
        }

        sprites.clear();
        spriteLists.clear();
    }



    private void drawList(SpriteList list)
    {
        if(list instanceof StaticSpriteList)
        {
            StaticSpriteList staticList = (StaticSpriteList) list;
            if(staticList.getTexture() != null)
            {
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, staticList.getTexture().getID());

                for(Sprite sprite : list)
                {
                    drawSprite(sprite);
                }
            }

        }
        else
        {
            for (Sprite sprite : list)
            {
                prepareSprite(sprite);
            }
        }
    }

    private void prepareSprite(Sprite sprite)
    {
        if(sprite.getTexture() != null)
        {

            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, sprite.getTexture().getID());
            drawSprite(sprite);
        }
    }

    private void drawSprite(Sprite sprite)
    {
        if (sprite.isVisible()) {

            mvp = new float[16];
            transformation = sprite.getTransformationMatrix();

            Matrix.multiplyMM(mvp, 0, projectionMatrix, 0, transformation, 0);

            shader.loadMVPMatrix(mvp);

            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, Sprite.VERTEX_COUNT);

        }
    }

    public void addSprite(Sprite sprite, int priority)
    {
        if(sprites.containsKey(priority))
        {
            sprites.get(priority).add(sprite);
        }
        else
        {
            SpriteList spriteContainer = new GenericSpriteList();
            spriteContainer.add(sprite);
            sprites.put(priority, spriteContainer);
        }
        updatePriorities();
    }

    public void addSpriteList(SpriteList spriteList, int priority)
    {
        if(spriteLists.containsKey(priority))
        {
            spriteLists.get(priority).add(spriteList);
        }
        else
        {
            LinkedList<SpriteList> spriteListContainer = new LinkedList<>();
            spriteListContainer.add(spriteList);
            spriteLists.put(priority, spriteListContainer);
        }
        updatePriorities();
    }

    private void updatePriorities()
    {
        priorities = new LinkedList<>(sprites.keySet());
        priorities.addAll(spriteLists.keySet());
        Collections.sort(priorities);
    }

    public static int getHeight()
    {
        return height;
    }

    public static int getWidth()
    {
        return width;
    }

    public static void setContext(Context context)
    {
        Loader.setContext(context);
    }

}
