package view.panels;

import interpreter.Translator;

import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import view.styledComponents.HappinessLabel;
import view.styledComponents.TextFieldImpl;
import main.Initialisation;

/**
 * This class is used for when the user enters a server to connect to
 * @author flanagdonn
 *
 */
public class InputPanel extends JPanel{

	//One of these messages will be displayed to the user
	private String standardMessage = "Please choose a server to connect to and then press enter";
	private String errorMessage = "Please choose a valid ip address and try again";

	/**
	 *
	 * @param initialisation The initialisation to be notified of user input
	 * @param state This state determines what labels to add. At the moment, state is always CONNECT_TO_SERVER
	 * @param validIP If true, display standard message, otherwise display the error message.
	 */
	public InputPanel(Initialisation initialisation, Translator.InitialisationCommand state, boolean validIP) {
		setLayout(new FlowLayout());

		if(state.equals(Translator.InitialisationCommand.CONNECT_TO_SERVER)){
			HappinessLabel message = new HappinessLabel(validIP ? standardMessage : errorMessage);

			add(message);
			//add the TextField for the user to enter the ip address
			add(new TextFieldImpl(initialisation));
		}

		revalidate();
	}
}
