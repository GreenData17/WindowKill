package ch.ee.windows;

import ch.ee.common.Vector2;
import ch.ee.core.Window;
import ch.ee.entities.Player;
import ch.ee.utils.InputManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

public class GameWindow extends Window {

    private Vector2 smallestSize = new Vector2(260, 290);

    // objects
    Player player;

    public GameWindow() {
        super("WindowKill :)");
    }

    @Override
    public void start(){
        player = new Player();
        instantiate(player);
    }

    @Override
    public void draw(GraphicsContext graphic) {
        graphic.fillRect(InputManager.getMouseWindowPosition().x - 5, InputManager.getMouseWindowPosition().y - 5,
                        10, 10);
    }

    @Override
    public void update(double deltaTime) {
        if(getSize().x > smallestSize.x){
            setSize(getSize().x - 20 * deltaTime, getSize().y);
            setPosition(getPosition().x + 10 * deltaTime, getPosition().y);
            player.position = Vector2.add(player.position, new Vector2(-10 * deltaTime, 0));
        }

        if(getSize().y > smallestSize.y){
            setSize(getSize().x, getSize().y - 20 * deltaTime);
            setPosition(getPosition().x, getPosition().y + 10 * deltaTime);
            player.position = Vector2.add(player.position, new Vector2(0, -10 * deltaTime));
        }
    }
}
