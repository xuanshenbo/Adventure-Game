package GUI;

import interpreter.StrategyInterpreter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

/**
 * A JTextField which stores the users input for retrieval from the game via the input interpreter
 *
 */
public class TextFieldImpl extends JTextField {
	//public final StrategyInterpreter input; do i need this?

	public TextFieldImpl(){
		//input = i;
		setPreferredSize(new Dimension(200,50));
		setBackground(Color.PINK);	//not working
		addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = getText();
			}

		});
	}
}