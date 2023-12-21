package ch.ee.core;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Window win = new Window("WindowKill ;)");
        win.show();
        win.setSize(700, 500);
    }
}
