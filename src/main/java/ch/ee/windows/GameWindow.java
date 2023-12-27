package ch.ee.windows;

import ch.ee.common.Vector2;
import ch.ee.core.Window;
import ch.ee.entities.Player;
import ch.ee.entities.TriangleEnemy;
import ch.ee.ui.UiPoints;
import javafx.scene.canvas.GraphicsContext;

import java.util.Random;

public class GameWindow extends Window {

    private Vector2 smallestSize = new Vector2(260, 290);
    private double spawnDelay;
    private double spawnSpeed = 1.5;

    // objects
    Player player;
    UiPoints points;

    public GameWindow() {
        super("WindowKill :)");
    }

    @Override
    public void start(){
        setDragable(false);

        player = new Player();
        instantiate(player);

        points = new UiPoints();
        instantiate(points);
    }

    @Override
    public void draw(GraphicsContext graphic) {
        // graphic.fillRect(InputManager.getMouseWindowPosition().x - 5, InputManager.getMouseWindowPosition().y - 5, 10, 10);
    }

    @Override
    public void update(double deltaTime) {
        if(player.isDead()) return;

        if(player.position != null) {

            if(getSize().x > smallestSize.x){
                setSize(getSize().x - 20 * deltaTime, getSize().y);
                setPosition(getPosition().x + 10 * deltaTime, getPosition().y);
                player.position = Vector2.add(player.position, new Vector2(-10 * deltaTime, 0));
            }

            if(getSize().y > smallestSize.y){
                setSize(getSize().x, getSize().y - 20 * deltaTime);
                setPosition(getPosition().x, getPosition().y + 10 * deltaTime);
                player.position = Vector2.add(player.position, new Vector2(0, -10 * deltaTime));
            }



            if (player.position.x <= 15) player.position.x = 15;
            if (player.position.x >= getSize().x - 30) player.position.x = getSize().x - 30;

            if (player.position.y <= 45) player.position.y = 46;
            if (player.position.y >= getSize().y - 30) player.position.y = getSize().y - 30;
        }

        if(spawnDelay <= 0){
            spawnDelay = spawnSpeed;
            spawnSpeed -= 0.005;
            Random random = new Random();
            int direction = random.nextInt(1, 5);
            System.out.println(direction);

            TriangleEnemy enemy = new TriangleEnemy(player, this, points);
            instantiate(enemy);

            // top down | left right
            if(direction == 1) enemy.position = new Vector2(random.nextDouble(0, getSize().x), -30);
            else if(direction == 2) enemy.position = new Vector2(random.nextDouble(0, getSize().x), getSize().y + 30);

            else if(direction == 3) enemy.position = new Vector2(-30, random.nextDouble(0, getSize().y));
            else if(direction == 4) enemy.position = new Vector2(getSize().x + 30, random.nextDouble(0, getSize().y));
        }
        else{
            spawnDelay -= deltaTime;
        }
    }
}
