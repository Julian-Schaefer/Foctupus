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

    private GameManager gameManager;

    public MyGLSurfaceView(Context context, boolean emulator) {
        super(context);

        renderer = new MyGLRenderer(getContext());

        setEGLContextClientVersion(2);

        if(emulator)
            setEGLConfigChooser(8, 8, 8, 8, 16, 0);

        setRenderer(renderer);

        gameManager = GameManager.getInstance();
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
    public boolean onTouchEvent(final MotionEvent event) {

            if(event != null && gameManager != null) {
                queueEvent(new Runnable() {
                    @Override
                    public void run() {
                        gameManager.onTouch(event.getX(),
                                foctupus.sheeper.com.foctupus.game.renderer.Renderer.getHeight() - event.getY(), event.getAction());
                    }
                });
            }
            else
                gameManager = GameManager.getInstance();

        return true;
    }
}
