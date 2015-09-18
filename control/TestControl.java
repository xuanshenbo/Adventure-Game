package control;

import java.io.IOException;
import java.net.Socket;

/**
 * Test the control package of the game. See if the networking part is working or not.
 * @author yanlong
 *
 */
public class TestControl {

	public static void main(String[] args) {
		Server ss = new Server();
		ss.start();
		try {
			Socket socket = new Socket(ss.getAddress(),ss.PORT);
			Client client = new Client(socket);
			client.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
