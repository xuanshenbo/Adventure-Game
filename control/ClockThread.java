/**
 * This class creates a constant pulse that is used to update the
 * game.
 */

package control;

import model.logic.Game;
import renderer.GameCanvas;
import renderer.GameRenderer;
import view.GameFrame;

/**
 * ClockThread is a clock trigger to display the game at a certain rate by updating the GameFrame
 * @author yanlong
 *
 */
public class ClockThread extends Thread{

	final int delay; // the delay between pulses.
	//final Game game;
	final GameCanvas canvas;

	public ClockThread(int d, GameCanvas canvas){
		this.delay = d;
		//this.game = g;
		this.canvas = canvas;
	}

	public void run(){
		while(1 == 1) {
			// Loop forever
			try {
				Thread.sleep(delay);
				//game.tick();
				if(canvas != null) {
					canvas.getRenderer().render();
				}
			} catch(InterruptedException e) {
				// should never happen
			}
		}
	}
}
