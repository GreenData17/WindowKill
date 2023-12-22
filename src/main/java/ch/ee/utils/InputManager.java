package ch.ee.utils;

import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class InputManager {
    public static final List<KeyCode> pressedKeys = new ArrayList<>();

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
}
