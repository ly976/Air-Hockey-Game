package strategy;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * The interface for any strategist
 */
public interface IStrategist {
	// Receives a list of images and return instruction for robot
	public String process(List<BufferedImage> images);
}
