package control;

import java.io.IOException;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Test the control package of the game. See if the networking part is working or not.
 * @author yanlong
 *
 */
public class TestControl {

	public static void main(String[] args) {
		//startNetwork();
	}

	/*public static void startNetwork(){
		Server ss = new Server();
		ss.start();
		try {
			//System.out.println(ss.getAddress().toString());//debug
			//Socket socket = new Socket(InetAddress.getByName( (ss.getAddress().getHostAddress().toString() ) ),ss.PORT);
			//Socket socket = new Socket(InetAddress.getByName("0.0.0.0"),ss.PORT);
			Socket socket = new Socket(ss.getAddress(), ss.PORT);
			Client client = new Client(socket);
			Writer output = client.getOutput();
			client.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/

}
