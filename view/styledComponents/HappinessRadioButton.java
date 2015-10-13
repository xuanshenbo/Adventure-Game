package view.styledComponents;

import java.awt.Font;

import javax.swing.JRadioButton;

import view.frames.GameFrame;

/**
 * This is a Radio Button styled with this game's theme
 * @author flanagdonn
 *
 */
public class HappinessRadioButton extends JRadioButton {

public HappinessRadioButton(String name){
	super(name);

	setFont(new Font("Serif", Font.BOLD, 14));
	setForeground(GameFrame.BUTTON_FONT_COLOR);
}
}
