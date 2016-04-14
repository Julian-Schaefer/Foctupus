package foctupus.sheeper.com.foctupus.screen;

import foctupus.sheeper.com.foctupus.game.gui.Button;
import foctupus.sheeper.com.foctupus.game.gui.Component;
import foctupus.sheeper.com.foctupus.game.gui.Screen;
import foctupus.sheeper.com.foctupus.game.gui.transition.PositionTransition;
import foctupus.sheeper.com.foctupus.game.gui.transition.RotateTransition;
import foctupus.sheeper.com.foctupus.game.gui.transition.Transition;
import foctupus.sheeper.com.foctupus.game.logic.GameManager;
import foctupus.sheeper.com.foctupus.game.renderer.Renderer;
import foctupus.sheeper.com.foctupus.game.renderer.Sprite;
import foctupus.sheeper.com.foctupus.game.renderer.Texture;
import foctupus.sheeper.com.foctupus.game.renderer.Textures;
import foctupus.sheeper.com.foctupus.game.renderer.util.Vector;

/**
 * Created by schae on 13.03.2016.
 */
public class StartScreen extends Screen {

    private static final String TRANS_SLIDE_IN = "slide_in";
    private static final String TRANS_SLIDING= "sliding";

    private Button startButton;
    private Button bestButton;

    public StartScreen(Renderer renderer) {
        super(renderer);

        init();
    }

    @Override
    protected void init() {
        startButton = new Button(new Sprite(new Texture(Textures.BTN_START)));
        startButton.setRelativePosition(new Vector(-50, 58));
        startButton.setRelativeSize(new Vector(55, USE_SAME));

        Transition startTransition = new Transition(TRANS_SLIDE_IN, startButton);
        startTransition.setPositionTransition(new PositionTransition(new Vector(-50, 58), new Vector(50, 58)));
        startTransition.setListener(this);
        startButton.startTransition(startTransition);

        bestButton = new Button(new Sprite(new Texture(Textures.BTN_BEST)));
        bestButton.setRelativePosition(new Vector(150, 25));
        bestButton.setRelativeSize(new Vector(38, USE_SAME));

        Transition bestTransition = new Transition(TRANS_SLIDE_IN, bestButton);
        bestTransition.setPositionTransition(new PositionTransition(new Vector(150, 25), new Vector(50, 25)));
        bestButton.startTransition(bestTransition);
        bestButton.addButtonListener(new Button.ButtonListener() {
            @Override
            public void onClick(Button button) {
                GameManager.getInstance().toggleAd();
            }
        });

        addChild(startButton);
        addChild(bestButton);
    }

    private void initTransitions()
    {

    }

    private void startSliding()
    {
        Vector startBtnPos = startButton.getRelativePosition();
        Transition slideTransition = new Transition(TRANS_SLIDING, startButton);
        slideTransition.setPositionTransition(new PositionTransition(new Vector(startBtnPos.getX() + 4, startBtnPos.getY()), new Vector(startBtnPos.getX() - 4, startBtnPos.getY())));
        slideTransition.setAnimationTime(1200);
        slideTransition.setAutoReverting(true);
        slideTransition.setAutoRepeating(true);
        startButton.startTransition(slideTransition);
    }

    @Override
    public void onFinished(Component component) {
        removePopUp();
    }

    @Override
    public void onTransitionFinished(Transition transition) {
        if(transition.getName().equals(TRANS_SLIDE_IN) && transition.getComponent() == startButton)
        {
            startSliding();
        }
    }
}
