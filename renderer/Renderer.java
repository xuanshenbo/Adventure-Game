package renderer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import state.Area;

public class Renderer extends JPanel{
	
	private int width,height;
	private Image boardImg;
	
	public Renderer(int width, int height){
		
		this.width = width;
		this.height = height;
		Dimension size = new Dimension(width, height);

//		boardImg = new ImageIcon("Images/Board.jpg").getImage();
		
		setPreferredSize(size);
		setBorder(BorderFactory.createLineBorder(Color.black));
		setVisible(true);
	}
	
	public void paintComponent(Graphics g) {
		//clear this panel
		this.removeAll();
		//draw the game board
			g.drawImage(boardImg, 0, 0, width, height, null);
	}

}