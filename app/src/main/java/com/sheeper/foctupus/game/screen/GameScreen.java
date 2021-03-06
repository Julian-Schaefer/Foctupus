package com.sheeper.foctupus.game.screen;

import android.os.AsyncTask;
import android.view.MotionEvent;

import com.sheeper.foctupus.engine.gui.Component;
import com.sheeper.foctupus.engine.gui.Screen;
import com.sheeper.foctupus.engine.gui.transition.ResizeTransition;
import com.sheeper.foctupus.engine.gui.transition.Transition;
import com.sheeper.foctupus.engine.renderer.Renderer;
import com.sheeper.foctupus.engine.renderer.Sprite;
import com.sheeper.foctupus.engine.renderer.Texture;
import com.sheeper.foctupus.engine.renderer.Textures;
import com.sheeper.foctupus.engine.renderer.util.Vector;
import com.sheeper.foctupus.game.logic.Game;
import com.sheeper.foctupus.game.logic.GameManager;
import com.sheeper.foctupus.game.logic.Score;
import com.sheeper.foctupus.game.logic.Tentacle;
import com.sheeper.foctupus.game.logic.Treasure;

/**
 * Created by julianschafer on 23.04.16.
 */
public class GameScreen extends Screen implements Game.GameListener {

    private enum GameState
    {
        STARTING, PLAYING, GAME_OVER
    }

    private Game game;

    private Score score;
    private Treasure treasure;
    private GameOverContainer gameOverContainer;

    private GameState gameState = GameState.STARTING;

    private boolean loadTaskFinished;

    public GameScreen(Renderer renderer) {
        super(renderer);

        this.treasure = GameManager.getBackground().getTreasure();

        init();
    }

    @Override
    public void draw() {
        if(gameState == GameState.PLAYING)
            game.draw();

        super.draw();
    }

    @Override
    public void onTouch(float x, float y, int mode) {
        if(gameState == GameState.PLAYING) {
            switch (mode) {
                case MotionEvent.ACTION_DOWN:
                    game.onPress(x, y);
                    break;
                case MotionEvent.ACTION_MOVE:
                    game.onMove(x, y);
                    break;
                case MotionEvent.ACTION_UP:
                    game.onRelease(x, y);
                    break;
            }
        }
        else
        {
            super.onTouch(x, y, mode);
        }
    }

    public void showStarter()
    {
        clearChilds();
        gameState = GameState.STARTING;

        new GeneratorTask().execute();

        Component starter = new Component(new Sprite(new Texture(Textures.LBL_GAMESTART)));
        starter.setRelativePosition(new Vector(50, 75));

        Transition inTransition = new Transition("STARTER_IN", starter);
        inTransition.setResizeTransition(new ResizeTransition(new Vector(50, USE_RATIO), new Vector(85, USE_RATIO)));
        starter.addTransition(inTransition);

        Transition outTransition = new Transition("STARTER_OUT", starter);
        outTransition.setResizeTransition(new ResizeTransition(new Vector(85, USE_RATIO), new Vector(0, USE_RATIO)));
        outTransition.setListener(this);
        starter.addTransition(outTransition);

        startTransition();

        addChild(starter);
    }

    public void startGame()
    {
        while(!loadTaskFinished);

        loadTaskFinished = false;

        clearChilds();

        score = new Score();
        addChild(score);

        game = new Game(treasure);
        game.setListener(this);
        game.start();

        gameState = GameState.PLAYING;
    }

    private void showGameOver()
    {
        gameState = GameState.GAME_OVER;
        clearChilds();

        gameOverContainer = new GameOverContainer(renderer, this, score.getCount());
        gameOverContainer.setRelativeSize(new Vector(100, 100));
        gameOverContainer.setRelativePosition(new Vector(50, 50));
        addChild(gameOverContainer);
    }

    public void showStartScreen()
    {
        finishScreen(new StartScreen(renderer));
    }

    @Override
    public void revalidate() {
        if(game != null)
            game.revalidate();

        if(score != null)
            score.revalidate();

        if(gameOverContainer != null)
            gameOverContainer.revalidate();
    }

    @Override
    protected void init() {
        showStarter();
    }

    @Override
    public void onFinished(Component component) {

    }

    @Override
    public void onTransitionFinished(Transition transition) {
        if(transition.getName().equals("STARTER_OUT"))
        {
            startGame();
        }
    }

    @Override
    public void onGameOver() {
        showGameOver();
    }

    @Override
    public void onCut() {
        score.increaseCount();
    }

    private class GeneratorTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids)
        {
            Tentacle.generateTentacles(60);

            return null;
        }

        @Override
        protected void onPostExecute(Void s)
        {
            loadTaskFinished = true;
        }
    }
}
