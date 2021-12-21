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

	public String getName() {
		return "julia";
	}

}
