package view.styledComponents;

import interpreter.StrategyInterpreter;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JTextField;

import view.frames.GameFrame;

/**
 * A JTextField which stores the users input for retrieval from the game via the input interpreter
 * @author flanagdonn
 */
public class TextFieldImpl extends JTextField {
	public final StrategyInterpreter inputInterp;

	/**
	 * This text field allows the user to enter the ip address they wish
	 * to connect to, and then notifies the interpreter of their input
	 * @param interp
	 */
	public TextFieldImpl(StrategyInterpreter interp){

		this.inputInterp = interp;

		setPreferredSize(new Dimension(200,50));
		setBackground(GameFrame.COL2);
		addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String IPAddress = getText();
				try {
					inputInterp.notify(IPAddress);
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}

		});
	}
}