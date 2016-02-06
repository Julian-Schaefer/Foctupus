package foctupus.sheeper.com.foctupus.game.screen;

import foctupus.sheeper.com.foctupus.game.renderer.Environment;
import foctupus.sheeper.com.foctupus.game.renderer.Sprite;
import foctupus.sheeper.com.foctupus.game.renderer.StaticContainer;
import foctupus.sheeper.com.foctupus.game.renderer.Textures;
import foctupus.sheeper.com.foctupus.game.tools.RelativeVector;
import foctupus.sheeper.com.foctupus.game.tools.Vector;

/**
 * Created by schae on 05.02.2016.
 */
public class TestScreen extends StaticContainer {


    public TestScreen(int priority) {
        super(priority);

        setup();
        init();
    }

    @Override
    public void setup() {
        setTexture(Textures.BACKGROUND);
        setBottomLeftAligned(true);
        setPosition(0, 0);
        setSize(Environment.width, Environment.height);
        setVisible(true);
    }

    @Override
    public void init() {

    }
}
