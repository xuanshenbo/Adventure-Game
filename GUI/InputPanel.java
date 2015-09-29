package GUI;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.Initialisation;

public class InputPanel extends JPanel{

	private String msg = "Please choose a server to connect to";

	public InputPanel(Initialisation initialisation, String state) {
		setLayout(new FlowLayout());

		if(state.equals("connect")){
			JLabel message = new JLabel(msg);
			add(new TextFieldImpl());
		}
	}
}
