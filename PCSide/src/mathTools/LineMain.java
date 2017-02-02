/**
 * This is just a test class
 */

package mathTools;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import stadium.Coordinate;

public class LineMain {

	public static void main(String[] args) throws IOException {
		
		LinkedList<Coordinate> cs = new LinkedList<Coordinate>();
		Random random = new Random();
		
		FileWriter output = new FileWriter("dongxi.txt", false);
		
		for (int i = 0; i < 100; i++) {
			double x = i + random.nextDouble() * 1;
			double y = 4 + i + random.nextDouble() * 2;
			String line = String.format("%s%n", x + "," + y);
			Coordinate c = new Coordinate(x, y);
			cs.add(c);
			
			output.write(line);
		}
		
		output.flush();
		output.close();
		
		LinearLine line = new LinearLine(cs);
		System.out.println(line.getK());
		System.out.println(line.getB());
		
	}

}
