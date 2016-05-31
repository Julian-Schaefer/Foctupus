package foctupus.sheeper.com.foctupus.game.screen;

import foctupus.sheeper.com.foctupus.game.FoctupusDatabase;
import foctupus.sheeper.com.foctupus.engine.gui.Button;
import foctupus.sheeper.com.foctupus.engine.gui.Component;
import foctupus.sheeper.com.foctupus.engine.gui.Container;
import foctupus.sheeper.com.foctupus.engine.gui.transition.PositionTransition;
import foctupus.sheeper.com.foctupus.engine.gui.transition.Transition;
import foctupus.sheeper.com.foctupus.game.logic.Scoreboard;
import foctupus.sheeper.com.foctupus.engine.renderer.Renderer;
import foctupus.sheeper.com.foctupus.engine.renderer.Sprite;
import foctupus.sheeper.com.foctupus.engine.renderer.Texture;
import foctupus.sheeper.com.foctupus.engine.renderer.Textures;
import foctupus.sheeper.com.foctupus.engine.renderer.util.Vector;

/**
 * Created by julianschafer on 30.04.16.
 */
public class GameOverContainer extends Container implements Transition.TransitionListener
{
    private GameScreen gameScreen;

    private Button clicked;

    private Component gameOver;
    private Button homeButton;
    private Button retryButton;
    private Scoreboard scoreboard;
    private Component newBest;

    public GameOverContainer(Renderer renderer, GameScreen gameScreen, int scoreCount) {
        super(renderer);

        this.gameScreen = gameScreen;
        init(scoreCount);
    }

    private void init(int scoreCount)
    {
        gameOver = new Component(new Sprite(new Texture(Textures.LBL_GAMEOVER)));
        gameOver.setRelativeSize(new Vector(85, 13));

        homeButton = new Button(new Sprite(new Texture(Textures.BTN_HOME)));
        homeButton.setRelativeSize(new Vector(38, USE_SAME));

        retryButton = new Button(new Sprite(new Texture(Textures.BTN_RETRY)));
        retryButton.setRelativeSize(new Vector(38, USE_SAME));

        scoreboard = new Scoreboard(renderer, scoreCount);
        scoreboard.setRelativeSize(new Vector(95, 45));

        if(scoreCount > FoctupusDatabase.getInstance().getBest() || true) {
            FoctupusDatabase.getInstance().setBest(scoreCount);

            newBest = new Component(new Sprite(new Texture(Textures.LBL_NEW_BEST)));
            newBest.setRelativeSize(new Vector(40, 14));

            Transition newBestTransition = new Transition("NEWBEST", newBest);
            newBestTransition.setPositionTransition(new PositionTransition(new Vector(40, 50), new Vector(65, 50)));
            newBestTransition.setAutoRepeating(true);
            newBestTransition.setAutoReverting(true);
            newBestTransition.setAnimationTime(1100);
            newBest.startTransition(newBestTransition);

            scoreboard.addChild(newBest);
        }

        animateIn();

        addChild(gameOver);
        addChild(homeButton);
        addChild(retryButton);
        addChild(scoreboard);
    }

    private void animateIn()
    {
        Transition gameOverInTransition = new Transition("SLIDE_IN", gameOver);
        gameOverInTransition.setPositionTransition(new PositionTransition(new Vector(50, 150), new Vector(50, 88)));
        gameOverInTransition.setListener(this);
        gameOver.startTransition(gameOverInTransition);

        Transition homeButtonInTransition = new Transition("SLIDE_IN", homeButton);
        homeButtonInTransition.setPositionTransition(new PositionTransition(new Vector(-50, 65), new Vector(25, 65)));
        homeButton.startTransition(homeButtonInTransition);

        Transition retryButtonInTransition = new Transition("SLIDE_IN", retryButton);
        retryButtonInTransition.setPositionTransition(new PositionTransition(new Vector(150, 65), new Vector(75, 65)));
        retryButton.startTransition(retryButtonInTransition);

        Transition scoreBoardInTransition = new Transition("SLIDE_IN", scoreboard);
        scoreBoardInTransition.setPositionTransition(new PositionTransition(new Vector(50, -50), new Vector(50, 28)));
        scoreboard.startTransition(scoreBoardInTransition);
    }

    private void animateOut()
    {
        Transition gameOverOutTransition = new Transition("SLIDE_OUT", gameOver);
        gameOverOutTransition.setPositionTransition(new PositionTransition(new Vector(50, 88), new Vector(50, 150)));
        gameOverOutTransition.setListener(this);
        gameOver.startTransition(gameOverOutTransition);

        Transition homeButtonOutTransition = new Transition("SLIDE_OUT", homeButton);
        homeButtonOutTransition.setPositionTransition(new PositionTransition(new Vector(25, 65), new Vector(-50, 65)));
        homeButton.startTransition(homeButtonOutTransition);

        Transition retryButtonOutTransition = new Transition("SLIDE_OUT", retryButton);
        retryButtonOutTransition.setPositionTransition(new PositionTransition(new Vector(75, 65), new Vector(150, 65)));
        retryButton.startTransition(retryButtonOutTransition);

        Transition scoreBoardOutTransition = new Transition("SLIDE_OUT", scoreboard);
        scoreBoardOutTransition.setPositionTransition(new PositionTransition(new Vector(50, 28), new Vector(50, -50)));
        scoreboard.startTransition(scoreBoardOutTransition);
    }


    @Override
    public void onTransitionFinished(Transition transition) {
        if(clicked == null)
        {
            retryButton.addButtonListener(new Button.ButtonListener() {
                @Override
                public void onClick(Button button) {
                    clicked = retryButton;
                    retryButton.clearButtonListeners();
                    homeButton.clearButtonListeners();

                    animateOut();
                }
            });

            homeButton.addButtonListener(new Button.ButtonListener() {
                @Override
                public void onClick(Button button) {
                    clicked = homeButton;
                    homeButton.clearButtonListeners();
                    retryButton.clearButtonListeners();

                    animateOut();
                }
            });
        }
        else
        {
            if(clicked == retryButton)
                gameScreen.showStarter();
            else if(clicked == homeButton)
                gameScreen.showStartScreen();
        }
    }
}
