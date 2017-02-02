package mathTools;

import java.util.List;

import stadium.Coordinate;

/**
 * The static tool to do linear regression
 */
public class LinearRegression {

	/**
	 * Calculate the trend line
	 * 
	 * @param coordinates
	 *            a list of coordinates
	 * @return a <code>LinearLine</code> object after doing linear regression
	 * 
	 * @see mathTools.LinearLine
	 */
	public static LinearLine calculate(Iterable<Coordinate> coordinates) {

		double xSum = 0;
		double ySum = 0;
		double xySum = 0;
		double xxSum = 0;
		int counter = 0;
		for (Coordinate coordinate : coordinates) {

			double x = coordinate.getX();
			double y = coordinate.getY();
			xSum += x;
			ySum += y;
			xySum += x * y;
			xxSum += x * x;
			counter++;
		}

		double xMean = xSum / counter;
		double yMean = ySum / counter;
		double xyMean = xySum / counter;
		double xxMean = xxSum / counter;

		double k = (xyMean - xMean * yMean) / (xxMean - xMean * xMean);
		double b = yMean - k * xMean;

		return new LinearLine(k, b);
	}

	/**
	 * 
	 * @param points
	 *            a list of coordinates
	 * @return the variance of given coordinates
	 */
	public static double getVariance(List<Coordinate> points) {
		double xMean = 0;
		double yMean = 0;
		int counter = 0;
		for (Coordinate coordinate : points) {
			if (!coordinate.isNaN()) {
				counter++;
				xMean += coordinate.getX();
				yMean += coordinate.getY();
			}
		}

		xMean /= counter;
		yMean /= counter;

		double xVariance = 0;
		double yVariance = 0;
		counter = 0;
		for (Coordinate coordinate : points) {
			if (!coordinate.isNaN()) {
				double xDiff = coordinate.getX() - xMean;
				double yDiff = coordinate.getY() - yMean;
				xVariance += xDiff * xDiff;
				yVariance += yDiff * yDiff;
				counter++;
			}
		}
		xVariance /= counter;
		yVariance /= counter;

		return (xVariance * 3 + yVariance * 7) / 10;
	}
}
