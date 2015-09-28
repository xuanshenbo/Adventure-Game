package dataStorage.test.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.sun.xml.internal.bind.CycleRecoverable;

@XmlType(propOrder = { "name", "phoneNumber", "friends" })
public class Author implements CycleRecoverable {

	private String name;
	private int age;
	private PhoneNumber phoneNumber;
	private List<Author> friends;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlTransient
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

	@XmlElementWrapper
	@XmlElement(name="friend")
	public List<Author> getFriends() {
		return friends;
	}

	public void setFriends(List<Author> friends) {
		this.friends = friends;
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
