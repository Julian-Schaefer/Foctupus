package foctupus.sheeper.com.foctupus.screen;

import android.view.MotionEvent;

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
public class GameScreen extends Screen implements Game.GameListener {

    private Game game;

    private Score score;
    private boolean gameOver;
    private Treasure treasure;

    public GameScreen(Renderer renderer, Treasure treasure) {
        super(renderer, true);

        setPriority(15);
        this.treasure = treasure;

        game = new Game(treasure);
        game.setListener(this);

        init();
    }

    @Override
    public void draw() {
        game.draw(gameOver);
        super.draw();
    }

    @Override
    public void onTouch(float x, float y, int mode) {
        //super.onTouch(x, y, mode);
        switch (mode)
        {
            case MotionEvent.ACTION_DOWN: game.onPress(x, y); break;
            case MotionEvent.ACTION_MOVE: game.onMove(x, y); break;
            case MotionEvent.ACTION_UP: game.onRelease(x, y); break;
        }
    }

    @Override
    public void revalidate() {
        super.revalidate();

        game.revalidate();
    }

    @Override
    protected void init() {

        score = new Score(renderer);
        addChild(score);

        game.start();
    }

    @Override
    public void onFinished(Component component) {

    }

    @Override
    public void onTransitionFinished(Transition transition) {

    }

    @Override
    public void onGameOver() {

        score.reset();

        game = new Game(treasure);
        game.setListener(this);
    }

    @Override
    public void onScoreIncrease() {
        score.increase();
    }
}
