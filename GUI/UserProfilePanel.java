package GUI;

import java.awt.Color;
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

public class UserProfilePanel extends JPanel{
	private JLabel playerPic;
	private JLabel lifeline;
	private JButton inventory;
	private Dimension size = new Dimension(100, 100);
	private Dimension pictureSize = new Dimension(50, 50);
	private Dimension labelSize = new Dimension(40, 30);
	private Image[] avatars;
	private JPanel picturePanel;
	private JPanel statusPanel;

	public UserProfilePanel(){

		//load all the images for the different available avatars
		avatars = new Image[1];
		loadImages();

		//set up the GridLayout with one row and two columns
		GridLayout layout = new GridLayout(1,2);
		setLayout(layout);

		//a panel which contains just the player's avatar
		picturePanel = new JPanel();
		addPictureToPanel();
		add(picturePanel);


		//a panel which displays lifeline, name etc in one column
		statusPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(picturePanel, BoxLayout.PAGE_AXIS);
		statusPanel.setLayout(boxLayout);
		fillStatusPanel();

		//add a plain black border around the whole panel
		Border blackline = BorderFactory.createLineBorder(Color.black);
		setBorder(blackline);

		setPreferredSize(size);
	}

	private void fillStatusPanel() {
		JLabel name = new JLabel("Donald Duck");
		name.setFont(new Font("Serif", Font.BOLD, 20));
		picturePanel.add(name);

		JLabel lifeline = new JLabel("Lifeline");
		picturePanel.add(lifeline);

		JLabel happiness = new JLabel("Happiness level");
		picturePanel.add(happiness);

	}

	private void loadImages() {
		avatars[0] = loadImage("playerpictest.jpeg");  //this generates an image file

	}

	//TODO decide if this will be taken care of by a button
	private void displayInventory() {
		// TODO Auto-generated method stub

	}

	private void addPictureToPanel() {

		Image image=avatars[0];

		ImageIcon icon = new ImageIcon(image);
		JLabel thumb = new JLabel();
		thumb.setIcon(icon);

		add(thumb);

	}

	/*
	 * Loads an image from file and handles exceptions
	 */
	private Image loadImage(String filename) {
		try {
			Image img = ImageIO.read(new File("images/"+filename));
			//scale the picture
			img = img.getScaledInstance(size.width, size.height, -1);

			return img;
		} catch (IOException e) {
			// we've encountered an error loading the image. There's not much we
			// can actually do at this point, except to abort the game.
			throw new RuntimeException("Unable to load image: " + filename);
		}

	}
}
