package control;

import java.io.IOException;
import java.io.OutputStreamWriter;
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

	public Server() {
		//System.out.println(Server.class.getClassLoader().getResource("requests"));
		try{
			server = new ServerSocket(PORT);
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

	public void run(){
		ExecutorService pool = Executors.newFixedThreadPool(50);
			while (true) {
				try {
					Socket connection = server.accept();
					Callable<Void> task = new DaytimeTask(connection);
					pool.submit(task);
				} catch (IOException ex) {
					errorLogger.log(Level.SEVERE, "accept error", ex);
				} catch (RuntimeException ex) {
					errorLogger.log(Level.SEVERE, "unexpected error " + ex.getMessage(), ex);
				}
			}

	}
	private static class DaytimeTask implements Callable<Void> {
		private Socket connection;
		DaytimeTask(Socket connection) {
			this.connection = connection;
		}
		@Override
		public Void call() {
			try {
				Date now = new Date();
				// write the log entry first in case the client disconnects
				auditLogger.info(now + " " + connection.getRemoteSocketAddress());
				Writer out = new OutputStreamWriter(connection.getOutputStream());
				/*
				 * test char 2D array
				 */
				char[][] bobotest = new char[50][50];
				for(int i=0; i<bobotest.length;i++){
					for(int j=0; i<bobotest[0].length;j++){

					}
				}
				out.write("boboxuan" +"\r\n");
				out.flush();
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


