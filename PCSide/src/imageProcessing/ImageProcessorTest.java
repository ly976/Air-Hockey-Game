package imageProcessing;

import java.io.File;
import java.util.ArrayList;

/**
 * This class is used to test if image processor works well
 */
public class ImageProcessorTest {

	public static void main(String[] args) {

		File trainningSet = new File(args[0]);
		File image = new File(args[1]);

		Target target = new Target(trainningSet);
		ArrayList<Target> targets = new ArrayList<>();
		targets.add(target);

		ImageProcessor imgp = ImageProcessor.getInstance();
		imgp.update(image, targets);

		System.out.println(target.getPosition());
	}

}
