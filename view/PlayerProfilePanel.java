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
	private JPanel picturePanel;
	private JPanel statusPanel;

	private int bar_left = 5;
	private int bar_top = 5;
	private int bar_width = 100;
	private int bar_height = 30;

	private int lifelineValue = 50;

	private PlayerInfo playerInfo;

	private int numAvatars = 4;

	/*public PlayerProfilePanel(PlayerInfo info){

		playerInfo = info;

		lifelineValue = info.getLifeline();

		//load all the images for the different available avatars
		avatars = new Image[numAvatars];
		loadImages();

		//set up the GridLayout with one row and two columns
		GridLayout layout = new GridLayout(1,2);
		setLayout(layout);

		//a panel which displays lifeline, name etc in one column
		statusPanel = new JPanel();
		statusPanel.setBackground(GameFrame.col1);
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
	}*/

	public PlayerProfilePanel(PlayerInfo info){

		playerInfo = info;

		lifelineValue = info.getLifeline();

		//load all the images for the different available avatars
		avatars = new Image[numAvatars];
		loadImages();

		setLayout(new BorderLayout());
		setBackground(GameFrame.col1);

		JLabel name = new JLabel(playerInfo.getName());
		name.setFont(new Font("Serif", Font.BOLD, 20));
		add(name, BorderLayout.NORTH);

		//add image
		Image image=avatars[Avatar.getAvatarAsInteger(playerInfo.getAvatar())-1];

		ImageIcon icon = new ImageIcon(image);
		JLabel thumb = new JLabel();
		thumb.setIcon(icon);
		thumb.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(thumb, BorderLayout.CENTER);

		//add a plain black border around the whole panel
		Border blackline = BorderFactory.createLineBorder(Color.black, 2, true);
		setBorder(blackline);
	}

	//used for determining the size of the top panel
	public PlayerProfilePanel() {
		// TODO Auto-generated constructor stub
	}

	private void fillStatusPanel() {
		JLabel name = new JLabel(playerInfo.getName());
		name.setFont(new Font("Serif", Font.BOLD, 20));
		statusPanel.add(name);

//		JLabel lifelineMsg = new JLabel("Lifeline");
//		JLabel lifeline = lifelineLabel();
//
//		statusPanel.add(lifelineMsg);
//		statusPanel.add(lifeline);
//
//		JLabel happiness = new JLabel("Happiness level");
//		statusPanel.add(happiness);

		repaint();
	}

	/*
	 * TODO make this work
	 */
	private JLabel lifelineLabel() {

		JLabel label = new JLabel(){
			@Override
			public void paintComponent(Graphics g){
			    super.paintComponent(g);
			    g.setColor(Color.BLACK);
			    g.drawRect(bar_left, bar_top, bar_width, bar_height);
			    g.setColor(Color.RED);
			    g.fillRect(bar_left, bar_top, lifelineValue, bar_height);

			}
		};
		return label;
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
		avatars[3] = ImageLoader.loadImage("bottomleypotts.png")
				.getScaledInstance(pictureSize.width, pictureSize.height, -1);

	}

	private void addPictureToPanel() {
		//display the image at the appropriate position in the array
		Image image=avatars[Avatar.getAvatarAsInteger(playerInfo.getAvatar())-1];

		ImageIcon icon = new ImageIcon(image);
		JLabel thumb = new JLabel();
		thumb.setIcon(icon);
		picturePanel.add(thumb);
	}




}
