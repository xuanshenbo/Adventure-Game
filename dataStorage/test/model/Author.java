package dataStorage.test.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.sun.xml.internal.bind.CycleRecoverable;

//@XmlType(propOrder = { "name", "phoneNumber", "friends", "pet" })
@XmlAccessorType(XmlAccessType.FIELD)
public class Author implements CycleRecoverable {

	private String name;
	private int age;
	private PhoneNumber phoneNumber;
	@XmlElementWrapper
	@XmlElement(name = "friend")
	private List<Author> friends;
	@XmlElementWrapper
	@XmlAnyElement
	private List<Pet> pets;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public PhoneNumber getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(PhoneNumber phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<Author> getFriends() {
		return friends;
	}

	public void setFriends(List<Author> friends) {
		this.friends = friends;
	}

	public List<Pet> getPet() {
		return pets;
	}

	public void setPet(List<Pet> pets) {
		this.pets = pets;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public Object onCycleDetected(Context arg0) {
		AuthorPointer ap = new AuthorPointer();
		ap.setName(name);
		ap.setPhoneNumber(phoneNumber);
		return ap;
	}
}
