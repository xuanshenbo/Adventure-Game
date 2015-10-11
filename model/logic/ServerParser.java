package model.logic;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import javax.xml.bind.JAXBException;

import control.Server;
import dataStorage.Serializer;
import model.items.Item;
import model.logic.Game.Direction;
import model.state.Player;
import static utilities.PrintTool.p;

public class ServerParser {




	private Game game;
	private Server server;
	private char[] tempItemArrayStorage = null;
	private boolean testing;

	public ServerParser(Game game, Server server, boolean testing) {
		this.game = game;
		this.server = server;
		this.testing = testing;
	}

	/**
	 * The following receives the user event from the client and process the
	 * logic needed to update the game.
	 *
	 * @param input
	 * @param out
	 * @param id
	 */
	public synchronized void processClientEvent(char[] message, Writer out,
			int id) {
		switch (message[0]) {
		case 'M'://move [M, direction]
			game.move(game.getGameState().getPlayer(id), parseDirection(message[1]));
			break;
		case 'U': //use [U, int inventorySlot)
			game.use(game.getGameState().getPlayer(id), Character.getNumericValue(message[1]));
			break;
		case 'I': //request Inventory [I]
			game.sendInventory(game.getGameState().getPlayer(id));
			break;
		case 'P'://Pickup [P, _]
			game.pickUp(game.getGameState().getPlayer(id));
			break;
		case 'C'://Selected [C, int inventorySlot]
			game.select(game.getGameState().getPlayer(id), Character.getNumericValue(message[1]));
			break;
		case 'm'://move Item [m, int inventorySlot, int containerSlot]
			game.moveInventory(game.getGameState().getPlayer(id), Character.getNumericValue(message[1]), Character.getNumericValue(message[2]));
			break;
		case 'D'://Drop [D, _]
			game.drop(game.getGameState().getPlayer(id));
			break;

		case 'F'://activating frame
			game.activateFrame();
			break;
		case 'S'://Saving game
			try {
				Serializer.serialize(game.getGameState());
			} catch (JAXBException e) {
				e.printStackTrace();
			}
			break;
		case 'L'://Loading game
			break;
		case 'J'://Client joins the game
			game.activatePlayer(id);
		}


	}

	/**
	 * The following transferred a char sent from the client into a Direction
	 *
	 * @param dir
	 * @return
	 */
	public synchronized Direction parseDirection(char dir) {
		switch (dir) {
		case 'N':
			return Direction.UP;
		case 'S':
			return Direction.DOWN;
		case 'W':
			return Direction.LEFT;
		case 'E':
			return Direction.RIGHT;
		default:
			return null;
		}
	}

	/**
	 * This method is called when the server needs to pass information to the client
	 * @param player: the Player that the information is being passed to
	 * @param action: the action that has happened
	 */
	public void sendToServer(Player player, char action){
		char[] message = null;
		if(action == 'M'){// map information
			List<char[][]> view = game.getGameView(player.getId());
			char type = player.getPosition().getArea().getType().id;
			message = new char[452];
			message[0] = action;
			message[1] = type;
			int index = 2;
			for(int r = 0; r < 15; r++){
				for(int c = 0; c < 15; c++){
					message[index++] = view.get(0)[r][c];
					message[index++] = view.get(1)[r][c];
				}
			}
		}else if(action == 'I'){// player inventory
			char[] inventory = new char[player.getInventory().length];
			message = new char[inventory.length+2];
			message[0] = action;
			for(int i = 1; i < inventory.length; i++){
				message[i] = inventory[i];
			}
		}else if(action == 'H'){// player happiness
			int happiness = player.getHappiness();
			message = new char[2];
			message[0] = action;
			message[1] = (char)(happiness +'0');
		}else if(action == 'C'){// container inventory information
			message = new char[tempItemArrayStorage.length+1];
			message[0] = action;
			for(int i = 1; i< tempItemArrayStorage.length; i++){
				message[i] = tempItemArrayStorage[i];
			}
		}else{
			message = new char[0];
		}

		try {
			if(!testing){
				server.getWriters()[player.getId()].write(message);
				server.getWriters()[player.getId()].flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Send the inventory of a container.
	 * @param player: the player to send the inventory to
	 * @param itemArray: the inventory of the container
	 */
	public void sendInventory(Player player, Item[] items) {
		char[] itemArray = new char[items.length];
		for (int i = 0; i < items.length; i++) {
			if(items[i] != null){
				itemArray[i] = items[i].getType();
			}
		}
		tempItemArrayStorage = itemArray;
		sendToServer(player, 'C');

	}

}
