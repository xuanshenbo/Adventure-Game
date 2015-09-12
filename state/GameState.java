/**
 * The main class that stores the state of the game.
 */

package state;

import java.util.ArrayList;

public class GameState {

	private ArrayList<Player> Players = new ArrayList<Player>(); // list of players in the game
	private World world; // The game world

	public GameState(World w){
		this.world = w;
	}

	public void addPlayer(Player p){
		Players.add(p);
	}

}
