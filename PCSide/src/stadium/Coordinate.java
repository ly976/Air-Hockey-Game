package stadium;

/**
 * To represent coordinate in pixel system
 */
public class Coordinate {
	private double x;
	private double y;

	public Coordinate(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Coordinate(Coordinate other) {
		this.x = other.x;
		this.y = other.y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	/**
	 * This method is to account for invalid instructions being calculated from
	 * the image processing
	 * 
	 * @return - This will return true if the value taken from image processing
	 *         is not a number
	 */
	public boolean isNaN() {
		boolean b1 = Double.isNaN(x);
		boolean b2 = Double.isNaN(y);

		return b1 || b2;
	}

	/**
	 * This method will compare two different coordinates and return the
	 * difference between the two
	 * 
	 * @param otherCoordinate
	 *            - the other set of coordinates that will be compared to the
	 *            objects coordinates
	 * @return - the distance between the two coordinates
	 */
	public double getDistance(Coordinate otherCoordinate) {
		double xDiff = this.x - otherCoordinate.getX();
		double yDiff = this.y - otherCoordinate.getY();

		return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
	}

	/**
	 * Gets the middle coordinates between two points
	 * 
	 * @param other
	 *            - the second set of coordinates that will be compared to the
	 *            objects' coordinates
	 * @return - The coordinates in the centre of the two coordinates used
	 */
	public Coordinate getMiddle(Coordinate other) {
		return new Coordinate((x + other.x) / 2, (y + other.y) / 2);
	}

	/**
	 * The string representation of a coordinate
	 */
	public String toString() {
		String result = "( " + x + ", " + y + " )";
		return result;
	}
}