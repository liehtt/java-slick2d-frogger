package main.java;

import org.newdawn.slick.Input;

/**
 * Inherited from main.java.MovingObject class.
 * This is main.java.Bike class.
 */
public class Bike extends MovingObject{

	/**
	 * Speed of a bike.
	 */
	public static final float BIKE_SPEED = 0.2f;

	/**
	 * Replace the constructor and creates a bike object.
	 *
	 * @param imagesrc : Image source for bike to be constructed.
	 * @param x : X-coordinate of bike.
	 * @param y : Y-coordinate of bike.
	 * @param moveRight : Tracks if move left or right.
	 * @return main.java.Bike object.
	 */
	public static Bike createBike(String imagesrc, float x, float y, boolean moveRight) {
		return new Bike(imagesrc, x, y, moveRight, new String[] {Sprite.HAZARD});
	}

	// Original constructor, creates object and sets the speed
	private Bike(String imagesrc, float x, float y, boolean moveRight, String[] tags) {
		super(imagesrc, x, y, moveRight, tags);
		setSpeed(BIKE_SPEED);
	}

	@Override
	public void update(Input input, int delta) {

		// If reaches wall, reverse direction
		if(getX() <= World.LEFT_WALL) {

			super.setMoveRight(true);

		}else if(getX() >= World.RIGHT_WALL) {

			super.setMoveRight(false);
		}

		super.update(input, delta);
	}
}
