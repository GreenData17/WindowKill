package ch.ee.entities;

import ch.ee.common.GameObject;
import ch.ee.common.Vector2;
import ch.ee.utils.InputManager;
import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;

public class Player extends GameObject {

    private BoundingBox collisioinBox;

    private final int SPEED = 10200;
    private double currentSpeedX;
    private double currentSpeedY;
    private Vector2 direction;
    private double lastAngle;

    // states
    private boolean shooting;
    private double shootingDelay;
    private boolean dead;

    // stats
    private double shootSpeed = .3;

    @Override
    protected void start() {
        position = new Vector2(150);
        collisioinBox = new BoundingBox(position.x - 15, position.y - 15, 30, 30);
        direction = Vector2.zero();
        lastAngle = 0;
        shootingDelay = 0;
    }

    @Override
    protected void update(double deltaTime) {
        if(InputManager.isPressed(KeyCode.W)) {
            currentSpeedY = SPEED * deltaTime;
            direction.y = -1;
        }

        if(InputManager.isPressed(KeyCode.S)) {
            currentSpeedY = SPEED * deltaTime;
            direction.y = 1;
        }

        if(InputManager.isPressed(KeyCode.A)) {
            currentSpeedX = SPEED * deltaTime;
            direction.x = -1;
        }

        if(InputManager.isPressed(KeyCode.D)) {
            currentSpeedX = SPEED * deltaTime;
            direction.x = 1;
        }

        if(!InputManager.isPressed(KeyCode.W) && !InputManager.isPressed(KeyCode.S))
            direction.y = 0;

        if(!InputManager.isPressed(KeyCode.A) && !InputManager.isPressed(KeyCode.D))
            direction.x = 0;

        // shooting
        if(shootingDelay > 0) shootingDelay -= deltaTime;

        if(InputManager.getMouseButton() == MouseButton.PRIMARY && !shooting && shootingDelay <= 0){
            shooting = true;
            shootingDelay = shootSpeed;
            instantiate(new Bullet(position, lastAngle, this));
        }
        else if(InputManager.getMouseButton() == MouseButton.NONE){
            shooting = false;
        }

        if(!window.getGameObjects().isEmpty()){
            for (GameObject gameObject : window.getGameObjects()){
                if(gameObject.getClass() != TriangleEnemy.class) continue;

                if(collisioinBox.intersects(((TriangleEnemy) gameObject).getCollisionBox())){
                    dead = true;
                }
            }
        }

        // TODO: for debuff? [ slippery ]
//        if(currentSpeedY > 0)
//            currentSpeedY -= 200 * deltaTime;
//        else direction.y = 0;
//
//        if(currentSpeedX > 0)
//            currentSpeedX -= 200 * deltaTime;
//        else direction.x = 0;

        position = Vector2.add(position, new Vector2((currentSpeedX * direction.x) * deltaTime, (currentSpeedY * direction.y) * deltaTime));
        collisioinBox = new BoundingBox(position.x - 15, position.y - 15, 30, 30);
    }

    @Override
    protected void draw(GraphicsContext graphic) {
        if(dead) return;

        graphic.setLineWidth(5);

        graphic.strokeOval(position.x - 15, position.y - 15, 30, 30);
        graphic.fillRect(position.x - 2.5, position.y - 2.5, 5, 5);

        graphic.setLineWidth(1);

        // debug direction

        graphic.translate(position.x, position.y);

        lastAngle = Vector2.getAngleTowards(new Vector2(position.x - 15, position.y - 15), InputManager.getMouseWindowPosition());

        graphic.rotate(lastAngle);

        graphic.setStroke(Color.CYAN);
        graphic.strokeLine(0, 0, 50, 0);

        graphic.rotate(-lastAngle);
        graphic.translate(-position.x, -position.y);
    }

    public boolean isDead() {
        return dead;
    }
}
