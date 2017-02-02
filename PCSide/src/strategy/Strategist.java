package strategy;

import gui.ScoreBoard;
import imageProcessing.ImageProcessor;
import imageProcessing.Target;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import mathTools.LinearLine;
import connection.Sender;
import player.Ball;
import player.Robot;
import stadium.Coordinate;
import stadium.Stadium;

/**
 * The Strategist receives a list of images and use <code>ImgProcessor</code> to
 * locate the positions of the robots and the ball. Then, Strategist will make
 * strategy for robot and return a formatted string which represents the
 * instruction for robot to perform
 * 
 * @see strategy.ImageTaker
 * @see imageProcessing.ImageProcessor
 * @see connection.Sender
 */
public class Strategist implements IStrategist {

	private static final int NO_ONE_WIN = 0;
	private static final int AI_ROBOT = 0;
	private static final int TRAVEL_UNIT = 3;
	private static final double ROTATION_RANGE = 10;
	private static final int ROTATION_DELAY = 2;
	
	private static final int ROTATE = 0;
	private static final int MOVE = 1;
	private static final int CELEBRATE = 2;

	private Stadium stadium;
	protected ImageProcessor imgProcessor;

	protected List<Robot> robots;
	protected Ball ball;
	protected ArrayList<Target> targets;

	private int lastAction = -1;
	private boolean isScoreCounted = false;
	private int rotateDelayCounter = ROTATION_DELAY;

	public Strategist(List<Robot> robots, Ball ball) {
		this.stadium = Stadium.getInstance();
		this.imgProcessor = ImageProcessor.getInstance();

		this.robots = robots;
		this.ball = ball;

		setTargets();
	}

	public Strategist() {
		this.stadium = Stadium.getInstance();
		this.imgProcessor = ImageProcessor.getInstance();

		this.robots = new ArrayList<Robot>();
		robots.add(new Robot());
		this.ball = new Ball();

		setTargets();
	}

	private void setTargets() {
		this.targets = new ArrayList<Target>();
		for (Robot robot : robots) {
			targets.add(robot);
			targets.add(robot.getHead());
		}

		targets.add(ball);
	}

	/**
	 * To make strategy for robot
	 * 
	 * @return an instruction for robot (a formated string)
	 */
	public String makeStrategy() {
		// Get the middle value of stadium and location of ball
		double midBoundary = stadium.getMiddlePixel();
		Coordinate ballPosition = ball.getPosition();
		// The initial position of robot at beginning of game
		Coordinate initialPosition = new Coordinate(
				stadium.getHeightPixel() / 2, midBoundary / 2);

		// Check if ball reach the goal, if ball reaches goal, then update score
		// and ask robot to celebrate
		int winner = ball.isReachGoal(stadium);
		if (winner != NO_ONE_WIN && !isScoreCounted) {
			isScoreCounted = true;
			ScoreBoard board = ScoreBoard.getInstance();
			board.updateScore(winner);
			return Sender.createMessage(CELEBRATE, 0, 0);
		} else {
			isScoreCounted = false;
		}

		// If ball is out of stadium then move back to initial position
		if (ball.isOutOfStadium()) {
			return moveTo(initialPosition);
		}

		if (ballPosition.getY() < midBoundary) {
			// If ball is in AI's field

			if (ball.isStatic()) {
				// If ball is static
				return staticStrategy();
			} else {
				return activeStrategy();
			}
		} else {
			// If ball is in opponent's filed
			if (ball.isIncoming()) {
				// If ball is incoming toward AI robot then predict the possible
				// position ball might reach and intercept it
				return activeStrategy();
			} else {
				// move to the initial position (center of field) to wait
				return moveTo(initialPosition);
			}
		}
	}

	private String moveTo(Coordinate destination) {
		// If robot is still rotating then do nothing
		if (rotateDelayCounter < ROTATION_DELAY && lastAction == 0) {
			rotateDelayCounter++;
			return Sender.DO_NOTHING;
		}

		// Initialization
		int action = 1;
		double rotateAngle = 0;
		double distance = 0;
		// Get AI robot
		Robot robot = robots.get(AI_ROBOT);
		// The angle between robot and destination position
		double angle = robot.getAngleToRotate(destination);
		// The distance from robot's head to destination
		Coordinate robotPosition = robot.getHead().getPosition();
		double distancePixel = robotPosition.getDistance(destination);
		double distanceInch = stadium.toRealDistance(distancePixel);

		// If angle between robot and destination is larger than range, then do
		// rotation Else move ahead to the destination
		if (Math.abs(angle) > ROTATION_RANGE) {
			// Rotate

			// Set the delay counter
			rotateDelayCounter = 0;
			action = ROTATE;
			distance = 0;
			rotateAngle = angle;
		} else {
			// Move ahead

			if (distanceInch > TRAVEL_UNIT) {
				action = MOVE;
				distance = TRAVEL_UNIT;
			} else {
				action = MOVE;
				distance = distanceInch;
			}
		}

		lastAction = action;
		return Sender.createMessage(action, rotateAngle, distance);
	}

	/**
	 * the strategy used for when ball is static, which asks robot to move to
	 * the ball directly
	 */
	private String staticStrategy() {
		// Move to the ball
		Coordinate ballPosition = ball.getPosition();
		return moveTo(ballPosition);
	}

	/**
	 * the strategy used for when ball is actively moving, which predicts the
	 * possible possible position that ball might appear by analyzing its
	 * movement path and asks robot to intercept the ball
	 */
	private String activeStrategy() {
		Robot robot = robots.get(AI_ROBOT);
		Coordinate robotPosition = robot.getPosition();
		LinearLine movementPath = ball.getMovementPath();
		Coordinate interceptPosition = movementPath
				.getIntersection(robotPosition);
		
		return moveTo(interceptPosition);
	}


	/**
	 * 
	 * This function receives a list of images, does
	 * <code>Image Processing</code> and make strategy for robot
	 * 
	 * @param images
	 *            - a list of images taken by the <code>ImageTaker</code>
	 * @return instruction - a formatted string which follows <i>Instruction
	 *         Protocol</i> and can be sent by <code>Sender</code> directly
	 * 
	 * @see strategy.ImageTaker
	 * @see imageProcessing.ImageProcessor
	 * @see connection.Sender
	 */
	public String process(List<BufferedImage> images) {
		// Update the locations of ball and robots
		ArrayList<Coordinate> ballPath = new ArrayList<>(ImageTaker.PIC_NUM);
		for (BufferedImage image : images) {
			imgProcessor.update(image, targets);
			ballPath.add(ball.getPosition());
		}
		ball.setPositions(ballPath);

		// Make the strategy
		return makeStrategy();
	}
}
