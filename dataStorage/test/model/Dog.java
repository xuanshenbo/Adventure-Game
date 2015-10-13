package dataStorage.test.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Dog implements Pet {

	private String name;
	private int weight;
	private int height;

	@Override
	public String sound() {
		return "bark";
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int getWeight() {
		return this.weight;
	}

	@Override
	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void setHeight(int height) {
		this.height = height;
	}

}
