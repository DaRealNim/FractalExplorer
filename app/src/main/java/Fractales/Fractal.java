package Fractales;

import org.apache.commons.math3.complex.Complex;

import Fractales.ComplexRectangle.TranslationDirection;


public abstract class Fractal
{

	// protected Complex p1, p2;
	protected ComplexRectangle rect;
	protected int maxIter;
	protected int radius;
	protected double step;
	protected int screenSize;

	public Fractal(ComplexRectangle rect, double step, int max, int r) {
		this.rect = rect;
		this.maxIter = max;
		this.radius = r;
		this.step = step;
		this.screenSize = (int)(Math.abs(rect.getEnd().getReal() - rect.getStart().getReal()) / step);
	}

	public Fractal(ComplexRectangle rect, int screenSize, int max, int r) {
		this.rect = rect;
		this.maxIter = max;
		this.radius = r;
		this.screenSize = screenSize;
		this.step = Math.abs(rect.getEnd().getReal() - rect.getStart().getReal()) / screenSize;
	}

	public void setRectangle(ComplexRectangle rect) {
		this.rect = rect;
		this.step = Math.abs(rect.getEnd().getReal() - rect.getStart().getReal()) / screenSize;
	}

	public void setMaxIterations(int maxIter) {
		this.maxIter = maxIter;
	}

	public int getMaxIter() {
		return maxIter;
	}

	public double getStep() {
		return step;
	}

	public int getScreenSize() {
		return screenSize;
	}

	public ComplexRectangle getRect() {
		return rect;
	}

	public abstract int escapeOrbit(Complex z);
	public abstract <T extends Fractal> T zoomed(double factor);
	public abstract <T extends Fractal> T translated(TranslationDirection direction);
	public abstract String getName();
}
