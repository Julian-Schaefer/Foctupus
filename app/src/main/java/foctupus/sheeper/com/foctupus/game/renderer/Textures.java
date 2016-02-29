package foctupus.sheeper.com.foctupus.game.renderer;

import android.graphics.Bitmap;

import java.util.HashMap;

/**
 * Created by schae on 09.11.2015.
 */
public class Textures {

    public static HashMap<String, Integer> textures = new HashMap<>();
    //public static HashMap<String, Float> ratios = new HashMap<>();
    public static HashMap<String, Bitmap> bitmaps = new HashMap<>();


    public static final String BEACH = "beach";
    public static final String BACKGROUND = "background";
    public static final String CLIFFS = "cliffs";
    public static final String BUBBLE = "bubble";
    public static final String SCOREBOARD = "scoreboard";
    public static final String TENTACLE = "tentacle";
    public static final String TREASURE = "treasure";
    public static final String TITLE = "title";
    public static final String LOADSCREEN = "loadscreen";
    public static final String SCORE_BACKGROUND = "score_background";
    public static final String IC_LAUNCHER = "ic_launcher";

    public static final String BTN_BACK = "btn_back";
    public static final String BTN_START = "btn_start";
    public static final String BTN_BEST = "btn_best";
    public static final String BTN_HOME = "btn_home";
    public static final String BTN_RETRY = "btn_retry";

    public static final String LBL_BEST = "lbl_best";
    public static final String LBL_GAMEOVER = "lbl_gameover";
    public static final String LBL_YOURBEST = "lbl_yourbest";
    public static final String LBL_SCORE = "lbl_score";

    public static final String CHAR_ZERO = "char_zero";
    public static final String CHAR_ONE = "char_one";
    public static final String CHAR_TWO = "char_two";
    public static final String CHAR_THREE = "char_three";
    public static final String CHAR_FOUR = "char_four";
    public static final String CHAR_FIVE = "char_five";
    public static final String CHAR_SIX = "char_six";
    public static final String CHAR_SEVEN = "char_seven";
    public static final String CHAR_EIGHT = "char_eight";
    public static final String CHAR_NINE = "char_nine";



    public static String[] pictureNames = {
            BEACH,
            BACKGROUND,
            CLIFFS,
            BUBBLE,
            SCOREBOARD,
            TENTACLE,
            TREASURE,
            LOADSCREEN,
            SCORE_BACKGROUND,
            TITLE,
            IC_LAUNCHER,
            BTN_BACK,
            BTN_BEST,
            BTN_HOME,
            BTN_RETRY,
            BTN_START,
            LBL_BEST,
            LBL_GAMEOVER,
            LBL_SCORE,
            LBL_YOURBEST,
            CHAR_ZERO, CHAR_ONE, CHAR_TWO, CHAR_THREE, CHAR_FOUR, CHAR_FIVE, CHAR_SIX, CHAR_SEVEN, CHAR_EIGHT, CHAR_NINE
    };


    public static int getTextureID(String textureName)
    {
        if(textures != null && textures.containsKey(textureName))
            return textures.get(textureName);

        return -1;
    }

    /*public static float getRatio(String textureName)
    {
        if(ratios != null && ratios.containsKey(textureName))
            return ratios.get(textureName);

        return 1;
    }*/

    public static Bitmap getBitmap(String textureName)
    {
        if(bitmaps.containsKey(textureName))
            return bitmaps.get(textureName);

        return null;
    }

    public static boolean areDecoded()
    {
        return Textures.bitmaps != null && Textures.bitmaps.size() == Textures.pictureNames.length;
    }

}