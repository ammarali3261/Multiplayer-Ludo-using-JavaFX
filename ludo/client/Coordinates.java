package ludo.client;

/**
 * This class provides a mechanism to store the X and Y coordinates.
 * It also provides the getters and setters for the coordinates
 */

public class Coordinates {
	public int X;
	public int Y;

        //class constructor
	public Coordinates(int tmpX, int tmpY) {
		this.X = tmpX;
		this.Y = tmpY;
	}
	
        //getter method
	public int getXCoordinates() {
		return this.X;
	}
	
        //setter method
	public int getYCoordinates() {
		return this.Y;
	}
}
