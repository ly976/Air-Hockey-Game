package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This class is used to draw a GUI out to display the scores of the game
 */
public class ScoreBoard extends JFrame implements IScoreBoard {

	private static final long serialVersionUID = -6781299214335055842L;
	// Singleton pattern
	private static ScoreBoard instance;
	private Dialog dialog;
	private String score1 = "0";
	private String score2 = "0";

	/**
	 * @return - scoreBoard containing the current game score. if a scoreBoard
	 *         has not yet been created, this method will create one
	 */
	public static ScoreBoard getInstance() {
		if (instance == null) {
			instance = new ScoreBoard();
		}
		return instance;
	}

	/**
	 * This method will increment the score after a successful goal for the
	 * appropriate player
	 * 
	 * @param player
	 *            - The player who has just scored
	 */
	@Override
	public void updateScore(int player) {
		if (player == 1) {
			int score = Integer.parseInt(score1);
			score++;
			score1 = score + "";
		}

		if (player == 2) {
			int score = Integer.parseInt(score2);
			score++;
			score2 = score + "";
		}

	}

	/**
	 * A primary constructor for the score board
	 */
	private ScoreBoard() {
		setTitle("Score Board");
		setSize(320, 320);
		dialog = new MyDialog(this, "Reminder", true);
		ScoreBoardPanel p = new ScoreBoardPanel();
		add(p);
	}

	class ScoreBoardPanel extends JPanel implements ActionListener {

		private static final long serialVersionUID = -5382657864818298992L;

		private JButton startButton, stopButton;

		private boolean onetime = true;;

		private JLabel minLabel, secLabel, timeLabel, score, liveScore,
				Countdown;

		private JTextField minField, secField;

		private int minitues;

		private int second;

		/**
		 * The visual side of the scoreBoard. All the physical features that you
		 * will see on the screen
		 */
		public ScoreBoardPanel() {

			JPanel row1 = new JPanel();
			minField = new JTextField(5);
			minLabel = new JLabel("min");
			secField = new JTextField(5);
			secLabel = new JLabel("sec");

			JPanel row2 = new JPanel();
			startButton = new JButton("Start");
			stopButton = new JButton("Stop");

			JPanel row3 = new JPanel();
			Countdown = new JLabel("Count down:");
			timeLabel = new JLabel("00:00");

			JPanel row4 = new JPanel();
			liveScore = new JLabel("Live Score:");
			score = new JLabel("00:00");

			timeLabel.setFont(new Font("Verdana", Font.BOLD, 30));
			timeLabel.setForeground(Color.RED);

			score.setFont(new Font("Verdana", Font.BOLD, 30));
			score.setForeground(Color.RED);

			startButton.addActionListener(this);
			stopButton.addActionListener(this);

			GridLayout gridLayout = new GridLayout(5, 1, 10, 10);
			setLayout(gridLayout);

			FlowLayout flowLayout1 = new FlowLayout(FlowLayout.CENTER, 10, 10);

			row1.setLayout(flowLayout1);
			row1.add(minField);
			row1.add(minLabel);
			row1.add(secField);
			row1.add(secLabel);
			add(row1);

			row2.setLayout(flowLayout1);
			row2.add(startButton);
			row2.add(stopButton);
			add(row2);

			row3.setLayout(flowLayout1);
			row3.add(Countdown);
			row3.add(timeLabel);
			add(row3);

			row4.setLayout(flowLayout1);
			row4.add(liveScore);
			row4.add(score);
			add(row4);
		}

		class ChangeLabel extends Thread { // display second thread

			private String sMinutes;

			private String sSecond;

			private String labelTime;

			public ChangeLabel(String time) {
				onetime = false;
				minitues = Integer
						.parseInt(time.substring(0, time.indexOf(':')));

				second = Integer
						.parseInt(time.substring(time.indexOf(':') + 1));
			}

			private long time1;

			private long time2;

			/**
			 * Implements the timer of the game. It will keep looping until the
			 * time is 0
			 */
			public void run() {
				time1 = System.currentTimeMillis();
				while (true) {
					time2 = System.currentTimeMillis();
					while (!(minitues == 0 & second == 0)
							&& time2 >= time1 + 1000) {
						time1 = time2;
						if (second == 0) {
							second = 59;
							minitues--;
						} else {
							second--;
						}
						labelTime = this.getTime();
						// int temp = Integer.parseInt(score1);
						// temp--;
						// score1 = String.valueOf(temp);
						display();
					}
					if (minitues == 0 && second == 0) {
						dialog.setVisible(true);
						onetime = true;
						break;
					}
				}
			}

			/**
			 * @return - The remaining game time in a string format
			 */
			private String getTime() {
				if (minitues < 10)
					this.sMinutes = "0" + minitues;
				else
					this.sMinutes = "" + minitues;
				if (second < 10)
					this.sSecond = "0" + second;
				else
					this.sSecond = "" + second;
				return this.sMinutes + ":" + this.sSecond;
			}

			public void display() {
				// display timing
				timeLabel.setText(this.labelTime);
				score.setText(score1 + ":" + score2);

			}
		}

		/**
		 * Listenning to the actions Depends on the different actions Preformed
		 * different performance
		 */
		public void actionPerformed(ActionEvent arg0) {
			String comm = arg0.getActionCommand();

			if ("Stop".equals(comm)) {
				minitues = 0;
				second = 0;
			} else if (onetime) {
				if (minField.getText().trim().equals("")) {
					minField.setText("00");
				}
				if (secField.getText().trim().equals("")) {
					secField.setText("00");
				}
				new ChangeLabel(minField.getText().trim() + ":"
						+ secField.getText().trim()).start();
			}
		}

	}

	/**
	 * The visual side of the reminding dialog Remind the user end timing
	 */
	class MyDialog extends Dialog implements WindowListener, ActionListener {

		private static final long serialVersionUID = -3556364297739370289L;

		JLabel label;
		JPanel panel1, panel2;
		JButton button;

		public MyDialog(Frame owner, String title, boolean modal) {
			super(owner, title, modal);
			label = new JLabel("End Timing!");
			button = new JButton("Confirm");
			panel1 = new JPanel();
			panel2 = new JPanel();
			panel1.setLayout(new BorderLayout());
			panel1.add("Center", label);
			panel2.add("Center", button);
			this.add("Center", panel1);
			this.add("South", panel2);
			this.setSize(200, 200);
			this.setResizable(false);
			this.addWindowListener(this);
			button.addActionListener(this);
		}

		public void windowOpened(WindowEvent e) {

		}

		public void windowClosing(WindowEvent e) {
			this.setVisible(false);
		}

		public void windowClosed(WindowEvent e) {

		}

		public void windowIconified(WindowEvent e) {

		}

		public void windowDeiconified(WindowEvent e) {

		}

		public void windowActivated(WindowEvent e) {

		}

		public void windowDeactivated(WindowEvent e) {

		}

		public void actionPerformed(ActionEvent e) {
			score1 = "00";
			score2 = "00";
			this.setVisible(false);
		}

	}

}