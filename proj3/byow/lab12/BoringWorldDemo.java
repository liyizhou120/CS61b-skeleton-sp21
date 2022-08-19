package byow.lab12;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

/**
 *  Draws a world that is mostly empty except for a small region.
 */
public class BoringWorldDemo {

    private static final int WIDTH = 80;
    private static final int HEIGHT = 50;

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
