package Fractales;

import java.io.File;
import java.io.IOException;
import javafx.scene.paint.Color;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;
import javafx.util.Pair;
import javax.imageio.ImageIO;
import javafx.embed.swing.*;
import org.apache.commons.math3.complex.Complex;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelWriter;



public class Renderer {

    private static Color getColorOfValue(int value, int maxIter) {
        if (value >= maxIter)
					return Color.BLACK;

					float hue = (float) value / maxIter;

        Color c = Color.hsb(hue * 360, 0.8f, 1.0f);
				return c;
    }

    public static Image drawFractal(final Fractal fractal) {
        final int sz = fractal.getScreenSize();

        WritableImage img = new WritableImage(sz, sz);
				PixelWriter pw = img.getPixelWriter();

        List<Pair<Integer, Integer>> xyPairs = new ArrayList<>();

				for (int x = 0; x < sz; x++) {
					for (int y = 0; y < sz; y++) {
						xyPairs.add(new Pair(x, y));
					}
				}

        xyPairs.parallelStream().forEach(p -> colorPixel(img, pw, fractal, p));

        return (img);
    }

    private static void colorPixel(WritableImage img, PixelWriter pw, final Fractal f, Pair<Integer, Integer> p) {
        final double step = f.getStep();
        int x = p.getKey();
        int y = p.getValue();


				Complex c = f.getRect().getStart().add(new Complex(x*step, -y*step));


        int value = f.escapeOrbit(c);
        Color color = getColorOfValue(value, f.getMaxIter());

				pw.setColor(x, y, color);
    }

    public static void saveToFile(final String filename, Image image) {
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "PNG", new File(filename + ".png"));
				} catch (IOException e) {
						e.printStackTrace();
				}
    }

}
