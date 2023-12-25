package ch.ee.core;

import ch.ee.common.GameObject;
import ch.ee.common.Vector2;
import ch.ee.utils.InputManager;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Window {
    // objects
    private final Stage stage;
    private final Scene scene;
    private final Canvas canvas;
    private final Rectangle clipRect;
    private final GraphicsContext graphic;
    private final List<GameObject> gameObjects = new ArrayList<>();

    // window colors
    private final Color TITLE_BAR_BUTTON_COLOR_DEFAULT = Color.web("#333333");
    private final Color TITLE_BAR_FOREGROUND_COLOR_ACTIVE_DEFAULT = Color.WHITE;
    private final Color TITLE_BAR_FOREGROUND_COLOR_INACTIVE_DEFAULT = Color.GREY;
    private Color closeButtonColor;
    private Color maximizeButtonColor;
    private Color minimizeButtonColor;
    private Color titleBarForegroundColor;

    // window infos
    private final String title;
    private boolean mouseInWindow;

    // window event states
    private boolean mouseClickedOnTitleBar;
    private boolean closeButtonHovering;
    private Vector2 oldMousePosition;

    // window state
    private long lastTime;

    public Window(String title) {
        this.title = title;
        closeButtonColor = TITLE_BAR_BUTTON_COLOR_DEFAULT;
        maximizeButtonColor = TITLE_BAR_BUTTON_COLOR_DEFAULT;
        minimizeButtonColor = TITLE_BAR_BUTTON_COLOR_DEFAULT;

        stage = new Stage();
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);

        scene = new Scene(new Pane());
        scene.setFill(Color.TRANSPARENT);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/transparent-window.css")).toExternalForm());
        stage.setScene(scene);

        canvas = new Canvas();
        canvas.setStyle("-fx-background-radius: 20");

        clipRect = new Rectangle();
        clipRect.setArcWidth(20);
        clipRect.setArcHeight(20);
        canvas.setClip(clipRect);

        graphic = canvas.getGraphicsContext2D();
        start();
        setSize(500, 500);

        lastTime = System.nanoTime();

        new AnimationTimer() {
            @Override
            public void handle(long currentTime) {
                double deltaTime = (currentTime - lastTime) / 1e9d;
                lastTime = currentTime;

                update(deltaTime);
                for (GameObject gameObject : gameObjects){
                    gameObject.triggerUpdate(deltaTime);
                }

                drawWindow();
            }
        }.start();

        Pane root = (Pane) scene.getRoot();
        root.getChildren().add(canvas);

        handleEvents();
    }

    public void show(){
        stage.show();
        start();
    }

    public void setSize(int w, int h){
        stage.setWidth(w);
        stage.setHeight(h);

        canvas.setWidth(w);
        canvas.setHeight(h);

        clipRect.setWidth(w);
        clipRect.setHeight(h);

        drawWindow();
    }

    // internal

    private void handleEvents(){
        stage.focusedProperty().addListener(e -> {
            if (stage.isFocused())
                titleBarForegroundColor = TITLE_BAR_FOREGROUND_COLOR_ACTIVE_DEFAULT;
            else
                titleBarForegroundColor = TITLE_BAR_FOREGROUND_COLOR_INACTIVE_DEFAULT;

            drawWindow();
        });

        scene.setOnMouseExited(e -> {
            mouseInWindow = false;
            System.out.println("Exited");
            closeButtonColor = TITLE_BAR_BUTTON_COLOR_DEFAULT;
            maximizeButtonColor = TITLE_BAR_BUTTON_COLOR_DEFAULT;
            closeButtonHovering = false;
            drawWindow();
        });

        scene.setOnMouseEntered(e -> {
            mouseInWindow = true;
            System.out.println("entered");
        });

        scene.setOnMousePressed(e -> {
            if(e.getButton() == MouseButton.PRIMARY){
                if(e.getSceneY() <= 30 && e.getSceneX() <= canvas.getWidth() - 150){
                    mouseClickedOnTitleBar = true;
                    oldMousePosition = new Vector2(e.getSceneX(), e.getSceneY());
                }

                if(closeButtonHovering){
                    Platform.exit();
                }
            }

            InputManager.setMouseButton(e.getButton());
        });

        scene.setOnMouseReleased(e -> {
            mouseClickedOnTitleBar = false;

            InputManager.setMouseButton(MouseButton.NONE);
        });

        scene.setOnMouseMoved(e -> {
            if(e.getSceneY() <= 30 && e.getSceneX() >= canvas.getWidth() - 50 && mouseInWindow){
                closeButtonColor = Color.web("#993333");
                closeButtonHovering = true;
            }else{
                closeButtonColor = TITLE_BAR_BUTTON_COLOR_DEFAULT;
                closeButtonHovering = false;
            }

            if(e.getSceneY() <= 30 && e.getSceneX() >= canvas.getWidth() - 100 && e.getSceneX() <= canvas.getWidth() - 50 && mouseInWindow){
                maximizeButtonColor = Color.web("#444444");
            }else{
                maximizeButtonColor = TITLE_BAR_BUTTON_COLOR_DEFAULT;
            }

            if(e.getSceneY() <= 30 && e.getSceneX() >= canvas.getWidth() - 150 && e.getSceneX() <= canvas.getWidth() - 100 && mouseInWindow){
                minimizeButtonColor = Color.web("#444444");
            }else{
                minimizeButtonColor = TITLE_BAR_BUTTON_COLOR_DEFAULT;
            }

            InputManager.setMouseWindowPosition(new Vector2(e.getSceneX(), e.getSceneY()));
            InputManager.setMouseScreenPosition(new Vector2(e.getScreenX(), e.getScreenY()));
        });

        scene.setOnMouseDragged(e -> {
            if(mouseClickedOnTitleBar){
                stage.setX(e.getScreenX() - oldMousePosition.x);
                stage.setY(e.getScreenY() - oldMousePosition.y);
            }
        });

        // register key presses

        scene.setOnKeyPressed(e -> InputManager.registerKey(e.getCode()));
        scene.setOnKeyReleased(e -> InputManager.unregisterKey(e.getCode()));
    }

    private void drawWindow(){
        graphic.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // background
        graphic.setFill(Color.BLACK);
        graphic.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // TODO: test laser
//        graphic.translate(100, 100);
//        graphic.rotate(135);
//        graphic.clearRect(-25,-25,200,50);
//        graphic.rotate(-135);
//        graphic.translate(-100, -100);


        for (GameObject gameObject : gameObjects){
            graphic.setFill(Color.WHITE);
            graphic.setStroke(Color.WHITE);
            gameObject.triggerDraw(graphic);
        }

        draw(graphic);

        // titlebar
        graphic.setFill(Color.web("#333333"));
        graphic.fillRect(0, 0, canvas.getWidth(), 30);

        graphic.setStroke(Color.WHITE);
        graphic.strokeOval(10, 5, 20, 20);
        graphic.strokeOval(13, 8, 14, 14);

        // title text
        graphic.setStroke(titleBarForegroundColor);
        graphic.strokeText(title, 35, 20);

        // close button
        graphic.setFill(closeButtonColor);
        graphic.fillRect(canvas.getWidth() - 50, 0,50, 30);
        graphic.strokeLine(canvas.getWidth() - 30, 10, canvas.getWidth() - 20, 20);
        graphic.strokeLine(canvas.getWidth() - 20, 10, canvas.getWidth() - 30, 20);

        // maximize button
        graphic.setStroke(TITLE_BAR_FOREGROUND_COLOR_INACTIVE_DEFAULT);
        graphic.setFill(maximizeButtonColor);
        graphic.fillRect(canvas.getWidth() - 100, 0,50, 30);
        graphic.strokeLine(canvas.getWidth() - 80, 10, canvas.getWidth() - 70, 10);
        graphic.strokeLine(canvas.getWidth() - 80, 10, canvas.getWidth() - 80, 20);
        graphic.strokeLine(canvas.getWidth() - 70, 20, canvas.getWidth() - 80, 20);
        graphic.strokeLine(canvas.getWidth() - 70, 10, canvas.getWidth() - 70, 20);

        // minimize button
        graphic.setStroke(titleBarForegroundColor);
        graphic.setFill(minimizeButtonColor);
        graphic.fillRect(canvas.getWidth() - 150, 0,50, 30);
        graphic.strokeLine(canvas.getWidth() - 120, 20, canvas.getWidth() - 130, 20);

        // window border (shall always be last!!!)
        graphic.setStroke(Color.web("#333333"));
        graphic.strokeRoundRect(0, 0, canvas.getWidth(), canvas.getHeight(), 20, 20);
    }

    public void instantiate(GameObject gameObject){
        gameObjects.add(gameObject);
        gameObject.triggerStart();
    }

    public abstract void start();

    public abstract void draw(GraphicsContext graphic);

    public abstract void update(double deltaTime);
}
