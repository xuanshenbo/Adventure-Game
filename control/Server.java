package control;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import static utilities.PrintTool.p;

/**
 * The following acts as the server for the whole multiplayer game. It receives user events from the player and propagates updated
 * game state to the player.
 * @author yanlong
 *
 */
public class Server extends Thread{
	public final static int PORT = 8888;
	private final static Logger auditLogger = Logger.getLogger("requests");
	private final static Logger errorLogger = Logger.getLogger("errors");
	private InetAddress address;
	private ServerSocket server;
	private char[] map;
	private int mapRow;
	private int mapCol;
	private int uid;

	public Server() {
		//System.out.println(Server.class.getClassLoader().getResource("requests"));
		try{
			server = new ServerSocket(PORT, 50, InetAddress.getLocalHost());
			address = server.getInetAddress();
		} catch (IOException ex) {
			errorLogger.log(Level.SEVERE, "Couldn't start server", ex);
		} catch (RuntimeException ex) {
			errorLogger.log(Level.SEVERE, "Couldn't start server: " + ex.getMessage(), ex);
		}
	}

	/**
	 *
	 * @return address
	 */
	public InetAddress getAddress() {
		return address;
	}

	public void updateMap(char[][] m){
		mapRow = m.length;
		mapCol = m[0].length;
		map = new char[mapRow*mapCol];
		int index = 0;
		for(int row=0; row<mapRow;row++){
			for(int col=0; col<mapCol;col++){
				map[index++] = m[row][col];
			}
		}
	}

	public void run(){
		//System.out.println("Server is stared");//debug
		ExecutorService pool = Executors.newFixedThreadPool(4);
		boolean exit = false;
		while (!exit) {
			try {
				Socket connection = server.accept();
				Callable<Void> task = new Task(connection);
				pool.submit(task);
				//Thread.yield();
			} catch (IOException ex) {
				errorLogger.log(Level.SEVERE, "accept error", ex);
			} catch (RuntimeException ex) {
				errorLogger.log(Level.SEVERE, "unexpected error " + ex.getMessage(), ex);
			}
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
	 * a setter for uid
	 * @param uid
	 */
	public void setUid(int uid) {
		this.uid = uid;
	}

	/**
	 * The following determines what the server should send to the client depends on the input
	 */
	public void feedback(String input, Writer out){
		switch(input){
		default:
		}
	}
	private class Task implements Callable<Void> {
		private Socket connection;
		Task(Socket connection) {
			this.connection = connection;
		}
		@Override
		public Void call() {
			try {
				Writer out = new OutputStreamWriter(connection.getOutputStream());
				out.write("Server address: "+address.getHostAddress().toString());
				out.flush();
				Reader in = new InputStreamReader(connection.getInputStream());
				//Date now = new Date();
				// write the log entry first in case the client disconnects
				//auditLogger.info(now + " " + connection.getRemoteSocketAddress());
				while(true){
					char[] message = new char[1024];
					//System.out.println("Stuck for twice");
					in.read(message);
					/*int counter = 0;
					System.out.println(counter++);*/
					String input = "";
					for(int i=0; i<message.length; i++){
						if(message[i] == '\0' || message[i] == '\r' || message[i] == '\n') break;
						input += message[i];
					}

					//System.out.println("skip the input?");//debug
					System.out.println("======================"+input+"====================");//debug

					//System.out.println("server printed input");//debug

					//feedback(input, out);

					//p("mapRow"+String.format("%s",mapRow).charAt(0)+String.format("%s",mapRow).charAt(1));
					//p("colRow"+(char)('0' + mapCol));
					/*out.write(String.valueOf(mapRow));
				out.write('x');
				out.write(String.valueOf(mapCol));
				out.write('x');
				out.write(map);*/


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


