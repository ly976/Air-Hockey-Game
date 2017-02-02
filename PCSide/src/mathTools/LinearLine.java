package mathTools;

import stadium.Coordinate;

/**
 * The representation of function y = kx + b
 */
public class LinearLine {
	private double k;
	private double b;

	public LinearLine(double k, double b) {
		this.k = k;
		this.b = b;
	}

	public LinearLine(Iterable<Coordinate> coordinates) {
		this(LinearRegression.calculate(coordinates));
	}

	public LinearLine(LinearLine otherLine) {
		this.k = otherLine.getK();
		this.b = otherLine.getB();
	}

	public double getK() {
		return k;
	}

	public double getB() {
		return b;
	}

	public double getY(double x) {
		return k * x + b;
	}

	public double getX(double y) {
		return (y - b) / k;
	}

	/**
	 * @param otherLine
	 *            another line
	 * @return the intersection position between lines
	 */
	public Coordinate getIntersection(LinearLine otherLine) {
		double x = (otherLine.getB() - b) / (k - otherLine.getK());
		double y = getY(x);

		return new Coordinate(x, y);
	}

	/**
	 * @param point
	 *            a coordinate
	 * @return the perpendicular intersection position between line and
	 *         coordinate
	 */
	public Coordinate getIntersection(Coordinate point) {
		double x0 = point.getX();
		double y0 = point.getY();

		double x = (k * y0 + x0 - k * b) / (k * k + 1);
		double y = (b + k * k * y0 + k * x0) / (k * k + 1);
		return new Coordinate(x, y);
	}

	/**
	 * To check if is a valid function
	 * 
	 * @return true if it is a valid function; false otherwise
	 */
	public boolean isFunction() {
		boolean validK = !Double.isNaN(k);
		boolean validB = !Double.isNaN(b);

		return validK && validB;
	}

	/**
	 * The representation in string
	 */
	@Override
	public String toString() {
		return "y = " + k + " * x + " + b;
	}
}
