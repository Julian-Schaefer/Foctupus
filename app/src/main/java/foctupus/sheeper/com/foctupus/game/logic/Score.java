package foctupus.sheeper.com.foctupus.game.logic;

import foctupus.sheeper.com.foctupus.engine.gui.Component;
import foctupus.sheeper.com.foctupus.engine.gui.Container;
import foctupus.sheeper.com.foctupus.engine.renderer.Renderer;
import foctupus.sheeper.com.foctupus.engine.renderer.Sprite;
import foctupus.sheeper.com.foctupus.engine.renderer.Texture;
import foctupus.sheeper.com.foctupus.engine.renderer.Textures;
import foctupus.sheeper.com.foctupus.engine.renderer.util.Vector;

/**
 * Created by julianschafer on 23.04.16.
 */
public class Score extends Counter {

    private Component background;

    public Score() {
        this(0);
    }

    public Score(int score) {
        super(score);

        setPriority(Game.SCORE_PRIO);
        setAdjustWidth(true);

        setRelativeSize(new Vector(1, 9));
        setRelativePosition(new Vector(50, 85));

        background = new Component(new Sprite(new Texture(Textures.SCORE_BACKGROUND)));
        background.setRelativePosition(new Vector(50, 50));
        background.setRelativeSize(new Vector(125, 125));

        addChild(background);
    }
}
