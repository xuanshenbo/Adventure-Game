/**
 * This class creates a constant pulse that is used to update the
 * game.
 */

package control;

import logic.StartGame;
import renderer.Renderer;

public class ClockThread extends Thread{

	final int delay; // the delay between pulses.
	final StartGame game;
	final Renderer renderer;

	public ClockThread(int d, StartGame g, Renderer r){
		this.delay = d;
		this.game = g;
		this.renderer = r;
	}

	public void run(){
		while(1 == 1) {
			// Loop forever
			try {
				Thread.sleep(delay);
				game.clockTick();
				if(renderer != null) {
					renderer.repaint();
				}
			} catch(InterruptedException e) {
				// should never happen
			}
		}
	}
}
