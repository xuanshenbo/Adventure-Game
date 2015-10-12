package interpreter;

import interpreter.Translator.Command;
import interpreter.Translator.InitialisationCommand;

import java.io.IOException;
import java.io.Writer;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import control.Client;
import jdk.nashorn.internal.runtime.linker.JavaAdapterFactory;
import view.Avatar;
import main.Initialisation;
import main.Main;


public class InitialStrategy implements StrategyInterpreter.Strategy{
	//private Initialisation s;
	private String ip;
	private Initialisation initialisation;

	public InitialStrategy(Initialisation i){
		initialisation = i;
	}

	@Override
	public void notify(String text) throws IOException {

		if(Translator.isInitialisationCommand(text)){
			notifyInitCommand(text);
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

			initialisation.getWelcomePanel().transitionToNewState(InitialisationCommand.CREATE_NEW_PLAYER);

			//TODO let game know the chosen parameters
		}


		//TODO fix bug here when I enter 130.195.6.190 as the address.
		else{	//entered ipaddress
			ip = text;
			InetAddress adr = null;
			try {
				adr = InetAddress.getByName(ip);
				Main.avatarClient(adr, 8888);

				//request available avatars from game, as this will be required in the next step
				Translator.InitialisationCommand cmd = Translator.InitialisationCommand.GET_AVAILABLE_AVATARS;
				String msg = Translator.encode(cmd);
				initialisation.getClient().send(msg);

				/*Socket socket = new Socket(adr, 8888);
				Client avatarClient = new Client(socket);
				client.setUid(4);//debug
				client.start();
				initial.setClient(client);*/

				/*Client avatarClient =
				Main.clientMode(adr, 8888);
				Main.setIP(ip);*/


				initialisation.getWelcomePanel().transitionToNewState(Translator.InitialisationCommand.LOAD_SAVED_PLAYER);
			}
			catch (IOException e) {
				//if invalid ip address entered, return to input state
				initialisation.getWelcomePanel().setValidIP(false);
				initialisation.getWelcomePanel().transitionToNewState(Translator.InitialisationCommand.CONNECT_TO_SERVER);

			}


		}
	}

	//receive information about the avatar chosen, and select the encoded message to the Model via the network
	private void notifyAvatar(String text) {
		Avatar a = Avatar.toAvatar(text);
		int avatarInteger = Avatar.getAvatarAsInteger(a);
		Translator.InitialisationCommand cmd = Translator.InitialisationCommand.SELECTED_AVATAR;

		//The msg contains the code for avatar selection
		String msg = Translator.encode(cmd);
		//add to the msg the integer corresponding to which avatar was chosen
		msg += avatarInteger;

		initialisation.setChosenAvatar(a);

		if(Main.getServer() != null){
			Main.connectClient(avatarInteger);
		}
		else{
			InetAddress adr = null;
			try {
				adr = InetAddress.getByName(ip);
				Main.clientMode(adr, 8888, avatarInteger);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}



		initialisation.getWelcomePanel().transitionToNewState(Translator.InitialisationCommand.START_GAME);



	}

	private void notifyCommand(String text) {
		Translator.Command cmd = Translator.toCommand(text);
		if(cmd.equals(Translator.Command.EXIT)){
			initialisation.closeServer();
		}
	}

	private void notifyInitCommand(String text) {
		Translator.InitialisationCommand initState = Translator.toInitState(text);

		if(initState.equals(Translator.InitialisationCommand.LOAD_GAME)){
			initialisation.getWelcomePanel().transitionToNewState(Translator.InitialisationCommand.LOAD_GAME);
		}

		else if(initState.equals(Translator.InitialisationCommand.SELECTED_NEW_GAME)){
			initialisation.getWelcomePanel().transitionToNewState(Translator.InitialisationCommand.CHOOSE_SLIDER_OPTIONS);
		}

		else if(initState.equals(Translator.InitialisationCommand.LOAD_SAVED_PLAYER)){
			initialisation.getWelcomePanel().transitionToNewState(Translator.InitialisationCommand.LOAD_SAVED_PLAYER);
		}

		else if(initState.equals(Translator.InitialisationCommand.CREATE_NEW_PLAYER)){
			initialisation.getWelcomePanel().transitionToNewState(Translator.InitialisationCommand.CREATE_NEW_PLAYER);
		}

		else if(initState.equals(Translator.InitialisationCommand.SELECTED_CLIENT)){
			//now display the option for which server to connect to
			initialisation.getWelcomePanel().transitionToNewState(Translator.InitialisationCommand.CONNECT_TO_SERVER);
		}

		else if(initState.equals(Translator.InitialisationCommand.SELECTED_CLIENT_AND_SERVER)){
			//now display the options of loading a game, or starting a new one
			initialisation.getWelcomePanel().transitionToNewState(Translator.InitialisationCommand.SHOW_LOAD_OR_NEW_OPTION);
		}

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

		if(initState.equals(Translator.InitialisationCommand.SELECTED_CLIENT_AND_SERVER)){
			Main.serverClient();
		}

		else if(initState.equals(Translator.InitialisationCommand.START_GAME)){
			initialisation.displayMainGameFrame();
		}

	}

	@Override
	public void setInterpreter(StrategyInterpreter i) {
		this.initialisation = (Initialisation) i;
	}




}