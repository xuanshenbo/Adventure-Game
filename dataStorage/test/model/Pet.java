package dataStorage.test.model;

import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({ Dog.class, Cat.class })
public interface Pet {

	public String sound();

	public String getName();

	public void setName(String name);

	public int getWeight();

	public void setWeight(int weight);

	public int getHeight();

	public void setHeight(int height);

}
