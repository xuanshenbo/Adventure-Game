package control;

import java.util.ArrayList;

import GUI.GameFrame;

/**
 * The following does the parsing job in the client when it receives a message from the server.
 * @author yanlong
 *
 */
public class ClientParser {
	private Client client;
	private GameFrame frame;
	public ClientParser(Client c){
		client = c;
	}

	/**
	 * a setter for the GameFrame
	 * @param frame
	 */
	public void setFrame(GameFrame frame) {
		this.frame = frame;
	}

	/**
	 * The following processes the message received from the server
	 * @param message
	 */
	public void processMessage(char[] message){
		switch(message[0]){
/*		case 'I'://id
			uid = Character.getNumericValue(message[1]);
			break;*/
		case 'A'://ip address and id
			readIP(message);
			break;
		case 'P'://player information
			break;
		case 'M'://map
			readMap(message);
			break;
		case 'I'://inventory information
			readInventory(message);
			break;
		default:
		}
	}

	/**
	 * The following parses the inventory information and passes it the frame
	 * @param message
	 */
	private void readInventory(char[] message) {
		ArrayList<String> inventory = new ArrayList<String>();
		for(int i=1; i<message.length; i++){
			switch(message[i]){
			case 'k':
				inventory.add("key");
			case '\0':
				inventory.add("null");
			}
		}

	}

	/**
	 * The following reads ip address and uid from the server and gives it to the gui
	 * @param message
	 */
	public void readIP(char[] message){
		String receive = "";
		int i = 1;
		for(; i<message.length; i++){
			if(message[i] == 'X') break;
			//System.out.println(message[i]);//debug
			receive+=message[i];
		}
		client.setIPaddress(receive);
		client.setUid(Character.getNumericValue(message[++i]));
		System.out.println(client.getIPaddress());//debug
		System.out.println(client.getUid());//debug
	}

	/**
	 * The following reads the map data from the server and gives it to the gui
	 * @param message
	 */
	public void readMap(char[] message){
		char[][] map = new char[15][15];
		char[][] items = new char[15][15];
		int index = 1;
		for(int row=0; row < map.length; row++){
			for(int col=0; col < map[0].length; col++){
				map[row][col] = message[index++];
				items[row][col] = message[index++];
			}
		}
		//p("reading map in the client");
		frame.updateRenderer(map, items);
	}


}
