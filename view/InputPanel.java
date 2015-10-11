package view;

import interpreter.Translator;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Initialisation;

public class InputPanel extends JPanel{

	private String msg = "Please choose a server to connect to and then press enter";

	public InputPanel(Initialisation initialisation, Translator.InitialisationCommand state) {
		setLayout(new FlowLayout());

		if(state.equals(Translator.InitialisationCommand.CONNECT_TO_SERVER)){
			JLabel message = new JLabel(msg);
			add(message);
			add(new TextFieldImpl(initialisation));
		}

		revalidate();
	}
}
