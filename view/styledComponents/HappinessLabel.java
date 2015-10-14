package view.styledComponents;

import java.awt.Font;

import javax.swing.JLabel;

import view.frames.GameFrame;

/**
 * This is a label styled with this game's theme
 * @author flanagdonn
 *
 */
public class HappinessLabel extends JLabel {

	/**
	 * This Label is like a JLabel but has the theme of the game
	 * @param message The message to be displayed on the label
	 */
	public HappinessLabel(String message){
		super(message);

		setFont(new Font("Serif", Font.BOLD, 14));
		setForeground(GameFrame.BUTTON_FONT_COLOR);

	}
}
