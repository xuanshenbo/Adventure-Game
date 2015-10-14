package control;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.logic.Game;
import model.logic.WorldParameters;
import model.state.GameState;
import static utilities.PrintTool.p;

/**
 * The following acts as the server for the whole multiplayer game. It receives user events from the player and propagates updated
 * game state to the player.
 * @author yanlong
 *
 */
public class Server extends Thread{
	public final static int PORT = 8888;
	private InetAddress address;
	private ServerSocket server;
	private Writer[] writers = new Writer[5];//writer[0] is for the avatar client. 1-4 will be used for plays 1-4
	private Socket[] sockets = new Socket[5];
	private boolean exit;
	private Game game;

	/**
	 * The following is a server constructed by taking all the parameters chosen by the user
	 * @param height The height of the game
	 * @param width The width of the game
	 * @param density The density of trees and buildings
	 * @param difficulty Affects number of zombies and chests
	 */
	public Server(int height, int width, int density, String difficulty) {

		game = new Game(this, height, width, difficulty, density);

		try{
			server = new ServerSocket(PORT, 50, InetAddress.getLocalHost());
			address = server.getInetAddress();
		} catch (IOException ex) {
			//ignore
		} catch (RuntimeException ex) {
			//ignore
		}
	}

	/**
	 * Alternative Constructor for loading the game();
	 */
	public Server(GameState state){
		game = new Game(this,state);
		try{
			server = new ServerSocket(PORT, 50, InetAddress.getLocalHost());
			address = server.getInetAddress();
		} catch (IOException ex) {
			//ignore
		} catch (RuntimeException ex) {
			//ignore
		}
	}

	/**
	 * A constructor for the JUnit test
	 */
	public Server(){

	}

	/**
	 * a getter for the InetAddress
	 * @return address
	 */
	public InetAddress getAddress() {
		return address;
	}

	/**
	 * the following always accept sockets connected from clients. New callable tasks based on the connection will be submited in the
	 * thread pool.
	 */
	public void run(){
		ExecutorService pool = Executors.newFixedThreadPool(50);
		while (!exit) {
			try {
				if(server.isClosed()) break;
				Socket connection = server.accept();
				Callable<Void> task = new Task(connection);
				pool.submit(task);
			} catch (IOException ex) {
				//ignore
			} catch (RuntimeException ex) {
				//ignore
			}
		}

	}

	/**
	 * a getter for the game
	 * @return game
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * a getter for all the writers
	 * @return
	 */
	public Writer[] getWriters() {
		return writers;
	}

	/**
	 * The following closes the server socket and all the client sockets connected to it
	 */
	public void closeServer(){
		try {
			exit = true;
			server.close();
			for(int i=0; i<sockets.length; i++){
				if(sockets[i]!= null){
					sockets[i].close();
					sockets[i] = null;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * The following creates a thread that is always listening to the client that establishes the socket connection
	 * @author yanlong
	 *
	 */
	private class Task implements Callable<Void> {
		private Socket connection;
		private int id;
		Task(Socket connection) {
			this.connection = connection;
		}
		@Override
		public Void call() {
			try {
				//System.out.println("Server 153: before reading from client");
				Reader in = new InputStreamReader(connection.getInputStream());
				//System.out.println("Server 155: after reading from client");
				char[] received = new char[2];
				in.read(received);
				id = Character.getNumericValue(received[1]);
				//System.out.println("Server 159: Id: "+id);

				Writer out = new OutputStreamWriter(connection.getOutputStream());
				writers[id] = out;
				sockets[id] = connection;
				if(id != 0){
					game.getParser().processClientEvent(received, out, id);
				}
				//System.out.println("Server 163: before sending address");
				out.write("A"+address.getHostAddress().toString()+"X");// 'X' indicates the end of the message
				out.flush();

				while(true){
					//handles the disconnection gracefully
					/*if(connection.isInputShutdown() || connection.isOutputShutdown()){
						System.out.println("Server 210: close the client");//debug
						char[] quit = {'Q'};
						game.getParser().processClientEvent(quit, out, id);
						writers[id] = null;
						break;
					}*/
					char[] message = new char[256];
					in.read(message);
					if(message[0] == '&'){//turn off the avatar client
						System.out.println("Server 205: close the avatar client");//debug
						writers[0] = null;
						break;
					}
					else if(message[0] == 'Q'){// this will close the socket properly when the user quits
						System.out.println("Server 210: close the client");//debug
						game.getParser().processClientEvent(message, out, id);
						writers[id] = null;
						break;
					}

					//The following prints the message received from the client
					/*String input = "";
					for(int i=0; i<message.length; i++){
						if(message[i] == '\0' || message[i] == '\r' || message[i] == '\n') break;
						input += message[i];
					}

					System.out.println("======================"+input+"====================");//debug
*/
					game.getParser().processClientEvent(message, out, id);
				}

			} catch (IOException ex) {
				// client disconnected; ignore;
			} finally {
				try {
					connection.close();
				} catch (IOException ex) {
					// ignore;
				}
			}
			return null;
		}
	}
}


