package byow.lab13;

import byow.Core.RandomUtils;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    /** The width of the window of this game. */
    private int width;
    /** The height of the window of this game. */
    private int height;
    /** The current round the user is on. */
    private int round;
    /** The Random object used to randomly generate Strings. */
    private Random rand;
    /** Whether or not the game is over. */
    private boolean gameOver;
    /** Whether or not it is the player's turn. Used in the last section of the
     * spec, 'Helpful UI'. */
    private boolean playerTurn;
    /** The characters we generate random Strings from. */
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    /** Encouraging phrases. Used in the last section of the spec, 'Helpful UI'. */
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
//        if (args.length < 1) {
//            System.out.println("Please enter a seed");
//            return;
//        }

//        long seed = Long.parseLong(args[0]);
    	long seed = 2873123;
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();

    }

    public MemoryGame(int width, int height, long seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        rand = new Random(seed);
        
        //TODO: Initialize random number generator
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
//    	char[] newCharArray = new char[n + 1]; 
//    	for(int i = 0; i < n ; i++) {
//    		int next = rand.nextInt();
//    		newCharArray[i] = CHARACTERS[next];
//    	}
//    	
//    	String randomString = newCharArray.toString();
//    	
//        return randomString;
    	StringBuilder sb = new StringBuilder();
    	while(sb.length() < n) {
    		sb.append(CHARACTERS[rand.nextInt(CHARACTERS.length)]);
    	}
    	
    	return sb.toString();
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
    	
    	 int midWidth = width / 2; 
    	 int midHeight = height / 2; 
    	 
    	 StdDraw.clear();
    	 StdDraw.clear(Color.BLACK);
    	 
    	 //Draw GUI 
    	 if(!gameOver) {
    		 Font uiFont = new Font("Monaco", Font.BOLD, 20);
    		 StdDraw.setFont(uiFont);
    		 StdDraw.textLeft(1, height - 1, "Round: " + round);
    		 StdDraw.text(midWidth, height - 1, playerTurn? "Type!" : "Watch");
    		 StdDraw.textRight(width - 1, height - 1, ENCOURAGEMENT[round%ENCOURAGEMENT.length]);
    		 StdDraw.line(0, height - 2, width, height - 2);
    	 }
    	 
    	 Font letterFont =  new Font("Monaco", Font.BOLD, 30);
    	 StdDraw.setFont(letterFont);
    	 StdDraw.setPenColor(Color.white);
    	 StdDraw.text(midWidth, midHeight, s);
    	 StdDraw.show();
    	 
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
    	for(char letter: letters.toCharArray()) {
    		String s = Character.toString(letter);
    		drawFrame(s);
    		StdDraw.pause(1000);
    		StdDraw.clear();
    		StdDraw.pause(500);
    	}
    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        String input = ""; 
        drawFrame(input);
    	
        while(input.length() < n) {
//        	if(!StdDraw.hasNextKeyTyped()) {
//        		continue; 
//        	}
          if(StdDraw.hasNextKeyTyped()) {
        	char key = StdDraw.nextKeyTyped();
        	input += String.valueOf(key);
        	drawFrame(input); 
          }
        }
        
        StdDraw.pause(500);
    	return input;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        //TODO: Establish Engine loop
    	gameOver = false; 
    	playerTurn = false; 
    	round = 1; 
    	
    	while(!gameOver) {
    		playerTurn = false; 
    		drawFrame("Round" + round + "! Good Luck"); 
    		StdDraw.pause(1500);

    		String roundString = generateRandomString(round);
    		flashSequence(roundString);
    		
    		playerTurn = true; 
    		String userInput = solicitNCharsInput(round);
    		
    		if(!userInput.equals(roundString)) {
    			gameOver = true; 
    			drawFrame("Game Over! Final Level: " + round);
    		}else {
    			gameOver =false; 
    			drawFrame("Correct! Next Level");
    			StdDraw.pause(500);
    			round+=1;
    		}
    	}
    }

}
