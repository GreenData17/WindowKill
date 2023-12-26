package ch.ee.core;

import ch.ee.windows.GameWindow;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Window win = new GameWindow();
        win.show();
        win.setSize(300, 330);
    }
}
