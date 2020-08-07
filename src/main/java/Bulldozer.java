package main.java;

import org.newdawn.slick.Input;
/**
 * Inherited from main.java.MovingObject class.
 * This is main.java.Bulldozer class.
 */
public class Bulldozer extends MovingObject{
	/**
	 * Speed of a bulldozer.
	 */
	public static final float BULLDOZER_SPEED = 0.05f;
	
	/**
	 * Replace the constructor and creates a bulldozer object.
	 * 
	 * @param imagesrc : Image source for bulldozer to be constructed.
	 * @param x : X-coordinate of bulldozer.
	 * @param y : Y-coordinate of bulldozer.
	 * @param moveRight : Tracks if move left or right.
	 * @return main.java.Bulldozer object.
	 */
	public static Bulldozer createBulldozer(String imagesrc, float x, float y, boolean moveRight) {
		return new Bulldozer(imagesrc, x, y, moveRight, new String[] {Sprite.BULLDOZER});
	}
	
	// Original constructor
	private Bulldozer(String imagesrc, float x, float y, boolean moveRight, String[] tags) {
		super(imagesrc, x, y, moveRight, tags);
		setSpeed(BULLDOZER_SPEED);
	}
	
	@Override
	public void update(Input input, int delta) {
			
		super.update(input, delta);
	}
}
