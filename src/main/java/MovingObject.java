package main.java;

import org.newdawn.slick.Input;

/**
 * Inherited from the main.java.Sprite class.
 * It is abstract and sets the base for all moving objects.
 */
public abstract class MovingObject extends Sprite {

	// Speed of the moving object
	private float speed;
	
	/**
	 * Constructor for a moving object.
	 *
	 * @param imagesrc : Image source of the moving object.
	 * @param x : X-coordinate of the moving object.
	 * @param y : Y-coordinate of the moving object.
	 * @param moveRight : Tracks if object moves left or right.
	 * @param tags : Tag of the moving object.
	 */
	public MovingObject(String imagesrc, float x, float y, boolean moveRight, String[] tags) {
		super(imagesrc, x, y, moveRight, tags);
		
	}
	
	@Override
	public void update(Input input, int delta) {
		
		move(speed * delta * (super.getMoveRight() ? 1 : -1), 0);	
		
		// This code snippet to make moving object reappear from other side is copied from Eleanor's solution for Project 1. 
		if (getX() > App.SCREEN_WIDTH + getImage().getWidth() / 2  || getX() < -getImage().getWidth() / 2
	       || getY() > App.SCREEN_HEIGHT + getImage().getWidth()  || getY() < -getImage().getWidth() ) 
		{
			setX(getInitialX());
		}
		
	}
	
	/**
	 * Getter for the speed of moving object.
	 */
	public float getSpeed() { return speed; }
	/**
	 * Setter for the speed of moving object
	 * @param speed : Speed of moving object
	 */
	public void setSpeed(float speed) { this.speed = speed; }
	
	// Get the coordinate of the moving object when it moves off the screen
	// This code snippet is copied from Eleanor's solution for Project 1. 
	private final float getInitialX() {
		return super.getMoveRight() ? -getImage().getWidth() / 2
						 : App.SCREEN_WIDTH + getImage().getWidth() / 2;
	}
	
	
	
	
	
}
