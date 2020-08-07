package main.java;

import org.newdawn.slick.Input;

/**
 * Inherited from main.java.MovingObject class.
 * This is main.java.Log class.
 */
public class Log extends MovingObject{
	
	/**
	 * Speed of a log
	 */
	public static final float LOG_SPEED = 0.1f;
	
	/**
	 * Replace the constructor and creates a log object.
	 * 
	 * @param imagesrc : Image source for log to be constructed
	 * @param x : X-coordinate of log
	 * @param y : Y-coordinate of log
	 * @param moveRight : Tracks if move left or right
	 * @return main.java.Log object
	 */
	public static Log createLog(String imagesrc, float x, float y, boolean moveRight) {
		return new Log(imagesrc, x, y, moveRight, new String[] {Sprite.LOG});
	}
	
	private Log(String imagesrc, float x, float y, boolean moveRight, String[] tags) {
		super(imagesrc, x, y, moveRight, tags);
		setSpeed(LOG_SPEED);
	}
	
	@Override
	public void update(Input input, int delta) {
				
		super.update(input, delta);
	}
}
