package main.java;

import org.newdawn.slick.Input;
/**
 * Inherited from main.java.MovingObject class.
 * This is main.java.Racecar class.
 */
public class Racecar extends MovingObject{
	/**
	 * Speed of a racecar.
	 */
	public static final float RACECAR_SPEED = 0.5f;
	
	/**
	 * Replace the constructor and creates a racecar object.
	 * 
	 * @param imagesrc : Image source for racecar to be constructed.
	 * @param x : X-coordinate of racecar.
	 * @param y : Y-coordinate of racecar.
	 * @param moveRight : Tracks if move left or right.
	 * @return main.java.Racecar object.
	 */
	public static Racecar createRacecar(String imagesrc, float x, float y, boolean moveRight) {
		return new Racecar(imagesrc, x, y, moveRight, new String[] {Sprite.HAZARD});
	}
	
	//Original constructor
	private Racecar(String imagesrc, float x, float y, boolean moveRight, String[] tags) {
		super(imagesrc, x, y, moveRight, tags);
		setSpeed(RACECAR_SPEED);
	}
	
	@Override
	public void update(Input input, int delta) {
		
		super.update(input, delta);
	}
}
