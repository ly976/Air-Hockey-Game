package connection;

import java.io.DataOutputStream;
import java.io.IOException;

//The third-pary library is used here
import lejos.pc.comm.NXTConnector;

/**
 * [This is PC side program]
 * 
 * To receive and decode the message sent from PC via Bluetooth NOTE: This
 * program must be executed on a Lejos-PC-Project with 32-bit JDK
 * 
 */
public class Sender {

	// Default Message action, telling the robot to nothing
	public static final String DO_NOTHING = "1,0.00,0.00\n";
	private static final String MSG_FORMAT = "%d,%.2f,%.2f\n";
	private DataOutputStream outputToNXT;

	/**
	 * Sender will set up an initial connection between the PC and the NXT
	 * brick, so that messages can be sent to the brick
	 */
	public Sender() {
		NXTConnector connector = new NXTConnector();
		System.out.println("Trying to connect to NXT...");
		if (!connector.connectTo()) {
			System.err.println("Connection fails");
		} else {
			outputToNXT = new DataOutputStream(connector.getOutputStream());
			System.out.println("Connection successfully");
		}
	}

	/**
	 * @param action
	 *            - the integer action the robot must perform (rotate/move
	 *            forward/strike/celebrate)
	 * @param angle
	 *            - the angle the robot must rotate
	 * @param distance
	 *            - the distance the robot must travel
	 * @return - overall message that will be sent to the robot to carry out
	 */
	public boolean sendMessage(int action, double angle, double distance) {
		String message = createMessage(action, angle, distance);
		return sendMessage(message);
	}

	/**
	 * @param message
	 *            - the message or instruction that will be sent to the robot
	 * @return - a boolean stating whether or not
	 */
	public boolean sendMessage(String message) {
		try {
			outputToNXT.write(message.getBytes());
			outputToNXT.flush();

			System.out.println("Message sent: " + message);
			return true;
		} catch (IOException e) {
			e.printStackTrace();

			System.out.println("Fail to send message");
			return false;
		}
	}

	/**
	 * @param action
	 *            - the integer action the robot must perform (rotate/move
	 *            forward/strike/celebrate)
	 * @param angle
	 *            - the angle the robot must rotate
	 * @param distance
	 *            - the distance the robot must travel
	 * @return - a formatted string of the instruction that will be sent to the
	 *         robot
	 */
	public static String createMessage(int action, double angle, double distance) {
		return String.format(MSG_FORMAT, action, angle, distance);
	}

	// For test
	public static void main(String[] args) {
		Sender sender = new Sender();
		sender.sendMessage(0, 0.0, 10000);
		while (true) {
		}
	}
}
