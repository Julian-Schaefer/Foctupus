package foctupus.sheeper.com.foctupus.screen;

import foctupus.sheeper.com.foctupus.game.gui.Component;
import foctupus.sheeper.com.foctupus.game.gui.Screen;
import foctupus.sheeper.com.foctupus.game.gui.transition.Transition;
import foctupus.sheeper.com.foctupus.game.logic.Game;
import foctupus.sheeper.com.foctupus.game.logic.Score;
import foctupus.sheeper.com.foctupus.game.logic.Treasure;
import foctupus.sheeper.com.foctupus.game.renderer.Renderer;

/**
 * Created by julianschafer on 23.04.16.
 */
public class GameScreen extends Screen {

    private Game game;

    public GameScreen(Renderer renderer, Treasure treasure) {
        super(renderer, true);

        game = new Game(treasure);
        game.start();
    }

    @Override
    public void draw() {
        game.render(false);
    }

    @Override
    protected void init() {
    }

    @Override
    public void onFinished(Component component) {

    }

    @Override
    public void onTransitionFinished(Transition transition) {

    }
}
