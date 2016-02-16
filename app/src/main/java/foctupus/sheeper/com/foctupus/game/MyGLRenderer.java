package foctupus.sheeper.com.foctupus.game;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import foctupus.sheeper.com.foctupus.BuildConfig;
import foctupus.sheeper.com.foctupus.game.logic.GameManager;
import foctupus.sheeper.com.foctupus.game.renderer.Environment;
import foctupus.sheeper.com.foctupus.game.renderer.Loader;
import foctupus.sheeper.com.foctupus.game.renderer.Renderer;
import foctupus.sheeper.com.foctupus.game.renderer.Textures;

/**
 * Created by schae on 07.11.2015.
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {

    private float[] projectionMatrix = new float[16];
    private GameManager gameManager;
    private boolean created = false;

    public MyGLRenderer(Context context)
    {
        Environment.context = context;
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        if (BuildConfig.DEBUG)
            Log.d(Environment.TAG, "MyGLRenderer: onSurfaceCreated called.");

        GLES20.glClearColor((110f / 256f), (161f / 256f), (255f / 256f), 1f);

        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA);

        GLES20.glDisable(GLES20.GL_DEPTH_TEST);
        GLES20.glDisable(GLES20.GL_TEXTURE_2D);
        GLES20.glDisable(GLES20.GL_CULL_FACE);


        created = true;
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        if (BuildConfig.DEBUG)
            Log.d(Environment.TAG, "MyGLRenderer: onSurfaceChanged called.");

        float oldX = Environment.width;
        float oldY = Environment.height;

        boolean surfaceChanged = false;
        if(width > 0 && height > 0 && (Environment.width != width || Environment.height != height))
        {
            GLES20.glViewport(0, 0, width, height);

            Environment.width = width;
            Environment.height = height;

            Matrix.orthoM(projectionMatrix, 0, 0, width, 0, height, -1f, 1f);

            surfaceChanged = true;
        }

        if(created || surfaceChanged)
        {
            if (BuildConfig.DEBUG)
                Log.d(Environment.TAG, "Surface got changed");

            if(Environment.gameManager == null || Environment.renderer == null)
            {
                Environment.renderer = new Renderer(projectionMatrix);
                Environment.gameManager = new GameManager();
            }
            else
            {
                Environment.renderer.revalidate(projectionMatrix);
                Environment.gameManager.revalidate(created, oldX, oldY);
            }

            gameManager = Environment.gameManager;

            created = false;
        }

    }


    @Override
    public void onDrawFrame(GL10 gl) {

        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

        if (gameManager != null) {
            gameManager.update();
            gameManager.draw();
        }
    }

}
