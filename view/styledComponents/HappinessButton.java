package view.styledComponents;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.CompoundBorder;

import view.frames.GameFrame;

/**
 * This is a Button styled with this game's theme
 * @author flanagdonn
 *
 */
public class HappinessButton extends JButton {

	/**
	 * This Button is like a JButton but has the theme of the game
	 * @param message The message to be displayed on the button
	 */
	public HappinessButton(String name){
		super(name);

		javax.swing.border.Border empty;

		empty = BorderFactory.createEmptyBorder(1, 1, 1, 1);
		final CompoundBorder compound;

		Color crl = GameFrame.COL2;
		compound = BorderFactory.createCompoundBorder(empty,
				new OldRoundedBorderLine(crl));

		setPreferredSize(new Dimension(50, 30));

		setBorderPainted(true);
		setFocusPainted(false);
		setBorder(compound);

		revalidate();

		setForeground(GameFrame.BUTTON_FONT_COLOR);
	}
}
