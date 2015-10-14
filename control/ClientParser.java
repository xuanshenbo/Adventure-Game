package control;

import java.util.ArrayList;

import main.Initialisation;
import main.Main;
import view.frames.GameFrame;
import view.utilities.Avatar;
import interpreter.Translator;
import static utilities.PrintTool.p;

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
		case 'H'://happiness level
			readHappiness(message);
			break;
		case 'M'://map
			readMap(message);
			break;
		case 'I'://inventory information
			p("received inventory information");
			readInventory(message);
			break;
		case 'S'://container information
			p("Received S");
			readContainer(message);
			break;
		case 'T'://time of day
			int time = Character.getNumericValue(message[1]);
			char dayNight = message[2];
			if(frame != null){
				frame.setTime(time);
				frame.getCanvas().getRenderer().updateDayNight(dayNight);
			}
			break;
		case 'R':
			readAvatar(message);
			break;
		case 'm':
			readMessageToDisplay(message);
			break;
		default:
		}
	}

	/**
	 * The following reads a message from the server and then display it on the frame
	 * @param message
	 */
	private void readMessageToDisplay(char[] message) {
		String messageToDisplay = "";
		int count = 1;
		while(count < message.length && message[count] != 'X'){
			messageToDisplay += message[count++];
		}
		frame.displayMessageFromGame(messageToDisplay);

	}

	/**
	 * The following parses the available avatars and passes it to the initialisation
	 * @param message
	 */
	private void readAvatar(char[] message) {
		//System.out.println("Got available avatars");//debug
		ArrayList<Avatar> avatars = new ArrayList<Avatar>();
		loop: for(int i=1; i<message.length; i++){
			switch(message[i]){
			case 'Y':
				avatars.add(Avatar.getAvatarFromInt(i));
				break;
			case 'N':
				break;
			case 'X':
				char[] newMessage = separateMessage(message, i);
				if(newMessage != null) processMessage(newMessage);
				break loop;
			default:
				System.out.println("unknown avatar");
			}
		}
		//System.out.println("ClientParser 101: avatar is being read");//debug
		//debug
		/*for(Avatar avatar: avatars){
			System.out.println(avatar.toString());
		}*/
		Initialisation initial = Main.getInitial();
		initial.setAvatars(avatars);
		//System.out.println("ClientParser 112: display avatar once");
		initial.getWelcomePanel().transitionToNewState(Translator.InitialisationCommand.LOAD_SAVED_PLAYER);//this will display avatar

	}

	/**
	 * The following parses the happiness level and passes it to the frame
	 * @param message
	 */
	private void readHappiness(char[] message) {
		int happiness = (Character.getNumericValue(message[1]))*10;
		frame.setHappinessLevel(happiness);
	}

	/**
	 * The following parses the container information and passes it to the frame
	 * @param message
	 */
	private void readContainer(char[] message) {
		ArrayList<String> container = new ArrayList<String>();
		loop: for(int i=1; i<message.length; i++){
			switch(message[i]){
			case 'k':
				container.add("key");
				break;
			case 'c':
				container.add("cupcake3");
				break;
			case 'b':
				container.add("bag");
				break;
			case '\0':
				container.add("emptyslot");
				break;
			case 'X':
				char[] newMessage = separateMessage(message, i);
				if(newMessage != null) processMessage(newMessage);
				break loop;
			default:
				System.out.println("ClientParser 157: unknown item: "+message[i]);//debug
				return;
			}
		}
		frame.setContainerContents(container);
	}

	/**
	 * The following parses the inventory information and passes it to the frame
	 * @param message
	 */
	private void readInventory(char[] message) {
		System.out.println("inventory reading");//debug
		ArrayList<String> inventory = new ArrayList<String>();
		loop: for(int i=1; i<message.length; i++){
			switch(message[i]){
			case 'k':
				inventory.add("key");
				break;
			case 'c':
				inventory.add("cupcake3");
				break;
			case 'b':
				inventory.add("bag");
				break;
			case '\0':
				inventory.add("emptyslot");
				break;
			case 'X':
				char[] newMessage = separateMessage(message, i);
				if(newMessage != null) processMessage(newMessage);
				break loop;
			default:
				System.out.println(message[i]);
			}
		}
		System.out.println("inventory size: "+inventory.size());//debug
		frame.setInventoryContents(inventory);
	}

	/**
	 * This will separate the messages if more than one messages are read together in the client
	 * @param message
	 * @param i
	 * @return
	 */
	private char[] separateMessage(char[] message, int i){
		if(message[i+1] == '\0') return null;
		char[] newMessage = new char[message.length];
		for(int k=0; k<newMessage.length; k++){
			if(message[i] == '\0') break;
			newMessage[k] = message[++i];
		}
		return newMessage;
	}

	/**
	 * The following reads ip address and uid from the server and gives it to the gui
	 * @param message
	 */
	public void readIP(char[] message){
		System.out.println("IP is being read in the client parser");//debug
		String receive = "";
		int i = 1;
		for(; i<message.length; i++){
			if(message[i] == 'X') break;
			//System.out.println(message[i]);//debug
			receive+=message[i];
		}
		client.setIPaddress(receive);
		//client.setUid(Character.getNumericValue(message[++i]));
		System.out.println("ClientParser 210: "+client.getIPaddress());//debug
		System.out.println("ClientParser 211: "+client.getUid());//debug
		if(Main.ipIsNull()){
			Main.setIP(receive);
		}
	}

	/**
	 * The following reads the map data from the server and gives it to the gui
	 * @param message
	 */
	public void readMap(char[] message){
		char[][] map = new char[31][31];
		char[][] items = new char[31][31];
		char type = message[1];
		int index = 2;
		for(int row=0; row < map.length; row++){
			for(int col=0; col < map[0].length; col++){
				map[row][col] = message[index++];
				items[row][col] = message[index++];
			}
		}
		/*char[][] playerOneView = map;
		System.out.println("\nPlayer 1 view");
		for(int row = 0; row<playerOneView.length; row++){
			for(int col = 0; col<playerOneView[0].length; col++){
				if(playerOneView[row][col] != '\u0000'){
					System.out.print(playerOneView[row][col]);
				}else{
					System.out.print("N");
				}
			}
			System.out.println("");
		}*/
		if(frame != null){
			frame.updateRenderer(type, map, items);
		}
	}


}
