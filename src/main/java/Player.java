package main.java;

import org.newdawn.slick.Input;

/**
 * Inherits from main.java.Sprite class.
 * This is main.java.Player class. User can control object of this class.
 */

public class Player extends Sprite {
	
	private static final String FROG_PATH = "src/main/resources/frog.png";
	private static final int REACH_GOAL = 72;
	private final int FIRST_GOAL_L = 72;
	private final int FIRST_GOAL_R = 168;
	private final int DISTANCE_BETWEEN_GOAL = 192;
	private final int TOTAL_GOAL = 5;
	
	/**
	 * Constructs a main.java.Player object.
	 * @param x X-coordinate of player.
	 * @param y Y-coordinate of player.
	 */
	public Player(float x, float y) {
		super(FROG_PATH, x, y, new String[] {Sprite.PLAYER});
	}
	
	// Store the previous movement made by user
	private int[] prevMove = new int[2];
	
	@Override
	public void update(Input input, int delta) {
		
		int dx = 0,
			dy = 0;
		
		// main.java.Player moves according to key pressed
		if (input.isKeyPressed(Input.KEY_LEFT)) {
			dx -= World.TILE_SIZE;
			prevMove[0] = World.TILE_SIZE;
			prevMove[1] = 0;
		}
		if (input.isKeyPressed(Input.KEY_RIGHT)) {
			dx += World.TILE_SIZE;
			prevMove[0] = -World.TILE_SIZE;
			prevMove[1] = 0;
		}
		if (input.isKeyPressed(Input.KEY_DOWN)) {
			dy += World.TILE_SIZE;
			prevMove[0] = 0;
			prevMove[1] = -World.TILE_SIZE;
		}
		if (input.isKeyPressed(Input.KEY_UP)) {
			dy -= World.TILE_SIZE;
			prevMove[0] = 0;
			prevMove[1] = World.TILE_SIZE;
		}

		// Make sure the frog stays on screen
		// This code snippet is copied from Eleanor's solution for project 1.
		if (getX() + dx - World.TILE_SIZE / 2 < 0 || getX() + dx + World.TILE_SIZE / 2 	> App.SCREEN_WIDTH) {
			dx = 0;
		}
		if (getY() + dy - World.TILE_SIZE / 2 < 0 || getY() + dy + World.TILE_SIZE / 2 > App.SCREEN_HEIGHT) {
			dy = 0;
		}
		
		// If a player reaches a goal, add a frog image to that goal and reset the player's position
		if (getY() <= REACH_GOAL) {
			
			World.addFrog(checkGap(getX()));
			restartPosition();
			
		}
		
		move(dx, dy);
	}
	
	/**
	 * Restart the position of the player.
	 */
	public void restartPosition() {
		setX(App.SCREEN_WIDTH / 2);
		setY(App.SCREEN_HEIGHT - World.TILE_SIZE);
	}
	
	/**
	 * Check which goal does the player meet.
	 * @param x : X-coordinate of the player.
	 * @return Goal that user meet.
	 */
	public int checkGap(float x) {
		
		for(int i = 0; i < TOTAL_GOAL; i++) {
			if(getX() >= FIRST_GOAL_L + DISTANCE_BETWEEN_GOAL * i && getX() <= FIRST_GOAL_R + DISTANCE_BETWEEN_GOAL * i) {
				return i + 1;
			}
		}
		
		return -1;
	}
	
	/**
	 * Remove life and restart position of player if player collides with moveable hazard (bus, racecar, bike).
	 */
	public void onCollisionHazard() {
		World.removeLife();
		restartPosition();
	}
	
	/**
	 * Add life if player collides with extralife object.
	 */
	public void onCollisionExtraLife() {
		World.addLife();
	}
	
	/**
	 * Moves to the same direction with the same speed as log when player collides with log.
	 * @param delta : Time passed since last frame (milliseconds).
	 * @param moveRight : Tracks if sprite move left or right.
	 */
	public void onCollisionLogs(int delta, boolean moveRight) {
		move(Log.LOG_SPEED * delta * (moveRight ? 1 : -1), 0);
	}
	
	/**
	 * Moves to the same direction with the same speed as longlog when player collides with longlog.
	 * @param delta : Time passed since last frame (milliseconds).
	 * @param moveRight : Tracks if sprite move left or right.
	 */
	public void onCollisionLongLogs(int delta, boolean moveRight) {
		move(LongLog.LONGLOG_SPEED * delta * (moveRight ? 1 : -1), 0);
	}
	
	/**
	 * Moves to the same direction with the same speed as turtle when player collides with turtle.
	 * @param delta : Time passed since last frame (milliseconds).
	 * @param moveRight : Tracks if sprite move left or right.
	 */
	public void onCollisionTurtles(int delta, boolean moveRight) {
		move(Turtle.TURTLE_SPEED * delta * (moveRight ? 1 : -1), 0);
	}
	
	/** 
	 * Stay in the same position when it wants to collide with a solid tree tile.
	 */
	public void onCollisionTrees() {
		move(prevMove[0], prevMove[1]);
	}
	
	/**
	 * main.java.Player pushed by bulldozer if player collides with right side of bulldozer, else stay in same position.
	 * @param delta : Time passed since last frame (milliseconds).
	 * @param intersectsRight : Tracks if player collides with right side of bulldozer.
	 */
	public void onCollisionBulldozers(int delta, boolean intersectsRight) {
		
		if(intersectsRight) {
			
			move(Bulldozer.BULLDOZER_SPEED*delta, 0);
			
			// If player is being pushed off the screen, lose a life instead and restart the position of player
			if(getX() > App.SCREEN_WIDTH - World.TILE_SIZE/2) {
				
				World.removeLife();
				restartPosition();
			}
			
		}else {
			move(prevMove[0], prevMove[1]);
		}
	}

}
