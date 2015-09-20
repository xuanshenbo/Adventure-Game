package renderer;

import state.Tile;

import javax.swing.*;
import java.awt.*;

public class Renderer extends JPanel{
	
	private int width,height;
	private Tile[][] area;
	private double squareHeight;
	private double squareWidth;
	private AvatarImages avatarImages;

	public Renderer(int width, int height, Tile[][] area){
		
		this.width = width;
		this.height = height;
		this.area = area;

		this.avatarImages = new AvatarImages("resource/image/map/assassin.png");

		setWidthHeight(width, height);
		Dimension size = new Dimension(width, height);

//		boardImg = new ImageIcon("Images/Board.jpg").getImage();
		
		setPreferredSize(size);
		setBorder(BorderFactory.createLineBorder(Color.black));
		setVisible(true);
	}

	public void setWidthHeight(int w, int h){
//		if(w != width || h != height){
			width = w;
			height = h;
			squareWidth = w/24.0;
			squareHeight = h/25.0;
//			return true;
//		}
//		return false;
	}
	
	public void paintComponent(Graphics g) {
		//clear this panel
		this.removeAll();
		//draw the game board
	}

}