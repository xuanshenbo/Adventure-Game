package control;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Test the control package of the game. See if the networking part is working or not.
 * @author yanlong
 *
 */
public class TestControl {

	public static void main(String[] args) {
		startNetwork();
	}

	public static void startNetwork(){
		Server ss = new Server();
		ss.start();
		try {
			System.out.println(ss.getAddress().toString());
			//Socket socket = new Socket(InetAddress.getByName( (ss.getAddress().getHostAddress().toString() ) ),ss.PORT);
			Socket socket = new Socket(InetAddress.getByName("0.0.0.0"),ss.PORT);
			Client client = new Client(socket);
			client.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
