package control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import GUI.GameFrame;

/**
 * A simple controller to send user actions to the server and receive notifications from the server.
 * The server will notify the current game state to the player that will be stored into the local copy of map.
 * @author yanlong
 *
 */
public class Client extends Thread implements KeyListener {
	private OutputStreamWriter output;
	private InputStreamReader input;
	private final Socket socket;
	private char[][] map;
	private int uid;
	private String IPaddress;
	private GameFrame gui;

	public Client(Socket s){
		socket = s;
		try {
			socket.setTcpNoDelay(true);//Data is not buffered but sent immediately
			output = new OutputStreamWriter(socket.getOutputStream());
			input = new InputStreamReader(socket.getInputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void run(){
		try {
			output.write("This is the server machine!");
			output.flush();
			boolean exit = false;
			while(!exit){
				//debug
				/*int counter = 0;
				output.write(counter++);*/

				//output.write("This is the server machine!");
				//output.flush();
				char[] message = new char[1024];
				//System.out.println("client starts reading");//debug
				input.read(message);
				processMessage(message);

				//only do something if the message is not null
				/*String receive = "";
				if(message[0] != '\0'){
					for(int i=0; i<message.length; i++){
						if(message[i] == '\0'|| message[i] == '\r' || message[i] == '\n') break;
						receive+=message[i];
						System.out.print(message[i]);
					}
					System.out.println();
				}
				if(receive.substring(0,2).equals("ID")){
					uid = Integer.valueOf(receive.substring(3,4));
				}*/
			}
			socket.close();
			/*int i = 0;
			String rowString = "";
			String colString = "";
			while(message[i] != 'x'){
				rowString = rowString+message[i];
				i++;
			}
			i++;
			while(message[i] != 'x'){
				colString = colString+message[i];
				i++;
			}
			i++;
			int rowInt = Integer.valueOf(rowString);
			int colInt = Integer.valueOf(colString);

			map = new char[rowInt][colInt];
			for(int row=0; row<rowInt; row++){
				for(int col=0; col<colInt; col++){
					map[row][col] = message[i];
					System.out.print(message[i]);
					i++;
				}
				System.out.println("");
			}*/
		} catch (IOException e) {
			e.printStackTrace();
			try {
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

	public char[][] getMap(){
		return map;
	}

	/**
	 * a getter for the outputstreamwriter of the client
	 * @return
	 */
	public OutputStreamWriter getOutput() {
		return output;
	}

	/**
	 * The following sends a string to the server
	 * @param s
	 * @throws IOException
	 */
	public void send(String s) throws IOException{
		output.write(s);
		output.flush();
	}

	/**
	 * The following processes the message received from the server
	 * @param message
	 */
	public void processMessage(char[] message){
		switch(message[0]){
		case 'I'://id
			uid = Character.getNumericValue(message[1]);
			break;
		case 'A'://ip address
			readIP(message);
			break;
		case 'P'://player information
			break;
		case 'M'://map
			readMap(message);
			break;
		default:
			System.out.println("Bad message");
		}
	}

	/**
	 * The following reads ip address from the server and gives it to the gui
	 * @param message
	 */
	public void readIP(char[] message){
		String receive = "";
		for(int i=1; i<message.length; i++){
			if(message[i] == '\0'|| message[i] == '\r' || message[i] == '\n') break;
			receive+=message[i];
		}
		IPaddress = receive;
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
		gui.updateRenderer(map, items);
	}

	/**
	 * a getter for uid
	 * @return
	 */
	public int getUid() {
		return uid;
	}

	/**
	 * a setter for GameFrame gui
	 * @param gui
	 */
	public void setGui(GameFrame gui) {
		this.gui = gui;
	}


}
