package byow.lab12;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import byow.Core.Engine;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;


/**
 *  Draws a world that is mostly empty except for a small region.
 */
public class BoringWorldDemo {

    private static final int WIDTH = 80;
    private static final int HEIGHT = 50;
    private static TETile[][] positionOfRoom;
    private static TETile[][] world;
    private static int ROOM_NUM = 10;
    private static Random random; 
    
    private static Position RandomStart(TETile[][] world) { 
    	Random random = new Random(2873123);
    	
    	while(true) {
    		int xPos = random.nextInt(WIDTH - 2);
        	int yPos = random.nextInt(HEIGHT - 2);
        	
        	xPos = xPos%2 == 1 ? xPos: xPos + 1;
        	yPos = yPos%2 == 1 ? yPos: yPos + 1;
	    	if(world[xPos][yPos] == Tileset.NOTHING) {
	    		return new Position(xPos,yPos);
	    	}
    	}
    }
    
    public static void generateHallWay(TETile[][] world, Position start) {
    	List<Position> positionList = new ArrayList<>();
    	positionList.add(start);
    	
    	Random random = new Random(3873123);
    	
    	while(!positionList.isEmpty()) {
    		int index = random.nextInt(positionList.size());
    		Position curPos = positionList.get(index);
    		
    		int curX = curPos.x;
    		int curY = curPos.y;
    		world[curX][curY] = Tileset.FLOOR;
    		
    		PrimGenerateHallWay(curPos, world, positionList);
    		positionList.remove(index);
    	}
    }
    
    public static void PrimGenerateHallWay(Position CurPos, TETile[][] world, List<Position> positionList) {
    	int[][] nextDirection = {
    		{2,0}, {0, -2}, {-2,0}, {0,2}
    	};
    	
    	Random random = new Random(3873123);
    	boolean[] visited = new boolean[4];
    	
    	boolean isConnected = false; 
    	
    	while(visited[0]==false || visited[1] == false || visited[2] == false || visited[3] == false) {
    		int next = random.nextInt(4);
    		
    		if(visited[next]) {
    			continue;
    		}
    		visited[next] = true; 
    		
    		int nextX = CurPos.x + nextDirection[next][0];
    		int nextY = CurPos.y + nextDirection[next][1];
    		
    		if(nextX < 0 || nextX > world.length - 1 || nextY < 0 || nextY > world[0].length - 1 ) {
    			continue; 
    		}
    		
    		if(Tileset.NOTHING.equals(world[nextX][nextY])) {
    			world[nextX][nextY] = Tileset.GRASS;
    			positionList.add(new Position(nextX,nextY));
    		}
    		
    		if(Tileset.FLOOR.equals(world[nextX][nextY]) && isConnected == false) {
    			int mx = (CurPos.x + nextX) / 2; 
    			int my = (CurPos.y + nextY) / 2; 
    			
    			world[mx][my] = Tileset.FLOOR;
    			isConnected = true; 
    		}
    	}
    }
    
    
    public static void fillWithRooms() {
    	byow.Core.Room room = new byow.Core.Room(random);
    	for(int i = 0; i < ROOM_NUM; i++) {
    		if((room.yPos + room.height + 1 >= HEIGHT) || (room.xPos + room.width + 1 >= WIDTH)) {
    			continue; 
    		}
    		if (isOverLap(room.xPos, room.yPos, room.width, room.height)) {
    			continue; 
    		}
    		
    		buildRoom(room.xPos, room.yPos, room.width, room.height);
    	}
    }
    
    public static boolean isOverLap(int x, int y, int width, int height) {
    	byow.Core.Room room = new byow.Core.Room(random);
    	for(int i = x; i <= x + width + 1; i++) {
    		for(int j = y; j <= y + height + 1; j++) {
    			if(positionOfRoom[i][j] == Tileset.WALL || positionOfRoom[i][j] == Tileset.FLOOR) {
    				return true; 
    			}
    		}
    	}
  
    	return false; 
    }
    
    
    public static void buildRoom(int x, int y, int width, int height) {
    	for(int i = x; i <= x + width + 1; i++) {
    		for(int j = y; j <= y + height; j++) {
    			if(i == x || i == x + width + 1 || j == y || j == y + height + 1) {
    				world[i][j] = Tileset.WALL;
    				positionOfRoom[i][j] = Tileset.WALL;
    				continue; 
    			}
    			world[i][j] = Tileset.FLOOR;
    			positionOfRoom[i][j] = Tileset.FLOOR;
    		}
    	}
    }
    
    
    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        random = new Random(2873123);
        // initialize tiles
        world = new TETile[WIDTH][HEIGHT];
        
//        for (int x = 0; x < WIDTH; x += 1) {
//            for (int y = 0; y < HEIGHT; y += 1) {
//                world[x][y] = Tileset.NOTHING;
//            }
//        }
        
        for (int x = 0; x < WIDTH; x ++ ) {
            for (int y = 0; y < HEIGHT; y ++) {
                world[x][y] = Tileset.WALL;
            }
        }


        // fills in a block 14 tiles wide by 4 tiles tall
        for (int x = 1; x < WIDTH - 1; x += 2) {
            for (int y = 1; y < HEIGHT - 1; y += 2) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        
 
        generateHallWay(world, RandomStart(world));
        byow.Core.Room room = new byow.Core.Room(random);
        positionOfRoom = new TETile[WIDTH][HEIGHT];
        //buildRoom(room.xPos,room.yPos,room.width,room.height);
        fillWithRooms();
        // draws the world to the screen
        ter.renderFrame(world);
    }
    
}

class Position{
 	int x; 
 	int y; 
 	
 	Position(int x, int y){
 		this.x = x; 
 		this.y = y; 
 	}
 	
 	public Position shift(int dx, int dy) {
 		return new Position(this.x + dx, this.y + dy);
 	}
 }
