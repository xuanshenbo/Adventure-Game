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

	public HappinessLabel(String message){
		super(message);

		setFont(new Font("Serif", Font.BOLD, 14));
		setForeground(GameFrame.BUTTON_FONT_COLOR);

	}
}
