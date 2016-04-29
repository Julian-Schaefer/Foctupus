package foctupus.sheeper.com.foctupus.screen;

import foctupus.sheeper.com.foctupus.game.gui.Button;
import foctupus.sheeper.com.foctupus.game.gui.Component;
import foctupus.sheeper.com.foctupus.game.gui.Screen;
import foctupus.sheeper.com.foctupus.game.gui.transition.PositionTransition;
import foctupus.sheeper.com.foctupus.game.gui.transition.Transition;
import foctupus.sheeper.com.foctupus.game.logic.Counter;
import foctupus.sheeper.com.foctupus.game.logic.GameManager;
import foctupus.sheeper.com.foctupus.game.logic.Scoreboard;
import foctupus.sheeper.com.foctupus.game.renderer.Renderer;
import foctupus.sheeper.com.foctupus.game.renderer.Sprite;
import foctupus.sheeper.com.foctupus.game.renderer.Texture;
import foctupus.sheeper.com.foctupus.game.renderer.Textures;
import foctupus.sheeper.com.foctupus.game.renderer.util.Vector;

/**
 * Created by julianschafer on 23.04.16.
 */
public class BestScreen extends Screen {

    private static final String TRANS_SLIDE_IN = "slide_in";
    private static final String TRANS_SLIDING= "sliding";

    private Scoreboard scoreboard;
    private Button backButton;

    public BestScreen(Renderer renderer) {
        super(renderer);

        init();
    }

    @Override
    protected void init() {

        scoreboard = new Scoreboard(renderer);
        scoreboard.setRelativeSize(new Vector(80, USE_RATIO));

        backButton = new Button(new Sprite(new Texture(Textures.BTN_BACK)));
        backButton.setRelativeSize(new Vector(30, USE_SAME));

        addChild(scoreboard);
        addChild(backButton);

        animateIn();
    }

    private void animateIn()
    {
        Transition scoreBoardTransition = new Transition(TRANS_SLIDE_IN, scoreboard);
        scoreBoardTransition.setPositionTransition(new PositionTransition(new Vector(50, 150), new Vector(50, 65)));
        scoreBoardTransition.setListener(this);
        scoreboard.startTransition(scoreBoardTransition);

        Transition backButtonTransition = new Transition(TRANS_SLIDE_IN, backButton);
        backButtonTransition.setPositionTransition(new PositionTransition(new Vector(50, -50), new Vector(50, 25)));
        backButton.startTransition(backButtonTransition);
    }

    private void animateOut()
    {
        Transition scoreBoardTransition = new Transition(TRANS_SLIDE_IN, scoreboard);
        scoreBoardTransition.setPositionTransition(new PositionTransition(new Vector(50, 65), new Vector(50, 150)));
        scoreboard.startTransition(scoreBoardTransition);

        Transition backButtonTransition = new Transition(TRANS_SLIDE_IN, backButton);
        backButtonTransition.setPositionTransition(new PositionTransition(new Vector(50, 25), new Vector(50, -50)));
        backButtonTransition.setListener(this);
        backButton.startTransition(backButtonTransition);
    }

    @Override
    public void onFinished(Component component) {

    }

    @Override
    public void onTransitionFinished(Transition transition) {

        if(transition.getComponent() == backButton)
        {
            finishScreen(new StartScreen(renderer));
        }
        else if(transition.getComponent() == scoreboard)
        {
            backButton.addButtonListener(new Button.ButtonListener() {
                @Override
                public void onClick(Button button) {
                    animateOut();
                    backButton.clearButtonListeners();
                }
            });
        }

    }
}
