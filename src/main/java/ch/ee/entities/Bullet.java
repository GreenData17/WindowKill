package ch.ee.entities;

import ch.ee.common.GameObject;
import ch.ee.common.Vector2;
import ch.ee.effects.FxBulletHit;
import javafx.scene.canvas.GraphicsContext;

public class Bullet extends GameObject {
    private Player player;

    private Vector2 originPosition;
    private double angle;

    private boolean running;

    // stats
    private double power = 50;

    public Bullet(Vector2 originPosition, double angle, Player player) {
        this.originPosition = originPosition;
        this.angle = angle;
        this.player = player;
    }

    @Override
    protected void start() {
        running = true;
    }

    @Override
    protected void update(double deltaTime) {
        if(running)
            position = Vector2.moveTowardsAngle(position, angle, 200 * deltaTime);

        // right left

        if(Vector2.add(originPosition, position).x >= window.getSize().x - 30 && running) {
            handleWindowHit();
            window.setSize(window.getSize().x + power, window.getSize().y);
        }

        if(Vector2.add(originPosition, position).x <= -15 && running) {
            handleWindowHit();
            window.setSize(window.getSize().x + power, window.getSize().y);
            window.setPosition(window.getPosition().x - power, window.getPosition().y);
            player.position = Vector2.add(player.position, new Vector2(power, 0));
        }

        // down top

        if(Vector2.add(originPosition, position).y >= window.getSize().y - 30 && running) {
            handleWindowHit();
            window.setSize(window.getSize().x, window.getSize().y + power);
        }

        if(Vector2.add(originPosition, position).y <= 45 && running) {
            handleWindowHit();
            window.setSize(window.getSize().x, window.getSize().y + power);
            window.setPosition(window.getPosition().x, window.getPosition().y - power);
            player.position = Vector2.add(player.position, new Vector2(0, power));
        }
    }

    private void handleWindowHit(){
        running = false;
        instantiate(new FxBulletHit(Vector2.add(new Vector2(originPosition.x + 25, originPosition.y + 25), position)));
        destroy();
    }

    @Override
    protected void draw(GraphicsContext graphic) {

        graphic.translate(originPosition.x + 25, originPosition.y + 25);

        graphic.fillOval(position.x - 7.5, position.y - 7.5, 15, 15);

        graphic.translate(-originPosition.x - 25, -originPosition.y - 25);
    }
}
