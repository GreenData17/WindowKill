package ch.ee.entities;

import ch.ee.common.GameObject;
import ch.ee.common.Vector2;
import ch.ee.utils.InputManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

public class Player extends GameObject {

    private final int SPEED = 200;
    private double currentSpeedX;
    private double currentSpeedY;
    private Vector2 direction;

    @Override
    protected void start() {
        position = new Vector2(100, 100);
        direction = Vector2.zero();
    }

    @Override
    protected void update(double deltaTime) {
        if(InputManager.isPressed(KeyCode.W)) {
            currentSpeedY = SPEED;
            direction.y = -1;
        }

        if(InputManager.isPressed(KeyCode.S)) {
            currentSpeedY = SPEED;
            direction.y = 1;
        }

        if(InputManager.isPressed(KeyCode.A)) {
            currentSpeedX = SPEED;
            direction.x = -1;
        }

        if(InputManager.isPressed(KeyCode.D)) {
            currentSpeedX = SPEED;
            direction.x = 1;
        }

        if(!InputManager.isPressed(KeyCode.W) && !InputManager.isPressed(KeyCode.S))
            direction.y = 0;

        if(!InputManager.isPressed(KeyCode.A) && !InputManager.isPressed(KeyCode.D))
            direction.x = 0;

        // TODO: for debuff? [ slippery ]
//        if(currentSpeedY > 0)
//            currentSpeedY -= 200 * deltaTime;
//        else direction.y = 0;
//
//        if(currentSpeedX > 0)
//            currentSpeedX -= 200 * deltaTime;
//        else direction.x = 0;

        position = Vector2.add(position, new Vector2((currentSpeedX * direction.x) * deltaTime, (currentSpeedY * direction.y) * deltaTime));
    }

    @Override
    protected void draw(GraphicsContext graphic) {
        graphic.setLineWidth(5);

        graphic.strokeOval(position.x, position.y, 30, 30);

        graphic.setLineWidth(1);
    }
}
