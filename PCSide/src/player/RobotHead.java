package player;

import java.io.File;

import imageProcessing.Target;

/**
* To represent the head of robot
*
* @see player.Target
*/
public class RobotHead extends Target{

	private static final String DEFAULT_TRAINNING_SET = "robotHead.png";

	public RobotHead() {
		this(new File(DEFAULT_TRAINNING_SET));
	}
	
	public RobotHead(File trainningSet) {
		super(trainningSet);
	}
}
