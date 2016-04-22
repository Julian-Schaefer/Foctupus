package foctupus.sheeper.com.foctupus.game.logic;

import foctupus.sheeper.com.foctupus.game.FoctupusDatabase;
import foctupus.sheeper.com.foctupus.game.gui.Component;
import foctupus.sheeper.com.foctupus.game.gui.Container;
import foctupus.sheeper.com.foctupus.game.renderer.Renderer;
import foctupus.sheeper.com.foctupus.game.renderer.Sprite;
import foctupus.sheeper.com.foctupus.game.renderer.Texture;
import foctupus.sheeper.com.foctupus.game.renderer.Textures;
import foctupus.sheeper.com.foctupus.game.renderer.util.Vector;

/**
 * Created by julianschafer on 23.04.16.
 */
public class Scoreboard extends Container {


    private Component best;
    private Counter bestCounter;

    public Scoreboard(Renderer renderer) {
        super(renderer, new Sprite(new Texture(Textures.SCOREBOARD)));

        best = new Component(new Sprite(new Texture(Textures.LBL_YOURBEST)));
        best.setRelativeSize(new Vector(70, 25));
        best.setRelativePosition(new Vector(50, 70));

        bestCounter = new Counter(FoctupusDatabase.getInstance().getBest());
        bestCounter.setRelativeSize(new Vector(75, 25));
        bestCounter.setRelativePosition(new Vector(50, 30));

        addChild(best);
        addChild(bestCounter);
    }


}
