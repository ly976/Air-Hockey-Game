package gui;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.*;

/**
 * This class is used for real-world testing, which displays the position of
 * robots/ball in a GUI
 */
public class RealWorldTestTool extends JFrame {

	private static final long serialVersionUID = -4915968435857871056L;
	public static final int GAME_WIDTH = 660;
	public static final int GAME_HEIGTH = 522;
	public int x = 200;
	public int y = 250;
	public double originalFactor = 1.5;
	public double constant = 100;

	public void setPosition(int x, int y) {

		this.x = x;
		this.y = y;

	}

	public void setPath(double k, double b) {

		this.originalFactor = k;
		this.constant = b;
	}

	// Refreshes the screen graphics
	public void update(Graphics g) {
		paint(g);
	}

	/**
	 * This is the GUI generator that will display the scores and timer on the
	 * screen
	 */
	public void paint(Graphics g) {
		super.paint(g);

		g.fillRect(325, 32, 10, 480);// middle
		g.fillRect(0, 22, 660, 10);// top
		g.fillRect(0, 512, 660, 10);// bottom
		g.fillRect(0, 22, 10, 500);// left
		g.fillRect(650, 22, 10, 500);// right

		Graphics2D graphics1 = (Graphics2D) g;
		Graphics2D graphics2 = (Graphics2D) g;
		Graphics2D graphics3 = (Graphics2D) g;

		BasicStroke bs = new BasicStroke(3, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_BEVEL);

		Rectangle2D rectangle2 = new Rectangle2D.Float(100, 200, 70, 70);
		graphics2.setStroke(bs);
		graphics2.setColor(Color.RED);
		graphics2.draw(rectangle2);

		graphics3.setPaint(Color.BLUE);
		graphics3.setStroke(bs);

		Ellipse2D ball = new Ellipse2D.Double(400, 220, 25, 25);
		graphics3.draw(ball);

		Polygon p = new Polygon();
		for (int x = -500; x <= 500; x++) {
			p.addPoint((int) (originalFactor * x + constant + 10), x + 32);
		}
		g.setColor(Color.RED);
		g.drawPolyline(p.xpoints, p.ypoints, p.npoints);

		graphics1.setStroke(new BasicStroke(7, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_BEVEL));
		graphics1.setColor(Color.GREEN);
		Ellipse2D point = new Ellipse2D.Double(220, 105, 7, 7);
		graphics1.draw(point);
	}

	/**
	 * Setting up the properties of the GUI and displays it on the screen
	 */
	public void LaunchFrame() {
		this.setBounds(0, 0, GAME_WIDTH, GAME_HEIGTH);
		this.setResizable(false);
		this.setVisible(true);

		new Thread(new PaintThread()).start();
	}

	/**
	 * The main method that will initialise all the GUI features
	 */
	public static void main(String[] args) {
		RealWorldTestTool pt = new RealWorldTestTool();
		pt.LaunchFrame();
		pt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Every 250 milliseconds this method will run the thread that updates the
	 * GUI
	 */
	private class PaintThread implements Runnable {

		public void run() {
			while (true) {
				repaint();
				try {
					Thread.sleep(250);
				} catch (InterruptedException e) {
				}
			}
		}
	}
}
