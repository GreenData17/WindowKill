package ch.ee.windows;

import ch.ee.common.Vector2;
import ch.ee.core.Window;
import ch.ee.entities.Player;
import ch.ee.utils.InputManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

public class GameWindow extends Window {

    public GameWindow() {
        super("WindowKill :)");
    }

    @Override
    public void start(){
        Player player = new Player();
        instantiate(player);
    }

    @Override
    public void draw(GraphicsContext graphic) {
        graphic.fillRect(InputManager.getMouseWindowPosition().x - 5, InputManager.getMouseWindowPosition().y - 5,
                        10, 10);
    }

    @Override
    public void update(double deltaTime) {

    }
}
