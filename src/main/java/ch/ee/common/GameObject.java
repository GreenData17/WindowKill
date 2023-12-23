package ch.ee.common;

public abstract class GameObject {
    protected Vector2 position;

    public void triggerStart(){
        position = Vector2.zero();
        start();
    }

    public void triggerUpdate(){
        update();
    }

    public void triggerDraw(){
        draw();
    }

    protected abstract void start();
    protected abstract void update();
    protected abstract void draw();

}
