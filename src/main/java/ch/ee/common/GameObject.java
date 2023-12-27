package ch.ee.common;

import ch.ee.core.Window;
import javafx.scene.canvas.GraphicsContext;

public abstract class GameObject {
    public Window window;
    public Vector2 position;

    protected void instantiate(GameObject gameObject){
        window.instantiate(gameObject);
    }

    protected void destroy(GameObject gameObject){
        window.destroy(gameObject);
    }

    protected void destroy(){
        window.destroy(this);
    }

    public void triggerStart(){
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
