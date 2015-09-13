/**
 * The main class that stores the state of the game.
 */

package state;

import java.util.ArrayList;
import static utilities.PrintTool.p;

import renderer.Renderer;

public class GameState {

	private ArrayList<Player> PlayerList = new ArrayList<Player>(); // list of players in the game
	private Area world; // The game world
	private Renderer renderer;

	public GameState(Area w, Renderer r){
		this.world = w;
		this.renderer = r;
	}

	public void addPlayer(Player p){
		PlayerList.add(p);
	}

	public Area getWorld(){
		p("help");
		return world;
	}
}
