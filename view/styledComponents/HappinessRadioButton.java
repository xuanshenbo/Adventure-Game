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

private JRadioButton button;

public HappinessRadioButton(String name){
	button.setFont(new Font("Serif", Font.BOLD, 14));
	button.setForeground(GameFrame.BUTTON_FONT_COLOR);
}
}
