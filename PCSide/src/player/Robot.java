package player;

import imageProcessing.Target;

import java.io.File;

import stadium.Coordinate;

/**
 * The object of this class is used to represent the robot which stores
 * information of the robot
 * 
 * NOTE: This class extends the <i>Target</i> Interface, which means it can be
 * located by image processing
 * 
 * @see player.Target
 */
public class Robot extends Target {

	// The bound used to judge if ball is collided
	private static final int DISTANCE_BOUND_PIXEL = 5;
	private static final String DEFAULT_TRAINNING_SET = "robot.png";

	// The head of robot
	private RobotHead head;

	public Robot() {
		this(new RobotHead());
	}

	public Robot(RobotHead head) {
		this(new File(DEFAULT_TRAINNING_SET), head);
	}

	public Robot(File trainningSet, RobotHead head) {
		super(trainningSet);
		this.head = head;
	}

	/**
	 * To get the position of ball (the middle between head and tail)
	 */
	@Override
	public Coordinate getPosition() {
		Coordinate headPos = head.getPosition();
		Coordinate tailPos = position;
		return headPos.getMiddle(tailPos);
	}

	public Coordinate getTailPosition() {
		return position;
	}

	public RobotHead getHead() {
		return head;
	}

	/**
	 * 
	 * @param destination
	 *            the given position for robot to reach
	 * @return the angle (degree) between robot and given position; <br>
	 *         positive angle means clockwise <br>
	 *         negative angle means anti-clockwise
	 */
	public double getAngleToRotate(Coordinate destination) {

		double result = 0;

		double x1 = head.getPosition().getX() - position.getX();
		double y1 = head.getPosition().getY() - position.getY();
		double x2 = destination.getX() - position.getX();
		double y2 = destination.getY() - position.getY();

		double dot = x1 * x2 + y1 * y2;
		double det = x1 * y2 - y1 * x2;
		result = Math.atan2(det, dot);
		result = Math.toDegrees(result);

		return result;
	}

	/**
	 * To check if robot collides with given position
	 * 
	 * @param position
	 *            the given position
	 * @return true if robot collides; false otherwise
	 */
	public boolean isCollidedWith(Coordinate position) {
		double actualRange = position.getDistance(position);
		System.out.println("actualRange for collision = " + actualRange);
		return actualRange < DISTANCE_BOUND_PIXEL;
	}

}
