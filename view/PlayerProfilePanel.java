package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class PlayerProfilePanel extends JPanel{
	private JLabel playerPic;
	private JLabel lifeline;
	private JButton inventory;
	private Dimension size = new Dimension(250, 100);
	private Dimension pictureSize = new Dimension(100, 100);
	private Dimension labelSize = new Dimension(40, 30);
	private Image[] avatars;

	private int bar_left = 5;
	private int bar_top = 5;
	private int bar_width = 100;
	private int bar_height = 30;

	private int avatarFontSize = 30;

	private PlayerInfo playerInfo;

	private int numAvatars = 4;

	/**
	 * The PlayerProfilePanel displays the player name, avatar image, and game logo (cupcake)
	 * @param gameFrame
	 */
	public PlayerProfilePanel(GameFrame gameFrame){

		playerInfo = gameFrame.getPlayer();

		//load all the images for the different available avatars
		avatars = new Image[numAvatars];
		loadImages();

		setLayout(new BorderLayout());
		setBackground(GameFrame.col1.darker());

		JLabel name = new JLabel(playerInfo.getName());
		name.setForeground(GameFrame.col2);
		name.setFont(new Font("Serif", Font.BOLD, avatarFontSize));
		add(name, BorderLayout.WEST);

		//add cupcake image
		Image cupcake= ImageLoader.loadImage("cupcake2.png");
		ImageIcon cupcakeicon = new ImageIcon(cupcake);
		JLabel cupcakethumb = new JLabel();
		cupcakethumb.setIcon(cupcakeicon);
		add(cupcakethumb, BorderLayout.CENTER);

		//add image of avatar
		Image avatarImage=avatars[Avatar.getAvatarAsInteger(playerInfo.getAvatar())-1];
		ImageIcon avataricon = new ImageIcon(avatarImage);
		JLabel avatarthumb = new JLabel();
		avatarthumb.setIcon(avataricon);
		add(avatarthumb, BorderLayout.EAST);

		//add a plain black border around the whole panel
		Border blackline = BorderFactory.createLineBorder(Color.black, 2, true);
		setBorder(blackline);
	}


	private void loadImages() {
		//this generates an image file and saves it to the array

		//image of Donald Duck
		avatars[0] = ImageLoader.loadImage("donaldduck.png")
				.getScaledInstance(pictureSize.width, pictureSize.height, -1);

		//image of Mickey Mouse
		avatars[1] = ImageLoader.loadImage("mickeymouse.png")
				.getScaledInstance(pictureSize.width, pictureSize.height, -1);

		//image of Hairy Maclary
		avatars[2] = ImageLoader.loadImage("hairymaclary.png")
				.getScaledInstance(pictureSize.width, pictureSize.height, -1);

		//image of Bottomley Potts
		avatars[3] = ImageLoader.loadImage("muffinmaclay.png")
				.getScaledInstance(pictureSize.width, pictureSize.height, -1);

	}


}
