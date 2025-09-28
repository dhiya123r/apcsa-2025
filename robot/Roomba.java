package robot;

import kareltherobot.*;

public class Roomba implements Directions {
	private Robot roomba;
	//Statistics we want to report
	private int area = 0;
	private int piles = 0; 
	private int totalBeepers = 0;
	private int largestPile = 0; 
	private int largestX = 0;
	private int largestY =0;

public static void main(String[] args) {
	String worldName = "robot/basicRoom.wld";
	Roomba cleaner = new Roomba();
	cleaner.cleanRoom(worldName, 7, 6);
	cleaner.report();
}
		
public void cleanRoom(String worldName, int startX, int startY) {

	World.readWorld(worldName);
	World.setVisible(true);
	World.setDelay(1);

	roomba = new Robot(startX, startY, West, 0);
	
	while (roomba.frontIsClear()) {
		roomba.move();
	}
	roomba.turnLeft();

	while (roomba.frontIsClear()) {
		roomba.move();
	}
	roomba.turnLeft();

	//Determine room width and height
	int width = 0; 
	int height = 0;
    while (roomba.frontIsClear()) {
		width++; 
		roomba.move();
	}
    roomba.turnLeft();


    while (roomba.frontIsClear()) {
		height++; 
		roomba.move();
	}
    roomba.turnLeft();
 
    int x = width;
    int y= height;
     
	while (x>0 && y>0) {
     //Moving East/West
 	for (int i=0; i<x; i++) {
	 roomba.move(); 
	 pickBeepers();
 }
   x--;
   roomba.turnLeft();

	for (int i=0; i<y; i++) {
	 roomba.move(); 
	 pickBeepers();
   }
	y--;
	roomba.turnLeft();
 }  // end of while 
} // end of method 

private void pickBeepers() {
	area++;
	int pileCount = 0;

	while (roomba.nextToABeeper()) {
		roomba.pickBeeper();
		pileCount++;
		totalBeepers++;
	}

	if (pileCount > 0) {
		piles++;
		if (pileCount > largestPile) {
			largestPile = pileCount;
			largestX = roomba.avenue();
			largestY = roomba.street();
		}
	}
}

public void report() {
	double averagePile = 0;
	double percentageDirty = 0;

	if (piles > 0) {
		averagePile = (double) totalBeepers / piles;		
	}

	if (area > 0) {
		percentageDirty = (double) piles / area;
	}

	// print all the statistics we have collected
	System.out.println("Printing report");
	System.out.println("Area of room: " + area);
	System.out.println("Number of piles: " + piles);
	System.out.println("Total number of beepers: " + totalBeepers);
    System.out.println("Largest Pile: " + largestPile);

	System.out.println("Largest Pile: Street " + largestY);
	System.out.println("Largest Pile: Avenue " + largestX);

	System.out.println("Average pile size: " + averagePile);
	System.out.println("Percentage Dirty: " + percentageDirty);
}

}




