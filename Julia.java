import java.awt.Color;
import java.awt.image.BufferedImage;
import org.apache.commons.math3.complex.Complex;
import java.lang.Math;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;


public class Julia implements Fractal {
	private BufferedImage image;
	private int width;
	private int height;

	private final Complex constant;
	private int maxIter;
	private int radius;

	public Julia(int w, int h, int max, double real, double imaginary, int r) {
		this.width = w;
		this.height = h;
		this.image = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);

		this.constant = new Complex(real, imaginary);
		this.maxIter = max;
		this.radius = r;
	}

	public void drawFractal() {
			for (int x = 0; x < this.width; x++) {
				for (int y = 0; y < this.height; y++) {

					Complex z = new Complex(1.2 * (x - width / 2) / (width / 2), 1.69 * (y-height / 2) / (height / 2));
					int i;

					for (i = 0; i < this.maxIter; i++) {
						Complex zOfF = z.pow(2.0).add(constant);

						if (zOfF.abs() > this.radius)
							break;
					}

					float s = 0.8f;
					float b = i < maxIter ? 1f : 0;
	        float h = (float) (i * x * y);
	        Color color = Color.getHSBColor(h, s, b);
	        image.setRGB(x, y, color.getRGB());
				}
			}

			try {
				ImageIO.write(image, "PNG", new File("julia.png"));
			}
			catch (IOException e) {
					e.printStackTrace();
			}
	}

	public BufferedImage getImage()
	{
		return(this.image);
	}
}
