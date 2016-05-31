package foctupus.sheeper.com.foctupus.game.screen;

import foctupus.sheeper.com.foctupus.engine.gui.Button;
import foctupus.sheeper.com.foctupus.engine.gui.Component;
import foctupus.sheeper.com.foctupus.engine.gui.Screen;
import foctupus.sheeper.com.foctupus.engine.gui.transition.PositionTransition;
import foctupus.sheeper.com.foctupus.engine.gui.transition.Transition;
import foctupus.sheeper.com.foctupus.game.logic.Bestboard;
import foctupus.sheeper.com.foctupus.engine.renderer.Renderer;
import foctupus.sheeper.com.foctupus.engine.renderer.Sprite;
import foctupus.sheeper.com.foctupus.engine.renderer.Texture;
import foctupus.sheeper.com.foctupus.engine.renderer.Textures;
import foctupus.sheeper.com.foctupus.engine.renderer.util.Vector;

/**
 * Created by julianschafer on 23.04.16.
 */
public class BestScreen extends Screen {

    private static final String TRANS_SLIDE_IN = "slide_in";

    private Bestboard bestboard;
    private Button backButton;

    public BestScreen(Renderer renderer) {
        super(renderer);

        init();
    }

    @Override
    protected void init() {

        bestboard = new Bestboard(renderer);
        bestboard.setRelativeSize(new Vector(95, 45));

        backButton = new Button(new Sprite(new Texture(Textures.BTN_BACK)));
        backButton.setRelativeSize(new Vector(32, USE_SAME));

        addChild(bestboard);
        addChild(backButton);

        animateIn();
    }

    private void animateIn()
    {
        Transition scoreBoardTransition = new Transition(TRANS_SLIDE_IN, bestboard);
        scoreBoardTransition.setPositionTransition(new PositionTransition(new Vector(50, 150), new Vector(50, 65)));
        scoreBoardTransition.setListener(this);
        bestboard.startTransition(scoreBoardTransition);

        Transition backButtonTransition = new Transition(TRANS_SLIDE_IN, backButton);
        backButtonTransition.setPositionTransition(new PositionTransition(new Vector(50, -50), new Vector(50, 25)));
        backButton.startTransition(backButtonTransition);
    }

    private void animateOut()
    {
        Transition scoreBoardTransition = new Transition(TRANS_SLIDE_IN, bestboard);
        scoreBoardTransition.setPositionTransition(new PositionTransition(new Vector(50, 65), new Vector(50, 150)));
        bestboard.startTransition(scoreBoardTransition);

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
        else if(transition.getComponent() == bestboard)
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
