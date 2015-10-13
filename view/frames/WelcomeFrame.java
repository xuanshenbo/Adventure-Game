package view.frames;

import interpreter.InitialStrategy;
import interpreter.StrategyInterpreter;
import interpreter.Translator;
import interpreter.Translator.Command;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import main.Initialisation;

/**
 * This frame contains the Welcome Panel, used to guide the logic at the beginning
 * of the game. It is in the theme of the game
 * @author flanagdonn
 *
 */
public class WelcomeFrame extends JFrame {

	private Initialisation initialisation;

	private YesNoOptionWindow yesno;

	public WelcomeFrame(String string, Initialisation initStrategy) {
		super(string);

		initialisation = initStrategy;

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		/*
		 *Prompt the user to confirm if they click the close button
		 */
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent we) {

				yesno = new YesNoOptionWindow(Command.EXIT, WelcomeFrame.this, "Happiness Game");

			}
		});

		//add a pink border around the whole frame
		getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.PINK));

	}

	/**
	 * This method notifies the server of whether the user wishes to exit the game
	 * or not
	 * @param state The state of the game
	 * @param isYes Whether the user confirmed or not
	 */
	public void yesSelected(Command state, boolean isYes){
		if(state.equals(Command.EXIT)){
			if(isYes){
				try {
					initialisation.notify(Command.EXIT.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			remove(yesno);
		}
	}

}
