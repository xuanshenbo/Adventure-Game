package dataStorage.test.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(namespace = "shelf")
public class Shelf {

	private List<Book> bookList;
	private String name;
	private String location;

	public List<Book> getBooks() {
		return bookList;
	}

	public void setBooks(List<Book> books) {
		this.bookList = books;
	}

	public List<Book> getBookList() {
		return bookList;
	}

	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
