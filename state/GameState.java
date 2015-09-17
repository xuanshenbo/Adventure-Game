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
	
	/**
	 * Goes through the player list and checks the players id
	 * @param id: the id of the player to return
	 * @return: the player with the id or null if no player with 
	 * that id
	 */
	public Player getPlayer(int id) {
		for(Player p: playerList){
			if(p.getId() == id){
				return p;
			}
		}
		return null;
	}

	public Area getArea(Player player) {
		if(player.getPosition().getArea() == area){
			return area;
		} else{
			for(Area childArea: area.getInternalAreas()){
				if(player.getPosition().getArea() == childArea){
					return childArea;
				}
			}
		}
		return null;
	}














	/**
	 * This method prints out the game state to the console, it is mainly
	 * used for debugging.
	 */
	public void printState(){
		Tile[][] a = area.getArray();
		for(int row = 0; row<a.length; row++){
			for(int col = 0; col<a[0].length; col++){
				boolean playerPos = false;
				for(Player p: playerList){
					if(p.getPosition().getX() == row && p.getPosition().getY() == col && p.getPosition().getArea() == area){
						System.out.print(p);
						playerPos = true;
					}
				}
				if(!playerPos){
					System.out.print(a[row][col]);
				}
			}
			System.out.println("");
		}
		for(Area innerArea: area.getInternalAreas()){
			System.out.println("");
			System.out.println(innerArea.getEntrance());

			Tile[][] innerTiles = innerArea.getArray();

			for(int row = 0; row<innerTiles.length; row++){
				for(int col = 0; col<innerTiles[0].length; col++){
					boolean playerPos = false;
					for(Player p: playerList){
						if(p.getPosition().getX() == row && p.getPosition().getY() == col && p.getPosition().getArea() == innerArea){
							System.out.print(p);
							playerPos = true;
						}
					}
					if(!playerPos){
						System.out.print(innerTiles[row][col]);
					}				
				}
				System.out.println("");
			}
		}
	}















}
