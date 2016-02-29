package foctupus.sheeper.com.foctupus.game.screen;

import foctupus.sheeper.com.foctupus.game.renderer.AnimatedSprite;
import foctupus.sheeper.com.foctupus.game.renderer.Environment;
import foctupus.sheeper.com.foctupus.game.renderer.Renderer;
import foctupus.sheeper.com.foctupus.game.renderer.Texture;
import foctupus.sheeper.com.foctupus.game.renderer.Textures;
import foctupus.sheeper.com.foctupus.game.renderer.util.RelativeVector;

/**
 * Created by schae on 05.02.2016.
 */
public class TestScreen extends Screen {


    public TestScreen(int priority) {
        super(priority);

        setup();
        init();
    }

    @Override
    public void setup() {
        setTexture(new Texture(Textures.BACKGROUND));
        setVisible(true);

        AnimatedSprite a = new AnimatedSprite();
        a.setPosition(Renderer.getWidth() / 2, Renderer.getHeight() / 2);
        a.addTexture(new Texture(Textures.BTN_BACK));
        a.addTexture(new Texture(Textures.BTN_RETRY));
        a.addTexture(new Texture(Textures.BTN_BEST));
        a.addTexture(new Texture(Textures.BTN_HOME));
        a.addTexture(new Texture(Textures.BTN_START));
        addChild(a);
    }

    @Override
    public void init() {

    }
}
