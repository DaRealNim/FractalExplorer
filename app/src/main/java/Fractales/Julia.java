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


public class Julia extends Fractal {
	protected final Complex constant;

	public Julia(int w, int h, int max, double real, double imaginary, int r) {
		super(w, h, max, r);
		this.constant = new Complex(real, imaginary);
	}

	public int escapeOrbit(Pair<Integer, Integer> p)
	{
		int x = p.getKey();
		int y = p.getValue();

		Complex z = new Complex(1.8 * (x - width / 2) / (width / 2), 1.69 * (y - height / 2) / (height / 2));
		int i;

		for (i = 0; i < maxIter; i++) {
			z = z.pow(2.0).add(constant);

			if (z.abs() > radius)
				return(i);
		}

		return(maxIter);
	}

	public void saveImage()
	{
		try {
		ImageIO.write(image, "PNG", new File("julia.png"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}


}
