package dataStorage.test.model;

public class PhoneNumber {

	private String prefix;
	private String number;

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return prefix + " " + number;
	}
}
