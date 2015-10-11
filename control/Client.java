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

import view.GameFrame;
import static utilities.PrintTool.p;

/**
 * A simple controller to send user actions to the server and receive notifications from the server.
 * The server will notify the current game state to the player that will be stored into the local copy of map.
 * @author yanlong
 *
 */
public class Client extends Thread {
	private OutputStreamWriter output;
	private InputStreamReader input;
	private final Socket socket;
	private char[][] map;
	private int uid;
	private String IPaddress;
	private GameFrame gui;
	private ClientParser parser;

	public Client(Socket s){
		socket = s;
		try {
			//socket.setTcpNoDelay(true);//Data is not buffered but sent immediately
			output = new OutputStreamWriter(socket.getOutputStream());
			input = new InputStreamReader(socket.getInputStream());
			parser = new ClientParser(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run(){
		try {
			output.write("Join server!");
			output.flush();
			boolean exit = false;
			while(!exit){
				//debug
				/*int counter = 0;
				output.write(counter++);*/

				//output.write("This is the server machine!");
				//output.flush();
				char[] message = new char[1924];
				//System.out.println("client starts reading");//debug
				input.read(message);
				parser.processMessage(message);

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

	/**
	 * a getter for IPaddress
	 * @return
	 */
	public String getIPaddress() {
		return IPaddress;
	}

	/**
	 * a setter for IPaddress
	 * @param iPaddress
	 */
	public void setIPaddress(String iPaddress) {
		IPaddress = iPaddress;
	}

	/**
	 * a setter for uid
	 * @param uid
	 */
	public void setUid(int uid) {
		this.uid = uid;
	}

	/**
	 * a getter for the ClientParser
	 * @return
	 */
	public ClientParser getParser() {
		return parser;
	}




}
