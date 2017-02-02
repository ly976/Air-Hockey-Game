package strategy;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

//The third-party library is used here
import com.github.sarxos.webcam.Webcam;

/**
 * To control the WebCam to take photos
 */
public class ImageTaker {

	// number of photos to take each time
	public static final int PIC_NUM = 5;
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;

	private Webcam webcam;
	private Dimension dimension;
	// If stored photos into hard drive
	private boolean saveMode;
	// The number of photos taken
	private int counter;

	public ImageTaker() {
		this(false);
	}

	/**
	 * @param saveMode
	 *            true stored the images into hard drive; false otherwise
	 */
	public ImageTaker(boolean saveMode) {
		this.counter = 0;
		this.saveMode = saveMode;
		this.webcam = Webcam.getDefault();
		this.dimension = new Dimension(WIDTH, HEIGHT);
		open();
	}

	public void setWebcam(Webcam webcam) {
		this.webcam = webcam;
		open();
	}

	public void setWebcam(int portNumber) {
		this.webcam = Webcam.getWebcams().get(portNumber);
		open();
	}

	public void open() {
		this.webcam.setViewSize(dimension);
		this.webcam.open();
	}

	public void setSaveMode(boolean saveMode) {
		this.saveMode = saveMode;
	}

	public BufferedImage getBufferImg() {
		return webcam.getImage();
	}

	public void close() {
		webcam.close();
	}

	/**
	 * To take a list of photos
	 * 
	 * @return photos taken
	 */
	public ArrayList<BufferedImage> takePhotos() {
		ArrayList<BufferedImage> images = new ArrayList<BufferedImage>(PIC_NUM);
		for (int i = 0; i < PIC_NUM; i++) {
			BufferedImage image = getBufferImg();
			images.add(image);
			counter++;
			if (saveMode) {
				try {
					ImageIO.write(image, "PNG", new File("temp/temp" + counter
							+ ".png"));
					System.out.println("pic-" + counter + " is saved");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return images;
	}

	/**
	 * To take a single photo and store it into hard drive
	 * 
	 * @param fileName
	 *            the name for photo
	 * @return the file of photo stored on hard drive
	 */
	public File storePhoto(String fileName) {
		File file = new File(fileName);
		try {
			ImageIO.write(getBufferImg(), "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
}
