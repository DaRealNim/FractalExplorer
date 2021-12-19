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
	protected BufferedImage image;
	protected int width;
	protected int height;

	protected int maxIter;
	protected int radius;

	public Fractal(int w, int h, int max, int r) {
		this.width = w;
		this.height = h;
		this.image = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);

		this.maxIter = max;
		this.radius = r;
	}

	public void drawFractal() {
			List<Pair<Integer, Integer>> xyPairs = new ArrayList<>();

			for (int x = 0; x < this.width; x++) {
				for (int y = 0; y < this.height; y++) {
					xyPairs.add(new Pair(x, y));
				}
			}

			xyPairs.parallelStream().forEach(p -> colorImage(p));
	}

	public void colorImage(Pair<Integer, Integer> p)
	{
		int iterations = escapeOrbit(p);

		if (iterations >= maxIter)
			image.setRGB(p.getKey(), p.getValue(), 0);
		else
		{
			float hue = (float) iterations / maxIter;

			/*
			//This parts just forbids the color red, remove it for performance.
			float codedHue = (float) (hue - Math.floor(hue)) * 360;
			if (codedHue < 30) //if it's red
				hue += 0.78f; //make it purple
				*/
			Color c = new Color(Color.HSBtoRGB(hue, 0.8f, 1f));
			image.setRGB(p.getKey(), p.getValue(), c.getRGB());
		}
	}

	public BufferedImage getImage()
	{
		return(this.image);
	}

	public void saveImage();
	public int escapteOrbit(Pair p);
}
