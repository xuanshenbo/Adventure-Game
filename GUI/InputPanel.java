package GUI;

import interpreter.Translator;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Initialisation;
import main.InitialisationState;

public class InputPanel extends JPanel{

	private String msg = "Please choose a server to connect to";

	public InputPanel(Initialisation initialisation, InitialisationState state) {
		setLayout(new FlowLayout());

		if(state.equals(InitialisationState.CONNECT_TO_SERVER)){
			JLabel message = new JLabel(msg);
			add(message);
			add(new TextFieldImpl(initialisation));
		}
		revalidate();
	}
}
