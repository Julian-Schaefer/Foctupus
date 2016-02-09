package foctupus.sheeper.com.foctupus.game.renderer;

import android.content.Context;

import foctupus.sheeper.com.foctupus.game.logic.GameManager;

/**
 * Created by schae on 04.02.2016.
 */
public final class Environment {

    private Environment() {}

    public static int width;
    public static int height;
    public static Renderer renderer;
    public static GameManager gameManager;
    public static Context context;

    public static final String TAG = "com.sheeper.foctupus";
}
