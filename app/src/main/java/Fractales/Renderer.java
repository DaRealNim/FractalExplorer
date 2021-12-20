package Fractales;

import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;
import javafx.util.Pair;
import javax.imageio.ImageIO;
import javafx.embed.swing.*;
import org.apache.commons.math3.complex.Complex;
import javafx.scene.image.Image;



public class Renderer {

    private static Color getColorOfValue(int value, int maxIter) {
        if (value >= maxIter)
			return Color.BLACK;
		float hue = (float) value / maxIter;
		/*
		//This parts just forbids the color red, remove it for performance.
		float codedHue = (float) (hue - Math.floor(hue)) * 360;
		if (codedHue < 30) //if it's red
			hue += 0.78f; //make it purple
			*/
        Color c = Color.getHSBColor(hue, 0.8f, 1.0f);
		return c;
    }

    public static BufferedImage drawFractal(final Fractal fractal) {
        final int sz = fractal.getScreenSize();

        final BufferedImage img = new BufferedImage(sz, sz, BufferedImage.TYPE_3BYTE_BGR);
        List<Pair<Integer, Integer>> xyPairs = new ArrayList<>();

		for (int x = 0; x < sz; x++) {
			for (int y = 0; y < sz; y++) {
				xyPairs.add(new Pair(x, y));
			}
		}

        xyPairs.parallelStream().forEach(p -> colorPixel(img, fractal, p));

        return (img);
    }

    private static void colorPixel(BufferedImage img, final Fractal f, Pair<Integer, Integer> p) {
        final double step = f.getStep();
        int x = p.getKey();
        int y = p.getValue();

        Complex c = f.getRectStart().add(new Complex(x*step, y*step));

        int value = f.escapeOrbit(c);
        Color color = getColorOfValue(value, f.getMaxIter());
        img.setRGB(x, y, color.getRGB());
    }

    public static void saveToFile(final String filename, BufferedImage image) {
        try {
            ImageIO.write(image, "PNG", new File(filename + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public static Image convertToFxImage(BufferedImage image) {
        return (SwingFXUtils.toFXImage(image, null));
    }
}
