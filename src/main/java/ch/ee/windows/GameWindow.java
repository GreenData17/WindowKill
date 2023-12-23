package ch.ee.windows;

import ch.ee.common.Vector2;
import ch.ee.core.Window;
import ch.ee.utils.InputManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

public class GameWindow extends Window {
    private final int SPEED = 200;
    private double currentSpeedx;
    private double currentSpeedy;
    private final Vector2 playerDirection;

    private Vector2 playerPosition;

    public GameWindow() {
        super("WindowKill :)");
        playerPosition = new Vector2(100, 100);
        playerDirection = Vector2.zero();
    }

    @Override
    public void start(){
        playerPosition = new Vector2(100, 100);
    }

    @Override
    public void draw(GraphicsContext graphic) {
        graphic.setLineWidth(5);

        graphic.strokeOval(playerPosition.x, playerPosition.y, 30, 30);

        graphic.setLineWidth(1);

        graphic.fillRect(InputManager.getMouseWindowPosition().x - 5, InputManager.getMouseWindowPosition().y - 5,
                        10, 10);
    }

    @Override
    public void update(double deltaTime) {
        if(InputManager.isPressed(KeyCode.W)) {
            currentSpeedy = SPEED;
            playerDirection.y = -1;
        }

        if(InputManager.isPressed(KeyCode.S)) {
            currentSpeedy = SPEED;
            playerDirection.y = 1;
        }

        if(InputManager.isPressed(KeyCode.A)) {
            currentSpeedx = SPEED;
            playerDirection.x = -1;
        }

        if(InputManager.isPressed(KeyCode.D)) {
            currentSpeedx = SPEED;
            playerDirection.x = 1;
        }

        if(!InputManager.isPressed(KeyCode.W) && !InputManager.isPressed(KeyCode.S))
            playerDirection.y = 0;

        if(!InputManager.isPressed(KeyCode.A) && !InputManager.isPressed(KeyCode.D))
            playerDirection.x = 0;

        // TODO: for debuff? [ slippery ]
//        if(currentSpeedy > 0)
//            currentSpeedy -= 200 * deltaTime;
//        else playerDirection.y = 0;
//
//        if(currentSpeedx > 0)
//            currentSpeedx -= 200 * deltaTime;
//        else playerDirection.x = 0;

        playerPosition = Vector2.add(playerPosition, new Vector2((currentSpeedx * playerDirection.x) * deltaTime, (currentSpeedy * playerDirection.y) * deltaTime));
    }
}
