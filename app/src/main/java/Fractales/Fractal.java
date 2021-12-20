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

	protected Complex p1, p2;
	protected int maxIter;
	protected int radius;
	protected Double step;
	protected Integer screenSize;

	public Fractal(Complex p1, Complex p2, double step, int max, int r) {
		this.p1 = p1;
		this.p2 = p2;
		this.maxIter = max;
		this.radius = r;
		this.step = step;
		this.screenSize = (int)(Math.abs(p2.getReal() - p1.getReal()) / step);
	}

	public Fractal(Complex p1, Complex p2, int screenSize, int max, int r) {
		this.p1 = p1;
		this.p2 = p2;
		this.maxIter = max;
		this.radius = r;
		this.screenSize = screenSize;
		this.step = Math.abs(p2.getReal() - p1.getReal()) / screenSize;
	}

	public int getMaxIter() {
		return maxIter;
	}

	public Complex getRectStart() {
		return p1;
	}

	public Complex getRectEnd() {
		return p2;
	}

	public double getStep() {
		return step;
	}

	public int getScreenSize() {
		return screenSize;
	}

	public abstract int escapeOrbit(Complex z);
	public abstract <T extends Fractal> T zoomed(double factor);
	public abstract String getName();
}
