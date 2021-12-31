package Fractales;

import org.apache.commons.math3.complex.Complex;

import Fractales.colorthemes.*;
import Fractales.fractals.Fractal;
import Fractales.fractals.Julia;
import Fractales.fractals.Mandelbrot;
import Fractales.utils.ComplexRectangle;
import Fractales.utils.ComplexRectangle.TranslationDirection;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * The graphical version of the software, Fractal Explorer. Uses JavaFX for the GUI.
 */
public class GraphicalApp extends Application {

    Fractal currentlyDisplayed;
    ColorTheme currentTheme;
    Text error = newText("", "errorText");
    int zoomX = -1;
    int zoomY = -1;

    private final int SIZE = 800;

    private void renderFractal(GraphicsContext gc) {
        if (currentlyDisplayed != null) {
            Image img = Renderer.drawFractal(currentlyDisplayed, currentTheme);
					  gc.drawImage(img, 0, 0);
        }
    }

    private Complex getJuliaConstantFromInputs(TextField real, TextField imag) {
        try {
            double r = Double.parseDouble(real.getText());
            double i = Double.parseDouble(imag.getText());
            return new Complex(r, i);
        } catch (NumberFormatException e) {}
        error.setText("Parsing failed for julia constant");
        return null;
    }

    private ComplexRectangle getComplexRectangleFromInputs(TextField x1Field,
                                                           TextField y1Field,
                                                           TextField x2Field,
                                                           TextField y2Field)
    {
        try {
            double x1 = Double.parseDouble(x1Field.getText());
            double y1 = Double.parseDouble(y1Field.getText());
            double x2 = Double.parseDouble(x2Field.getText());
            double y2 = Double.parseDouble(y2Field.getText());
            ComplexRectangle rect = new ComplexRectangle(x1, y1, x2, y2);
            if(!rect.isValidComplexSquare()) {
                error.setText("Complex rectangle isn't a square, or second point\nis behind first point"+(Math.abs(x2-x1)-Math.abs(y2-y1)));
                return null;
            }
                
            return rect;
        } catch (NumberFormatException e) {}
        error.setText("Parsing failed for complex rectangle coordinates");
        return null;
    }

    private void updateComplexRectangleFields(TextField x1Field,
                                              TextField y1Field,
                                              TextField x2Field,
                                              TextField y2Field)
    {
        x1Field.setText(""+currentlyDisplayed.getRect().getStart().getReal());
        y1Field.setText(""+currentlyDisplayed.getRect().getStart().getImaginary());
        x2Field.setText(""+currentlyDisplayed.getRect().getEnd().getReal());
        y2Field.setText(""+currentlyDisplayed.getRect().getEnd().getImaginary());
    }

    private int getIterationsFromInput(TextField field) {
        try {
            return Integer.parseInt(field.getText());
        } catch (NumberFormatException e) {}
        error.setText("Parsing failed for iterations");
        return -1;
    }

    @Override
    public void start(Stage stage) {

        currentTheme = new DefaultColorTheme();

        //Panels principaux : zone de la fractale, et panel de contrôle
        HBox mainPane = new HBox();
        Canvas fractalCanvas = new Canvas(SIZE, SIZE);
        Canvas lineCanvas = new Canvas(SIZE, SIZE);

        GridPane controlPane = new GridPane();
        Scene scene = new Scene(mainPane, SIZE+400, SIZE, Color.BEIGE);

        scene.getStylesheets().add("style.css");
        controlPane.setMinSize(400, SIZE);

        GraphicsContext gc = fractalCanvas.getGraphicsContext2D();
        GraphicsContext lineGC = lineCanvas.getGraphicsContext2D();

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

        Text displayedRectangleTitle = newText("Complex rectangle", "subSubTitleText");
        TextField firstPointRealField = new TextField("-2");
        TextField firstPointImaginaryField = new TextField("2");
        TextField secondPointRealField = new TextField("2");
        TextField secondPointImaginaryField = new TextField("-2");
        HBox firstPointInput = new HBox(3);
        firstPointInput.getChildren().addAll(newText("z1 = "), firstPointRealField, newText(" + i*"), firstPointImaginaryField);
        HBox secondPointInput = new HBox(3);
        secondPointInput.getChildren().addAll(newText("z2 = "), secondPointRealField, newText(" + i*"), secondPointImaginaryField);
        VBox rectanglePointsInputs = new VBox(3);
        rectanglePointsInputs.getChildren().addAll(firstPointInput, secondPointInput);
        VBox displayedRectanglePanel = new VBox(3);
        displayedRectanglePanel.getChildren().addAll(displayedRectangleTitle,
                                                     rectanglePointsInputs);
        Button resetButton = new Button("Reset rectangle");


        Button generateButton = new Button("Generate");

        // Zone exploration de la fractale
        Text exploreTitle = newText("Explore current fractal", "subTitleText");
        Button upButton = new Button("↑");
        Button downButton = new Button("↓");
        Button leftButton = new Button("←");
        Button rightButton = new Button("→");
        Button zoomInButton = new Button("Zoom +");
        Button zoomOutButton = new Button("Zoom -");
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
        movementPane.getChildren().addAll(mvtAndZoomPane);

        Text colorThemeTitle = newText("Change color theme", "subTitleText");
        Button defaultThemeButton = new Button("Colorful");
        Button snowThemeButton = new Button("Snowy");
        Button electricThemeButton = new Button("Electric");
        HBox themeButtonPane = new HBox(10);
        themeButtonPane.getChildren().addAll(defaultThemeButton, snowThemeButton, electricThemeButton);


        controlPane.addColumn(0, titleText,
                                 new Separator(),
                                 typeText,
                                 fractalTypeButtonsPane,
                                 juliaConstantInputPanel,
                                 iterationsPanel,
                                 displayedRectanglePanel,
                                 resetButton,
                                 generateButton,
                                 new Separator(),
                                 exploreTitle,
                                 movementPane,
                                 new Separator(),
                                 colorThemeTitle,
                                 themeButtonPane,
                                 new Separator(),
                                 error

        );
        GridPane.setHalignment(titleText, HPos.CENTER);
        GridPane.setHalignment(resetButton, HPos.RIGHT);
        controlPane.setStyle("-fx-background-color: #EEEEEE");
        controlPane.setPadding(new Insets(10,50,10,50));
        controlPane.setVgap(12);

        generateButton.setOnAction(event -> {
            Fractal frac = null;
            int maxIter = getIterationsFromInput(iterField);
            if (maxIter <= 0)
                return;
            ComplexRectangle rect = getComplexRectangleFromInputs(firstPointRealField,
                                                                        firstPointImaginaryField,
                                                                        secondPointRealField,
                                                                        secondPointImaginaryField);
            if (rect == null)
                return;
            if(juliaRadio.isSelected()) {
                Complex constant = getJuliaConstantFromInputs(realField, imaginaryField);
                if (constant != null)
                    frac = new Julia(rect, constant, SIZE, maxIter, 2);
            } else if (mandelRadio.isSelected()) {
                frac = new Mandelbrot(rect, SIZE, maxIter, 2);
            }
            if (frac != null) {
                currentlyDisplayed = frac;
                renderFractal(gc);
                error.setText("");
            } else {
                error.setText("No fractal type selected");
            }
        });

        resetButton.setOnAction(event -> {
            firstPointRealField.setText("-2");
            firstPointImaginaryField.setText("2");
            secondPointRealField.setText("2");
            secondPointImaginaryField.setText("-2");
        });

        zoomInButton.setOnAction(event -> {
            if(zoomX != -1 && zoomY != -1 && currentlyDisplayed != null) {
                double step = currentlyDisplayed.getStep();
                double deltaReal = zoomX*step;
                double deltaImag = zoomY*step;
                Complex newCenter = currentlyDisplayed.getRect().getStart().add(new Complex(deltaReal, -deltaImag));
                Complex newZ1 = newCenter.add(new Complex(-(SIZE*step)/2, (SIZE*step)/2));
                Complex newZ2 = newZ1.add(new Complex(SIZE*step, -SIZE*step));
                currentlyDisplayed.setRectangle(new ComplexRectangle(newZ1, newZ2));
                currentlyDisplayed = currentlyDisplayed.zoomed(0.7);
                renderFractal(gc);
                updateComplexRectangleFields(firstPointRealField, firstPointImaginaryField,
                                             secondPointRealField, secondPointImaginaryField);
                lineGC.clearRect(0, 0, SIZE, SIZE);
                zoomX = SIZE/2;
                zoomY = zoomX;
                lineGC.strokeLine(0, zoomX, SIZE, zoomX);
                lineGC.strokeLine(zoomX, 0, zoomX, SIZE);
                error.setText("");
            } else {
                error.setText("No fractal displayed, or no zoom location selected\n(click somewhere on the fractal!)");
            }
        });

        zoomOutButton.setOnAction(event -> {
            if (currentlyDisplayed != null) {
                currentlyDisplayed = currentlyDisplayed.zoomed(1.2);
                renderFractal(gc);
                updateComplexRectangleFields(firstPointRealField, firstPointImaginaryField,
                                             secondPointRealField, secondPointImaginaryField);
                error.setText("");
            } else {
                error.setText("No fractal displayed, or no zoom location selected\n(click somewhere on the fractal!)");
            }
        });

        leftButton.setOnAction(event -> {
            if (currentlyDisplayed != null) {
                currentlyDisplayed = currentlyDisplayed.translated(TranslationDirection.LEFT);
                renderFractal(gc);
                updateComplexRectangleFields(firstPointRealField, firstPointImaginaryField,
                                             secondPointRealField, secondPointImaginaryField);
            }
        });

        rightButton.setOnAction(event -> {
            if (currentlyDisplayed != null) {
                currentlyDisplayed = currentlyDisplayed.translated(TranslationDirection.RIGHT);
                renderFractal(gc);
                updateComplexRectangleFields(firstPointRealField, firstPointImaginaryField,
                                             secondPointRealField, secondPointImaginaryField);
            }
        });

        upButton.setOnAction(event -> {
            if (currentlyDisplayed != null) {
                currentlyDisplayed = currentlyDisplayed.translated(TranslationDirection.UP);
                renderFractal(gc);
                updateComplexRectangleFields(firstPointRealField, firstPointImaginaryField,
                                             secondPointRealField, secondPointImaginaryField);
            }
        });

        downButton.setOnAction(event -> {
            if (currentlyDisplayed != null) {
                currentlyDisplayed = currentlyDisplayed.translated(TranslationDirection.DOWN);
                renderFractal(gc);
                updateComplexRectangleFields(firstPointRealField, firstPointImaginaryField,
                                             secondPointRealField, secondPointImaginaryField);
            }
        });

        defaultThemeButton.setOnAction(event -> {
            currentTheme = new DefaultColorTheme();
            if (currentlyDisplayed != null) {
                renderFractal(gc);
            }
        });

        snowThemeButton.setOnAction(event -> {
            currentTheme = new SnowColorTheme();
            if (currentlyDisplayed != null) {
                renderFractal(gc);
            }
        });

        electricThemeButton.setOnAction(event -> {
            currentTheme = new ElectricColorTheme();
            if (currentlyDisplayed != null) {
                renderFractal(gc);
            }
        });

        lineCanvas.setOnMouseReleased(event -> {
            lineGC.clearRect(0, 0, SIZE, SIZE);
            lineGC.setStroke(Color.GREY);
            lineGC.strokeLine(0, (int)event.getY(), SIZE, (int)event.getY());
            lineGC.strokeLine((int)event.getX(), 0, (int)event.getX(), SIZE);
            zoomX = (int)event.getX();
            zoomY = (int)event.getY();
        });

        StackPane fractalPane = new StackPane();
        fractalPane.getChildren().addAll(fractalCanvas, lineCanvas);

        mainPane.getChildren().addAll(fractalPane, new Separator(Orientation.VERTICAL), controlPane);

        stage.setScene(scene);
        stage.setTitle("Fractal Explorer");
        stage.setResizable(false);
        stage.show();
    }

    private Text newText(String text) {
        return newText(text, "text");
    }

    private Text newText(String text, String styleClass) {
        Text txt = new Text(text);
        txt.getStyleClass().add(styleClass);
        return txt;
    }
}
