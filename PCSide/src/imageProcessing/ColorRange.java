package imageProcessing;

/**
 * The object of this class is used to represent the range of color in HSV color
 * space
 * @see imageProcessing.ImageProcessor
 */
public class ColorRange {
	private double maxH;
	private double minH;
	private double maxS;
	private double minS;

	public ColorRange() {
		this.maxH = -1;
		this.minH = 2;
		this.maxS = -1;
		this.minS = 2;
	}

	public boolean isInRange(float h, float s) {
		boolean isHInRange = h >= minH && h <= maxH;
		boolean isSInRange = s >= minS && s <= maxS;
		return isHInRange && isSInRange;
	}

	public void updateRange(float h, float s) {
		updateH(h);
		updateS(s);
	}

	public void updateH(float h) {
		if (maxH < h) {
			this.maxH = h;
		}

		if (minH > h) {
			this.minH = h;
		}

	}

	public void updateS(float s) {
		if (maxS < s) {
			this.maxS = s;
		}

		if (minS > s) {
			this.minS = s;
		}
	}
}
