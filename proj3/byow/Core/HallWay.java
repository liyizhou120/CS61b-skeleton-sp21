package byow.Core;

import java.util.Random;

public class HallWay {
	
	private int hallwayLength; 
	private int startXPos; 
	private int startYPos;
	private Direction direction; 
	private Random random; 
	private HallWay incidentHallWay; 
	
	
	
	public enum Direction{
		North, East, South, West, 
	}
}
