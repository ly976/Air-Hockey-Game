package action;

import connection.Instruction;
import connection.Receiver;
import lejos.nxt.Motor;
import lejos.robotics.navigation.DifferentialPilot;

public class TaskPerformer {
	
	// Setting up the robot movement calibration
	private static final double WHEEL_DIAMETER = 2.20472441; // inches
	private static final double TRACK_WIDTH = 4.4488189; // inches
	
	private DifferentialPilot pilot;
	
	
	public TaskPerformer() {
		this.pilot = new DifferentialPilot(WHEEL_DIAMETER, TRACK_WIDTH, Motor.A, Motor.C); 
	}
	
	private void turn(double angle) {
		
		int turnAngle = (int)angle * 4;
		
		if(angle >= 0) { // Turn Right
			
			Motor.A.rotate(turnAngle);
		} else { // Turn Left
			
			Motor.C.rotate(-turnAngle);
		}
	}
	
	private void moveForward(double distance) {		
		pilot.travel(distance);
	}
	
	private void strike(double distance) {
		moveForward(angle, distance);
		moveForward(0, 5);
	}
	
	private void celebrate() {
		//Celebrates for the winner... sad for loser
	}
	
	public static void main(String[] args) {
		
		//Set up
		double maxSpeed = 14.5;
		TaskPerformer robot = new TaskPerformer();
		robot.pilot.setTravelSpeed(maxSpeed);
		
		//Connect via Bluetooth
		Receiver receiver = new Receiver();
		
		boolean running = true;
		while (running) {
			Instruction message = receiver.getInstruction();
			int action = message.getAction();
			double angle = message.getAngle();
			double distance = message.getDistance();
			switch(action) {
				case 0: 	robot.turn(angle);
							break;
				case 1: 	robot.moveForward(distance);
							break;
				case 2: 	robot.strike(distance);
							break;
				case 3:		robot.celebrate();
							break;
				case 4:		
				default:	running = false;	
			}
		}
	}
}