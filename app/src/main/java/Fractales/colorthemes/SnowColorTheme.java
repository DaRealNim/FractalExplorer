package Fractales.colorthemes;

import javafx.scene.paint.Color;

public class SnowColorTheme extends ColorTheme {

    public Color apply(Integer value, Integer maxIter) {
        if (value >= maxIter)
			return Color.WHITE;

		float intensity = (float) value / maxIter;
		return Color.hsb(0.0f, 0.0f, intensity);
    }
    
}
