package ch.ee.effects;

import ch.ee.common.GameObject;
import ch.ee.common.Vector2;
import javafx.scene.canvas.GraphicsContext;

public class FxBulletHit extends GameObject {

    private Vector2 originPoint;
    public double size;

    public FxBulletHit(Vector2 originPoint) {
        this.originPoint = originPoint;
    }

    @Override
    protected void start() {
        size = 0;
    }

    @Override
    protected void update(double deltaTime) {
        size += 100 * deltaTime;

        if(size >= 50) destroy();
    }

    @Override
    protected void draw(GraphicsContext graphic) {
        graphic.translate(originPoint.x, originPoint.y);

        graphic.setLineWidth(5);
        graphic.strokeOval(0 - size / 2, 0 - size / 2, size, size);
        graphic.setLineWidth(1);

        graphic.translate(-originPoint.x, -originPoint.y);
    }
}
