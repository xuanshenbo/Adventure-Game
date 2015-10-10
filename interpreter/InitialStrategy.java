package interpreter;

import interpreter.Translator.Command;

import java.io.IOException;
import java.io.Writer;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

import view.Avatar;
import main.Initialisation;
import main.Main;


public class InitialStrategy implements StrategyInterpreter.Strategy{
	private Initialisation s;
	private String ip;

	@Override
	public void notify(String text) throws IOException {

		if(Translator.isInitialisationState(text)){
			notifyInitState(text);
		}
		else if(Translator.isCommand(text)){
			notifyCommand(text);
		}
		else if(Avatar.isAvatar(text)){
			notifyAvatar(text);
		}
		else if(text.startsWith("open")){
			Scanner sc = new Scanner(text);
			String command = sc.next(); //should be "open"
			String filename = sc.next();
			//notify game passing it the filename
		}
		else if(text.startsWith("parameters")){
			Scanner sc = new Scanner(text);
			String command = sc.next(); //should be "parameters"
			int height, width, difficulty, density;
			height = sc.nextInt();
			width = sc.nextInt();
			difficulty = sc.nextInt();
			density = sc.nextInt();

			//TODO let game know the chosen parameters
		}

		else{	//entered ipaddress
			ip = text;
			InetAddress adr = null;
			try {
				adr = InetAddress.getByName(ip);
				Main.clientMode(adr, 8888);
				Main.setIP(ip);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}




	}

	private void notifyAvatar(String text) {
		Avatar a = Avatar.toAvatar(text);
		int avatarInteger = Avatar.getAvatarAsInteger(a);
		//TODO notify felix of the avatar integer


	}

	private void notifyCommand(String text) {
		Translator.Command cmd = Translator.toCommand(text);
		if(cmd.equals(Translator.Command.EXIT)){
			Main.closeServer();
		}
	}

	private void notifyInitState(String text) {
		Translator.InitialisationState initState = Translator.toInitState(text);
/*
		if(initState.equals(Translator.InitialisationState.SELECTED_CLIENT)){
			//testing for now to use a fixed IP
			InetAddress adr = null;
			try {

				adr = InetAddress.getByName("130.195.6.190");
				Main.clientMode(adr, 8888);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}*/

		if(initState.equals(Translator.InitialisationState.SELECTED_CLIENT_AND_SERVER)){
			Main.serverClient();
		}

		else if(initState.equals(Translator.InitialisationState.START_GAME)){
			s.displayMainGameFrame();
		}

	}

	@Override
	public void setInterpreter(StrategyInterpreter i) {
		this.s = (Initialisation) i;
	}




}