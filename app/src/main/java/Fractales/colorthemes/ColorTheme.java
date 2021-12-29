package Fractales.colorthemes;

import java.util.function.BiFunction;

import javafx.scene.paint.Color;

public abstract class ColorTheme implements BiFunction<Integer, Integer, Color> {
    public Color getColor(Integer value, Integer maxIter) {
        return apply(value, maxIter);
    }
}
