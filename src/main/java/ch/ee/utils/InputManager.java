package ch.ee.utils;

import ch.ee.common.Vector2;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class InputManager {
    private static final List<KeyCode> pressedKeys = new ArrayList<>();
    private static MouseButton mouseButton = MouseButton.NONE;
    private static Vector2 mouseScreenPosition = Vector2.zero();
    private static Vector2 mouseWindowPosition = Vector2.zero();

    public static boolean isPressed(KeyCode key){
        return pressedKeys.contains(key);
    }

    public static void registerKey(KeyCode key){
        if(pressedKeys.contains(key)) return;

        pressedKeys.add(key);
    }

    public static void unregisterKey(KeyCode key) {
        if(!pressedKeys.contains(key)) return;

        pressedKeys.remove(key);
    }

    public static MouseButton getMouseButton() {
        return mouseButton;
    }

    public static void setMouseButton(MouseButton mouseButton) {
        InputManager.mouseButton = mouseButton;
    }

    public static Vector2 getMouseScreenPosition() {
        return mouseScreenPosition;
    }

    public static void setMouseScreenPosition(Vector2 mouseScreenPosition) {
        InputManager.mouseScreenPosition = mouseScreenPosition;
    }

    public static Vector2 getMouseWindowPosition() {
        return mouseWindowPosition;
    }

    public static void setMouseWindowPosition(Vector2 mouseWindowPosition) {
        InputManager.mouseWindowPosition = mouseWindowPosition;
    }
}
