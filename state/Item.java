package state;

import java.awt.Dimension;
import java.awt.Image;

import GUI.ImageLoader;

/**
 * 0..* of these inside a player inventory
 * @author flanagdonn
 *
 */
public class Item {

	private String description;
	private Image img;
	private Dimension size = new Dimension(40, 40);

	public String getDescription() {

		if(description==null){
			return "Description here"; //testing purposes
		}

		return description;
	}

	/**
	 * Returns a picture of the item for displaying in the user inventory
	 * @return The picture associated with this item
	 */
	public Image getPicture() {
		if(img==null){
			return ImageLoader.loadImage("key.png").getScaledInstance(size.width, size.height, -1);
		}

		return img;
	}

}
