package foctupus.sheeper.com.foctupus.game.renderer;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.FloatBuffer;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import foctupus.sheeper.com.foctupus.game.shader.TextureShader;

/**
 * Created by schae on 10.11.2015.
 */
public class Renderer {

    private TextureShader shader;

    private volatile LinkedHashMap<Integer, LinkedList<SpriteList>> spriteLists;
    private volatile LinkedHashMap<Integer, LinkedList<Sprite>> sprites;
    private volatile LinkedList<Integer> priorities;

    private float[] projectionMatrix;
    private float[] mvp;
    private float[] transformation;

    private int mPositionHandle;
    private int mTexHandle;

    private FloatBuffer vertices;
    private FloatBuffer textureInformation;

    public Renderer(float[] projectionMatrix)
    {
        this.projectionMatrix = projectionMatrix;

        spriteLists = new LinkedHashMap<>();
        sprites = new LinkedHashMap<>();
        priorities = new LinkedList<>();

        setup();
    }

    public void loadMatrix(float[] projectionMatrix)
    {
        this.projectionMatrix = projectionMatrix;
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



    public void render()
    {
        for(int priority : priorities)
        {
            if(sprites.containsKey(priority))
                for(Sprite sprite : sprites.get(priority))
                {
                    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, sprite.getTextureID());
                    renderSprite(sprite);
                }

            if(spriteLists.containsKey(priority))
                for(LinkedList<Sprite> spriteList : spriteLists.get(priority))
                {
                    renderList(spriteList);
                }
        }

        sprites.clear();
    }


    private void renderList(LinkedList<Sprite> list)
    {
        if(list instanceof StaticSpriteList)
        {
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, ((StaticSpriteList) list).getTextureID());

            for(Sprite sprite : list)
            {
                renderSprite(sprite);
            }
        }
        else {
            for (Sprite sprite : list)
            {
                GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, sprite.getTextureID());
                renderSprite(sprite);
            }
        }
    }

    private void renderSprite(Sprite sprite)
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
            LinkedList<Sprite> spriteContainer = new LinkedList<>();
            spriteContainer.add(sprite);
            sprites.put(priority, spriteContainer);
        }
        updatePriorities();
    }

    public void addSpriteList(SpriteList spriteList)
    {
        if(spriteLists.containsKey(spriteList.getPriority()))
        {
            spriteLists.get(spriteList.getPriority()).add(spriteList);
        }
        else
        {
            LinkedList<SpriteList> spriteListContainer = new LinkedList<>();
            spriteListContainer.add(spriteList);
            spriteLists.put(spriteList.getPriority(), spriteListContainer);
        }
        updatePriorities();
    }

    private void updatePriorities()
    {
        priorities = new LinkedList<>(sprites.keySet());
        priorities.addAll(spriteLists.keySet());
        Collections.sort(priorities);
    }




}
