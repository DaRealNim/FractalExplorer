package Fractales.colorthemes;

import javafx.scene.paint.Color;

public class DefaultColorTheme extends ColorTheme {

    public Color apply(Integer value, Integer maxIter) {
        if (value >= maxIter)
			return Color.BLACK;

		float hue = (float) value / maxIter;
		return Color.hsb(hue * 360, 0.8f, 1.0f);
    }
    
}
