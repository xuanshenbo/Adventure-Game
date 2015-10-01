package model.logic;

/**
 * The Clock Thread is responsible for producing a consistent "pulse" which is
 * used to update the game state, and refresh the display. Setting the pulse
 * rate too high may cause problems, when the point is reached at which the work
 * done to service a given pulse exceeds the time between pulses.
 * 
 * @author djp
 * 
 */
public class Clock extends Thread {
	private final int delay; // delay between pulses in us
	private Game game;
	
	public Clock(int delay, Game game) {
		this.game = game;
		this.delay = delay;
	}
	
	public void run() {
		while(true) {
			// Loop forever			
			try {
				Thread.sleep(delay);
				game.tick();
			} catch(InterruptedException e) {
				// should never happen
			}			
		}
	}
}
