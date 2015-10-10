package model.items;

import java.awt.Dimension;
import java.awt.Image;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

import view.ImageLoader;
import model.state.Player;

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
	@XmlTransient
	private Image img;
	private int id;
	private char type;

	public Item(char c){
		id = idCounter;
		idCounter++;
		type = c;
	}

	public abstract Item[] use(Player player);

	public int getId() {
		return id;
	}

	public char getType(){
		return type;
	}
}
