package imageProcessing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * The object of this class is used to locate the position of <i>Target</i>s
 * objects in a given image
 *
 * @see imageProcessing.Target
 */
public class ImageProcessor {

	// Singleton pattern
	private static ImageProcessor instance;

	private ImageProcessor() {
	};

	public synchronized static ImageProcessor getInstance() {
		if (instance == null) {
			instance = new ImageProcessor();
		}
		return instance;
	}

	/**
	 * This method will update the positions of all given targets into the
	 * positions of them appear in the given image
	 * 
	 * @param img
	 *            given image
	 * @param targets
	 *            a list of targets
	 * @return the given list of targets
	 * 
	 * @see imageProcessing.Target
	 */
	public List<Target> update(File img, List<Target> targets) {
		try {
			BufferedImage imgBuffer = ImageIO.read(img);
			return update(imgBuffer, targets);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Given image file (" + img.getName()
					+ ") is wrong");
			return null;
		}
	}

	/**
	 * 
	 * This method will update the positions of all given targets into the
	 * positions of them appear in the given image
	 * 
	 * @param imgBuffer
	 *            given image
	 * @param targets
	 *            a list of targets
	 * @return the given list of targets
	 * 
	 * @see imageProcessing.Target
	 */
	public List<Target> update(BufferedImage imgBuffer, List<Target> targets) {

		int size = targets.size();
		int[] xs = new int[size];
		int[] ys = new int[size];
		int[] counters = new int[size];

		for (int i = 0; i < imgBuffer.getWidth(); i++) {
			for (int j = 0; j < imgBuffer.getHeight(); j++) {
				int rgb = imgBuffer.getRGB(i, j);
				int red = (rgb >> 16) & 0x000000FF;
				int green = (rgb >> 8) & 0x000000FF;
				int blue = (rgb) & 0x000000FF;

				float[] hsv = Color.RGBtoHSB(red, green, blue, null);
				float h = hsv[0];
				float s = hsv[1];

				int k = 0;
				for (Target target : targets) {
					if (target.isTarget(h, s)) {
						xs[k] += i;
						ys[k] += j;
						counters[k]++;
					}
					k++;
				}
			}
		}

		int k = 0;
		for (Target target : targets) {
			double xk = (double) xs[k] / counters[k];
			double yk = (double) ys[k] / counters[k];
			target.setPosition(yk, xk);
			k++;
		}

		return targets;
	}
}
