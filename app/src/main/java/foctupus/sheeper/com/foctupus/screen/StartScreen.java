package foctupus.sheeper.com.foctupus.screen;

import foctupus.sheeper.com.foctupus.game.gui.Button;
import foctupus.sheeper.com.foctupus.game.gui.Component;
import foctupus.sheeper.com.foctupus.game.gui.Popup;
import foctupus.sheeper.com.foctupus.game.gui.Screen;
import foctupus.sheeper.com.foctupus.game.gui.transition.PositionTransition;
import foctupus.sheeper.com.foctupus.game.gui.transition.ResizeTransition;
import foctupus.sheeper.com.foctupus.game.gui.transition.RotateTransition;
import foctupus.sheeper.com.foctupus.game.gui.transition.Transition;
import foctupus.sheeper.com.foctupus.game.logic.Counter;
import foctupus.sheeper.com.foctupus.game.logic.GameManager;
import foctupus.sheeper.com.foctupus.game.logic.Treasure;
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

    private Component title;
    private Button startButton;
    private Button bestButton;
    private Button soundButton;

    private Button clicked;

    public StartScreen(Renderer renderer) {
        super(renderer);

        init();
    }

    @Override
    protected void init() {

        title = new Component(new Sprite(new Texture(Textures.TITLE)));
        title.setRelativePosition(new Vector(50, 90));

        startButton = new Button(new Sprite(new Texture(Textures.BTN_START)));
        startButton.setRelativeSize(new Vector(52, USE_SAME));

        bestButton = new Button(new Sprite(new Texture(Textures.BTN_BEST)));
        bestButton.setRelativeSize(new Vector(38, USE_SAME));

        soundButton = new Button(new Sprite(new Texture(Textures.BTN_RETRY)));
        soundButton.setRelativeSize(new Vector(22, USE_SAME));

        addChild(title);
        addChild(startButton);
        addChild(bestButton);
        addChild(soundButton);

        animateIn();
    }

    private void animateIn()
    {
        Transition titleGrowTransition = new Transition(TRANS_SLIDE_IN, title);
        titleGrowTransition.setResizeTransition(new ResizeTransition(new Vector(0, USE_RATIO), new Vector(100, USE_RATIO)));
        titleGrowTransition.setListener(this);
        title.addTransition(titleGrowTransition);

        Transition titleShrinkTransition = new Transition(TRANS_SLIDE_IN, title);
        titleShrinkTransition.setResizeTransition(new ResizeTransition(new Vector(100, USE_RATIO), new Vector(94, USE_RATIO)));
        title.addTransition(titleShrinkTransition);

        title.startTransition();

        Transition startTransition = new Transition(TRANS_SLIDE_IN, startButton);
        startTransition.setPositionTransition(new PositionTransition(new Vector(-50, 67), new Vector(50, 67)));
        startTransition.setListener(this);
        startButton.startTransition(startTransition);

        Transition bestTransition = new Transition(TRANS_SLIDE_IN, bestButton);
        bestTransition.setPositionTransition(new PositionTransition(new Vector(150, 38), new Vector(50, 38)));
        bestButton.startTransition(bestTransition);

        Transition soundTransition = new Transition(TRANS_SLIDE_IN, soundButton);
        soundTransition.setPositionTransition(new PositionTransition(new Vector(-50, 20), new Vector(30, 20)));
        soundButton.startTransition(soundTransition);
    }

    private void animateOut()
    {
        Transition titleGrowTransition = new Transition(TRANS_SLIDE_IN, title);
        titleGrowTransition.setResizeTransition(new ResizeTransition(new Vector(94, USE_RATIO), new Vector(100, USE_RATIO)));
        titleGrowTransition.setListener(this);
        titleGrowTransition.setAnimationTime(200);
        title.addTransition(titleGrowTransition);

        Transition titleShrinkTransition = new Transition(TRANS_SLIDE_IN, title);
        titleShrinkTransition.setResizeTransition(new ResizeTransition(new Vector(100, USE_RATIO), new Vector(0, USE_RATIO)));
        titleShrinkTransition.setAnimationTime(400);
        title.addTransition(titleShrinkTransition);

        title.startTransition();

        Transition startTransition = new Transition(TRANS_SLIDE_IN, startButton);
        startTransition.setPositionTransition(new PositionTransition(new Vector(50, 67), new Vector(-50, 67)));
        startTransition.setListener(this);
        startButton.startTransition(startTransition);

        Transition bestTransition = new Transition(TRANS_SLIDE_IN, bestButton);
        bestTransition.setPositionTransition(new PositionTransition(new Vector(50, 38), new Vector(150, 38)));
        bestTransition.setListener(this);
        bestButton.startTransition(bestTransition);

        Transition soundTransition = new Transition(TRANS_SLIDE_IN, soundButton);
        soundTransition.setPositionTransition(new PositionTransition(new Vector(30, 20), new Vector(-50, 20)));
        soundButton.startTransition(soundTransition);
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

        if(clicked != null && transition.getComponent() == startButton)
        {
            if(clicked == startButton)
            {
                finishScreen(new GameScreen(renderer, new Treasure()));
            }
            else if(clicked == bestButton)
            {
                finishScreen(new BestScreen(renderer));
            }
        }
        else if(clicked == null) {

            if (transition.getName().equals(TRANS_SLIDE_IN) && transition.getComponent() == startButton) {
                startSliding();

                bestButton.addButtonListener(new Button.ButtonListener() {
                    @Override
                    public void onClick(Button button) {
                        animateOut();
                        startButton.clearButtonListeners();
                        bestButton.clearButtonListeners();
                        clicked = bestButton;
                    }
                });

                startButton.addButtonListener(new Button.ButtonListener() {
                    @Override
                    public void onClick(Button button) {
                        animateOut();
                        startButton.clearButtonListeners();
                        bestButton.clearButtonListeners();
                        clicked = startButton;
                    }
                });
            }
        }
    }
}
