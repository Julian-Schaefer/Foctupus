package foctupus.sheeper.com.foctupus.screen;

import foctupus.sheeper.com.foctupus.game.gui.Button;
import foctupus.sheeper.com.foctupus.game.gui.Component;
import foctupus.sheeper.com.foctupus.game.gui.Container;
import foctupus.sheeper.com.foctupus.game.gui.transition.PositionTransition;
import foctupus.sheeper.com.foctupus.game.gui.transition.Transition;
import foctupus.sheeper.com.foctupus.game.logic.GameOverScoreboard;
import foctupus.sheeper.com.foctupus.game.renderer.Renderer;
import foctupus.sheeper.com.foctupus.game.renderer.Sprite;
import foctupus.sheeper.com.foctupus.game.renderer.Texture;
import foctupus.sheeper.com.foctupus.game.renderer.Textures;
import foctupus.sheeper.com.foctupus.game.renderer.util.Vector;

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
    private GameOverScoreboard scoreboard;

    public GameOverContainer(Renderer renderer, GameScreen gameScreen) {
        super(renderer);

        this.gameScreen = gameScreen;
        init();
    }

    private void init()
    {
        gameOver = new Component(new Sprite(new Texture(Textures.LBL_GAMEOVER)));
        gameOver.setRelativeSize(new Vector(80, USE_RATIO));

        homeButton = new Button(new Sprite(new Texture(Textures.BTN_HOME)));
        homeButton.setRelativeSize(new Vector(40, USE_SAME));

        retryButton = new Button(new Sprite(new Texture(Textures.BTN_RETRY)));
        retryButton.setRelativeSize(new Vector(40, USE_SAME));

        animateIn();

        addChild(gameOver);
        addChild(homeButton);
        addChild(retryButton);
    }

    private void animateIn()
    {
        Transition gameOverInTransition = new Transition("SLIDE_IN", gameOver);
        gameOverInTransition.setPositionTransition(new PositionTransition(new Vector(50, 150), new Vector(50, 75)));
        gameOverInTransition.setListener(this);
        gameOver.startTransition(gameOverInTransition);

        Transition homeButtonInTransition = new Transition("SLIDE_IN", homeButton);
        homeButtonInTransition.setPositionTransition(new PositionTransition(new Vector(-50, 60), new Vector(30, 60)));
        homeButton.startTransition(homeButtonInTransition);

        Transition retryButtonInTransition = new Transition("SLIDE_IN", retryButton);
        retryButtonInTransition.setPositionTransition(new PositionTransition(new Vector(150, 60), new Vector(70, 60)));
        retryButton.startTransition(retryButtonInTransition);
    }

    private void animateOut()
    {
        Transition gameOverOutTransition = new Transition("SLIDE_IN", gameOver);
        gameOverOutTransition.setPositionTransition(new PositionTransition(new Vector(50, 75), new Vector(50, 150)));
        gameOverOutTransition.setListener(this);
        gameOver.startTransition(gameOverOutTransition);

        Transition homeButtonOutTransition = new Transition("SLIDE_IN", homeButton);
        homeButtonOutTransition.setPositionTransition(new PositionTransition(new Vector(30, 60), new Vector(-50, 60)));
        homeButton.startTransition(homeButtonOutTransition);

        Transition retryButtonOutTransition = new Transition("SLIDE_IN", retryButton);
        retryButtonOutTransition.setPositionTransition(new PositionTransition(new Vector(70, 60), new Vector(150, 60)));
        retryButton.startTransition(retryButtonOutTransition);
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
