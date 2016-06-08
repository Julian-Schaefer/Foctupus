package com.sheeper.foctupus.game.screen;

import com.sheeper.foctupus.engine.gui.Component;
import com.sheeper.foctupus.engine.gui.transition.PositionTransition;
import com.sheeper.foctupus.engine.renderer.Textures;

import com.sheeper.foctupus.engine.gui.Button;
import com.sheeper.foctupus.engine.gui.Screen;
import com.sheeper.foctupus.engine.gui.transition.Transition;
import com.sheeper.foctupus.engine.renderer.Renderer;
import com.sheeper.foctupus.engine.renderer.Sprite;
import com.sheeper.foctupus.engine.renderer.Texture;
import com.sheeper.foctupus.engine.renderer.util.Vector;
import com.sheeper.foctupus.game.logic.Bestboard;

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
