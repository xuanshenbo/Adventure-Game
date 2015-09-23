package dataStorage.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import dataStorage.test.model.*;

public class SerialiseMain {

	private static final String SHELF_XML = "./shelf-jaxb.xml";

	public static void main(String[] args) throws JAXBException,
			FileNotFoundException {
		Shelf politics = new Shelf();
		politics.setName("Politics");
		politics.setLocation("C-3");

		initShelf(politics);

		// create JAXB context and instantiate marshaller
		JAXBContext context = JAXBContext.newInstance(Shelf.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		// Write to System.out
		m.marshal(politics, System.out);

		// Write to File
		m.marshal(politics, new File(SHELF_XML));

		// get variables from our xml file, created before
		System.out.println();
		System.out.println("Output from our XML File: ");
		Unmarshaller um = context.createUnmarshaller();
		Shelf politics2 = (Shelf) um.unmarshal(new FileReader(SHELF_XML));
		ArrayList<Book> list = (ArrayList<Book>) politics2.getBookList();
		for (Book book : list) {
			System.out.println("Book: " + book.getName() + " from "
					+ book.getAuthor());
		}
	}

	private static void initShelf(Shelf politics) {
		List<Book> bookList = new ArrayList<Book>();

		// create Phone numbers
		PhoneNumber wellingtonNumber = new PhoneNumber();
		wellingtonNumber.setPrefix("(04)");
		wellingtonNumber.setNumber("232-XXXX");

		PhoneNumber aucklandNumber = new PhoneNumber();
		aucklandNumber.setPrefix("(09)");
		aucklandNumber.setNumber("781-xxxx");

		// create authors
		Author celia = new Author();
		celia.setAge(45);
		celia.setName("Celia");
		celia.setPhoneNumber(wellingtonNumber);

		Author len = new Author();
		len.setAge(55);
		len.setName("Len");
		len.setPhoneNumber(aucklandNumber);

		// create books
		Book book1 = new Book();
		book1.setIsbn("978-0060554736");
		book1.setName("Wellington Mayor");
		book1.setAuthor(celia);
		book1.setPublisher("Harpercollins");
		bookList.add(book1);

		Book book2 = new Book();
		book2.setIsbn("978-3832180577");
		book2.setName("Auckland Mayor");
		book2.setAuthor(len);
		book2.setPublisher("Dumont Buchverlag");
		bookList.add(book2);

		Book book3 = new Book();
		book3.setIsbn("978-3832180527");
		book3.setName("Auckland Mayor - 2");
		book3.setAuthor(len);
		book3.setPublisher("University of Auckland");
		bookList.add(book3);

		politics.setBookList(bookList);
	}
}
