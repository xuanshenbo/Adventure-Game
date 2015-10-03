package dataStorage.test.model;

import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({Dog.class,Cat.class})
public abstract class Pet {
	private String name;
	private int weight;
	private int height;

	public abstract String sound();

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}


}
