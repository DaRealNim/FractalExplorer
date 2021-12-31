package Fractales.colorthemes;

import javafx.scene.paint.Color;

/**
 * The electric color theme. Each pixel will be a different shade of blue, from light blue/greenish cyan
 * for more divergent points, and a darker shade of blue for less divergent points.
 */
public class ElectricColorTheme extends ColorTheme {

    public Color apply(Integer value, Integer maxIter) {
        if (value >= maxIter)
			return Color.hsb(180.0f, 1.0f, 1.0f);

		float factor = (float) value / maxIter;
        float hue = 210.0f + (150.0f - 210.0f) * factor;
		return Color.hsb(hue, 1.0f, 1.0f);
    }
    
}
