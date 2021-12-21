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


public abstract class Fractal
{

	// protected Complex p1, p2;
	protected ComplexRectangle rect;
	protected int maxIter;
	protected int radius;
	protected Double step;
	protected Integer screenSize;

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
	public abstract String getName();
}
