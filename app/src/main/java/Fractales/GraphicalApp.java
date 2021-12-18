package Fractales;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.ParallelCamera;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class GraphicalApp extends Application {

    @Override
    public void start(Stage stage) {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        Scene scene = new Scene(new StackPane(l), 640, 480);

        ParallelCamera camera = new ParallelCamera();
        scene.setCamera(camera);

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                camera.setTranslateX(camera.getTranslateX() - 20);
            }
            if (event.getCode() == KeyCode.RIGHT) {
                camera.setTranslateX(camera.getTranslateX() + 20);
            }
            if (event.getCode() == KeyCode.UP) {
                camera.setTranslateY(camera.getTranslateY() - 20);
            }
            if (event.getCode() == KeyCode.DOWN) {
                camera.setTranslateY(camera.getTranslateY() + 20);
            }
        });



        stage.setScene(scene);
        stage.show();
    }

}
