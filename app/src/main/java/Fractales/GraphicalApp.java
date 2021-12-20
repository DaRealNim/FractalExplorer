package Fractales;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.ParallelCamera;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.input.KeyCode;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.*;


public class GraphicalApp extends Application {

    @Override
    public void start(Stage stage) {
        int width = 800;
        int height = 600;

        Button juliaButton = createButton("Julia Set", "button");
        Button mandelbrotButton = createButton("Mandelbrot Set", "button");

        HBox hButtons = new HBox(8);
        hButtons.setAlignment(Pos.CENTER);
        hButtons.getChildren().addAll(juliaButton, mandelbrotButton);


        Text title = new Text("very menu much wow many fractal");
        title.setFont(new Font(36));

        BorderPane mainPane = new BorderPane();
        mainPane.setAlignment(title, Pos.CENTER);
        mainPane.setTop(title);
        mainPane.setCenter(hButtons);

        Scene scene = new Scene(mainPane, width, height, Color.BEIGE);
        scene.getStylesheets().add("buttonStylesheet.css");

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
        stage.setTitle("Fractal Viewer");
        stage.show();
    }

    public Button createButton(String text, String styleClass)
    {
        Button newButton = new Button();

        newButton.setText(text);
        newButton.getStyleClass().add(styleClass);

        return (newButton);
    }

}
