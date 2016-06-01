package foctupus.sheeper.com.foctupus.game.screen;

import foctupus.sheeper.com.foctupus.engine.gui.Button;
import foctupus.sheeper.com.foctupus.engine.gui.Component;
import foctupus.sheeper.com.foctupus.engine.gui.Screen;
import foctupus.sheeper.com.foctupus.engine.gui.transition.PositionTransition;
import foctupus.sheeper.com.foctupus.engine.gui.transition.ResizeTransition;
import foctupus.sheeper.com.foctupus.engine.gui.transition.Transition;
import foctupus.sheeper.com.foctupus.game.FoctupusDatabase;
import foctupus.sheeper.com.foctupus.game.logic.Treasure;
import foctupus.sheeper.com.foctupus.engine.renderer.Renderer;
import foctupus.sheeper.com.foctupus.engine.renderer.Sprite;
import foctupus.sheeper.com.foctupus.engine.renderer.Texture;
import foctupus.sheeper.com.foctupus.engine.renderer.Textures;
import foctupus.sheeper.com.foctupus.engine.renderer.util.Vector;

/**
 * Created by schae on 13.03.2016.
 */
public class StartScreen extends Screen {

    private static final String TRANS_SLIDE_IN = "slide_in";
    private static final String TRANS_SLIDING= "sliding";
    private static final String TRANS_SLIDE_OUT = "slide_out";

    private Component title;
    private Button startButton;
    private Button bestButton;
    private Button soundButton;
    private Button helpButton;

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

        soundButton = new Button(new Sprite(getSoundButtonTexture()));
        soundButton.setRelativeSize(new Vector(22, USE_SAME));
        soundButton.setRelativePosition(new Vector(25, 20));
        soundButton.addButtonListener(new Button.ButtonListener() {
            @Override
            public void onClick(Button button)
            {
                if(FoctupusDatabase.getInstance().isSoundEnabled())
                    FoctupusDatabase.getInstance().setSoundEnabled(false);
                else
                    FoctupusDatabase.getInstance().setSoundEnabled(true);

                soundButton.getSprite().setTexture(getSoundButtonTexture());
            }
        });

        helpButton = new Button(new Sprite(new Texture(Textures.BTN_HELP)));
        helpButton.setRelativeSize(new Vector(22, USE_SAME));
        helpButton.setRelativePosition(new Vector(75, 20));
        helpButton.addButtonListener(new Button.ButtonListener() {
            @Override
            public void onClick(Button button)
            {
                showHelpInstructions();
                Sprite.writeSizes();
            }
        });

        addChild(title);
        addChild(startButton);
        addChild(bestButton);
        addChild(soundButton);
        addChild(helpButton);

        helpButton.getSprite().setVisible(false);
        soundButton.getSprite().setVisible(false);

        animateIn();
    }

    private Texture getSoundButtonTexture()
    {
        if(FoctupusDatabase.getInstance().isSoundEnabled())
            return new Texture(Textures.BTN_UNMUTED);
        else
            return new Texture(Textures.BTN_MUTED);
    }


    private void animateIn()
    {
        Transition titleGrowTransition = new Transition(TRANS_SLIDE_IN, title);
        titleGrowTransition.setResizeTransition(new ResizeTransition(new Vector(0, 0), new Vector(98, 12)));
        titleGrowTransition.setListener(this);
        title.addTransition(titleGrowTransition);

        Transition titleShrinkTransition = new Transition(TRANS_SLIDE_IN, title);
        titleShrinkTransition.setResizeTransition(new ResizeTransition(new Vector(98, 12), new Vector(92, 11)));
        title.addTransition(titleShrinkTransition);

        title.startTransition();

        Transition startTransition = new Transition(TRANS_SLIDE_IN, startButton);
        startTransition.setPositionTransition(new PositionTransition(new Vector(-50, 67), new Vector(50, 67)));
        startTransition.setListener(this);
        startButton.startTransition(startTransition);

        Transition bestTransition = new Transition(TRANS_SLIDE_IN, bestButton);
        bestTransition.setPositionTransition(new PositionTransition(new Vector(150, 38), new Vector(50, 38)));
        bestButton.startTransition(bestTransition);
    }

    private void animateOut()
    {
        helpButton.getSprite().setVisible(false);
        soundButton.getSprite().setVisible(false);

        Transition titleGrowTransition = new Transition(TRANS_SLIDE_OUT, title);
        titleGrowTransition.setResizeTransition(new ResizeTransition(new Vector(92, 11), new Vector(98, 12)));
        titleGrowTransition.setListener(this);
        titleGrowTransition.setAnimationTime(200);
        title.addTransition(titleGrowTransition);

        Transition titleShrinkTransition = new Transition(TRANS_SLIDE_OUT, title);
        titleShrinkTransition.setResizeTransition(new ResizeTransition(new Vector(98, 12), new Vector(0, 0)));
        titleShrinkTransition.setAnimationTime(400);
        title.addTransition(titleShrinkTransition);

        title.startTransition();

        Transition startTransition = new Transition(TRANS_SLIDE_OUT, startButton);
        startTransition.setPositionTransition(new PositionTransition(new Vector(50, 67), new Vector(-50, 67)));
        startTransition.setListener(this);
        startButton.startTransition(startTransition);

        Transition bestTransition = new Transition(TRANS_SLIDE_OUT, bestButton);
        bestTransition.setPositionTransition(new PositionTransition(new Vector(50, 38), new Vector(150, 38)));
        bestTransition.setListener(this);
        bestButton.startTransition(bestTransition);
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
    public void onTransitionFinished(Transition transition) {

        if(clicked != null && transition.getComponent() == startButton)
        {
            if(clicked == startButton)
            {
                finishScreen(new GameScreen(renderer));
            }
            else if(clicked == bestButton)
            {
                finishScreen(new BestScreen(renderer));
            }
        }
        else if(clicked == null) {

            if (transition.getName().equals(TRANS_SLIDE_IN) && transition.getComponent() == startButton) {
                startSliding();

                helpButton.getSprite().setVisible(true);
                soundButton.getSprite().setVisible(true);

                bestButton.addButtonListener(new Button.ButtonListener() {
                    @Override
                    public void onClick(Button button) {
                        animateOut();
                        setClicked(bestButton);
                    }
                });

                startButton.addButtonListener(new Button.ButtonListener() {
                    @Override
                    public void onClick(Button button) {

                        if(FoctupusDatabase.getInstance().hasPlayedBefore())
                            animateOut();
                        else
                            showHelpInstructions();

                        setClicked(startButton);
                    }
                });
            }
        }
    }

    private void showHelpInstructions()
    {
        title.getSprite().setVisible(false);
        showPopUp(new HelpPopup(renderer));
    }

    private void setClicked(Button clicked)
    {
        startButton.clearButtonListeners();
        bestButton.clearButtonListeners();

        this.clicked = clicked;
    }

    @Override
    public void onFinished(Component component)
    {
        super.onFinished(component);

        if(!FoctupusDatabase.getInstance().hasPlayedBefore())
        {
            FoctupusDatabase.getInstance().setHasPlayedBefore();
            animateOut();
        }

        title.getSprite().setVisible(true);
    }

}
