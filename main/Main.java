package main;

import java.io.IOException;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import control.Client;
import control.Server;
import interpreter.ButtonStrategy;
import interpreter.KeyStrategy;
import interpreter.MenuStrategy;
import interpreter.InitialStrategy;
import interpreter.StrategyInterpreter;
import GUI.GameFrame;

/**
 * The following is the main class for the whole game.
 * @author yanlong
 *
 */
public class Main {
	private static Server server = new Server();
	private static ArrayList<Client> clients = new ArrayList<Client>();
	private static Initialisation initial;
	private static int uid;
	//private static boolean initialised;

	/**
	 * Displays welcome dialog and set up interpreters, before displaying the main GameFrame
	 * @author flanagdonn
	 * @param args
	 */
	public static void main(String[] args) {

		initial = new Initialisation();
		/*GameFrame game = new GameFrame("Adventure Game");

		//create the Strategy Interpreters with different Strategies as appropriate
		StrategyInterpreter keyInterpreter = new StrategyInterpreter(game, new KeyStrategy());
		StrategyInterpreter buttonInterpreter = new StrategyInterpreter(game, new ButtonStrategy());
		StrategyInterpreter menuInterpreter = new StrategyInterpreter(game, new MenuStrategy());

		//add the Strategy Interpreters to the GameFrame
		game.setKeyInterpreter(keyInterpreter);
		game.setButtonInterpreter(buttonInterpreter);
		game.setMenuInterpreter(menuInterpreter);*/
	}

	/**
	 * Sets up the network for a server-client mode
	 */
	public static void serverClient(){
		server.start();
		try {
			//System.out.println(ss.getAddress().toString());//debug
			//Socket socket = new Socket(InetAddress.getByName( (ss.getAddress().getHostAddress().toString() ) ),ss.PORT);
			//Socket socket = new Socket(InetAddress.getByName("0.0.0.0"),ss.PORT);
			Socket socket = new Socket(server.getAddress(), server.PORT);
			Client client = new Client(socket);
			clients.add(client);
			//Writer output = client.getOutput();
			client.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets up the network for client only mode
	 * @param adr
	 * @param port
	 */
	public static void clientMode(InetAddress adr, int port){
		server = null;
		try {
			Socket socket = new Socket(adr, port);
			Client client = new Client(socket);
			clients.add(client);
			client.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void displayMainGameFrame(Client c){
		//frame.dispose();	//get rid of welcome frame

		GameFrame game = new GameFrame("Adventure Game");
		//create the Strategy Interpreters with different Strategies as appropriate
		StrategyInterpreter keyInterpreter = new StrategyInterpreter(game, new KeyStrategy(),c);
		StrategyInterpreter buttonInterpreter = new StrategyInterpreter(game, new ButtonStrategy(),c);
		StrategyInterpreter menuInterpreter = new StrategyInterpreter(game, new MenuStrategy(),c);

		//add the Strategy Interpreters to the GameFrame
		game.setKeyInterpreter(keyInterpreter);
		game.setButtonInterpreter(buttonInterpreter);
		game.setMenuInterpreter(menuInterpreter);
	}

	public static void closeWelcome() {
		initial.getFrame().dispose();
		initial.setClient(clients.get(uid++));
	}

	/**
	 * A getter for Initialisation
	 * @return
	 */
	public static Initialisation getInitial() {
		return initial;
	}



}
