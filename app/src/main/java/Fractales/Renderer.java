package Fractales;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.math3.complex.Complex;

import Fractales.colorthemes.ColorTheme;
import Fractales.fractals.Fractal;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.util.Pair;



public class Renderer {

    public static Image drawFractal(final Fractal fractal, ColorTheme theme) {
        final int sz = fractal.getScreenSize();

        WritableImage img = new WritableImage(sz, sz);
				PixelWriter pw = img.getPixelWriter();

        List<Pair<Integer, Integer>> xyPairs = new ArrayList<>();

				for (int x = 0; x < sz; x++) {
					for (int y = 0; y < sz; y++) {
						xyPairs.add(new Pair<Integer, Integer>(x, y));
					}
				}

        xyPairs.parallelStream().forEach(p -> colorPixel(theme, img, pw, fractal, p));

        return (img);
    }

    private static void colorPixel(ColorTheme theme, WritableImage img, PixelWriter pw, final Fractal f, Pair<Integer, Integer> p) {
        final double step = f.getStep();
        int x = p.getKey();
        int y = p.getValue();


				Complex c = f.getRect().getStart().add(new Complex(x*step, -y*step));


        int value = f.escapeOrbit(c);
        Color color = theme.getColor(value, f.getMaxIter());

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
