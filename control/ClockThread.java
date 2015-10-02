/**
 * This class creates a constant pulse that is used to update the
 * game.
 */

package control;

import GUI.GameFrame;
import model.logic.Game;
import renderer.GameRenderer;

/**
 * ClockThread is a clock trigger to display the game at a certain rate by updating the GameFrame
 * @author yanlong
 *
 */
public class ClockThread extends Thread{

	final int delay; // the delay between pulses.
	//final Game game;
	final GameFrame frame;

	public ClockThread(int d, GameFrame f){
		this.delay = d;
		//this.game = g;
		this.frame = f;
	}

	public void run(){
		while(1 == 1) {
			// Loop forever
			try {
				Thread.sleep(delay);
				//game.tick();
				if(frame != null) {
					frame.repaint();
				}
			} catch(InterruptedException e) {
				// should never happen
			}
		}
	}
}
