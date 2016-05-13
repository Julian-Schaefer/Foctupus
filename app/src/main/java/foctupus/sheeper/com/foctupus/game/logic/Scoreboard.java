package foctupus.sheeper.com.foctupus.game.logic;

import foctupus.sheeper.com.foctupus.game.FoctupusDatabase;
import foctupus.sheeper.com.foctupus.engine.gui.Component;
import foctupus.sheeper.com.foctupus.engine.gui.Container;
import foctupus.sheeper.com.foctupus.engine.renderer.Renderer;
import foctupus.sheeper.com.foctupus.engine.renderer.Sprite;
import foctupus.sheeper.com.foctupus.engine.renderer.Texture;
import foctupus.sheeper.com.foctupus.engine.renderer.Textures;
import foctupus.sheeper.com.foctupus.engine.renderer.util.Vector;

/**
 * Created by julianschafer on 10.05.16.
 */
public class Scoreboard extends Container {

    private Component lblScore;
    private Component lblBest;
    private Counter score;
    private Counter best;

    public Scoreboard(Renderer renderer, int score) {
        super(renderer, new Sprite(new Texture(Textures.SCOREBOARD)));

        init(score);
    }

    private void init(int scoreCount)
    {
        lblScore = new Component(new Sprite(new Texture(Textures.LBL_SCORE)));
        lblBest = new Component(new Sprite(new Texture(Textures.LBL_BEST)));
        score = new Counter(scoreCount);
        best = new Counter(FoctupusDatabase.getInstance().getBest());

        lblScore.setRelativePosition(new Vector(31, 75));
        lblScore.setRelativeSize(new Vector(34, 20));

        lblBest.setRelativePosition(new Vector(30, 25));
        lblBest.setRelativeSize(new Vector(34, 20));

        score.setRelativePosition(new Vector(70, 75));
        score.setRelativeSize(new Vector(39, 18));

        best.setRelativePosition(new Vector(70, 25));
        best.setRelativeSize(new Vector(39, 18));

        addChild(lblScore);
        addChild(lblBest);
        addChild(score);
        addChild(best);
    }
}
