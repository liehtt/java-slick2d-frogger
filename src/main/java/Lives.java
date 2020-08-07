package main.java;

/**
 * Inhertied from main.java.Sprite class.
 * This is a main.java.Lives class, representing lives of the player.
 */
public class Lives extends Sprite{
	
	// Image source of main.java.Lives.
	private static final String LIFE_PATH = "src/main/resources/lives.png";
	
	/**
	 * Replace the constructor and returns a main.java.Lives object.
	 * 
	 * @param x : X-coordinate of main.java.Lives.
	 * @param y : Y-coordinate of main.java.Lives.
	 * @return main.java.Lives object.
	 */
	public static Lives createLife(float x, float y) {
		return new Lives(LIFE_PATH, x, y);
	}
	
	// Original constructor 
	private Lives(String imagesrc, float x, float y) {
		super(imagesrc, x, y);
	}
}
