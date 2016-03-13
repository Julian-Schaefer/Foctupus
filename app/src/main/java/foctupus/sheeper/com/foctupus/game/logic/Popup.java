package foctupus.sheeper.com.foctupus.game.logic;

import foctupus.sheeper.com.foctupus.game.gui.Button;
import foctupus.sheeper.com.foctupus.game.gui.Container;
import foctupus.sheeper.com.foctupus.game.gui.Screen;
import foctupus.sheeper.com.foctupus.game.renderer.Renderer;
import foctupus.sheeper.com.foctupus.game.renderer.Sprite;
import foctupus.sheeper.com.foctupus.game.renderer.Texture;
import foctupus.sheeper.com.foctupus.game.renderer.Textures;
import foctupus.sheeper.com.foctupus.game.renderer.util.Vector;

/**
 * Created by schae on 12.03.2016.
 */
public class Popup extends Container {

    public Popup(Renderer renderer) {
        super(renderer, new Sprite(new Texture(Textures.SCOREBOARD)));

        init();
    }

    private void init()
    {
        setRelativePosition(new Vector(50, 50));
        setRelativeSize(new Vector(80, 70));

        Button b = new Button(new Sprite(new Texture(Textures.BTN_BEST)));
        b.setRelativeSize(new Vector(30, USE_SAME));
        b.setRelativePosition(new Vector(50, 20));

        b.addButtonListener(new Button.ButtonListener() {
            @Override
            public void onClick(Button button) {
                listener.onFinished(Popup.this);
            }
        });
        addChild(b);
    }

}
