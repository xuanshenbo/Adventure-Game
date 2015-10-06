package model.items;

import java.awt.Dimension;
import java.awt.Image;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

import model.state.Player;
import GUI.ImageLoader;

/**
 * 0..* of these inside a player inventory
 * @author flanagdonn
 *
 */
@XmlSeeAlso({ Bag.class, Key.class })
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Item {

	// every time an Item is created, generate an id for it using this field
	private static int idCounter = 0;

	private String description;
	private String imgpath;
	@XmlTransient
	private Image img;
	private Dimension size = new Dimension(40, 40);
	private int id;
	private char type;

	public Item(String description, String imgpath, char c){
		this.description = description;
		this.imgpath = imgpath;
		id = idCounter;
		idCounter++;
		type = c;
	}

	public abstract Item[] use(Player player);

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
