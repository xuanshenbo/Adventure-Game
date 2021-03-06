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
import view.utilities.Avatar;
import main.Initialisation;
import main.Main;

/**
 * An implementation of the Observer and Strategy design patterns, combined to
 * allow strict observation of the overarching MVC design pattern
 * The ButtonStrategy governs interaction between the user, during the
 * setting up stages of the game
 * @author  flanagdonn
 */
public class InitialStrategy implements StrategyInterpreter.Strategy{

	private String ip;
	private Initialisation initialisation;

	/**
	 * assigns the initialisation object as this strategy's interpreter
	 * @param i The initialisation StrategyInterpreter
	 */
	public InitialStrategy(Initialisation i){
		initialisation = i;
	}

	/**
	 * This method performs the logic required after user input.
	 * Often this involves sending an appropriately encoded message
	 * across the network, and transitioning to a new state.
	 */
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
		else if(text.startsWith("parameters")){
			Scanner sc = new Scanner(text);
			String command = sc.next(); //should be "parameters"
			int height, width, difficulty, density;
			height = sc.nextInt();
			width = sc.nextInt();
			difficulty = sc.nextInt();
			density = sc.nextInt();

			sc.close();

			initialisation.getWelcomePanel().transitionToNewState(InitialisationCommand.CREATE_NEW_PLAYER);
		}


		else{	//entered ipaddress
			ip = text;	//@author yanlong
			InetAddress adr = null; //@author yanlong
			try {
				adr = InetAddress.getByName(ip); //@author yanlong
				Main.avatarClient(adr, 8888); //@author yanlong

				//request available avatars from game, as this will be required in the next step
				Translator.InitialisationCommand cmd = Translator.InitialisationCommand.GET_AVAILABLE_AVATARS;
				String msg = Translator.encode(cmd);

				if(initialisation.getClient() == null){
					initialisation.getWelcomePanel().setValidIP(false);
					initialisation.getWelcomePanel().transitionToNewState(Translator.InitialisationCommand.CONNECT_TO_SERVER);
				}
				else{
					initialisation.getClient().send(msg);
				}

				//initialisation.getWelcomePanel().transitionToNewState(Translator.InitialisationCommand.LOAD_SAVED_PLAYER);
			}
			catch (IOException e) {
				//if invalid ip address entered, return to input state
				initialisation.getWelcomePanel().setValidIP(false);
				initialisation.getWelcomePanel().transitionToNewState(Translator.InitialisationCommand.CONNECT_TO_SERVER);

			}


		}
	}

	/*
	 * receive information about the avatar chosen, and select the encoded message to the Model via the network
	 * @author yanlong, flanagdonn
	 */
	private void notifyAvatar(String text) {
		Avatar a = Avatar.toAvatar(text);
		int avatarInteger = Avatar.getAvatarAsInteger(a);
		Translator.InitialisationCommand cmd = Translator.InitialisationCommand.SELECTED_AVATAR;

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

		else if(initState.equals(Translator.InitialisationCommand.START_GAME)){
			initialisation.displayMainGameFrame();
		}

	}

	/**
	 * Sets this interpreter
	 * @param i The interpreter using this strategy
	 */
	@Override
	public void setInterpreter(StrategyInterpreter i) {
		this.initialisation = (Initialisation) i;
	}




}