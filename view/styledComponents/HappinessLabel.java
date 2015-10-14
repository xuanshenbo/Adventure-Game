package view.styledComponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.Map;

import javax.swing.JLabel;

import view.frames.GameFrame;

/**
 * This is a label styled with this game's theme
 * @author flanagdonn
 *
 */
public class HappinessLabel extends JLabel {

	private int fontSize = 14;
	private Color fontColor = GameFrame.BUTTON_FONT_COLOR;

	/**
	 * This Label is like a JLabel but has the theme of the game
	 * @param message The message to be displayed on the label
	 */
	public HappinessLabel(String message){
		super(message);

		setFont(new Font("Serif", Font.BOLD, fontSize));
		setForeground(fontColor);

	}

	/**
	 * Create a header label with a darker colour and larger font size
	 * @param size The size of the header
	 */
	public void createHeader(int size) {
		fontSize = size;
		fontColor = fontColor.darker();

		setFont(new Font("Serif", Font.BOLD, fontSize));
		setForeground(fontColor);

		Font font = getFont();
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

		setFont(font.deriveFont(attributes));

		repaint();

	}
}
