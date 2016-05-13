package foctupus.sheeper.com.foctupus.game.logic;

import java.util.Iterator;
import java.util.LinkedList;

import foctupus.sheeper.com.foctupus.engine.gui.Component;
import foctupus.sheeper.com.foctupus.engine.gui.Screen;
import foctupus.sheeper.com.foctupus.engine.gui.transition.Transition;
import foctupus.sheeper.com.foctupus.engine.renderer.Renderer;
import foctupus.sheeper.com.foctupus.engine.renderer.Sprite;
import foctupus.sheeper.com.foctupus.engine.renderer.Texture;
import foctupus.sheeper.com.foctupus.engine.renderer.Textures;
import foctupus.sheeper.com.foctupus.engine.renderer.util.Vector;
import foctupus.sheeper.com.foctupus.game.tools.Maths;

/**
 * Created by julianschafer on 14.04.16.
 */
public class BackgroundScreen extends Screen {

    private static final int BUBBLE_PRIO = 1000;
    private static final int BUBBLE_SPAWN_TIME = 11000;

    private Component cliffs;
    private Component beach;
    private LinkedList<Bubble> bubbles;

    private int priority;

    private Treasure treasure;

    private long lastBubble;

    public BackgroundScreen()
    {
        super(Renderer.getInstance(), true);

        bubbles = new LinkedList<>();

        setSprite(new Sprite(new Texture(Textures.BACKGROUND)));
        getSprite().setVisible(true);

        init();
    }

    @Override
    protected void init()
    {

        cliffs = new Component(new Sprite(new Texture(Textures.CLIFFS)));
        cliffs.getSprite().setBottomLeftAligned(true);
        cliffs.setRelativePosition(new Vector(0, 8));
        cliffs.setRelativeSize(new Vector(80, USE_RATIO));

        beach = new Component(new Sprite(new Texture(Textures.BEACH)));

        beach.getSprite().setBottomLeftAligned(true);
        beach.setRelativePosition(new Vector(0, 0));
        beach.setRelativeSize(new Vector(100, USE_RATIO));

        addChild(cliffs);
        addChild(beach);

        priority = getPriority();
        treasure = new Treasure();
    }

    public Treasure getTreasure()
    {
        return treasure;
    }


    @Override
    public void revalidate() {
        super.revalidate();

        treasure = new Treasure();

        for(Bubble bubble : bubbles)
            bubble.getTexture().revalidate();
    }

    @Override
    public void update() {

        Iterator<Bubble> iterator = bubbles.iterator();
        while(iterator.hasNext())
        {
            Bubble bubble = iterator.next();

            if(bubble.isOut()) {
                iterator.remove();
                continue;
            }

            bubble.update();
        }

        if(System.currentTimeMillis() - lastBubble > BUBBLE_SPAWN_TIME)
        {
            for(int i = 0; i < Maths.randInt(1,2); i++) {
                Bubble b = new Bubble();
                bubbles.add(b);
            }
            lastBubble = System.currentTimeMillis();
        }
    }

    @Override
    public void draw() {
        super.draw();

        renderer.addSprite(treasure, priority);

        for(Bubble bubble : bubbles)
        {
            renderer.addSprite(bubble, BUBBLE_PRIO);
        }
    }

    @Override
    public void onFinished(Component component) {

    }

    @Override
    public void onTransitionFinished(Transition transition) {

    }

    private class Bubble extends Sprite
    {


        private double speed;

        public Bubble()
        {
            super(new Texture(Textures.BUBBLE));
            init();
        }

        public void init()
        {
            int size = Maths.randInt((int) Maths.toPercent(4, Renderer.getHeight()), (int) Maths.toPercent(8, Renderer.getHeight()));
            int yPos = -size;
            int xPos = Maths.randInt(size/2, Renderer.getWidth()-(size/2));

            setPosition(xPos, yPos);
            setSize(size, size);

            speed = Maths.randInt((int) Maths.toPercent(0.1, Renderer.getHeight()), (int) Maths.toPercent(0.2, Renderer.getHeight()));

            setVisible(true);
        }

        public void update()
        {
            setPosition(getXPos(), (float) (getYPos() + speed));
        }



        public boolean isOut()
        {
            return (getYPos() > (Renderer.getHeight() + (getYSize()/2)));
        }
    }

}


