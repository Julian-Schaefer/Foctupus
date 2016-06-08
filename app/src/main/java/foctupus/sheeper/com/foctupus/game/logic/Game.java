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
    public static final int[] SPEED_LEVELS = { 2270, 2130, 2030, 1950 };

    private static final int GAME_OVER_TIMEOUT = 1000;

    private Renderer renderer;
    private Treasure treasure;

    private LinkedList<Tentacle> tentacles;

    private SoundPlayer soundPlayer;
    private GameListener listener;
    private Slider slider;

    private int score;
    private int speed;
    private boolean hasCut = false;

    private Vector lastTouch;

    private boolean gameOver = false;
    private long endTime = -1;

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
        Tentacle.Position position;
        Tentacle tentacle;

        int count;
        do
        {
            switch (Maths.randInt(1, 4))
            {
                case 1:
                    position = Tentacle.Position.LEFT;
                    break;
                case 2:
                    position = Tentacle.Position.RIGHT;
                    break;
                case 3:
                    position = Tentacle.Position.BOTTOM;
                    break;
                case 4:
                    position = Tentacle.Position.TOP;
                    break;
                default:
                    position = Tentacle.Position.BOTTOM;
            }

            count = 0;
            for (Tentacle t : tentacles)
                if(t.getPosition() == position)
                    count++;

            tentacle = new Tentacle(treasure, speed, position);

        } while(containsTentacleWay(tentacle.getWay()) || count > 1);

        tentacle.setListener(this);
        return tentacle;
    }

    public void draw()
    {
        ListIterator<Tentacle> iterator = tentacles.listIterator();

        while (iterator.hasNext())
        {
            Tentacle tentacle = iterator.next();

            tentacle.update();

            if (tentacle.isOutside())
            {
                iterator.remove();
                if(!gameOver)
                    iterator.add(createTentacle());
            } else
                renderer.addSpriteList(tentacle, Container.STD_PRIORITY);
        }

        if(!gameOver)
            slider.draw(renderer);
        else
            onGameOver();
    }

    private boolean containsTentacleWay(Tentacle.TentacleWay way)
    {
        for (Tentacle tentacle : tentacles)
            if(tentacle.getWay() == way)
                return true;

        return false;
    }

    public void revalidate()
    {
        for(Tentacle tentacle : tentacles)
        {
            tentacle.createTexture();
        }

        slider.revalidate();
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

        if(lastTouch != null)
        {
            for (Tentacle tentacle : tentacles)
            {
                if (!hasCut && !gameOver)
                    tentacle.checkPoints(lastTouch, currentTouch);
            }

            slider.addPoint(x, y);
            lastTouch = currentTouch;
        }
        else
            lastTouch = currentTouch;
    }

    public void onRelease(float x, float y)
    {
        Vector currentTouch = new Vector(x, y);
        for(Tentacle tentacle : tentacles)
        {
            if(!hasCut && !gameOver)
                tentacle.checkPoints(lastTouch, currentTouch);
        }

        lastTouch = null;
        hasCut = false;

        slider.reset();
    }

    public void start()
    {
        speed = SPEED_LEVELS[0];
        score = 0;
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

        score++;

        if(score == 10)
            speed = SPEED_LEVELS[1];
        else if(score == 22)
            speed = SPEED_LEVELS[2];
        else if(score == 35)
            speed = SPEED_LEVELS[3];


        if(listener != null)
            listener.onCut();
    }

    @Override
    public void hasFinished(Tentacle t) {
        if(!gameOver)
        {
            gameOver = true;
            endTime = System.currentTimeMillis();
        }
    }

    private void onGameOver()
    {
        if(endTime != -1 && System.currentTimeMillis() - endTime > GAME_OVER_TIMEOUT)
        {
            slider.reset();
            endTime = -1;
            if(listener != null)
                listener.onGameOver();
        }

    }

    public void setListener(GameListener listener)
    {
        this.listener = listener;
    }


    public interface GameListener
    {
        void onGameOver();
        void onCut();
    }
}
