/**
 * This class creates a constant pulse that is used to update the
 * game.
 */

package control;

import model.logic.Game;
import renderer.GameCanvas;
import renderer.GameRenderer;
import view.frames.GameFrame;

/**
 * ClockThread is a clock trigger to display the game at a certain rate by updating the GameFrame
 * @author yanlong, Mingmin Ying
 *
 */
public class ClockThread extends Thread{

	final int delay; // the delay between pulses.
	final GameCanvas canvas;

	/**
	 * The following constructs a ClockThread with the given delay parameter. It will update the given GameCanvas.
	 * @param d The delay time of the thread.
	 * @param canvas The GameCanvas of the game.
	 */
	public ClockThread(int d, GameCanvas canvas){
		this.delay = d;
		this.canvas = canvas;
	}

	/**
	 * The following runs a eternal loop to keep updating the game canvas.
	 * It updates 1000/delay times a second.
	 */
	public void run(){
		while(1 == 1) {
			// Loop forever
			try {
				Thread.sleep(delay);
				if(canvas != null) {
					canvas.getRenderer().render();
				}
			} catch(InterruptedException e) {
				// should never happen
			}
		}
	}
}
