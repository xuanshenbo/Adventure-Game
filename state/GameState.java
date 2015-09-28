/**
 * The main class that stores the state of the game.
 */

package state;

import java.util.ArrayList;

import tiles.Tile;
import static utilities.PrintTool.p;

public class GameState {

	private ArrayList<Player> playerList = new ArrayList<Player>(); // list of players in the game
	private Area area; // The game world
	private int viewPortSize = 15;



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

	public ArrayList<Player> getPlayerList(){
		return playerList;
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
	 * This methods returns a 15 by 15 array of tiles that are around the player,
	 * this is for the network to send down the pipe to the client to draw.
	 * @param player
	 * @return
	 */
	public String[][] getGameView(Player player){
		String[][] view = new String[viewPortSize][viewPortSize];
		Area a = getArea(player);
		int left = player.getPosition().getY() - (viewPortSize/2);
		int right = player.getPosition().getY() + (viewPortSize/2);
		int top = player.getPosition().getX() - (viewPortSize/2);
		int bottom = player.getPosition().getX() + (viewPortSize/2);

		int r = 0;
		int c = 0;
		for(int row = top; row < bottom+1; row++){
			for(int col = left; col < right+1; col++){
				if(row>-1 && col >-1 && row <a.getTileArray().length && col < a.getTileArray()[0].length){
					boolean playerPos = false;

					for(Player p: playerList){
						if(p.getPosition().getX() == row && p.getPosition().getY() == col && p.getPosition().getArea() == a){
							view[r][c] = Integer.toString(p.getId());
							playerPos = true;
						}
					}
					if(!playerPos){
						view[r][c] = Character.toString(a.getTileArray()[row][col].getType());
					}
				}
				c++;
			}
			c=0;
			r++;
		}
		return view;
	}














	/**
	 * This method prints out the game state to the console, it is mainly
	 * used for debugging.
	 */
	public void printState(){
		Tile[][] a = area.getTileArray();
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

			Tile[][] innerTiles = innerArea.getTileArray();

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


		String[][] playerOneView = getGameView(playerList.get(0));
		System.out.println("\nPlayer 1 view");
		for(int row = 0; row<playerOneView.length; row++){
			for(int col = 0; col<playerOneView[0].length; col++){
				if(playerOneView[row][col] != null){
					System.out.print(playerOneView[row][col]);
				}else{
					System.out.print("N");
				}
			}
			System.out.println("");
		}
	}















}
