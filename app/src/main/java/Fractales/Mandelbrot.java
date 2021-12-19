package Fractales;

import java.awt.Color;
import java.awt.image.BufferedImage;
import org.apache.commons.math3.complex.Complex;
import java.lang.Math;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import javafx.util.Pair;
import java.util.List;
import java.util.ArrayList;


public class Mandelbrot extends Julia {
	public Mandelbrot(int w, int h, int max, int r) {
		super(w, h, max, 0, 0, r);
	}

	@Override
	public int escapeOrbit(Pair<Integer, Integer> p)
	{
		int x = p.getKey();
		int y = p.getValue();

		Complex z = new Complex(1.8 * (x - width / 2) / (width / 2), 1.69 * (y - height / 2) / (height / 2));
		Complex c = z;

		int i = 0;
	 	while (i < maxIter && z.abs() < radius) {
			z = z.pow(2.0).add(c);
			i++;
		}

		return(i);
	}

	public void saveImage()
	{
		try {
		ImageIO.write(image, "PNG", new File("mandelbrot.png"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
