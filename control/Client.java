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
			output.write("J"+uid);
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

				/*String receive = "";
				if(message[0] != '\0'){
					for(int i=0; i<message.length; i++){
						if(message[i] == '\0'|| message[i] == '\r' || message[i] == '\n' || message[i] == 'X') break;
						receive+=message[i];
						//System.out.print(message[i]);
					}
					System.out.println(receive);
				}*/
				boolean update = true;
				/*				if(message[1448] == '\0' && message[0] == 'M' ){
					System.out.println("Client 104: false update detected");
					update = false;
				}*/
				if(update){
					parser.processMessage(message);
				}
				update = true;
			}
			socket.close();

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
