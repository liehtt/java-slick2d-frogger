package main.java;

import org.newdawn.slick.Input;

/**
 * Inherited from main.java.MovingObject class.
 * This is main.java.Turtle class.
 */
public class Turtle extends MovingObject{
	
	/**
	 * Speed of a turtle.
	 */
	public static final float TURTLE_SPEED = 0.085f;
	
	// Tracks if turtle is underwater, false if turtle is underwater
	private static boolean turtlePresent = true;
	
	/**
	 * Replace the constructor and creates a turtle object.
	 * 
	 * @param imagesrc Image source for turtle to be constructed.
	 * @param x : X-coordinate of turtle.
	 * @param y : Y-coordinate of turtle.
	 * @param moveRight : Tracks if move left or right.
	 * @return main.java.Turtle object.
	 */
	public static Turtle createTurtle(String imagesrc, float x, float y, boolean moveRight) {
		return new Turtle(imagesrc, x, y, moveRight, new String[] {Sprite.TURTLE});
		
	}
	
	// Original constructor
	private Turtle(String imagesrc, float x, float y, boolean moveRight, String[] tags) {
		super(imagesrc, x, y, moveRight, tags);
		setSpeed(TURTLE_SPEED);
	}
	
	@Override
	public void update(Input input, int delta) {
		
		// main.java.Turtle dives after 7 second, time tracker reset
		if(World.getTimeTurtle() >= 7000 && turtlePresent == true) {
			turtlePresent = false;
			World.resetTimeTurtle();
		}
		
		// main.java.Turtle resurfaces after 2 second, time tracker reset again
		if(World.getTimeTurtle() >= 2000 && turtlePresent == false) {
			turtlePresent = true;
			World.resetTimeTurtle();
		}
		
		super.update(input, delta);
	}
	
	/**
	 * Checks whether turtle is underwater.
	 * 
	 * @return True if turtle is not underwater.
	 */
	public static boolean getTurtlePresent() {
		return turtlePresent;
	}
	
}
