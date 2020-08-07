package main.java;

import java.util.HashMap;

/**
 * Inherited from main.java.Sprite class.
 * This is a main.java.Frog class, different from main.java.Player class, main.java.Frog class fills the five goals of the game when player reaches the goal.
 */
public class Frog extends Sprite {
	
	// Image source for main.java.Frog object.
	private static final String FROG_PATH = "src/main/resources/frog.png";
	
	// X-coordinate of the frog to be insert when player reaches a goal.
	private static final float FIRST_GOAL = 120;
	private static final float SECOND_GOAL = 312;
	private static final float THIRD_GOAL = 504;
	private static final float FORTH_GOAL = 696;
	private static final float FIFTH_GOAL = 888;
	
	// HashMap for storing position of goals as keys, and X-coordinate of frog image to be insert as values.
	private static HashMap<Integer, Float> hmap = new HashMap<>();
	
	/**
	 * Input keys and values into hashmap.
	 */
	public static void initialize() {
		hmap.put(1, FIRST_GOAL);
		hmap.put(2, SECOND_GOAL);
		hmap.put(3, THIRD_GOAL);
		hmap.put(4, FORTH_GOAL);
		hmap.put(5, FIFTH_GOAL);
	}
	
	/**
	 * Replace the constructor and returns a main.java.Frog object.
	 * @param positionGoal : Position of goal.
	 * @return main.java.Frog object.
	 */
	public static Frog createFrog(int positionGoal) {
		return new Frog(FROG_PATH, hmap.get(positionGoal),(float)48, new String[] {Sprite.FROG});
	}
	
	// Original constructor 
	private Frog(String imagesrc, float x, float y, String[] tags) {
		super(imagesrc, x, y, tags);
	}

}
