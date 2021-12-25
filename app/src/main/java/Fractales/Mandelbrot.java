package Fractales;

import org.apache.commons.math3.complex.Complex;


public class Mandelbrot extends Fractal {

	public Mandelbrot(ComplexRectangle rect, double step, int max, int r) {
		super(rect, step, max, r);
	}

	public Mandelbrot(ComplexRectangle rect, int screenSize, int max, int r) {
		super(rect, screenSize, max, r);
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
		return new Mandelbrot(getRect().scaled(factor), screenSize, maxIter, radius);
	}

	public String getName() {
		return "mandelbrot";
	}
}
