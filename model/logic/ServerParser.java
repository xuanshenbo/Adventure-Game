package model.logic;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import javax.xml.bind.JAXBException;

import control.Server;
import dataStorage.Serializer;
import model.logic.Game.Direction;
import model.state.Player;
import static utilities.PrintTool.p;

public class ServerParser {




	private Game game;
	private Server server;
	private char[] tempItemArrayStorage = null;

	public ServerParser(Game game, Server server) {
		this.game = game;
		this.server = server;
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
		case 'U': //use [I, int inventorySlot)
			game.use(game.getGameState().getPlayer(id), message[1]);
			break;
		case 'P'://Pickup [P, _]
			game.pickUp(game.getGameState().getPlayer(id));
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

	public void sendToServer(Player player, char action){
		char[] message = null;
		if(action == 'M'){// map information
			List<char[][]> view = game.getGameView(player.getId());
			message = new char[451];
			message[0] = action;
			int index = 1;
			for(int r = 0; r < 15; r++){
				for(int c = 0; c < 15; c++){
					message[index++] = view.get(0)[r][c];
					message[index++] = view.get(1)[r][c];
				}
			}
		}else if(action == 'P'){// player information
			char[] inventory = new char[player.getInventory().length];
			int happiness = player.getHappiness();
			message = new char[inventory.length+2];
			message[0] = action;
			message[1] = (char)(happiness +'0');
			for(int i = 2; i < inventory.length; i++){
				message[i] = inventory[i];
			}
		}else if(action == 'I'){// inventory information
			message = new char[tempItemArrayStorage.length+1];
			message[0] = action;
			for(int i = 1; i< tempItemArrayStorage.length; i++){
				message[i] = tempItemArrayStorage[i];
			}			
		}else{
			message = new char[0];
		}

		try {
			//p();
			server.getWriters()[player.getId()].write(message);
			server.getWriters()[player.getId()].flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendInventory(Player player, char[] itemArray) {
		tempItemArrayStorage = itemArray;
		sendToServer(player, 'I');

	}

}
