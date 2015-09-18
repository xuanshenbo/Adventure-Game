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

/**
 * A simple controller to send user actions to the server and receive notifications from the server.
 * The server will notify the current game state to the player that will be stored into the local copy of map.
 * @author yanlong
 *
 */
public class Client extends Thread implements KeyListener {
	private OutputStreamWriter output;
	private InputStreamReader input;
	private final Socket socket;

	public Client(Socket s){
		socket = s;
		try {
			socket.setTcpNoDelay(true);//Data is not buffered but sent immediately
			output = new OutputStreamWriter(socket.getOutputStream());
			input = new InputStreamReader(socket.getInputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void run(){
		try {
			output.write(1);
			char[] message = new char[1024];
			input.read(message);
			for(int i=0;i<message.length;i++){
				System.out.print(message[i]);
			}
			System.out.println();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
