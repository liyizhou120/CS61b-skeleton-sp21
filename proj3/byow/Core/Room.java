package byow.Core;

import java.util.ArrayList;
import java.util.Random;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Room {
	
	private static final int ROOM_MAX_SIZE = 8; 
	private static final int ROOM_MIN_SIZE = 5; 
	private static final int ROOM_NUM = 100; 
	
    private int xPos;
    private int yPos;
    private int width; 
    private int height; 
    private Random random;
    private static ArrayList<Room> Rooms;
    private static TETile[][] positionOfRoom;
    
    
    
    
    public Room(Random random) {
    	this.random = random;
    	this.xPos = random.nextInt(Engine.WIDTH);
    	this.yPos = random.nextInt(Engine.HEIGHT);
    	this.width = RandomUtils.uniform(random, ROOM_MIN_SIZE, ROOM_MAX_SIZE);
    	this.height = RandomUtils.uniform(random, ROOM_MIN_SIZE, ROOM_MAX_SIZE);
  
    }
    
    public void fillWithRooms() {
    
    	for(int i = 0; i < ROOM_NUM; i++) {
    		if((yPos + height + 1 >= Engine.HEIGHT) || (xPos + width + 1 >= Engine.WIDTH)) {
    			continue; 
    		}
    		if (isOverLap(xPos, yPos, width, height)) {
    			continue; 
    		}
    		
    		buildRoom(xPos, yPos, width, height);
    		Rooms.add(new Room(random));
    	}
    	
    }
    
    public boolean isOverLap(int x, int y, int width, int height) {
    	for(int i = x; i <= x + width + 1; i++) {
    		for(int j = y; j <= y + height + 1; j++) {
    			if(positionOfRoom[i][j] == Tileset.WALL || positionOfRoom[i][j] == Tileset.FLOOR) {
    				return true; 
    			}
    		}
    	}
  
    	return false; 
    }
    
    
    public void buildRoom(int x, int y, int width, int height) {
    	for(int i = x; i <= x + width + 1; i++) {
    		for(int j = y; j <= y + height; j++) {
    			if(i == x || i == x + width + 1 || j == y || j == y + height + 1) {
    				Engine.world[i][j] = Tileset.WALL;
    				positionOfRoom[i][j] = Tileset.WALL;
    				continue; 
    			}
    			Engine.world[i][j] = Tileset.FLOOR;
    			positionOfRoom[i][j] = Tileset.FLOOR;
    		}
    	}
    }
}
