package ch.ee.ui;

import ch.ee.common.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class UiPoints extends GameObject {
    private int points;

    @Override
    protected void start() {

    }

    @Override
    protected void update(double deltaTime) {

    }

    @Override
    protected void draw(GraphicsContext graphic) {
        graphic.setFill(Color.WHITE);
        graphic.setFont(new Font(30));
        graphic.fillText(String.valueOf(points), 0, 60);

        graphic.setFont(new Font(-1));
    }

    public void addPoints(int points){
        this.points += points;
    }
}
