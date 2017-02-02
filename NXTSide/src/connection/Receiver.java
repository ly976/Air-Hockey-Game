/**
 * [This is NXT side program]
 * 
 * <b>To receive and decode the message sent from PC via bluetooth</b>
 * 
 * <br>
 * NOTE: This program must be executed on a Lejos-NXT-Project with 32-bit JDK
 *  
 * @see connection.Instruction
 */

package connection;

import java.io.DataInputStream;
import java.io.IOException;

import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;

public class Receiver {
	private DataInputStream inputFromPC;

	public Receiver() {
		System.out.println("Start Waitting");
		//Blocked and wait for connection
		NXTConnection connection = Bluetooth.waitForConnection();
		inputFromPC = connection.openDataInputStream();
	}

	public Instruction getInstruction() {
		byte buffer[] = new byte[256];
		int counter = 0;
		byte data;
		try {
			while ((data = (byte) inputFromPC.read()) != '\n' && counter < 256) {
				buffer[counter] = data;
				counter++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Decode and Parse codes
		return new Instruction(buffer);
	}

}
