package Main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import GUI.WelcomeDialog;

/**
 * The following initialise a game. It asks user to choose from creating a client or a server together with a client.
 * @author yanlong
 *
 */
public class Initialisation implements ActionListener{

	public Initialisation(){
		WelcomeDialog welcome = new WelcomeDialog();
	}
	@Override
	public void actionPerformed(ActionEvent e) {

	}

}
