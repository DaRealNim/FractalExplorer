package Fractales;

import java.awt.image.BufferedImage;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.ParallelCamera;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.apache.commons.math3.complex.Complex;


public class GraphicalApp extends Application {
    // int canvasSize;
    // Scene scene;

    Fractal currentlyDisplayed;

    // public GraphicalApp(int size) {
    //     super();
    //     this.canvasSize = size;
    // }

    private void renderFractal(GraphicsContext gc) {
        if (currentlyDisplayed != null) {
            BufferedImage jImage = Renderer.drawFractal(currentlyDisplayed);
            Image img = Renderer.convertToFxImage(jImage);
            gc.drawImage(img, 0, 0);
        }
    }

    private Pair<Complex, Complex> getComplexRectangleFromInputs(TextField x1Field,
                                                                 TextField y1Field,
                                                                 TextField x2Field,
                                                                 TextField y2Field)
    {
        try {
            double x1 = Double.parseDouble(x1Field.getText());
            double y1 = Double.parseDouble(y1Field.getText());
            double x2 = Double.parseDouble(x2Field.getText());
            double y2 = Double.parseDouble(y2Field.getText());
            Complex z1 = new Complex(x1, y1);
            Complex z2 = new Complex(x2, y2);
            return new Pair(z1, z2);
        } catch (NumberFormatException e) {}
        return null;
    }

    @Override
    public void start(Stage stage) {
        int size = 800;

        //Panels principaux : zone de la fractale, et panel de contrôle
        HBox mainPane = new HBox();
        Canvas fractalCanvas = new Canvas(800, 800);
        GridPane controlPane = new GridPane();
        Scene scene = new Scene(mainPane, size+400, size, Color.BEIGE);

        scene.getStylesheets().add("style.css");
        controlPane.setMinSize(400, 800);

        GraphicsContext gc = fractalCanvas.getGraphicsContext2D();
        // spawnJuliaFractal();

        // Elements du panel de contrôle
        // Titre
        Text titleText = newText("Fractal Explorer", "titleText");
        titleText.setTextAlignment(TextAlignment.CENTER);

        // Zone génération d'une nouvelle fractale
        Text typeText = newText("Generate a new fractal", "subTitleText");

        // Groupe de boutons pour la selection du type de fractale
        ToggleGroup fractalTypeToggleGroupe = new ToggleGroup();
        RadioButton juliaRadio = new RadioButton("Julia Set");
        juliaRadio.setToggleGroup(fractalTypeToggleGroupe);
        RadioButton mandelRadio = new RadioButton("Mandelbrot Set");
        mandelRadio.setToggleGroup(fractalTypeToggleGroupe);
        VBox fractalTypeButtonsPane = new VBox(8);
        fractalTypeButtonsPane.getChildren().addAll(juliaRadio, mandelRadio);

        //Panel d'entrée pour la constante pour la fractale Julia
        Text juliaConstantInputTitle = newText("Constant to use for Julia Set", "subSubTitleText");
        TextField realField = new TextField("-0.70176");
        Text operators = newText(" + i*");
        TextField imaginaryField = new TextField("-0.3842");
        HBox juliaConstantTextInputPanel = new HBox(3);
        juliaConstantTextInputPanel.getChildren().addAll(realField, operators, imaginaryField);
        VBox juliaConstantInputPanel = new VBox(3);
        juliaConstantInputPanel.getChildren().addAll(juliaConstantInputTitle,
                                                     juliaConstantTextInputPanel);
        //Ce panel est caché si julia n'est pas selectionnée
        juliaConstantInputPanel.managedProperty().bind(juliaConstantInputPanel.visibleProperty());
        juliaConstantInputPanel.visibleProperty().bind(juliaRadio.selectedProperty());

        Text iterTitle = newText("Number of iterations", "subSubTitleText");
        TextField iterField = new TextField("50");
        Text iterText = newText("iterations");
        HBox iterationsInputPanel = new HBox(3);
        iterationsInputPanel.getChildren().addAll(iterField, iterText);
        VBox iterationsPanel = new VBox(3);
        iterationsPanel.getChildren().addAll(iterTitle, iterationsInputPanel);

        Button generateButton = new Button("Generate");


        // Zone exploration de la fractale
        Text exploreTitle = newText("Explore current fractal", "subTitleText");

        Text displayedRectangleTitle = newText("Currently displayed rectangle", "subSubTitleText");
        TextField firstPointRealField = new TextField("-2");
        TextField firstPointImaginaryField = new TextField("-2");
        TextField secondPointRealField = new TextField("2");
        TextField secondPointImaginaryField = new TextField("2");
        HBox firstPointInput = new HBox(3);
        firstPointInput.getChildren().addAll(newText("z1 = "), firstPointRealField, newText(" + i*"), firstPointImaginaryField);
        HBox secondPointInput = new HBox(3);
        secondPointInput.getChildren().addAll(newText("z2 = "), secondPointRealField, newText(" + i*"), secondPointImaginaryField);
        VBox rectanglePointsInputs = new VBox(3);
        rectanglePointsInputs.getChildren().addAll(firstPointInput, secondPointInput);
        VBox displayedRectanglePanel = new VBox(3);
        displayedRectanglePanel.getChildren().addAll(displayedRectangleTitle,
                                                     rectanglePointsInputs);
        Button jumpButton = new Button("Set rectangle");

        Text movementTitle = newText("Move inside fractal", "subSubTitleText");
        Button upButton = new Button("↑");
        Button downButton = new Button("↓");
        Button leftButton = new Button("←");
        Button rightButton = new Button("→");
        Button zoomInButton = new Button("Zoom +");
        Button zoomOutButton = new Button("Zoom -");
        // upButton.setMaxWidth(Double.MAX_VALUE);
        // downButton.setMaxWidth(Double.MAX_VALUE);
        // leftButton.setMaxWidth(Double.MAX_VALUE);
        // rightButton.setMaxWidth(Double.MAX_VALUE);
        zoomInButton.setMaxWidth(Double.MAX_VALUE);
        zoomOutButton.setMaxWidth(Double.MAX_VALUE);
        VBox movementPane = new VBox(3);
        VBox zoomButtonsPane = new VBox(2);
        zoomButtonsPane.getChildren().addAll(zoomInButton, zoomOutButton);
        GridPane movementButtonsPane = new GridPane();
        movementButtonsPane.add(upButton, 1, 0);
        movementButtonsPane.add(downButton, 1, 2);
        movementButtonsPane.add(leftButton, 0, 1);
        movementButtonsPane.add(rightButton, 2, 1);
        HBox mvtAndZoomPane = new HBox(50);
        mvtAndZoomPane.getChildren().addAll(movementButtonsPane, zoomButtonsPane);
        mvtAndZoomPane.setStyle("-fx-padding: 30 0 0 0;");
        movementPane.getChildren().addAll(movementTitle, mvtAndZoomPane);

        controlPane.addColumn(0, titleText,
                                 new Separator(),
                                 typeText,
                                 fractalTypeButtonsPane,
                                 juliaConstantInputPanel,
                                 iterationsPanel,
                                 generateButton,
                                 new Separator(),
                                 exploreTitle,
                                 displayedRectanglePanel,
                                 jumpButton,
                                 movementPane

        );
        controlPane.setHalignment(titleText, HPos.CENTER);
        controlPane.setStyle("-fx-background-color: #EEEEEE");
        controlPane.setPadding(new Insets(10,50,10,50));
        controlPane.setVgap(15);

        generateButton.setOnAction(event -> {
            Fractal frac = null;

            if(juliaRadio.isSelected()) {
                frac = new Julia(new Complex(-2, 2), new Complex(2, -2), new Complex(-0.70176, -0.3842), 800, 200, 2);
            } else if (mandelRadio.isSelected()) {
                frac = new Mandelbrot(new Complex(-2, 2), new Complex(2, -2), 800, 200, 2);
            }
            if (frac != null) {
                firstPointRealField.setText("-2");
                firstPointImaginaryField.setText("2");
                secondPointRealField.setText("2");
                secondPointImaginaryField.setText("-2");
                currentlyDisplayed = frac;
                renderFractal(gc);
            }
        });

        jumpButton.setOnAction(event -> {
            if (currentlyDisplayed != null) {
                Pair<Complex, Complex> rect = getComplexRectangleFromInputs(firstPointRealField,
                                                                            firstPointImaginaryField,
                                                                            secondPointRealField,
                                                                            secondPointImaginaryField);
                if (rect != null) {
                    currentlyDisplayed.setRectangle(rect);
                    renderFractal(gc);
                }
            }
        });

        mainPane.getChildren().addAll(fractalCanvas, new Separator(Orientation.VERTICAL), controlPane);

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 100, 100);

        stage.setScene(scene);
        stage.setTitle("Fractal Explorer");
        stage.setResizable(false);
        stage.show();

        // ParallelCamera camera = new ParallelCamera();
        // scene.setCamera(camera);
        //
        // scene.setOnKeyPressed(event -> {
        //     if (event.getCode() == KeyCode.LEFT) {
        //         camera.setTranslateX(camera.getTranslateX() - 20);
        //     }
        //     if (event.getCode() == KeyCode.RIGHT) {
        //         camera.setTranslateX(camera.getTranslateX() + 20);
        //     }
        //     if (event.getCode() == KeyCode.UP) {
        //         camera.setTranslateY(camera.getTranslateY() - 20);
        //     }
        //     if (event.getCode() == KeyCode.DOWN) {
        //         camera.setTranslateY(camera.getTranslateY() + 20);
        //     }
        // });


    }

    private Text newText(String text) {
        return newText(text, "text");
    }

    private Text newText(String text, String styleClass) {
        Text txt = new Text(text);
        txt.getStyleClass().add(styleClass);
        return txt;
    }

    private Button createButton(String text, String styleClass)
    {
        Button newButton = new Button();

        newButton.setText(text);
        newButton.getStyleClass().add(styleClass);

        return (newButton);
    }

}
