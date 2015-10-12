package main;

import java.io.IOException;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import view.GameFrame;
import model.logic.Game;
import control.Client;
import control.ClockThread;
import control.Server;
import interpreter.ButtonStrategy;
import interpreter.KeyStrategy;
import interpreter.MenuStrategy;
import interpreter.InitialStrategy;
import interpreter.StrategyInterpreter;
import interpreter.Translator;

/**
 * The following is the main class for the whole game.
 * @author yanlong
 *
 */
public class Main {
	private static Server server;
	private static Client client;
	private static Client avatarClient;
	private static Initialisation initial;
	private static int uid;
	private static Game game;
	private static GameFrame frame;

	private static boolean devMode = false;
	//private static boolean initialised;
	private static String ipAddress;

	/**
	 * Displays welcome dialog and set up interpreters, before displaying the main GameFrame
	 * @author flanagdonn
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
	/*	String ObjButtons[] = {"Yes", "No"};
		int PromptResult = JOptionPane.showOptionDialog(null, "Do you want to enter Dev mode??", "DON'T DO IT!!!!!!!",
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons, ObjButtons[1]);

		if (PromptResult == JOptionPane.YES_OPTION) {
			devMode = true;
			displayMainGameFrame();
		}
		else{
			initial = new Initialisation();
			initial.setGame(game);
		}
*/

		initial = new Initialisation();
		initial.setGame(game);

	}

	/**
	 * Sets up the network for a server-client mode
	 */
	public static void serverClient(){
		int height = 61, width = 61, players = 4, trees = 20;
		int buildings = 2, caves = 1, chests = 5, lootValue = 1;
		int[] parameters = {height, width, players,trees, buildings, caves, chests, lootValue};
		server = new Server(parameters);
		game = server.getGame();
		server.start();
	}

	/**
	 * The following connects the set up client with the server. This happens in the serverClient mode.
	 */
	public static void connectClient(int id){
		try {
			//System.out.println(ss.getAddress().toString());//debug
			//Socket socket = new Socket(InetAddress.getByName( (ss.getAddress().getHostAddress().toString() ) ),ss.PORT);
			//Socket socket = new Socket(InetAddress.getByName("0.0.0.0"),ss.PORT);
			Socket socket = new Socket(server.getAddress(), server.PORT);
			client = new Client(socket);
			client.setUid(id);
			//Writer output = client.getOutput()repaint;
			client.start();
			//System.out.println("The client should be set after ip is read");//debug
			initial.setClient(client);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets up the network for client only mode
	 * @param adr
	 * @param port
	 * @throws IOException
	 */
	public static void clientMode(InetAddress adr, int port, int uid) throws IOException{
		try {
			avatarClient.send("&");//this is to close the avatarClient socket
			avatarClient = null;
			Socket socket = new Socket(adr, port);
			client = new Client(socket);
			client.setUid(uid);//debug
			client.start();
			initial.setClient(client);
		}

		catch(java.net.ConnectException e){
			//if invalid ip address entered, return to input state
			initial.getWelcomePanel().setValidIP(false);
			initial.getWelcomePanel().transitionToNewState(Translator.InitialisationCommand.CONNECT_TO_SERVER);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		//displayMainGameFrame();//debug
	}

	/**
	 * The following creates a client specifically for choosing avatar
	 * @param adr
	 * @param port
	 * @throws IOException
	 */
	public static void avatarClient(InetAddress adr, int port) throws IOException{
		server = null;
		try {
			Socket socket = new Socket(adr, port);
			avatarClient = new Client(socket);
			avatarClient.setUid(0);//debug
			avatarClient.start();
			initial.setClient(avatarClient);
			System.out.println("Main 141: avatar client is sending R");//debug
			avatarClient.send("R");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void displayMainGameFrame() throws IOException{
		closeWelcome();
		/*if(!devMode){closeWelcome();}
		else{
//			clientMode();
		}*/

		/*
		 * Initialise StrategyInterpreters here so as to pass the interpreter to the Strategy constructors
		 */
		//System.out.println("main.display is called?");//debug
		StrategyInterpreter keyInterpreter = null;
		StrategyInterpreter buttonInterpreter = null;
		StrategyInterpreter menuInterpreter = null;
		StrategyInterpreter radioInterpreter = null;

		frame = new GameFrame("Adventure Game");
		//set the chosen avatar
		frame.setAvatar(initial.getChosenAvatar());

		//create the Strategy Interpreters with different Strategies as appropriate
		keyInterpreter = new StrategyInterpreter(frame, new KeyStrategy(keyInterpreter),client);
		buttonInterpreter = new StrategyInterpreter(frame, new ButtonStrategy(buttonInterpreter),client);
		menuInterpreter = new StrategyInterpreter(frame, new MenuStrategy(menuInterpreter, frame),client);

		//add the Strategy Interpreters to the GameFrame
		frame.setKeyInterpreter(keyInterpreter);
		frame.setButtonInterpreter(buttonInterpreter);
		frame.setMenuInterpreter(menuInterpreter);
		frame.setRadioInterpreter(buttonInterpreter);	//want the radio panel to talk to the buttonInterpreter

		//set the ip address of the client for display
		ipAddress = client.getIPaddress();
		//System.out.println("Main 180: This ip is "+ipAddress);//debug
		frame.setIP(ipAddress);

		frame.setUpLayoutAndDisplay();

		client.setGui(frame);
		client.getParser().setFrame(frame);
		client.send("F");
		ClockThread clock = new ClockThread(20,frame.getCanvas());
		clock.start();

	}

	public static void closeWelcome() {
		initial.getFrame().dispose();
	}

	/**
	 * A getter for Initialisation
	 * @return
	 */
	public static Initialisation getInitial() {
		return initial;
	}

	/**
	 * Close the server so as to not have to manually terminate
	 */
	public static void closeServer() {
		if(server != null){
			server.closeServer();
		}
		System.exit(0);
	}

	/**
	 * A setter for IP address
	 * @param ip
	 */
	public static void setIP(String ip) {
		ipAddress = ip;
	}

	/**
	 * a getter for server
	 * @return
	 */
	public static Server getServer() {
		return server;
	}

	/**
	 * set the ip string on the frame
	 * @param ip
	 */
	public static void setFrameIP(String ip){
		frame.setIP(ipAddress);
	}

	/**
	 * Check if the ip address in null at this stage
	 * @return
	 */
	public static boolean ipIsNull(){
		return ipAddress == null;
	}


	/* A getter for the client
	 * @return
	 */
	/*public static Client getClient() {
		return client;
	}*/




}
