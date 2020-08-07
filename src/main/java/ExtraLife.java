package main.java;

import org.newdawn.slick.Input;

/**
 * Inherited from main.java.Sprite class.
 * This is an main.java.ExtraLife class, the object created adds a life when player touches it.
 */
public class ExtraLife extends Sprite {
	
	// Image source
	private static final String EXTRALIFE_PATH = "src/main/resources/extralife.png";
	
	// main.java.Log which extralife is on
	private MovingObject log;
	
	private int time = 0;
	private float distance = 0;
	private boolean moveRight = true;
	
	/**
	 * Replace the constructor and creates main.java.ExtraLife object.
	 * @param x : X-coordinate of Extralife object.
	 * @param y : Y-coordinate of Extralife object.
	 * @param logAttached : main.java.Log which extralife object is on.
	 * @return Extralife object.
	 */
	public static ExtraLife createExtraLife(float x, float y, MovingObject logAttached) {
		return new ExtraLife(EXTRALIFE_PATH, x, y, new String[] {Sprite.EXTRALIFE}, logAttached);
	}
	
	// Original constructor
	private ExtraLife(String imagesrc, float x, float y, String[] tags, MovingObject logAttached) {
		super(imagesrc, x, y, tags);
		this.log = logAttached;
	}
	
	@Override
	public void update(Input input, int delta) { 
		
		// Extralife object follows the log or longlog, reappearing from other side of screen
		if (log.getX() == -log.getImage().getWidth()/2) {
			setX(-log.getImage().getWidth()/2 + distance);
		}
		
		if (log.getX() == App.SCREEN_WIDTH + log.getImage().getWidth() / 2 ) {
			setX(App.SCREEN_WIDTH + log.getImage().getWidth() / 2 + distance);
		}
		
		
		time += delta;
		
		// Moves left or right every 2 second  
		if(time >= 2000) {
			
			time = 0;
			
			// If moving right will fall into water, move left until it reaches log left end.
			if(getX() + World.TILE_SIZE > log.getX()+log.getImage().getWidth()/2 || moveRight == false) {
				if(getX() - World.TILE_SIZE < log.getX()-log.getImage().getWidth()/2) {
					moveRight = true;
					move(World.TILE_SIZE, 0);
					distance += World.TILE_SIZE;
				}else {
					moveRight = false;
					move(-World.TILE_SIZE, 0);
					distance -= World.TILE_SIZE;
					
				}
			
			// Else, move right until it reaches log right end.
			}else if(moveRight == true){
				
				move(World.TILE_SIZE, 0);
				distance += World.TILE_SIZE;
			}
		}
	};
	
	/**
	 * Move at the same speed as log when in contact with log.
	 * @param delta : Time passed since last frame (milliseconds).
	 * @param moveRight : Direction of moving log, true if log moves right. 
	 */
	public void onCollisionLogs(int delta, boolean moveRight) {
		move(Log.LOG_SPEED * delta * (moveRight ? 1 : -1), 0);
	}
	
	/**
	 * Move at the same speed as longlog when in contact with longlog.
	 * @param delta : Time passed since last frame (milliseconds).
	 * @param moveRight : Direction of moving log, true if log moves right. 
	 */
	public void onCollisionLongLogs(int delta, boolean moveRight) {
		move(LongLog.LONGLOG_SPEED * delta * (moveRight ? 1 : -1), 0);
	}
	

}
