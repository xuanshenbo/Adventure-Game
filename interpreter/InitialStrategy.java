package interpreter;

import interpreter.Translator.Command;

import java.io.IOException;
import java.io.Writer;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

import main.Initialisation;
import main.Main;


public class InitialStrategy implements StrategyInterpreter.Strategy{
	private Initialisation s;

	@Override
	public void notify(String text) throws IOException {
		if(text.equals("start")){
			//System.out.println("START");//debug
			s.displayMainGameFrame();
		}
		else if(text.startsWith("open")){
			Scanner sc = new Scanner(text);
			String command = sc.next();
			String filename = sc.next();
			//notify game passing it the filename

		}
		else if(text.equals("loadPlayer")){

		}
		else if(text.equals("createPlayer")){

		}
		else if(text.equals("client")){
			//testing for now to use a fixed IP
			InetAddress adr = null;
			try {
				adr = InetAddress.getByName("130.195.6.190");
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			Main.clientMode(adr, 8888);
		}
		else if(text.equals("clientserver")){
			Main.serverClient();
		}

		else{	//entered ipaddress
			String ip = text;
			//TODO notify felix of ip address
		}




	}

	@Override
	public void setInterpreter(StrategyInterpreter i) {
		this.s = (Initialisation) i;
	}

	@Override
	public void notify(Command cmd) {
		if(cmd.equals(Translator.Command.EXIT)){
			Main.closeServer(); //TODO is this right?
			System.exit(0);
		}

	}

}