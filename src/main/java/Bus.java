package main.java;

import org.newdawn.slick.Input;
/**
 * Inherited from main.java.MovingObject class.
 * This is main.java.Bus class.
 */
public class Bus extends MovingObject{
	/**
	 * Speed of a bus.
	 */
	public static final float BUS_SPEED = 0.15f;
	
	/**
	 * Replace the constructor and creates a bus object.
	 * 
	 * @param imagesrc : Image source for bus to be constructed.
	 * @param x : X-coordinate of bus.
	 * @param y : Y-coordinate of bus.
	 * @param moveRight : Tracks if move left or right.
	 * @return main.java.Bus object.
	 */
	public static Bus createBus(String imagesrc, float x, float y, boolean moveRight) {
		return new Bus(imagesrc, x, y, moveRight, new String[] {Sprite.HAZARD});
	}
	
	// Original constructor
	private Bus(String imagesrc, float x, float y, boolean moveRight, String[] tags) {
		super(imagesrc, x, y, moveRight, tags);
		setSpeed(BUS_SPEED);
	}
	
	@Override
	public void update(Input input, int delta) {
				
		super.update(input, delta);
	}
}
