package view.styledComponents;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;

import view.frames.GameFrame;

/**
 * This is a Button styled with this game's theme
 * @author flanagdonn
 *
 */
public class HappinessButton extends JButton {

	public HappinessButton(String name){
		super(name);

		javax.swing.border.Border line, raisedbevel, loweredbevel;
		TitledBorder title;
		javax.swing.border.Border empty;
		line = BorderFactory.createLineBorder(Color.black);
		raisedbevel = BorderFactory.createRaisedBevelBorder();
		loweredbevel = BorderFactory.createLoweredBevelBorder();
		title = BorderFactory.createTitledBorder("");
		empty = BorderFactory.createEmptyBorder(1, 1, 1, 1);
		final CompoundBorder compound, compound1, compound2;

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
