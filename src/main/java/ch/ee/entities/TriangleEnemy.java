package ch.ee.entities;

import ch.ee.common.GameObject;
import ch.ee.common.Vector2;
import ch.ee.core.Window;
import ch.ee.effects.FxBulletHit;
import javafx.geometry.BoundingBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TriangleEnemy extends GameObject {
    private final double SPEED = 100;

    private Player player;
    private Window window;

    private BoundingBox collisionBox;

    public TriangleEnemy(Player player, Window window) {
        this.player = player;
        this.window = window;
    }

    @Override
    protected void start() {

    }

    @Override
    protected void update(double deltaTime) {
        position = Vector2.moveTowardsAngle(position, Vector2.getAngleTowards(position, player.position), SPEED * deltaTime);

        for (GameObject gameobject : window.getGameObjects()){
            if(gameobject.getClass() != Bullet.class) continue;

            if(collisionBox.intersects(((Bullet) gameobject).getCollisionBox())){
                destroy(gameobject);
                instantiate(new FxBulletHit(position, Color.YELLOW));
                destroy();
            }
        }

        collisionBox = new BoundingBox(position.x - 15, position.y - 20, 30, 40);
    }

    @Override
    protected void draw(GraphicsContext graphic) {
        graphic.setFill(Color.CYAN);
        graphic.fillRect(position.x - 15, position.y - 20, 30, 40);

        graphic.translate(position.x, position.y);
        graphic.setStroke(Color.YELLOW);
        graphic.setLineWidth(5);

        graphic.rotate(Vector2.getAngleTowards(position, player.position));

        graphic.strokeLine(-15, -20, 14, 0);
        graphic.strokeLine(-15, 20, 15, 0);
        graphic.strokeLine(-15, -20, -15, 20);

        graphic.rotate(-Vector2.getAngleTowards(position, player.position));

        graphic.setLineWidth(1);
        graphic.translate(-position.x, -position.y);
    }
}