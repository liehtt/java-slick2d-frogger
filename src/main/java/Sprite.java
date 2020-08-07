package main.java;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import main.java.utilities.BoundingBox;

/**
 * A general sprite class of the game.
 * It is abstract and sets the base for all sprites.
 */
public abstract class Sprite {
	
	/* This 'tag' system that assists collision detection, the overloading of constructors for sprite class, and
	 * the use of 'moveRight' and 'bounds' attributes are inspired by Eleanor's solution for project 1. 
	 */
	
	/** 'hazard' tag for bus, racecar and bike. */
	public final static String HAZARD = "hazard";
	/** 'tree' tag for tree tiles. */
	public final static String TREE = "tree";
	/** 'log' tag for logs. */
	public final static String LOG = "log";
	/** 'longlog' tag for longlogs. */
	public final static String LONGLOG = "longlog";
	/** 'bulldozer' tag for bulldozers. */
	public final static String BULLDOZER = "bulldozer";
	/** 'water' tag for water tiles. */
	public final static String WATER = "water";
	/** 'turtle' tag for turtle. */
	public final static String TURTLE = "turtle";
	/** 'extralife' tag for extralife. */
	public final static String EXTRALIFE = "extralife";
	/** 'frog' tag for goal. */
	public final static String FROG = "frog";
	/** 'player' tag for player frog. */
	public final static String PLAYER = "player";
	
	// X and Y coordinate of the sprite
	private float x, y;
	// Image of the sprite
	private Image image;
	// Bound of a sprite, for collision detection
	private BoundingBox bounds; 
	// Tracks if the sprite move left or right, true if move right
	private boolean moveRight;
	
	private String[] tags = new String[2];
	
	/**
	 * Constructs a sprite that is going to be rendered and updated in game.
	 * 
	 * @param imagesrc : Image source for the sprite to be constructed.
	 * @param x : X-coordinate of the sprite.
	 * @param y : Y-coordinate of the sprite.
	 */
	public Sprite (String imagesrc, float x, float y) {
		setupSprite(imagesrc, x, y);
	}
	
	/**
	 * Constructs a sprite that is going to be rendered, updated in game, and contains a tag.
	 * 
	 * @param imagesrc : Image source for the sprite to be constructed.
	 * @param x : X-coordinate of the sprite.
	 * @param y : Y-coordinate of the sprite.
	 * @param tags : Tag of the sprite.
	 */
	public Sprite (String imagesrc, float x, float y, String[] tags) {
		setupSprite(imagesrc, x, y);
		this.tags = tags;
	}
	
	/**
	 * Constructs a moving sprite that is going to be rendered, updated in game, and contains a tag.
	 * 
	 * @param imagesrc : Image source for the sprite to be constructed.
	 * @param x : X-coordinate of the sprite.
	 * @param y : Y-coordinate of the sprite.
	 * @param moveRight : Tracks if sprite move left or right.
	 * @param tags : Tag of the sprite.
	 */
	public Sprite (String imagesrc, float x, float y, boolean moveRight, String[] tags) {
		setupSprite(imagesrc, x, y);
		this.moveRight = moveRight;
		this.tags = tags;
	}
	
	
	private void setupSprite(String imagesrc, float x, float y){
		
		try {
			image = new Image(imagesrc);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		this.x = x;
		this.y = y;
		
		bounds = new BoundingBox(image, (int) x, (int) y);
	
	}
	
	/** Render the sprite and treats coordinates of sprite as centre of the image. */
	public void render() {
		image.drawCentered(x, y);
	}
	
	/**
	 * Checks if sprite collides other sprite.
	 * 
	 * @param other : Other sprite.
	 * @return True if sprites collide.
	 */
	public boolean collides(Sprite other) { 
		return bounds.intersects(other.bounds);
	}
	
	/**
	 * Checks if sprite collides the right side of other sprite.
	 * @param other : Other sprite.
	 * @return True if sprites collide.
	 */
	public boolean collidesRight(Sprite other) {
		return bounds.intersectsRight(other.bounds);
	}
	
	/**
	 * Update the sprite.
	 * 
	 * @param input : Key pressed.
	 * @param delta : Time passed since last frame (milliseconds).
	 */
	public void update(Input input, int delta) { };
	
	/**
	 * Moves the sprite.
	 *  
	 * @param dx : X-distance to be moved.
	 * @param dy : Y-distance to be moved.
	 */
	// This method is also inspired by Eleanor's solution. 
	public void move(float dx, float dy) {
		
		setX(x + dx);
		setY(y + dy);
	}
	
	/**
	 * Getter for X-coordinate of the sprite.
	 * @return X-coordinate.
	 */
	public float getX() { return x; }
	/**
	 * Getter for Y-coordinate of the sprite.
	 * @return Y-coordinate.
	 */
	public float getY() { return y; }
	/**
	 * Getter for image of the sprite.
	 * @return Image.
	 */
	public Image getImage() { return image; }
	/** 
	 * Getter for direction of sprite moving to.
	 * @return Direction of the moving sprite, true if move right.
	 */
	public boolean getMoveRight() { return moveRight; }
	/**
	 * Setter for direction of sprite moving to.
	 * @param moveRight : Direction of the moving sprite, true if move right.
	 */
	public void setMoveRight(boolean moveRight) {this.moveRight = moveRight;}
	/**
	 * Setter for X-coordinate of the sprite.
	 * @param x : X-coordinate.
	 */
	public void setX(float x) { this.x = x; bounds.setX((int)x);}
	/**
	 * Setter for Y-coordinate of the sprite.
	 * @param y : Y-coordinate.
	 */
	public void setY(float y) { this.y = y; bounds.setY((int)y);}
	
	/**
	 * Check if sprite has a certain tag.
	 * @param tag : Tag to be checked.
	 * @return True if sprite contains the tag.
	 */
	public boolean hasTag(String tag) {
		for (String test: tags) {
			if (tag.equals(test)) {
				return true;
			}
		}
		return false;
	}
}
