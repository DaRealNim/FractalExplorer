package Fractales.fractals;

import org.apache.commons.math3.complex.Complex;

import Fractales.utils.ComplexRectangle;
import Fractales.utils.ComplexRectangle.TranslationDirection;

/**
 * The abstract class representing a Fractal. It has two constructors, one for calculating it with
 * a step-between-pixels, and one with an image size.
 */
public abstract class Fractal
{
	protected ComplexRectangle rect;
	protected int maxIter;
	protected int radius;
	protected double step;
	protected int screenSize;

	/**
	 * 
	 * @param rect The complex rectangle the fractal is drawn in
	 * @param step The step to add between each complex point for each pixel (image size is automatically calculated)
	 * @param max The maximum number of iterations before a point becomes fully divergent.
	 * @param r The radius
	 */
	public Fractal(ComplexRectangle rect, double step, int max, int r) {
		this.rect = rect;
		this.maxIter = max;
		this.radius = r;
		this.step = step;
		this.screenSize = (int)(Math.abs(rect.getEnd().getReal() - rect.getStart().getReal()) / step);
	}

	/**
	 * 
	 * @param rect The complex rectangle the fractal is drawn in
	 * @param screenSize The size in pixel the fractal is represented in (the step is automatically calculated)
	 * @param max The maximum number of iterations before a point becomes fully divergent.
	 * @param r The radius
	 */
	public Fractal(ComplexRectangle rect, int screenSize, int max, int r) {
		this.rect = rect;
		this.maxIter = max;
		this.radius = r;
		this.screenSize = screenSize;
		this.step = Math.abs(rect.getEnd().getReal() - rect.getStart().getReal()) / screenSize;
	}

	/**
	 * Sets the complex rectangle of the Fractal
	 * @param rect
	 */
	public void setRectangle(ComplexRectangle rect) {
		this.rect = rect;
		this.step = Math.abs(rect.getEnd().getReal() - rect.getStart().getReal()) / screenSize;
	}

	/**
	 * Returns the max iteration of the fractal
	 * 
	 * @return
	 */
	public int getMaxIter() {
		return maxIter;
	}

	/**
	 * Returns the step of the fractal
	 * 
	 * @return
	 */
	public double getStep() {
		return step;
	}

	/**
	 * Return the screen (or image size) the fractal is represented in
	 * 
	 * @return
	 */
	public int getScreenSize() {
		return screenSize;
	}

	/**
	 * Returns the complex rectangle of the fractal
	 * 
	 * @return
	 */
	public ComplexRectangle getRect() {
		return rect;
	}

	/**
	 * Calculates the number of iterations before a specific point of the fractal escapes the orbit
	 * @param z The complex point to compute
	 * @return The number of iterations
	 */
	public abstract int escapeOrbit(Complex z);

	/**
	 * Returns a version of the Fractal zoomed by the provided factor
	 * 
	 * @param <T> The type of fractal
	 * @param factor The zoom factor (1 is no change, &lt; 1 zooms in, &gt; 1 zooms out)
	 * @return The new zoomed fractal
	 */
	public abstract <T extends Fractal> T zoomed(double factor);

	/**
	 * Returns a version of the Fractal who's complex rectangle was translated in the provided direction
	 * @param <T> The type of fractal
	 * @param direction The direction to shift the complex rectangle to
	 * @return The new translated fractal
	 */
	public abstract <T extends Fractal> T translated(TranslationDirection direction);
	public abstract String getName();
}
