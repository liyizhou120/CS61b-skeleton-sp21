package gh2;
import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

public class GuitarHero {
	public static final String KEYBOARD = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
	public static final int KEYBOARD_NUM = KEYBOARD.length();
	
	public static void main(String[] args) {
		GuitarString[] guitarStrings = new GuitarString[KEYBOARD_NUM];
		for(int i = 0; i < KEYBOARD_NUM; i++) {
			double frequency = 440 * Math.pow(2, (i - 24)/12.0);
			guitarStrings[i] = new GuitarString(frequency);
		}
		
		while(true) {
			  /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int keyIndex = KEYBOARD.indexOf(key);
                
                if(keyIndex > 0) {
                	guitarStrings[keyIndex].pluck();
                }
            }
            /* compute the superposition of samples */
            double sample = 0.0;

            /* play the sample on standard audio */
            for(GuitarString s : guitarStrings) {
            	sample += s.sample();
            }
            StdAudio.play(sample);
            
            for(GuitarString s : guitarStrings) {
            	s.tic();
            }
		}
		
	}
	
}
