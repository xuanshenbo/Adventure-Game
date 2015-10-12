package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 	This displays the time, ip address, and the happiness level of the player
 * @author flanagdonn
 *
 */
public class StatusPanel extends JPanel {

	private GameFrame frame;

	private int bar_left = 20;
	private int bar_top = 70;
	private int bar_width = 100;
	private int bar_height = 20;

	//This JLabel displays all the information
	private JLabel statusWindow;

	/**
	 * The constructor sets the layout and creates the statusWindow JLabel
	 * @param layout The layout for the panel
	 * @param gameFrame The GameFrame from whom this panel needs information to display
	 */
	public StatusPanel(BorderLayout layout, GameFrame gameFrame) {
		setLayout(layout);

		this.frame = gameFrame;

		/*
		 * Add a JLabel which draws the status panel on top the canvas
		 */
		statusWindow = new JLabel(){
			@Override
			public void paintComponent(Graphics g){
				//set the font and size of the text to be drawn
				g.setFont(new Font("Serif", Font.BOLD, 16));

				//draw the happiness level title
				g.setColor(GameFrame.statusBarFontColor);
				g.drawString("Happiness Level", frame.getGap(), bar_top - 40);

				//draw the happiness bar
				g.setColor(GameFrame.happinessBarColor);
				g.fillRect(frame.getGap(), bar_top - 20, frame.getHappinessLevel(), bar_height);

				//draw a pink outline around the bar
				g.setColor(GameFrame.statusBarFontColor);
				g.drawRect(frame.getGap(), bar_top - 20, bar_width, bar_height);

				//draw the time info
				g.setColor(GameFrame.statusBarFontColor);
				g.drawString("The time is: "+frame.getTime(), frame.getGap(), bar_top + 20);

				//draw the player's ip address
				g.setColor(GameFrame.statusBarFontColor);
				g.drawString(frame.getIP()+"", frame.getGap(), bar_top + 40);

			}

		};

		add(statusWindow, BorderLayout.CENTER);

		//want to see the map behind panel, so make panel only semi-opaque
		int r, g, b, opacity;
		r = GameFrame.statusPanelColour.getRed();
		g = GameFrame.statusPanelColour.getGreen();
		b = GameFrame.statusPanelColour.getBlue();
		opacity = 200;

		setBackground(new Color(r, g, b, opacity));
	}

}
