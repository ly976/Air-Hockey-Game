package strategy;

import gui.ScoreBoard;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import connection.Sender;
import stadium.Stadium;

/**
 * The main entry of whole game
 */
public class Main {

	public static void main(String[] args) {

		//Set up the score board
		ScoreBoard scoreBoard = ScoreBoard.getInstance();
		scoreBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		scoreBoard.setVisible(true);

		//Set up the stadium
		Stadium stadium = Stadium.getInstance();
		stadium.setWidthInches(80.5);
		stadium.setHeightInches(64.1);
		stadium.setWidthPixel(ImageTaker.WIDTH);
		stadium.setHeightPixel(ImageTaker.HEIGHT);

		//Set up of Webcam
		final ImageTaker webcam = new ImageTaker(false);
		//Set up of Strategist
		final Strategist strategist = new Strategist();
		//Set up the connection between PC and robot
		final Sender sender = new Sender();
		
		// Execute at fixed rate
		Timer timer = new Timer();
		int delay = 0;
		int period = 250;
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				String message = strategist.process(webcam.takePhotos());
				//send instruction to robot
				sender.sendMessage(message);
			}
		}, delay, period);
	}

}
