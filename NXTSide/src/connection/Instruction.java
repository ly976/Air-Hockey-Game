/**
 * [This is NXT side program]
 * 
 * <b>The object of this class is used to represent the decoded message</b>
 * 
 * <br>
 * NOTE: This program must be executed on a Lejos-NXT-Project with 32-bit JDK
 * 
 * @see connection.Receiver
 */

package connection;

import lejos.nxt.Button;

public class Instruction {

	private static final char SEPARATOR = ',';

	private int action;
	private double angle;
	private double distance;

	public Instruction(byte[] encodedMsg) {
		decode(encodedMsg);
	}

	private void decode(byte[] encodedMsg) {
		String cleanedString = new String(encodedMsg).trim();
		int indexs[] = getIndexs(cleanedString);
				
		if(indexs == null){
			System.err.println("Invalid Message");
			this.action = 3;
			this.angle = 0;
			this.distance = 0;
		}else{
			this.action = Integer.parseInt(cleanedString.substring(0, indexs[0]));
			this.angle = Double.parseDouble(cleanedString.substring(indexs[0]+1, indexs[1]));
			this.distance = Double.parseDouble(cleanedString.substring(indexs[1]+1));
		}
		
		System.out.println("action =  " + action);
		System.out.println("angle =  " + angle);
		System.out.println("distance =  " + distance);
	}
	
	private int[] getIndexs(String str){
		int indexs [] = new int[2];
		int counter = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == SEPARATOR) {
				if (counter < 2){
					indexs[counter] = i;
				}
				counter++;
			}
		}
		
		return counter == 2 ? indexs : null;
	}
	
	public int getAction() {
		return action;
	}
	
	public double getAngle() {
		return angle;
	}
	
	public double getDistance() {
		return distance;
	}
}
