package items;

import java.awt.Dimension;
import java.awt.Image;

import GUI.ImageLoader;

/**
 * 0..* of these inside a player inventory
 * @author flanagdonn
 *
 */
public abstract class Item {

	private String description;
	private String imgpath;
	private Image img;
	private Dimension size = new Dimension(40, 40);
	private static int idCounter = 0;
	private int id;
	private char type;

	public Item(String description, String imgpath, char c){
		this.description = description;
		this.imgpath = imgpath;
		id = idCounter;
		idCounter++;
		type = c;
	}

	public abstract void use();

	/**
	 * This method used to display available options to the user
	 * @return The action specific to this item. Eg "unlock door" for a Key Item
	 */
	public abstract String getUseDescription();

	// ================================================
	// getters from here
	// ================================================

	public String getDescription() {

		if(description==null){
			return "Description here"; //testing purposes
		}

		return description;
	}

	public String getImgpath() {
		return imgpath;
	}

	/**
	 * Returns a picture of the item for displaying in the user inventory
	 * @return The picture associated with this item
	 */
	public Image getPicture() {
		if(img==null){
			return ImageLoader.loadImage(imgpath+".png").getScaledInstance(size.width, size.height, -1);
		}

		return img;
	}

	public Dimension getSize() {
		return size;
	}

	public int getId() {
		return id;
	}
	
	public char getType(){
		return type;
	}
}
