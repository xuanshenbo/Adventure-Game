package model.logic;

import java.io.IOException;
import java.io.Writer;
import java.net.SocketException;
import java.util.List;

import javax.xml.bind.JAXBException;

import control.Server;
import dataStorage.Deserializer;
import dataStorage.Serializer;
import model.items.Item;
import model.logic.Game.Direction;
import model.state.GameState;
import model.state.Player;
import static utilities.PrintTool.p;

/**
 * This is the parser for the server. It recieves the messages from the client
 * and parses it into an action. It also takes actions from the game logic and
 * parses it into messages to be sent to the client
 * @author tuckergare
 *
 */

public class ServerParser {
	private Game game;
	private Server server;
	private char[] tempItemArrayStorage = null;
	private boolean testing;
	private char[] playerList;
	private char[] stringMessage;

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
			p("U:"+message[1]);
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
		case 'V'://move Item in 1st inventory slot  into a bag in the 2nd inventory slot[m, int inventorySlot, int inventorySlot]
			game.moveInventory(game.getGameState().getPlayer(id), Character.getNumericValue(message[1]), Character.getNumericValue(message[2]));
			break;
		case 'D'://Drop [D, inventorySlot]
			game.drop(game.getGameState().getPlayer(id), Character.getNumericValue(message[1]));
			break;
		case 'F'://activating frame
			game.activateFrame();
			break;
		case 'Z'://moving item from container to inventory [Z, containerSlot]
			game.moveFromContainerToInventory(game.getGameState().getPlayer(id), Character.getNumericValue(message[1]));
			break;
		case 'S'://Saving game
			try {
				Serializer.serialize(game.getGameState());
			} catch (JAXBException e) {
				e.printStackTrace();
			}
			break;
		case 'Y'://Save As
			try {
				Serializer.serializeAs(game.getGameState());
			} catch (JAXBException e) {
				e.printStackTrace();
			}
			break;
		case 'L'://Loading game
			try {
				GameState toLoad = Deserializer.deserialize();
				if (toLoad == null) {
					return;
				}
				game.setGameState(toLoad);
			} catch (JAXBException e) {
				e.printStackTrace();
			}
			break;
		case 'J'://Client joins the game
			game.activatePlayer(id);
			break;
		case 'Q'://Client quits the game
			game.deActivatePlayer(id);
			break;
		case 'R'://requesting available players
			game.getAvailablePlayers(id);
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

	/**
	 * This method is called when the server needs to pass information to the client
	 * @param player: the Player that the information is being passed to
	 * @param action: the action that has happened
	 */
	public void sendToServer(Player player, char action){
		char[] message = null;
		if(action == 'M'){// map information
			List<char[][]> view = game.getGameView(player.getId());
			int size = view.get(0).length*view.get(0)[0].length;
			char type = player.getPosition().getArea().getType().id;
			message = new char[(size*2)+3];
			message[0] = action;
			message[1] = type;
			int index = 2;
			for(int r = 0; r < 31; r++){
				for(int c = 0; c < 31; c++){
					message[index++] = view.get(0)[r][c];
					message[index++] = view.get(1)[r][c];

				}
			}
		}else if(action == 'm'){
			message = new char[stringMessage.length+2];
			message[0] = action;
			for(int i = 0; i < stringMessage.length; i++){
				message[i+1] = stringMessage[i];
			}
		}else if(action == 'I'){// player inventory
			char[] inventory = new char[player.getInventory().length];
			for(int i = 0; i< inventory.length; i++){
				if(player.getInventory()[i] != null){
					inventory[i] = player.getInventory()[i].getType();
				}
			}
			message = new char[inventory.length+2];
			message[0] = action;
			for(int i = 0; i < inventory.length; i++){
				message[i+1] = inventory[i];
			}

		}else if(action == 'H'){// player happiness
			int happiness = player.getHappiness();
			p("sending Happiness");
			message = new char[3];
			message[0] = action;
			message[1] = (char)(happiness +'0');

		}else if(action == 'S'){// container inventory information
			p("sending container info");
			message = new char[tempItemArrayStorage.length+2];
			message[0] = action;
			for(int i = 0; i< tempItemArrayStorage.length; i++){
				message[i+1] = tempItemArrayStorage[i];
			}
		}else if(action == 'T'){//time of day update
			message = new char[4];
			message [0] = action;
			boolean day = game.getGameState().getDay();
			int time = game.getGameState().getTime();
			message [1] = (char) (time +'0');
			if(day){
				message[2] = 'D';
			}else{
				message[2] = 'N';
			}
		}else if(action == 'R'){//sending list of players
			message = new char[6];
			message[0] = action;
			for(int i = 0; i < playerList.length; i++){
				message[i+1] = playerList[i];
			}

		}else{
			message = new char[0];
		}
		message[message.length-1] = 'X';
		try {
			if(!testing){
				server.getWriters()[player.getId()].write(message);
				server.getWriters()[player.getId()].flush();
			}
		} catch (SocketException s){//this is handling the unusual disconnection of the client
			System.out.println("ServerParser 213: the client is disconnected");
			game.deActivatePlayer(player.getId());
			server.getWriters()[player.getId()] = null;
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Send the inventory of a container.
	 * @param player: the player to send the inventory to
	 * @param itemArray: the inventory of the container
	 */
	public void sendContainer(Player player, Item[] items) {
		char[] itemArray = new char[items.length];
		for (int i = 0; i < items.length; i++) {
			if(items[i] != null){
				itemArray[i] = items[i].getType();
			}
		}
		tempItemArrayStorage = itemArray;
		sendToServer(player, 'S');

	}

	/**
	 * Send the inventory of a player.
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
		sendToServer(player, 'I');

	}
	
	/**
	 * This sends a messages to be displayed to the client in a dialog
	 * box
	 * @param player
	 * @param message
	 */
	public void sendMessage(Player player, String message){
		int count = 0;
		stringMessage = new char[message.length()];
		while(count < message.length()){
			char c = message.charAt(count);
			stringMessage[count++] = c;
		}
		sendToServer(player, 'm');
	}
	
	/**
	 * This method is called when the server is getting the player list and 
	 * returns the list of active players
	 * @param player: the active player
	 * @param players: the list of other players
	 */
	public void sendPlayers(Player player, char[] players) {
		p(player);
		playerList = players;
		for(int i = 0; i < players.length; i++){
			p(players[i]);
		}
		System.out.println("ServerParser 277: send player");///debug
		sendToServer(player, 'R');
	}

}
