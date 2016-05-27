package foctupus.sheeper.com.foctupus.game.screen;

import foctupus.sheeper.com.foctupus.engine.gui.Button;
import foctupus.sheeper.com.foctupus.engine.gui.Container;
import foctupus.sheeper.com.foctupus.engine.gui.Popup;
import foctupus.sheeper.com.foctupus.engine.renderer.Renderer;
import foctupus.sheeper.com.foctupus.engine.renderer.Sprite;
import foctupus.sheeper.com.foctupus.engine.renderer.Texture;
import foctupus.sheeper.com.foctupus.engine.renderer.Textures;
import foctupus.sheeper.com.foctupus.engine.renderer.util.Vector;

/**
 * Created by julianschafer on 28.05.16.
 */
public class HelpPopup extends Popup {

    private Button okButton;

    public HelpPopup(Renderer renderer)
    {
        super(renderer, new Sprite(new Texture(Textures.HELP_INSTRUCTION)));

        setRelativePosition(new Vector(50, 50));
        setRelativeSize(new Vector(95, 95));

        okButton = new Button(new Sprite(new Texture(Textures.BTN_OK)));
        okButton.setRelativePosition(new Vector(50, 11));
        okButton.setRelativeSize(new Vector(USE_RATIO, 11));
        okButton.addButtonListener(new Button.ButtonListener() {
            @Override
            public void onClick(Button button)
            {
                close();
            }
        });

        addChild(okButton);
    }
}
