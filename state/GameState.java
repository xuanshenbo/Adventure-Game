/**
 * The main class that stores the state of the game.
 */

package state;

import java.util.ArrayList;
import static utilities.PrintTool.p;

import renderer.Renderer;

public class GameState {

	private ArrayList<Player> playerList = new ArrayList<Player>(); // list of players in the game
	private Area area; // The game world

	public GameState(Area a, ArrayList<Player> p){
		this.area = a;
		this.playerList = p;
		printState();
	}

	public void addPlayer(Player p){
		playerList.add(p);
	}

	public Area getWorld(){
		return area;
	}
	
	public void printState(){
		Tile[][] a = area.getArea();
		for(int row = 0; row<a.length; row++){
			for(int col = 0; col<a[0].length; col++){
				boolean playerPos = false;
				for(Player p: playerList){
					if(p.getPosition().getAreaX() == row && p.getPosition().getAreaY() == col){
						System.out.print(p);
						playerPos = true;
					}
				}
				if(a[row][col] == null && !playerPos){
					System.out.print(area.getType().id);
				}else if(!playerPos){
					System.out.print(a[row][col]);
				}
			}
			System.out.println("");
		}
	}
}
