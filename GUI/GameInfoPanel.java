package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class GameInfoPanel extends JPanel{

	private JPanel map;
	private JPanel gameInfo;
	private String time = "09:00";
	private String playerPosition = "(3, 5)";
	private Dimension size = new Dimension(250, 100);

	private Dimension mapSize = new Dimension(100, 100);

	public GameInfoPanel(){
		//set up the GridLayout with one row and two columns
		GridLayout layout = new GridLayout(1,2);
		setLayout(layout);

		//a panel which contains a zoomed-out map
		map = new JPanel();
		addMapToPanel();
		add(map);


		//a panel which displays player position, the time, etc in one column
		gameInfo = new JPanel();
		BoxLayout boxLayout = new BoxLayout(gameInfo, BoxLayout.PAGE_AXIS);
		gameInfo.setLayout(boxLayout);
		fillGameInfoPanel();
		add(gameInfo);

		//add a plain black border around the whole panel
		Border blackline = BorderFactory.createLineBorder(Color.black);
		setBorder(blackline);

		setSize(size);

		setBackground(new Color(204, 255, 229));
	}

	private void fillGameInfoPanel() {
		JLabel clock = new JLabel("Time: "+time);
		gameInfo.add(clock);

		JLabel position = new JLabel("Current Position: "+playerPosition);
		gameInfo.add(position);
	}

	private void addMapToPanel() {
		//display the map. Need to get this from Lucas.
		Image image=ImageLoader.loadImage("mapTest.jpg").getScaledInstance(mapSize.width, mapSize.height, -1);

		ImageIcon icon = new ImageIcon(image);
		JLabel thumb = new JLabel();
		thumb.setIcon(icon);
		map.add(thumb);

	}
}
