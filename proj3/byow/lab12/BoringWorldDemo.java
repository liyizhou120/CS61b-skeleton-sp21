package byow.lab12;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;


/**
 *  Draws a world that is mostly empty except for a small region.
 */
public class BoringWorldDemo {

    private static final int WIDTH = 80;
    private static final int HEIGHT = 50;
    
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
    	Random random = new Random(3873123);
    	while(!positionList.isEmpty()) {
    		
    	}
    }
    
    
    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        
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
