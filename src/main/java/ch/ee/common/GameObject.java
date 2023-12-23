package ch.ee.common;

import javafx.scene.canvas.GraphicsContext;

public abstract class GameObject {
    protected Vector2 position;

    public void triggerStart(){
        position = Vector2.zero();
        start();
    }

    public void triggerUpdate(double deltaTime){
        update(deltaTime);
    }

    public void triggerDraw(GraphicsContext graphic){
        draw(graphic);
    }

    protected abstract void start();
    protected abstract void update(double deltaTime);
    protected abstract void draw(GraphicsContext graphic);

}
