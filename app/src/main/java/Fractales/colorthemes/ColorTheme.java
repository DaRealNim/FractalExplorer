package Fractales.colorthemes;

import java.util.function.BiFunction;

import javafx.scene.paint.Color;

/**
 * An abstract class representing a function that takes two integer values and returns a color.
 * To add a color theme, a developper just needs to make a new class that extends this one and
 * implements the apply function, and make the necessary minor changes in command line options
 * and graphical app
 */
public abstract class ColorTheme implements BiFunction<Integer, Integer, Color> {

    /**
     * The apply function of the BiFunction class. 
     */
    public abstract Color apply(Integer value, Integer maxIter);

    /**
     * A wrapper around the apply function with a more explicit name
     * @param value The number of iterations ran for a specific point of the fractal before it started to diverge.
     * @param maxIter The maximum iterations defined for the fractal.
     * @return The color calculated by the theme according the the number of iterations and max iterations.
     */
    public Color getColor(Integer value, Integer maxIter) {
        return apply(value, maxIter);
    }
}
