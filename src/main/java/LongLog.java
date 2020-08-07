package main.java;

import org.newdawn.slick.Input;

/**
 * Inherited from main.java.MovingObject class.
 * This is main.java.LongLog class.
 */
public class LongLog extends MovingObject{
	
	/**
	 * Speed of a longlog.
	 */
	public static final float LONGLOG_SPEED = 0.07f;
	
	/**
	 * Replace the constructor and creates a longlog object.
	 * 
	 * @param imagesrc : Image source for longlog to be constructed.
	 * @param x : X-coordinate of longlog.
	 * @param y : Y-coordinate of longlog.
	 * @param moveRight : Tracks if move left or right.
	 * @return longlog object.
	 */
	public static LongLog createLongLog(String imagesrc, float x, float y, boolean moveRight) {
		return new LongLog(imagesrc, x, y, moveRight, new String[] {Sprite.LONGLOG});
	}
	
	// Original constructor
	private LongLog(String imagesrc, float x, float y, boolean moveRight, String[] tags) {
		super(imagesrc, x, y, moveRight, tags);
		setSpeed(LONGLOG_SPEED);
	}
	
	@Override
	public void update(Input input, int delta) {
				
		super.update(input, delta);
	}

}
