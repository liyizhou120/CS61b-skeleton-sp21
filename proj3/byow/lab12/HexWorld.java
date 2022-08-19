package byow.lab12;
import org.junit.Test;
import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
		private static final int WIDTH = 50;
	    private static final int HEIGHT = 50;

	    private static final long SEED = 2873123;
	    private static final Random RANDOM = new Random(SEED);
	    
	    public static void drawRow(TETile[][] tiles,Position p, TETile tile, int length) {
	    	for(int dx = 0; dx < length; dx++) {
	    		tiles[p.x + dx][p.y] = tile; 
	    	}
	    }
	    
	    /**
	     * Fills the given 2D array of tiles with Nothing tiles.
	     * @param tiles
	     */
	    public static void fillBoardWithNothing(TETile[][] tiles) {
	        int height = tiles[0].length;
	        int width = tiles.length;
	        for (int x = 0; x < width; x += 1) {
	            for (int y = 0; y < height; y += 1) {
	                tiles[x][y] = Tileset.NOTHING;
	            }
	        }
	    }

	    //private helper class to deal with positions
     private static class Position{
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
     
     //Draws the hexgonal world
     public static void addHexgon(TETile[][] tiles, TETile tile, Position p, int size) {
     	if(size < 2) {
     		return; 
     	}
     	
     	addHexgonHelper(tiles, p , tile, size - 1, size);
     	
     }
     
     public static void addHexgonHelper(TETile[][] tiles, Position p, TETile tile, int b, int t) {
     	// Draw current row 
    	Position startOfRow = p.shift(b, 0);
     	drawRow(tiles, startOfRow, tile, t);
     	
     	//Draw remaining row recursively 
     	
     	if(b > 0) {
     		Position nextPos = p.shift(0, -1);
     		addHexgonHelper(tiles, nextPos, tile, b - 1, t + 2);
     	}
     	
     	
     	//Draw this row again to be the reflection 
     	Position startOfReflectedRow = startOfRow.shift(0, -(2*b + 1));
     	drawRow(tiles, startOfReflectedRow, tile, t);
     	
     }
     
     
    /**
     *  adds a column of Hexgons, each of whose biomes are chosen randomly 
     * to the world at Position P. Each of the hexgon are of size SIZE
     * @param tiles
     * @param p
     * @param size
     */
     
     public static void addHexCol(TETile[][] tiles, Position p, int size, int num) {
    	 if(num < 1) {
    		 return; 
    	 }
    	 
    	 //Draw this hexgon
    	 
    	 addHexgon(tiles, randomTile(), p, size);
    	 if(num > 1) {
    		 Position bottomNeighbor = getBottomNeighbor(p, size);
    		 addHexCol(tiles, bottomNeighbor, size, num - 1);
    	 }
     }
     
     /**
      * Gets the position of the bottom neighbor at position p 
      */
     public static Position getBottomNeighbor(Position p, int size) {
    	 	Position posNeighbor = p.shift(0, -2 * size);
    	 	return posNeighbor; 
     }
     
     /**
      * Gets the neighbor of top right neighbor of a hexgon at point P 
      * @param p
      * @param size
      * @return
      */
     
     
     
     public static Position getTopRightNeighbor(Position p, int size) {
 	 	Position posNeighbor = p.shift(2 * size - 1, size);
 	 	return posNeighbor; 
     }
     
     
     /**
      * Gets the neighbor of bottom right neighbor of a hexgon at point P  
      * @param p
      * @param size
      * @return
      */
     

     public static Position getBottomRightNeighbor(Position p, int size) {
 	 	Position posNeighbor = p.shift(2 * size - 1, - size);
 	 	return posNeighbor; 
     }
     
     
     /**
      * Draw the world
      * @param tiles
      */
     
     public static void drawWorld(TETile[][] tiles, Position p, int hexSize, int tessSize) {
     	 
//     	 Position p = new Position(20, 40);
//     	 //addHexgon(tiles,randomTile(), p, 4);
//     	 addHexCol(tiles, p, 3, 4);
    	 
    	 //Draw the first Hexgon 
    	 
    	 addHexCol(tiles, p, hexSize, tessSize);
    	 
    	 //Expand up and to right 
    	 for(int i = 1; i < tessSize; i++) {
    		  p = getTopRightNeighbor(p, hexSize);
    		  addHexCol(tiles, p, hexSize, tessSize + i);
    	 }
    	 
    	 //Expand down and to right
    	 for(int i = tessSize - 2; i >= 0 ; i--) {
    		 p = getBottomRightNeighbor(p, hexSize);
    		 addHexCol(tiles, p, hexSize, tessSize + i);
    	 }
    	 
     }
	 
     
     /**
      * Picks a random biome tile  
      */
     
     private static TETile randomTile() {
    	 int TileNum = RANDOM.nextInt(5);
    	 switch(TileNum) {
	    	 case 0: return Tileset.GRASS;
	    	 case 1: return Tileset.FLOWER;
	    	 case 2: return Tileset.SAND;
	    	 case 3: return Tileset.MOUNTAIN;
	    	 case 4: return Tileset.TREE;
	    	 default: return Tileset.NOTHING;
    	 }
    	 
    	 
    	 
     }

	    public static void main(String[] args) {
	        TERenderer ter = new TERenderer();
	        ter.initialize(WIDTH, HEIGHT);

//	        TETile[][] randomTiles = new TETile[WIDTH][HEIGHT];
//	        fillBoardWithNothing(randomTiles);
//	        ter.renderFrame(randomTiles);
	    	
	        TETile[][] world = new TETile[WIDTH][HEIGHT];
	        fillBoardWithNothing(world);
	        Position anchor = new Position(10, 30);
	        drawWorld(world, anchor, 3, 3); 
	    	 
	        ter.renderFrame(world);
	    }

}
