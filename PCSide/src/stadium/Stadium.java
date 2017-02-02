package stadium;

/**
 * 
 * To model/represent the real-world stadium; stores the basic information of
 * stadium used and provides conversion between pixels and inches
 * 
 */
public class Stadium {
	// Singleton pattern
	private static Stadium instance;
	private double widthPixel;
	private double heightPixel;
	private double widthInches;
	private double heightInches;

	private Stadium() {
	}

	public synchronized static Stadium getInstance() {
		if (instance == null) {
			instance = new Stadium();
		}
		return instance;
	}

	/**
	 * Converts the pixel distance into inches by scaling
	 */
	public double toRealDistance(double pixelDistance) {
		return pixelDistance * widthInches / widthPixel;
	}

	/**
	 * @return the middle of whole stadium in pixels
	 */
	public double getMiddlePixel() {
		return widthPixel / 2;
	}

	public void setHeightInches(double heightInches) {
		this.heightInches = heightInches;
	}

	public void setHeightPixel(double heightPixel) {
		this.heightPixel = heightPixel;
	}

	public void setWidthInches(double widthInches) {
		this.widthInches = widthInches;
	}

	public void setWidthPixel(double widthPixel) {
		this.widthPixel = widthPixel;
	}

	public double getHeightInches() {
		return heightInches;
	}

	public double getHeightPixel() {
		return heightPixel;
	}

	public double getWidthInches() {
		return widthInches;
	}

	public double getWidthPixel() {
		return widthPixel;
	}

}
