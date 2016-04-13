package foctupus.sheeper.com.foctupus.screen;

import foctupus.sheeper.com.foctupus.game.gui.Button;
import foctupus.sheeper.com.foctupus.game.gui.Component;
import foctupus.sheeper.com.foctupus.game.gui.Screen;
import foctupus.sheeper.com.foctupus.game.gui.transition.PositionTransition;
import foctupus.sheeper.com.foctupus.game.gui.transition.RotateTransition;
import foctupus.sheeper.com.foctupus.game.gui.transition.Transition;
import foctupus.sheeper.com.foctupus.game.logic.Popup;
import foctupus.sheeper.com.foctupus.game.renderer.Renderer;
import foctupus.sheeper.com.foctupus.game.renderer.Sprite;
import foctupus.sheeper.com.foctupus.game.renderer.Texture;
import foctupus.sheeper.com.foctupus.game.renderer.Textures;
import foctupus.sheeper.com.foctupus.game.renderer.util.Vector;

/**
 * Created by schae on 13.03.2016.
 */
public class StartScreen extends Screen {

    private Button startButton;
    private Button bestButton;

    public StartScreen(Renderer renderer) {
        super(renderer, new Sprite(new Texture(Textures.BACKGROUND)));

        init();
    }

    @Override
    protected void init() {
        startButton = new Button(new Sprite(new Texture(Textures.BTN_START)));
        startButton.setRelativePosition(new Vector(-50, 70));
        startButton.setRelativeSize(new Vector(55, USE_SAME));

        Transition startTransition = new Transition("slideIn", startButton);
        startTransition.setPositionTransition(new PositionTransition(new Vector(-50, 70), new Vector(50, 70)));
        startTransition.setRotateTransition(new RotateTransition(0, 720));
        startTransition.setListener(this);
        startButton.startTransition(startTransition);

        bestButton = new Button(new Sprite(new Texture(Textures.BTN_BEST)));
        bestButton.setRelativePosition(new Vector(150, 35));
        bestButton.setRelativeSize(new Vector(35, USE_SAME));

        Transition bestTransition = new Transition("slideIn", bestButton);
        bestTransition.setPositionTransition(new PositionTransition(new Vector(150, 35), new Vector(50, 35)));
        bestButton.addTransition(bestTransition);
        bestButton.startTransition("slideIn");
        bestButton.addButtonListener(new Button.ButtonListener() {
            @Override
            public void onClick(Button button) {
                showPopUp(new Popup(Renderer.getInstance()));
            }
        });

        addChild(startButton);
        addChild(bestButton);
    }

    @Override
    public void onFinished(Component component) {
        removePopUp();
    }

    @Override
    public void onTransitionFinished(Transition transition) {
        if(transition.getName().equals("slideIn"))
        {
            Transition startTransition = new Transition("slideIn", startButton);
            startTransition.setPositionTransition(new PositionTransition(new Vector(50, 70), new Vector(-50, 70)));
            startTransition.setRotateTransition(new RotateTransition(720, -720));
            startButton.addTransition(startTransition);
            startButton.startTransition("slideIn");
        }
    }
}
