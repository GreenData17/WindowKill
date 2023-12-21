package ch.ee.core;

import ch.ee.common.Vector2;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class Window {
    // objects
    private final Stage stage;
    private final Scene scene;
    private final Canvas canvas;
    private final Rectangle clipRect;
    private final GraphicsContext graphic;

    // window colors
    private final Color CLOSE_BUTTON_COLOR_DEFAULT = Color.web("#333333");
    private final Color TITLE_BAR_FOREGROUND_COLOR_ACTIVE_DEFAULT = Color.WHITE;
    private final Color TITLE_BAR_FOREGROUND_COLOR_INACTIVE_DEFAULT = Color.GREY;
    private Color closeButtonColor;
    private Color titleBarForegroundColor;

    // window infos
    private String title;
    private boolean mouseInWindow;

    // window event states
    private boolean mouseClickedOnTitleBar;
    private boolean closeButtonHovering;
    private Vector2 oldMousePosition;

    public Window(String title) {
        this.title = title;
        closeButtonColor = CLOSE_BUTTON_COLOR_DEFAULT;

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
        setSize(500, 500);
        drawWindowBase();

        Pane root = (Pane) scene.getRoot();
        root.getChildren().add(canvas);

        handleEvents();
    }

    public void show(){
        stage.show();
    }

    public void setSize(int w, int h){
        stage.setWidth(w);
        stage.setHeight(h);

        canvas.setWidth(w);
        canvas.setHeight(h);

        clipRect.setWidth(w);
        clipRect.setHeight(h);

        drawWindowBase();
    }

    // internal

    private void handleEvents(){
        stage.focusedProperty().addListener(e -> {
            if (stage.isFocused())
                titleBarForegroundColor = TITLE_BAR_FOREGROUND_COLOR_ACTIVE_DEFAULT;
            else
                titleBarForegroundColor = TITLE_BAR_FOREGROUND_COLOR_INACTIVE_DEFAULT;

            drawWindowBase();
        });

        scene.setOnMouseExited(e -> {
            mouseInWindow = false;
            System.out.println("Exited");
            closeButtonColor = CLOSE_BUTTON_COLOR_DEFAULT;
            closeButtonHovering = false;
            drawWindowBase();
        });

        scene.setOnMouseEntered(e -> {
            mouseInWindow = true;
            System.out.println("entered");
        });

        scene.setOnMousePressed(e -> {
            if(e.getButton() == MouseButton.PRIMARY){
                if(e.getSceneY() <= 30 && e.getSceneX() <= canvas.getWidth() - 50){
                    mouseClickedOnTitleBar = true;
                    oldMousePosition = new Vector2(e.getSceneX(), e.getSceneY());
                }

                if(closeButtonHovering){
                    Platform.exit();
                }
            }
        });

        scene.setOnMouseReleased(e -> {
            mouseClickedOnTitleBar = false;
        });

        scene.setOnMouseMoved(e -> {
            if(e.getSceneY() <= 30 && e.getSceneX() >= canvas.getWidth() - 50 && mouseInWindow){
                closeButtonColor = Color.web("#993333");
                closeButtonHovering = true;
            }else{
                closeButtonColor = CLOSE_BUTTON_COLOR_DEFAULT;
                closeButtonHovering = false;
            }

            drawWindowBase();
        });

        scene.setOnMouseDragged(e -> {
            if(mouseClickedOnTitleBar){
                stage.setX(e.getScreenX() - oldMousePosition.x);
                stage.setY(e.getScreenY() - oldMousePosition.y);
            }
        });
    }

    private void drawWindowBase(){
        graphic.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // background
        graphic.setFill(Color.BLACK);
        graphic.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // TODO: test laser
        graphic.rotate(-45);
        graphic.clearRect(-400, 300, stage.getWidth() + 50, 50);
        graphic.rotate(45);

        // titlebar
        graphic.setFill(Color.web("#333333"));
        graphic.fillRect(0, 0, canvas.getWidth(), 30);

        // title bar
        graphic.setStroke(titleBarForegroundColor);
        graphic.strokeText(title, 20, 20);

        // close button
        graphic.setFill(closeButtonColor);
        graphic.fillRect(canvas.getWidth() - 50, 0,50, 30);
        graphic.strokeText("X", canvas.getWidth() - 30, 20);

        // window border
        graphic.setStroke(Color.web("#333333"));
        graphic.strokeRoundRect(0, 0, canvas.getWidth(), canvas.getHeight(), 20, 20);



    }
}
