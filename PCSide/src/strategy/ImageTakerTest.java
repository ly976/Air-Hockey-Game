package strategy;

import java.util.Scanner;

/**
* This class is used to test if ImageTaker can take a photo successfully
* It will store the photo taken into hard drive
*/
public class ImageTakerTest{
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
        ImageTaker taker = new ImageTaker();
         
        int a = 1;
        int counter = 0;
        //Keep taking photos until "0" has been entered
        while(a!=0){
            System.out.print("Enter (0 to exit): ");
            a = scan.nextInt();
            String name = "[test]ImageTaker-" + counter + ".png";
            taker.storePhoto(name);
            System.out.println("Image: " + name + "has been stored");
            counter++;
        }
        
        scan.close();
	}
}