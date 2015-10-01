package main;

import java.io.IOException;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import logic.Game;
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
	private static Server server;
	private static Client client;
	private static Initialisation initial;
	private static int uid;
	private static Game game;
	//private static boolean initialised;

	/**
	 * Displays welcome dialog and set up interpreters, before displaying the main GameFrame
	 * @author flanagdonn
	 * @param args
	 */
	public static void main(String[] args) {

		initial = new Initialisation();
		initial.setGame(game);

	}

	/**
	 * Sets up the network for a server-client mode
	 */
	public static void serverClient(){
		int[] parameters = {200,200,4,20,25,10,25,50};
		server = new Server(parameters);
		game = server.getGame();
		server.start();
		try {
			//System.out.println(ss.getAddress().toString());//debug
			//Socket socket = new Socket(InetAddress.getByName( (ss.getAddress().getHostAddress().toString() ) ),ss.PORT);
			//Socket socket = new Socket(InetAddress.getByName("0.0.0.0"),ss.PORT);
			Socket socket = new Socket(server.getAddress(), server.PORT);
			client = new Client(socket);
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
			client = new Client(socket);
			client.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void displayMainGameFrame(Client c){
		//frame.dispose();	//get rid of welcome frame

		GameFrame gameFrame = new GameFrame("Adventure Game", game);
		//create the Strategy Interpreters with different Strategies as appropriate
		StrategyInterpreter keyInterpreter = new StrategyInterpreter(gameFrame, new KeyStrategy(),c);
		StrategyInterpreter buttonInterpreter = new StrategyInterpreter(gameFrame, new ButtonStrategy(),c);
		StrategyInterpreter menuInterpreter = new StrategyInterpreter(gameFrame, new MenuStrategy(),c);

		menuInterpreter.setGame(gameFrame.getGame());

		//add the Strategy Interpreters to the GameFrame
		gameFrame.setKeyInterpreter(keyInterpreter);
		gameFrame.setButtonInterpreter(buttonInterpreter);
		gameFrame.setMenuInterpreter(menuInterpreter);

	}

	public static void closeWelcome() {
		initial.getFrame().dispose();
		initial.setClient(client);
	}

	/**
	 * A getter for Initialisation
	 * @return
	 */
	public static Initialisation getInitial() {
		return initial;
	}



}
