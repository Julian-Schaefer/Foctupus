package foctupus.sheeper.com.foctupus.engine.renderer;

/**
 * Created by schae on 05.02.2016.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

public final class Loader {

    private static Context context;

    private Loader()
    {

    }

    /*public static float getRatio(Bitmap bitmap)
    {
        if(bitmap != null)
            return (float) bitmap.getWidth() / (float) bitmap.getHeight();

        return -1;
    }*/

    public static Bitmap decodeTexture(String name)
    {
        int resID = context.getResources().getIdentifier(name , "drawable", context.getPackageName());
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resID, options);

        return bitmap;
    }

    public static int loadTexture(Bitmap bitmap)
    {
        final int[] textureHandle = new int[1];

        GLES20.glGenTextures(1, textureHandle, 0);

        if (textureHandle[0] != 0)
        {

            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);

            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);


            return textureHandle[0];
        }

        if (textureHandle[0] == 0)
        {
            throw new RuntimeException("Error loading texture.");
        }

        return textureHandle[0];
    }



    public static void setContext(Context context)
    {
        Loader.context = context;
    }

    public static Context getContext()
    {
        return context;
    }

}
