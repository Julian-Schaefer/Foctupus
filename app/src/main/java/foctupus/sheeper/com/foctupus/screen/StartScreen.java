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
        bestButton.addButtonListener(new Button.ButtonListener() {
            @Override
            public void onClick(Button button) {
                GameManager.getInstance().toggleAd();
            }
        });

        soundButton = new Button(new Sprite(new Texture(Textures.BTN_RETRY)));
        soundButton.setRelativeSize(new Vector(22, USE_SAME));


        Counter counter = new Counter(10023);
        counter.setRelativePosition(new Vector(60, 70));
        counter.setRelativeSize(new Vector(50, 30));

        addChild(counter);

        addChild(title);
        addChild(startButton);
        addChild(bestButton);
        addChild(soundButton);

        initTransitions();
    }

    private void initTransitions()
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

        soundButton.addButtonListener(new Button.ButtonListener() {
            @Override
            public void onClick(Button button) {
                showPopUp(new Popuptest());


            }
        });
    }

    private class Popuptest extends Popup
    {
        public Popuptest()
        {
            super(Renderer.getInstance());
            init();
        }

        @Override
        protected void init() {



            setSprite(new Sprite(new Texture(Textures.SCORE_BACKGROUND)));
            getSprite().setVisible(true);
            setRelativePosition(new Vector(50, 50));
            setRelativeSize(new Vector(80, 80));
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Popuptest.this.close();
                }
            }).start();
        }
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
        if(transition.getComponent() == title)
        {
            title.startTransition();
        }

        if(transition.getName().equals(TRANS_SLIDE_IN) && transition.getComponent() == startButton)
        {
            startSliding();
        }
    }
}
