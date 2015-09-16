package control;

import java.net.Socket;

/**
 * The following acts as the server for the whole multiplayer game. It receives user events from the player and propagates updated
 * game state to the player.
 * @author yanlong
 *
 */
public class Server extends Thread{
	private final Socket socket;
	public Server(Socket s){
		socket = s;
	}
}
