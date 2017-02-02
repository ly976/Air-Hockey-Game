package imageProcessing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import stadium.Coordinate;

/**
 * To represent the object which can be tracked/located by
 * <code>ImageProccessor</code> object in a given image
 * @see imageProcessing.ImageProccessor
 */
public class Target {

	// The position of target in given image
	protected Coordinate position;
	// The color range of target
	protected ColorRange colorRange;

	public Target(File trainningSet) {
		setColorRange(trainningSet);
		this.position = new Coordinate(0, 0);
	}

	public Coordinate getPosition() {
		return position;
	}

	public Target setPosition(double x, double y) {
		this.position = new Coordinate(x, y);
		return this;
	}
	
	public Target setColorRange(File trainningSet) {
		try {
			// To reset recorders
			this.colorRange = new ColorRange();

			BufferedImage trainningFile = ImageIO.read(trainningSet);
			for (int i = 0; i < trainningFile.getWidth(); i++) {
				for (int j = 0; j < trainningFile.getHeight(); j++) {
					int rgb = trainningFile.getRGB(i, j);
					int red = (rgb >> 16) & 0x000000FF;
					int green = (rgb >> 8) & 0x000000FF;
					int blue = (rgb) & 0x000000FF;

					float[] hsv = Color.RGBtoHSB(red, green, blue, null);
					float h = hsv[0];
					float s = hsv[1];

					colorRange.updateRange(h, s);
				}
			}

		} catch (IOException e) {
			System.err.println("Given trainning-set image file ("
					+ trainningSet.getName() + ") is wrong");
		}

		return this;
	}

	/**
	 * To check if given color matches the target's color range
	 * 
	 * @param h
	 *            the value of h in HSV color
	 * @param s
	 *            the value of s in HSV color
	 * @return true if the given color matches the target; false otherwise
	 */
	public boolean isTarget(float h, float s) {
		return colorRange.isInRange(h, s);
	}


}
