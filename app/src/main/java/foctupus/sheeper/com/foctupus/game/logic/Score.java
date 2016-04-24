package foctupus.sheeper.com.foctupus.game.logic;

import foctupus.sheeper.com.foctupus.game.gui.Container;
import foctupus.sheeper.com.foctupus.game.renderer.Renderer;
import foctupus.sheeper.com.foctupus.game.renderer.Sprite;
import foctupus.sheeper.com.foctupus.game.renderer.Texture;
import foctupus.sheeper.com.foctupus.game.renderer.Textures;
import foctupus.sheeper.com.foctupus.game.renderer.util.Vector;

/**
 * Created by julianschafer on 23.04.16.
 */
public class Score extends Container {

    private Counter score;

    public Score(Renderer renderer) {
        this(renderer, 0);
    }

    public Score(Renderer renderer, int scoreCount) {
        super(renderer, new Sprite(new Texture(Textures.SCORE_BACKGROUND)));

        setPriority(Game.SCORE_PRIO);

        setRelativeSize(new Vector(USE_RATIO, 10));
        setRelativePosition(new Vector(50, 80));

        score = new Counter(scoreCount);
        score.setRelativePosition(new Vector(50, 50));
        score.setRelativeSize(new Vector(80, 80));

        addChild(score);
    }

    public void increase()
    {
        score.increaseCount();
    }

    public void reset()
    {
        score.reset();
    }

    public int getCount()
    {
        return score.getCount();
    }
}
