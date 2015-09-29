package Main;

import interpreter.InitialStrategy;
import interpreter.StrategyInterpreter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import GUI.WelcomePanel;

/**
 * The following initialises a game. It asks user to choose from creating a client or a server together with a client.
 * @author yanlong, flanagdonn
 *
 */
public class Initialisation extends StrategyInterpreter{

	public Initialisation(){
		super(null, new InitialStrategy());
		JFrame frame = new JFrame("Welcome to Adventure Game");
		WelcomePanel welcome = new WelcomePanel(this);
		frame.add(welcome);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void main(String[] args){
		new Initialisation();
	}



}
