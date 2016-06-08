package com.sheeper.foctupus.game.logic;

import com.sheeper.foctupus.engine.gui.Component;
import com.sheeper.foctupus.engine.gui.Container;
import com.sheeper.foctupus.engine.renderer.Renderer;
import com.sheeper.foctupus.engine.renderer.Sprite;
import com.sheeper.foctupus.engine.renderer.Texture;
import com.sheeper.foctupus.engine.renderer.Textures;
import com.sheeper.foctupus.engine.renderer.util.Vector;
import com.sheeper.foctupus.game.FoctupusDatabase;

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

        lblScore.setRelativePosition(new Vector(31, 72));
        lblScore.setRelativeSize(new Vector(34, 20));

        lblBest.setRelativePosition(new Vector(30, 28));
        lblBest.setRelativeSize(new Vector(34, 20));

        score.setRelativePosition(new Vector(70, 72));
        score.setRelativeSize(new Vector(39, 18));

        best.setRelativePosition(new Vector(70, 28));
        best.setRelativeSize(new Vector(39, 18));

        addChild(lblScore);
        addChild(lblBest);
        addChild(score);
        addChild(best);
    }
}
