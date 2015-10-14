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
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import view.frames.GameFrame;
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
	private int uid;
	private String IPaddress;
	private GameFrame gui;
	private ClientParser parser;

	public Client(Socket s){
		socket = s;
		try {
			output = new OutputStreamWriter(socket.getOutputStream());
			input = new InputStreamReader(socket.getInputStream());
			parser = new ClientParser(this);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * The following sends the client id to the server and then keep receiving messages from the server
	 */
	public void run(){
		try {
			output.write("J"+uid);
			output.flush();
			boolean exit = false;
			//System.out.println("Client 55: after sending id");
			while(!exit){
				//handles the disconnection gracefully
				/*if(socket.isInputShutdown() || socket.isOutputShutdown()){
					break;
				}*/
				char[] message = new char[3072];
				input.read(message);

				//The following prints out the map array
				/*if(message[0] == 'M'){
					char[][] map = new char[31][31];
					int index = 2;
					for(int row=0; row < map.length; row++){
						for(int col=0; col < map[0].length; col++){
							if(message[index] == '\0'){
								//System.out.println("Client 71: index: "+index);
								break;
							}
							map[row][col] = message[index++];
							index++;
							//items[row][col] = message[index++];
						}
					}
					char[][] playerOneView = map;
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
					}
				}*/

				//The following prints out the whole message
				/*String receive = "";
				if(message[0] != '\0'){
					for(int i=0; i<message.length; i++){
						if(message[i] == '\0'|| message[i] == '\r' || message[i] == '\n' || message[i] == 'X') break;
						receive+=message[i];
						//System.out.print(message[i]);
					}
					System.out.println(receive);
				}*/
				/*				if(message[1448] == '\0' && message[0] == 'M' ){
					System.out.println("Client 104: false update detected");
					update = false;
				}*/

					parser.processMessage(message);

			}
			socket.close();

		} catch (SocketException ex){//handles the exception that the client does not get respond from the server
			System.out.println("Server is busy! Restart the game!");//debug
			System.exit(0);
		}
		catch (IOException e) {
			//e.printStackTrace();
			try {
				socket.close();
				System.exit(0);
			} catch (IOException e1) {
				//e1.printStackTrace();
			}
		}

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
	public void send(String s) throws IOException {
		try {
			output.write(s);
			output.flush();

		} catch (SocketException ex){//this handles the disconnection of the server
			System.out.println("The server is broken");
			System.exit(0);
		}
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
