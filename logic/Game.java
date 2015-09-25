/**
 * The main Game logic class, this holds the game tick and has the logical
 * updates that have to be done every tick
 */

package logic;
import java.util.Scanner;

import state.Area;
import state.Direction;
import state.GameState;
import state.Player;
import state.Position;
import state.Tile;
import state.Tile.TileType;
import static utilities.PrintTool.p;

public class Game{

	private GameState gameState;

	public Game(GameState state) {
		this.gameState = state;
		while(true){
			int direction = Input.getInputBetween(1,4);
			move(gameState.getPlayer(1), direction);
		}
	}


	/**
	 * This method moves the player in the direction that is passed in.
	 * @param p: player to move
	 * @param n: direction to move in.
	 */
	public void move(Player player, int direction){
		Position playerPosition = player.getPosition();
		int x = playerPosition.getX();
		int y = playerPosition.getY();
		Area currentArea = playerPosition.getArea();
		Position newPosition;
		TileType toTileType;

		if(direction == 1){
			newPosition = new Position(x-1, y, currentArea);
			toTileType = destinationTileType(1, player);

		}else if(direction == 2){
			toTileType = destinationTileType(2, player);
			newPosition = new Position(x+1, y, currentArea);

		}else if(direction == 3){
			toTileType = destinationTileType(3, player);
			newPosition = new Position(x, y+1, currentArea);

		}else{
			toTileType = destinationTileType(4, player);
			newPosition = new Position(x, y-1, currentArea);

		}
		if(toTileType != null){
			if(isEmptyTile(toTileType)){
				player.setPosition(newPosition);
			}else if(toTileType.equals(TileType.DOOR)){
				Area newArea = playerPosition.getArea().getInternalArea(newPosition);
				if(newArea == null){
					player.setPosition(currentArea.getEntrance());
				}else{
					player.setPosition(newArea.exitPosition());
				}
			}
		}
		gameState.printState();
	}

	/**
	 * This method will return the tileType the that is in the direction
	 * the player want to move in
	 * @param direction: direction of the player
	 * @param player: player that is moving
	 * @return: tileType of destination
	 */
	private TileType destinationTileType(int direction, Player player) {
		int x = player.getPosition().getX();
		int y = player.getPosition().getY();
		Tile[][] areaArray = gameState.getArea(player).getArray();

		if(direction == 1){
			if(x == 0){
				return null;
			}
			return areaArray[x-1][y].getType();
		}else if(direction == 2){
			if(x == areaArray.length-1){
				return null;
			}
			return areaArray[x+1][y].getType();
		}else if(direction == 3){
			if(y == areaArray[0].length-1){
				return null;
			}
			return areaArray[x][y+1].getType();
		}else if(direction == 4){
			if(y == 0){
				return null;
			}
			return areaArray[x][y-1].getType();
		}
		return null;
	}


	/**
	 * This method returns whether this is a base tile that the player
	 * can move onto.
	 * that they are in
	 * @param tileType: The tile type that needs to be checked
	 * @return: If it is a base tile that the player can move into
	 */
	private boolean isEmptyTile(TileType tileType) {
		return (tileType.equals(TileType.GRASS) || tileType.equals(TileType.ROCK) || tileType.equals(TileType.WOOD));
	}


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


	public GameState getState() {
		 return gameState;
	}



}
