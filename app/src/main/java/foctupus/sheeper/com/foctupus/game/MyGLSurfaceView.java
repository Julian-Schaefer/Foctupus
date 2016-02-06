package foctupus.sheeper.com.foctupus.game;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

import foctupus.sheeper.com.foctupus.game.MyGLRenderer;
import foctupus.sheeper.com.foctupus.game.logic.GameManager;
import foctupus.sheeper.com.foctupus.game.renderer.Environment;

/**
 * Created by schae on 07.11.2015.
 */
public class MyGLSurfaceView extends GLSurfaceView {

    private MyGLRenderer renderer;

    public MyGLSurfaceView(Context context) {
        super(context);

        Log.i("Testdas", "SurfaceView Constructor ");
        renderer = new MyGLRenderer(getContext());

        setPreserveEGLContextOnPause(false);
        setEGLContextClientVersion(2);
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);

        setRenderer(renderer);
    }

    @Override
    public void onPause() {
        Log.i("Testdas", "SurfaceView onPause");
        super.onPause();

    }

    @Override
    public void onResume() {
        Log.i("Testdas", "SurfaceView onResume");
        super.onResume();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event != null)
        {
            final int eventAction = event.getAction();
            final GameManager gameManager = Environment.gameManager;

            if(gameManager != null) {
                final int x = (int) event.getX();
                final int y = (int) (Environment.height - event.getY());

                queueEvent(new Runnable() {
                    @Override
                    public void run() {
                        gameManager.onClick(x, y, eventAction);
                    }
                });
            }
        }

        return true;
    }
}
