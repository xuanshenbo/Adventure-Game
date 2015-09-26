package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
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
	private JPanel picturePanel;
	private JPanel statusPanel;

	private PlayerInfo playerInfo;

	//change this, as more pictures and avatars are added
	private int numAvatars = 1;

	public PlayerProfilePanel(PlayerInfo info){
		playerInfo = info;

		//load all the images for the different available avatars
		avatars = new Image[numAvatars];
		loadImages();

		//set up the GridLayout with one row and two columns
		GridLayout layout = new GridLayout(1,2);
		setLayout(layout);

		//a panel which displays lifeline, name etc in one column
		statusPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(statusPanel, BoxLayout.PAGE_AXIS);
		statusPanel.setLayout(boxLayout);
		fillStatusPanel();
		add(statusPanel);

		//a panel which contains just the player's avatar
		picturePanel = new JPanel();
		addPictureToPanel();
		//picturePanel.setSize(pictureSize);

		picturePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		add(picturePanel);




		//add a plain black border around the whole panel
		Border blackline = BorderFactory.createLineBorder(Color.black);
		setBorder(blackline);

		//setPreferredSize(size);
	}

	public PlayerProfilePanel() {
		// TODO Auto-generated constructor stub
	}

	private void fillStatusPanel() {
		JLabel name = new JLabel(playerInfo.getName());
		name.setFont(new Font("Serif", Font.BOLD, 20));
		statusPanel.add(name);

		JLabel lifeline = new JLabel("Lifeline");
		statusPanel.add(lifeline);

		JLabel happiness = new JLabel("Happiness level");
		statusPanel.add(happiness);



	}

	private void loadImages() {
		//this generates an image file and saves it to the array
		avatars[0] = ImageLoader.loadImage("playerpictest.jpeg")
				.getScaledInstance(pictureSize.width, pictureSize.height, -1);

	}

	//TODO decide if this will be taken care of by a button
	private void displayInventory() {
		// TODO Auto-generated method stub

	}

	private void addPictureToPanel() {
		//display the image at the appropriate position in the array
		Image image=avatars[Avatar.getAvatarAsInteger(playerInfo.getAvatar())];

		ImageIcon icon = new ImageIcon(image);
		JLabel thumb = new JLabel();
		thumb.setIcon(icon);
		picturePanel.add(thumb);
	}


}
