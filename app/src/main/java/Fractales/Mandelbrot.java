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


public class Mandelbrot extends Fractal {

	public Mandelbrot(Complex p1, Complex p2, double step, int max, int r) {
		super(p1, p2, step, max, r);
	}

	public Mandelbrot(Complex p1, Complex p2, int screenSize, int max, int r) {
		super(p1, p2, screenSize, max, r);
	}

	@Override
	public int escapeOrbit(Complex z)
	{
		Complex c = z;
		int i = 0;


	 	while (i < maxIter && z.abs() < radius) {
			z = z.pow(2.0).add(c);
			i++;
		}

		return(i);
	}

	public Mandelbrot zoomed(double factor) {
		System.out.println("Keeping screen size of "+screenSize);
		return new Mandelbrot(p1.multiply(factor), p2.multiply(factor), screenSize, maxIter, radius);
	}

	public String getName() {
		return "mandelbrot";
	}
}
