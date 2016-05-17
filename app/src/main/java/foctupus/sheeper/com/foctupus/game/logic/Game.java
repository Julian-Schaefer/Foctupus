package foctupus.sheeper.com.foctupus.game.logic;

import java.util.LinkedList;
import java.util.ListIterator;

import foctupus.sheeper.com.foctupus.engine.gui.Container;
import foctupus.sheeper.com.foctupus.engine.renderer.Renderer;
import foctupus.sheeper.com.foctupus.engine.renderer.util.Vector;
import foctupus.sheeper.com.foctupus.game.MyGLRenderer;
import foctupus.sheeper.com.foctupus.game.tools.Maths;

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

    private int animationTime = 3000;
    private boolean hasCut = false;

    private Vector lastTouch;

    public Game(Treasure treasure)
    {
        renderer = Renderer.getInstance();
        this.treasure = treasure;
        tentacles = new LinkedList<>();

        soundPlayer = SoundPlayer.getInstance();
        slider = new Slider();

        start();
    }


    private Tentacle createTentacle()
    {
        Tentacle t = new Tentacle(treasure, animationTime);
        t.setListener(this);
        return t;
    }

    public void draw(boolean gameOver)
    {
        ListIterator<Tentacle> iterator = tentacles.listIterator();

        while(iterator.hasNext())
        {
            Tentacle tentacle = iterator.next();

            if(!gameOver)
                tentacle.update();

            if(tentacle.isOutside())
            {
                iterator.remove();
                iterator.add(createTentacle());
            }
            else
                renderer.addSpriteList(tentacle, Container.STD_PRIORITY);
        }

        if(!gameOver) {
            slider.render(renderer);
        }

    }

    public void revalidate()
    {
        for(Tentacle tentacle : tentacles)
        {
            tentacle.createTexture();
        }
    }

    public void onPress(float x, float y)
    {
        slider.reset();
        lastTouch = new Vector(x, y);
        slider.addPoint(x, y);
    }

    public void onMove(float x, float y)
    {
        Vector currentTouch = new Vector(x, y);
        if(lastTouch != null && Maths.lengthOf(currentTouch, lastTouch) < Renderer.getHeight()/4)
        {
            for (Tentacle tentacle : tentacles)
            {
                if (!hasCut)
                    tentacle.checkPoints(lastTouch, currentTouch);
            }

            slider.addPoint(x, y);
        }

        lastTouch = currentTouch;
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
        slider.reset();
        tentacles.clear();

        for(int i = 0; i < 4; i++)
        {
            tentacles.add(createTentacle());
        }
    }

    @Override
    public void isCut(Tentacle t) {
        soundPlayer.playCutSound();
        hasCut = true;

        if(listener != null)
            listener.onScoreIncrease();
    }

    @Override
    public void hasFinished() {
        if(listener != null)
            listener.onGameOver();
    }

    public void setListener(GameListener listener)
    {
        this.listener = listener;
    }


    public interface GameListener
    {
        void onGameOver();
        void onScoreIncrease();
    }
}
