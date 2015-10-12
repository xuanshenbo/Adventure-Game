package view;

import interpreter.Translator;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Initialisation;

public class InputPanel extends JPanel{

	private String standardMessage = "Please choose a server to connect to and then press enter";
	private String errorMessage = "Please choose a valid ip address and try again";

	public InputPanel(Initialisation initialisation, Translator.InitialisationCommand state, boolean validIP) {
		setLayout(new FlowLayout());

		if(state.equals(Translator.InitialisationCommand.CONNECT_TO_SERVER)){
			JLabel message = new JLabel(validIP ? standardMessage : errorMessage);
			add(message);
			add(new TextFieldImpl(initialisation));
		}

		revalidate();
	}
}
