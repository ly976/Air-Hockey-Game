package strategy;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import player.Robot;
import stadium.Coordinate;

/**
 * This class is used to test the Strategist. It simply extends the Strategist
 * (behaves same as the Strategist) but stores the images into hard drive so
 * that it's easy for human to compare the images stored with the behavior of
 * robots and ball in real world
 * 
 * @see strategy.Strategist
 */
public class StrategistTest extends Strategist {
	// counter used to count the number of photos stored
	private int counter = 0;

	@Override
	public String process(List<BufferedImage> images) {
		Robot robot = robots.get(0);
		// store each photo into hard drive
		for (BufferedImage image : images) {
			imgProcessor.update(image, targets);
			// store the image with ball's location as file name
			debug(image, ball.getPosition());
			// store the image with robot head location as file name
			debug(image, robot.getHead().getPosition());
			// store the image with robot tail location as file name
			debug(image, robot.getPosition());
		}

		//return the result of Strategist to behave as same
		return super.process(images);
	}

	/**
	 * This function will store the image into a file named with
	 * "temp/counter-(x,y).png", where (x,y) is derived from a coordinate
	 * 
	 * @param image
	 *            - A <code>BufferedImage</code> object
	 * @param coor
	 *            - A <code>Coordinate</code> object
	 * 
	 */
	private void debug(BufferedImage image, Coordinate coor) {
		double dx = coor.getX();
		double dy = coor.getY();
		String x = Double.isNaN(dx) ? "NaN" : "" + ((int) dx);
		String y = Double.isNaN(dy) ? "NaN" : "" + ((int) dy);
		String name = "temp/" + counter + "-(" + x + "_" + y + ").png";
		try {
			ImageIO.write(image, "PNG", new File(name));
			System.out.println("Image: " + name + " is saved");
		} catch (IOException e) {
			e.printStackTrace();
		}
		counter++;
	}
}