/**
 * The main Game logic class, this holds the game tick and has the logical
 * updates that have to be done every tick
 */

package logic;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import state.Area;
import state.GameState;
import state.Player;
import state.Position;
import tiles.Tile;
import static utilities.PrintTool.p;

public class Game{

	private GameState gameState;

	public Game(GameState state) {
		this.gameState = state;
		/*while(true){
			int direction = Input.getInputBetween(1,4);
			move(gameState.getPlayer(1), direction);
		}*/
	}


	/**
	 * This method moves the player in the direction that is passed in.
	 * @param p: player to move
	 * @param n: direction to move in.
	 */
	public void move(Player player, int direction){
		Position playerPosition = player.getPosition();
		Tile toTile = destinationTile(direction, player);

		if(toTile != null){
			p("toTile: "+toTile.getType());
			p("toTile Position: "+toTile.getPosition());
			p("current tile: "+player.getPosition());
			toTile.move(player, direction);
		}
//		gameState.printState();
	}

	public List<char[][]> getGameView(int id){
		Player player = gameState.getPlayer(id);
		return gameState.getGameView(player);
	}

	public Player getPlayer(int id){
		return gameState.getPlayer(id);
	}
	public ArrayList<Player> getPlayerList() {
		return gameState.getPlayerList();
	}

	/**
	 * This method will return the Tile the that is in the direction
	 * the player want to move in
	 * @param direction: direction of the player
	 * @param player: player that is moving
	 * @return: Tile of destination
	 */
	private Tile destinationTile(int direction, Player player) {
		int x = player.getPosition().getX();
		int y = player.getPosition().getY();
		Tile[][] areaArray = gameState.getWorld(player).getArea();

		if(direction == 1){//up
			if(x == 0){
				return null;
			}
			return areaArray[x-1][y];

		}else if(direction == 2){//down
			if(x == areaArray.length-1){
				return null;
			}
			return areaArray[x+1][y];

		}else if(direction == 3){//right
			if(y == areaArray[0].length-1){
				return null;
			}
			return areaArray[x][y+1];

		}else if(direction == 4){//left
			if(y == 0){
				return null;
			}
			return areaArray[x][y-1];
		}
		return null;
	}


//	/**
//	 * This method returns whether this is a base tile that the player
//	 * can move onto.
//	 * that they are in
//	 * @param tileType: The tile type that needs to be checked
//	 * @return: If it is a base tile that the player can move into
//	 */
//	private boolean isEmptyTile(TileType tileType) {
//		return (tileType.equals(TileType.GRASS) || tileType.equals(TileType.ROCK) || tileType.equals(TileType.WOOD));
//	}


	/**
	 * Private class that is for testing to simulate the key presses that will
	 * come from to GUI.
	 * @author tuckergare
	 *
	 */
	public static class Input {

	    public static int getInputBetween(int minValue, int maxValue) {
	        Scanner reader = new Scanner(System.in);
	        int choice = -1;

	        while (!(choice >= minValue && choice <= maxValue)) {
	            try {
	                choice = reader.nextInt();
	            } catch (java.util.InputMismatchException e) {
	                reader.next();
	            }
	        }
	        return choice;
	    }

	}



	public void clockTick() {
		// TODO Auto-generated method stub
	}

	// ================================================
	// getters from here
	// ================================================

	public GameState getGameState() {
		 return gameState;
	}





}
