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
 * Created by julianschafer on 23.04.16.
 */
public class Bestboard extends Container {


    private Component best;
    private Counter bestCounter;

    public Bestboard(Renderer renderer) {
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
