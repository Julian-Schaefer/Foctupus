package com.sheeper.foctupus.game;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.sheeper.foctupus.game.logic.GameManager;


/**
 * Created by schae on 07.11.2015.
 */
public class MyGLSurfaceView extends GLSurfaceView {

    private MyGLRenderer renderer;

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
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {

            if(event != null && gameManager != null) {
                queueEvent(new Runnable() {
                    @Override
                    public void run() {
                        gameManager.onTouch(event.getX(),
                                com.sheeper.foctupus.engine.renderer.Renderer.getHeight() - event.getY(), event.getAction());
                    }
                });
            }
            else
                gameManager = GameManager.getInstance();

        return true;
    }
}
