package foctupus.sheeper.com.foctupus.game.logic;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;

import java.util.Iterator;
import java.util.LinkedList;

import foctupus.sheeper.com.foctupus.engine.gui.Component;
import foctupus.sheeper.com.foctupus.engine.gui.Screen;
import foctupus.sheeper.com.foctupus.engine.renderer.Loader;
import foctupus.sheeper.com.foctupus.engine.renderer.Renderer;
import foctupus.sheeper.com.foctupus.engine.renderer.Sprite;
import foctupus.sheeper.com.foctupus.engine.renderer.StaticSpriteList;
import foctupus.sheeper.com.foctupus.engine.renderer.Texture;
import foctupus.sheeper.com.foctupus.engine.renderer.Textures;
import foctupus.sheeper.com.foctupus.engine.renderer.util.Vector;
import foctupus.sheeper.com.foctupus.game.tools.Maths;

/**
 * Created by julianschafer on 23.05.16.
 */
public class Background {

    private static final int BUBBLE_PRIO = 1000;

    private StaticSpriteList bubbles;

    private int priority;

    private Treasure treasure;

    private long lastBubble;
    private int bubbleSpawnTime;

    private Sprite background;

    private Renderer renderer;


    public Background()
    {
        renderer = Renderer.getInstance();

        bubbles = new StaticSpriteList(new Texture(Textures.BUBBLE));

        priority = Screen.STD_PRIORITY;
        init();
    }

    private void init()
    {
        treasure = new Treasure();

        //DEBUG
        Sprite.addSprite(treasure.getTexture().getName(), treasure.getXSize(), treasure.getYSize());

        Bitmap output = Bitmap.createBitmap(Renderer.getWidth(), Renderer.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        Bitmap back = Renderer.getBitmap(Textures.BACKGROUND);
        Rect backRect = new Rect(0, 0, back.getWidth(), (int) (back.getWidth() * Texture.calcRatio(back)));

        canvas.drawBitmap(back, backRect, new Rect(0, 0, output.getWidth(), output.getHeight()), paint);


        Bitmap cliffs = Renderer.getBitmap(Textures.CLIFFS);
        Rect cliffRect = new Rect(0, 0, cliffs.getWidth(), (int) (cliffs.getWidth() * Texture.calcRatio(cliffs)));

        float innerWidth = Renderer.getHeight() * (9f/16f);
        float centerX = (Renderer.getWidth() - innerWidth) / 2 + (float) Maths.toPercent(30, innerWidth);
        float centerY = Renderer.getHeight() - (float) Maths.toPercent(31, Renderer.getHeight());
        float height =  (float) Maths.toPercent(60, Renderer.getHeight());

        canvas.drawBitmap(cliffs, cliffRect, new Rect((int) (centerX - innerWidth/2), (int) (centerY - height/2),
                (int) (centerX + innerWidth/2), (int) (centerY + height/2)), paint);


        //DEBUG
        Sprite.addSprite("cliffs", innerWidth, height);
        Sprite.addSprite("beach", Renderer.getWidth(), (int) (Maths.toPercent(32, Renderer.getHeight())));

        Bitmap beach = Renderer.getBitmap(Textures.BEACH);
        Rect beachRect = new Rect(0, 0, beach.getWidth(), (int) (beach.getWidth() * Texture.calcRatio(beach)));

        canvas.drawBitmap(beach, beachRect, new Rect(0, Renderer.getHeight() - (int) (output.getWidth() * Texture.calcRatio(beach)),
                Renderer.getWidth(), output.getHeight()), paint);


        Bitmap treasureBitmap = Renderer.getBitmap(Textures.TREASURE);
        Rect treasureRect = new Rect(0, 0, treasureBitmap.getWidth(), (int) (treasureBitmap.getWidth() * Texture.calcRatio(treasureBitmap)));

        canvas.drawBitmap(treasureBitmap, treasureRect, new Rect((int) (treasure.getActualXPos() - treasure.getXSize()/2), (int) (treasure.getActualYPos() - treasure.getYSize()/2),
                (int) (treasure.getActualXPos() + treasure.getXSize()/2), (int) (treasure.getActualYPos() + treasure.getYSize()/2)), paint);

        background = new Sprite(new Texture("background", Loader.loadTexture(output)));
        background.setPosition(Renderer.getWidth()/2f, Renderer.getHeight()/2f);
        background.setSize(Renderer.getWidth(), Renderer.getHeight());
        background.setVisible(true);

        //DEBUG
        Sprite.addSprite(background.getTexture().getName(), background.getXSize(), background.getYSize());
    }

    public void updateAndDraw()
    {
        long time = System.currentTimeMillis();
        Iterator<Sprite> iterator = bubbles.iterator();
        while(iterator.hasNext())
        {
            Bubble bubble = (Bubble) iterator.next();

            if(bubble.isOut()) {
                iterator.remove();
                continue;
            }

            bubble.update(time);
        }

        if(time - lastBubble > bubbleSpawnTime)
        {
            for(int i = 0; i < Maths.randInt(0,2); i++)
                bubbles.add(new Bubble());

            lastBubble = System.currentTimeMillis();
            bubbleSpawnTime = Maths.randInt(6500, 11000);
        }

        renderer.addSprite(background, priority);
        renderer.addSpriteList(bubbles, BUBBLE_PRIO);
    }

    public void revalidate()
    {
        init();
        bubbles.getTexture().revalidate();
    }

    public Treasure getTreasure()
    {
        return treasure;
    }

    private class Bubble extends Sprite
    {
        private int speed;
        private long startTime;

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

            speed = Maths.randInt(7000, 10000);

            setVisible(true);
            startTime = System.currentTimeMillis();
        }

        public void update(long time)
        {
            float diff = (float) (time - startTime);
            setPosition(getXPos(), Renderer.getHeight() * (diff / speed));
        }

        public boolean isOut()
        {
            return (getYPos() > (Renderer.getHeight() + (getYSize()/2)));
        }
    }
}
