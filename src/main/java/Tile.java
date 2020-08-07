package main.java;

/**
 * Inherited from main.java.Sprite class.
 * It is a main.java.Tile class. It creates object that forms the background of game.
 */
public class Tile extends Sprite {
	
	// Image sources
	private static final String GRASS_PATH = "src/main/resources/grass.png";
	private static final String WATER_PATH = "src/main/resources/water.png";
	private static final String TREE_PATH = "src/main/resources/tree.png";
	
	// Check if water contacts with (1)log, (2)longlog, (3)turtle or (0)nothing
	private int isContact = 0;
	
	// Direction of moving objects
	private boolean moveRight;
	
	/**
	 * Replace the constructor and creates a grass tile.
	 * 
	 * @param x : X-coordinate of grass tile.
	 * @param y : Y-coordinate of grass tile.
	 * @return Grass tile.
	 */
	public static Tile createGrassTile(float x, float y) {
		return new Tile(GRASS_PATH, x, y);
	}
	
	/**
	 * Replace the constructor and creates a water tile.
	 * 
	 * @param x : X-coordinate of water tile.
	 * @param y : Y-coordinate of water tile.
	 * @return Water tile.
	 */
	public static Tile createWaterTile(float x, float y) {
		return new Tile(WATER_PATH, x, y, new String[] { Sprite.WATER});
	}
	
	/**
	 * Replace the constructor and creates a tree tile.
	 * 
	 * @param x : X-coordinate of tree tile.
	 * @param y : Y-coordinate of tree tile.
	 * @return Tree tile.
	 */
	public static Tile createTreeTile(float x, float y) {
		return new Tile(TREE_PATH, x, y, new String[] {Sprite.TREE});
	}
	
	// Original constructor 
	private Tile(String imagesrc, float x, float y) {
		super(imagesrc, x, y);
	}
	
	private Tile(String imagesrc, float x, float y, String[] tags) {
		super(imagesrc, x, y, tags);
	}
	
	/**
	 * Set isContact to 1, denoting water is contacting log and store direction of moving logs.
	 * @param other : Other sprite.
	 */
	public void contactLog(Sprite other) {
		isContact = 1;
		moveRight = other.getMoveRight();	
	}
	
	/**
	 * Set isContact to 2, denoting water is contacting longlog and store direction of moving longlog.
	 * @param other : Other sprite.
	 */
	public void contactLongLog(Sprite other) {
		
		isContact = 2;
		moveRight = other.getMoveRight();
	}
	
	/**
	 * Set isContact to 3, denoting water is contacting turtle, and store direction of moving turtle.
	 * @param other : Other sprite.
	 */
	public void contactTurtle(Sprite other) {
		
		isContact = 3;
		moveRight = other.getMoveRight();
	}
	
	/**
	 * Set isContact to 0, denoting water is not contacting anything.
	 */
	public void noContact() {
		isContact = 0;
	}
	
	/**
	 * Returns direction of moving object water is contacting.
	 * @return True if object moves right.
	 */
	public boolean waterGetMoveRight() {
		return moveRight;
	}
	
	/**
	 * Returns number that denotes which object water tile is contacting.
	 * @return isContact number.
	 */
	public int getContact() {
		return isContact;
	}
	
	

}
