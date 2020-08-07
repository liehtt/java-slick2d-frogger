package main.java;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import java.util.Random;
import java.util.ArrayList;
import java.io.FileReader;
import java.util.Scanner;

/**
 * This is a main.java.World class, to be initialized and updated in main.java.App.java.
 */
public class World {
	
	/** Size of a tile. */
	public static final int TILE_SIZE = 48;
	/** X-coordinate that for an image that is closest to the left wall. */
	public static final int LEFT_WALL = 24;
	/** X-coordinate that for an image that is closest to the right wall. */
	public static final int RIGHT_WALL = 1000;
	/** Y-coodinate for putting lives. */
	public static final int LIFE_ROW = 744;
	/** Distance between each life object. */
	public static final int LIFE_OFFSET =32;
	/** The border between grassland and water region. */
	public static final int MEETS_WATER = 336;
	 
	// 'sprites' Arraylist for storing objects created from level file
	private static ArrayList<Sprite> sprites = new ArrayList<>();
	
	// Arraylists storing objects from 'sprites' Arraylist for easier collision detections
	private ArrayList<MovingObject> waterMovingObjGroup = new ArrayList<>();
	private ArrayList<MovingObject> allLogsGroup = new ArrayList<>();
	private ArrayList<Tile> waterGroup = new ArrayList<>(); 
	
	// ArrayList for storing objects that are created manually and for easier colllision detection.
	private ArrayList<ExtraLife> extralifeGroup = new ArrayList<>(); 
	private static ArrayList<Lives> lifeGroup = new ArrayList<>();
	private static ArrayList<Frog> goalGroup = new ArrayList<>();
	
	// A single player object for storing extracted player object from 'sprites' arraylist.
	private static Player player;
	
	// Source for level file
	private String level = "src/main/resources/levels/0.lvl";

	// Time tracker for extralife
	private int timeExtraLife = 0;
	// Time tracker for turtle
	private static int timeTurtle = 0;
	
	// Level file number
	private int checkLevel = 0;
	// If extralife is present in the game
	private boolean checkExtraLife = false;
	
	// Randomizer to make random number for extralife.
	private Random randomizer = new Random();
	// Starting from 25seconds
	private int randomnumber = (randomizer.nextInt(11) + 25) * 1000;
	
	/**
	 * Constructor to create main.java.World object.
	 * @throws SlickException
	 */
	public World() throws SlickException {
		
		// Input keys and values into hashmap for recognition of goal positions.
		Frog.initialize();
		
		// Start the level based on level file. 
		startLevel(level);	
	}
	

	/**
	 * Return time tracked for turtle objects.
	 * @return Time tracked. 
	 */
	public static int getTimeTurtle() { return timeTurtle; }
	
	/**
	 * Reset the time tracker for turtle objects.
	 */
	public static void resetTimeTurtle() { timeTurtle = 0; }
	
	/**
	 * Update the main.java.World, contains series of collision detections and tracks the time for turtle and extralife objects.
	 * 
	 * @param input : Key pressed
	 * @param delta : Time passed since last frame (milliseconds)
	 */
	public void update(Input input, int delta) {
		
		// If player on rideable objects, it will stay along the screen of game when logs wants to move player off the screen.
		playerOnRideable();
		
		// Tracks time for turle and extralife objects.
		timeExtraLife += delta;
		timeTurtle += delta;
		
		// After time passed a random number of 25-35s, and there's no extralife present, creates an extralife object on a random log or longlog.
		// Then, restart the timer. 
		if(timeExtraLife >= randomnumber && checkExtraLife == false ) {
			
			MovingObject randomLogs = allLogsGroup.get(randomizer.nextInt(allLogsGroup.size()));
			extralifeGroup.add(ExtraLife.createExtraLife(randomLogs.getX(), randomLogs.getY(), randomLogs));
			randomnumber = (randomizer.nextInt(11) + 25) * 1000;
			timeExtraLife = 0;
			checkExtraLife = true;
		}
		
		// If extralife object is present
		if (checkExtraLife == true) {
			
			// Destroy extralife object if time tracker passes 14s, resets timetracker.
			if(timeExtraLife >= 14000) {
				extralifeGroup.clear();
				timeExtraLife = 0;
				checkExtraLife = false;
				
			// Else if collides with player, adds a life, resets timetracker and destroy extralife object.
			}else if(player.collides(extralifeGroup.get(0))) {
				player.onCollisionExtraLife();
				extralifeGroup.clear();
				timeExtraLife = 0;
				checkExtraLife = false;
			}
		}
		
		// When all goals are met, check which level is it.
		if(goalGroup.size() == 5) {
			
			// If it's a first level, move on to next level
			if(checkLevel == 0) {
				level = "src/main/resources/levels/1.lvl";
				startLevel(level);
				checkLevel = 1;
				
			// Else, safely exit the game.
			}else if (checkLevel == 1) {
				System.exit(0);
			}
		}
		
		// Update sprites
		for (Sprite sprite : sprites) {
			sprite.update(input, delta);
		}
		
		// Update extralife.
		for (ExtraLife extralife: extralifeGroup) {
			extralife.update(input, delta);
		}
		
		// Check contact between extralife object and (log or longlog).
		for(ExtraLife extralife: extralifeGroup) {
			for(MovingObject alog: allLogsGroup) {
				if(extralife.collides(alog)) {
					// If there is contact, move at same speed and direction as the log collided.
					if(alog.hasTag(Sprite.LOG)) { extralife.onCollisionLogs(delta, alog.getMoveRight()); }
					if(alog.hasTag(Sprite.LONGLOG)) {extralife.onCollisionLongLogs(delta, alog.getMoveRight());}
				}
			}
		}
		
		// main.java.Player cannot enter goal that has been reached
		for(Frog frog: goalGroup) {
			if(player.collides(frog)) {
				player.onCollisionTrees();	
			}
		}
		
		// Check contact between water tiles and moving objects on water
		for(Tile water: waterGroup) {
			for(Sprite temp: waterMovingObjGroup) {
				if(water.collides(temp) || player.collides(temp)) {
					
					if(temp.hasTag(Sprite.LOG)) { water.contactLog(temp); break; }
					if(temp.hasTag(Sprite.LONGLOG)) { water.contactLongLog(temp); break; }
					if(temp.hasTag(Sprite.TURTLE)) {
						Turtle temp1 = (Turtle)temp;
						if(Turtle.getTurtlePresent()) { water.contactTurtle(temp1); break;}
					}
				}else if(!water.collides(temp)) {
					if(temp.hasTag(Sprite.LOG) && water.getContact() == 1) { water.noContact(); }
					if(temp.hasTag(Sprite.LONGLOG) && water.getContact() == 2) { water.noContact(); }
					if(temp.hasTag(Sprite.TURTLE) && water.getContact() == 3 || Turtle.getTurtlePresent()) { water.noContact(); }
				}
			}
		}
		
		// Check collisions between player and sprites, and response to them depends on which object is it 
		for(Sprite sprite: sprites) {	
			
			if(player.collides(sprite)) {
				if(sprite.hasTag(Sprite.HAZARD)) { player.onCollisionHazard(); }
				if(sprite.hasTag(Sprite.BULLDOZER)) { player.onCollisionBulldozers(delta, player.collidesRight(sprite)); }
				if(sprite.hasTag(Sprite.TREE)) { player.onCollisionTrees(); }
				if(sprite.hasTag(Sprite.WATER)) {
					Tile water = (Tile)sprite;
					if(water.getContact() == 0) { player.onCollisionHazard(); }
					if(water.getContact() == 1) { player.onCollisionLogs(delta, water.waterGetMoveRight()); break;}
					if(water.getContact() == 2) { player.onCollisionLongLogs(delta, water.waterGetMoveRight()); break;}
					if(water.getContact() == 3) { player.onCollisionTurtles(delta, water.waterGetMoveRight());break;}
				}
			}	
		}
}
		
	/**
	 * Render sprites in the game.
	 * @param g : Graphics.
	 */
	public void render(Graphics g) {
		
		for (Sprite sprite : sprites) {
			// If sprite is turtle, check whether turtle resurfaces
			if(sprite.hasTag(Sprite.TURTLE)) {
				if(Turtle.getTurtlePresent()) {
					sprite.render();
				}
			}else {
				sprite.render();
			}
		}
		
		for(ExtraLife extralife: extralifeGroup) {
			extralife.render();
		}
		
		for (Lives life : lifeGroup) {
			life.render();
		}
		
		for (Frog frog : goalGroup) {
			frog.render();
		}
	}
	
	

	
	/** 
	 * Add a frog image to the goal according to which goal it is.
	 * @param positionGoal : A number that denotes which goal the player meets.
	 */
	public static void addFrog(int positionGoal) {
		goalGroup.add(Frog.createFrog(positionGoal));
	}
	
	
	/**
	 * Add a life.
	 */
	public static void addLife() {
		
		if(lifeGroup.size() == 0) { 
			lifeGroup.add(Lives.createLife(LEFT_WALL, LIFE_ROW)); 
		}else { 
			lifeGroup.add(Lives.createLife(lifeGroup.get(lifeGroup.size() - 1).getX() + LIFE_OFFSET, LIFE_ROW));
		}
	}
	
	/**
	 * Remove a life, exit the game if there's no life to be removed.
	 */
	public static void removeLife() {
		if(lifeGroup.isEmpty()) {
			System.exit(0);
		}
		lifeGroup.remove(lifeGroup.size()-1);
	}
	
	
	//Keeps the player on the screen, when moving objects on water try to move player off the screen. 
	private void playerOnRideable() {
		// Check if player is in water region
		if(player.getY() <= MEETS_WATER) {
		
			if(player.getX() > App.SCREEN_WIDTH - LEFT_WALL) { player.setX(App.SCREEN_WIDTH - LEFT_WALL); }
			
			if(player.getX() < LEFT_WALL) { player.setX(LEFT_WALL); }
		}
	}
	
	
	//This method creates a sprite and returns it to an arraylist filled with sprites.
	private Sprite createObject(String[] array) {
		
		// store first column as object name
		String objectName = array[0];
		
		// build image source
		String imagesrc = "src/main/resources/"+objectName+".png";
		
		// store second and third column as position
		float x = Float.parseFloat(array[1]);
		float y = Float.parseFloat(array[2]);
		
		// default moveRight
		boolean moveRight = false;
		
		// if there is four column, parse last one to get moveRight
		
		if(array.length == 4) { moveRight = Boolean.parseBoolean(array[3]); }
		
		// creates object based on object name in the first column
		
		if (objectName.equals("water")) { return Tile.createWaterTile(x, y); }
		if (objectName.equals("grass")) { return Tile.createGrassTile(x, y); }
		if (objectName.equals("tree")) { return Tile.createTreeTile(x, y); } 
		if (objectName.equals("bus")) { return Bus.createBus(imagesrc, x, y, moveRight); }  
		if (objectName.equals("racecar")) { return Racecar.createRacecar(imagesrc, x, y, moveRight); } 
		if (objectName.equals("bike")) { return Bike.createBike(imagesrc, x, y, moveRight); } 
		if (objectName.equals("log")) { return Log.createLog(imagesrc, x, y, moveRight); } 
		if (objectName.equals("longLog")) { return LongLog.createLongLog(imagesrc, x, y, moveRight); }
		if (objectName.equals("turtle")) { return Turtle.createTurtle("src/main/resources/turtles.png", x, y, moveRight); }
		if (objectName.equals("bulldozer")) { return Bulldozer.createBulldozer(imagesrc, x, y, moveRight); } 
		
		return null;
	}
	
	
	// Start the level, create objects based on level file.
	private void startLevel(String level) {
		
		// If starting next level, clear used objects from previous level file first.
		if(level == "src/main/resources/levels/1.lvl") {
			
			sprites.clear();
			waterGroup.clear();
			goalGroup.clear();
			lifeGroup.clear();
			allLogsGroup.clear();
			waterMovingObjGroup.clear();
			
			extralifeGroup.clear();
			checkExtraLife = false;			
		}
		
		// Process the level file and insert objects created into 'sprites' arraylist. 
		try (Scanner scanner = new Scanner (new FileReader(level))) {
			
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] arr = line.split(",");
				
				sprites.add(createObject(arr));
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		// Add player and lives objects manually
		sprites.add(new Player(App.SCREEN_WIDTH / 2, App.SCREEN_HEIGHT - TILE_SIZE));
		lifeGroup.add(Lives.createLife(LEFT_WALL, LIFE_ROW));
		lifeGroup.add(Lives.createLife(LEFT_WALL + LIFE_OFFSET, LIFE_ROW));
		lifeGroup.add(Lives.createLife(LEFT_WALL + LIFE_OFFSET * 2, LIFE_ROW));
		
		// Make several other arraylists with objects in 'sprites' arraylist for flexible and easier collision detection.
		groupWaterTiles();
		groupWaterMovingObj();
		groupAllLogs();
		
		// Extract single player object out for easier collision detection. 
		extractPlayer();
	}
	
	
	// Group all moving objects on water into an arraylist for easier collision detections. 
	private void groupWaterMovingObj() {
		for(Sprite sprite: sprites) {
			if(sprite.hasTag(Sprite.LONGLOG) || sprite.hasTag(Sprite.LOG) || sprite.hasTag(Sprite.TURTLE)) {
				waterMovingObjGroup.add((MovingObject)sprite);
			}
		}
	}
	
	
	// Group all logs and longlogs into an arraylist for easier collision detections. 
	private void groupAllLogs() {
		for(Sprite sprite: sprites) {
			if(sprite.hasTag(Sprite.LONGLOG) || sprite.hasTag(Sprite.LOG)) {
				allLogsGroup.add((MovingObject)sprite);
			}
		}
		
	}
	

	// Group all water tiles into an arraylist for easier collision detections.
	private void groupWaterTiles() {
		for(Sprite sprite: sprites) {
			if(sprite.hasTag(Sprite.WATER)) {
				waterGroup.add((Tile)sprite);
			}
		}
		
	}
	
	
	// Extract out the player object from sprites arraylist for easier collision detections.
	private void extractPlayer() {
		for(Sprite sprite: sprites) {
			if(sprite.hasTag(Sprite.PLAYER)) {
				player = (Player)sprite;
				break;
			}
		}
	}
}


