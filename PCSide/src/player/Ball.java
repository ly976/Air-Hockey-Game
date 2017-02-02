package player;

import imageProcessing.Target;

import java.io.File;
import java.util.List;

import mathTools.LinearLine;
import mathTools.LinearRegression;
import stadium.Coordinate;
import stadium.Stadium;

/**
 * The object of this class is used to represent the ball which stores
 * information of the ball
 * 
 * NOTE: This class extends the <i>Target</i> Interface, which means it can be
 * located by image processing
 * 
 * @see player.Target
 */
public class Ball extends Target {

	private static final int GOAL_BOUND = 30;
	private static final String DEFAULT_TRAINNING_SET = "ball.png";
	// The bound to judge if ball is static
	private static final double VARIANCE_BOUND = 1;
	// All positions of ball at different time in one second
	private List<Coordinate> positions;
	// The bias is used to reduce error
	private double bias;
	private Coordinate lastPosition;

	public Ball() {
		this(new File(DEFAULT_TRAINNING_SET));
	}

	public Ball(File trainningSet) {
		this(trainningSet, 5);
	}

	public Ball(File trainningSet, double bias) {
		super(trainningSet);
		this.bias = bias;
		this.lastPosition = new Coordinate(0, 0);
	}

	public void setPositions(List<Coordinate> path) {
		this.positions = path;
	}

	public void setBias(double bias) {
		this.bias = bias;
	}
	
	/**
	 * To check if ball is static or actively moving
	 * 
	 * @return true ball is static; false otherwise
	 */
	public boolean isStatic() {
		if (positions == null) {
			return false;
		}

		// Filter some potential error positions
		int counter = 0;
		for (Coordinate c : positions) {
			if (c.isNaN()) {
				counter++;
			}
		}

		if (counter > (positions.size() / 2 + 1)) {
			return false;
		}

		double actualVariance = LinearRegression.getVariance(positions);
		return actualVariance < VARIANCE_BOUND;
	}

	/**
	 * Get the possible movment path of ball
	 * 
	 * @return an <code>LinearLine</code> object (y=kx+b) to represent the
	 *         movement path ; <b>null</b> if no movement path is not found
	 * 
	 * @see mathTools.LinearLine
	 */
	public LinearLine getMovementPath() {
		if (isStatic()) {
			return null;
		}
		return LinearRegression.calculate(positions);
	}

	/**
	 * To calculate the direction of ball
	 * 
	 * @return true if ball is incoming toward ball; false otherwise
	 */
	public boolean isIncoming() {
		// Get first and last coordinates
		Coordinate p0 = positions.get(0);
		Coordinate p4 = positions.get(positions.size() - 1);
		// Get the movement path of ball
		LinearLine movementPath = getMovementPath();
		double k = movementPath.getK();

		if (k < 0) {
			if (p0.getX() < p4.getX()) {
				return true;
			} else {
				return false;
			}

		} else {

			if (p0.getX() < p4.getX()) {
				return false;
			} else {
				return true;
			}
		}
	}

	/**
	 * To calculate the direction of ball
	 * 
	 * @return false if ball is incoming toward ball; false otherwise
	 */
	public boolean isGoingOut() {
		return !isIncoming();
	}

	/**
	 * To check if ball reaches the goal
	 * 
	 * @param stadium
	 *            the stadium
	 * @return 
	 *         0 - not reaches goal <br>
	 *         1 - reaches player1's goal (default is AI robot) <br>
	 *         2 - reaches player2's goal <br>
	 *         otherwise - exception
	 * 
	 * @see stadium.Stadium
	 */
	public int isReachGoal(Stadium stadium) {
		double currentY = position.getY();
		for (int i = positions.size() - 1; i >= 0; i--) {
			if (!positions.get(i).isNaN()) {
				currentY = positions.get(i).getY();
				break;
			}
		}

		if (currentY < GOAL_BOUND) {
			return 1;
		} else if (currentY > stadium.getWidthPixel() - GOAL_BOUND) {
			return 2;
		} else {
			return 0;
		}
	}

	/**
	 * To check if ball is out of stadium
	 * 
	 * @return true if ball is out of stadium; false otherwise
	 */
	public boolean isOutOfStadium() {
		int counter = 0;
		for (Coordinate coordinate : positions) {
			if (coordinate.isNaN()) {
				counter++;
			}
		}
		return counter == positions.size();
	}

	/**
	 * get last position of ball
	 * @return the last position of ball
	 */
	public Coordinate getLastPosition() {
		return lastPosition;
	}

	/**
	 * To get the biased position of ball
	 * 
	 * @return the biased position of ball
	 */
	public Coordinate getBiasPosition() {
		double x = position.getX();
		double y = position.getY();
		double biasY = y <= bias ? 0 : y - bias;
		return new Coordinate(x, biasY);
	}
}
