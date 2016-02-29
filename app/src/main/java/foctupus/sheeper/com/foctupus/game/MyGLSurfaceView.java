package foctupus.sheeper.com.foctupus.game;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

import foctupus.sheeper.com.foctupus.MainActivity;
import foctupus.sheeper.com.foctupus.game.MyGLRenderer;
import foctupus.sheeper.com.foctupus.game.logic.GameManager;
import foctupus.sheeper.com.foctupus.game.renderer.Environment;
import foctupus.sheeper.com.foctupus.game.renderer.Renderer;

/**
 * Created by schae on 07.11.2015.
 */
public class MyGLSurfaceView extends GLSurfaceView {

    private MyGLRenderer renderer;
    private boolean visible;
    public MyGLSurfaceView(Context context, boolean emulator) {
        super(context);

        renderer = new MyGLRenderer(getContext());

        setEGLContextClientVersion(2);

        if(emulator)
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

        if(!visible) {
            visible = true;
            MainActivity.showAd();
        }
        else {
            visible = false;
            MainActivity.hideAd();
        }

        if(event != null)
        {
            final int eventAction = event.getAction();
            final GameManager gameManager = GameManager.getInstance();

            if(gameManager != null) {
                final int x = (int) event.getX();
                final int y = (int) (foctupus.sheeper.com.foctupus.game.renderer.Renderer.getHeight() - event.getY());

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
