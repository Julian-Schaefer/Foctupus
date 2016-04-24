package foctupus.sheeper.com.foctupus.game.logic;

import java.util.Iterator;
import java.util.LinkedList;

import foctupus.sheeper.com.foctupus.game.MyGLRenderer;
import foctupus.sheeper.com.foctupus.game.renderer.Renderer;
import foctupus.sheeper.com.foctupus.game.renderer.util.Vector;

/**
 * Created by julianschafer on 23.04.16.
 */
public class Game implements Tentacle.TentacleListener {

    public static final int SLIDER_PRIO = 20;
    public static final int SCORE_PRIO = 30;

    private Renderer renderer;
    private Treasure treasure;

    private LinkedList<Tentacle> tentacles;

    private SoundPlayer soundPlayer;
    private GameListener listener;
    private Slider slider;
    private Score score;

    private int animationTime = 3000;
    private boolean hasCut = false;

    private Vector lastTouch;
    private boolean clear;

    public Game(Treasure treasure)
    {
        renderer = Renderer.getInstance();
        this.treasure = treasure;
        tentacles = new LinkedList<>();

        soundPlayer = new SoundPlayer();
        slider = new Slider();
    }


    private void createTentacle()
    {
        Tentacle t = new Tentacle(treasure, animationTime);
        t.setListener(this);
        tentacles.add(t);
    }

    public void render(boolean gameOver)
    {
        if(clear)
            tentacles.clear();

        Iterator<Tentacle> iterator = tentacles.iterator();
        int removed = 0;

        while(iterator.hasNext())
        {
            Tentacle tentacle = iterator.next();

            if(!gameOver)
                tentacle.update();

            if(tentacle.isOutside())
            {
                iterator.remove();
                removed++;
            }
            else
                renderer.addSpriteList(tentacle, 15);
        }

        for(int i = 0; i < removed; i++)
            createTentacle();

        if(!gameOver) {
            slider.render(renderer);
            score.draw();
        }


    }

    public void clear()
    {
        clear = true;
    }

    public void onPress(float x, float y)
    {
        lastTouch = new Vector(x, y);
        slider.addPoint(x, y);
    }

    public void onMove(float x, float y)
    {
        Vector currentTouch = new Vector(x, y);
        for(Tentacle tentacle : tentacles)
        {
            if(!hasCut)
                tentacle.checkPoints(lastTouch, currentTouch);
        }

        lastTouch = currentTouch;
        slider.addPoint(x, y);
    }

    public void onRelease(float x, float y)
    {
        Vector currentTouch = new Vector(x, y);
        for(Tentacle tentacle : tentacles)
        {
            if(!hasCut)
                tentacle.checkPoints(lastTouch, currentTouch);
        }

        lastTouch = null;
        hasCut = false;

        slider.reset();
    }

    public void start()
    {
        score = new Score(renderer);

        slider.reset();
        tentacles.clear();
        for(int i = 0; i < 4; i++)
        {
            createTentacle();
        }
    }

    @Override
    public void isCut(Tentacle t) {
        soundPlayer.playCutSound();
        score.increase();
        hasCut = true;
    }

    @Override
    public void hasFinished() {
        if(listener != null)
            listener.onGameOver(score.getCount());
    }

    public void setListener(GameListener listener)
    {
        this.listener = listener;
    }


    public interface GameListener
    {
        void onGameOver(int score);
    }
}
