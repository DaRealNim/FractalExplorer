package Fractales;

import org.apache.commons.math3.complex.Complex;

import Fractales.ComplexRectangle.TranslationDirection;


public class Julia extends Fractal {
	protected final Complex constant;

	public Julia(ComplexRectangle rect, Complex constant, double step, int max, int r) {
		super(rect, step, max, r);
		this.constant = constant;
	}

	public Julia(ComplexRectangle rect, Complex constant, int screenSize, int max, int r) {
		super(rect, screenSize, max, r);
		this.constant = constant;
	}

	@Override
	public int escapeOrbit(Complex z)
	{
		int i;

		for (i = 0; i < maxIter; i++) {
			z = z.pow(2.0).add(constant);
			if (z.abs() > radius)
				return(i);
		}

		return(maxIter);
	}

	public Julia zoomed(double factor) {
		return new Julia(getRect().scaled(factor), constant, screenSize, maxIter, radius);
	}

	public Julia translated(TranslationDirection direction) {
		double zoomfactor = (getRect().getEnd().getReal() - getRect().getStart().getReal()) / 4.0;
		return new Julia(getRect().translated(direction, zoomfactor), constant, screenSize, maxIter, radius);
	}

	public String getName() {
		return "julia";
	}

}
