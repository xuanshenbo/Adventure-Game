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

	private static final String SHELF_XML = "./myshelf.xml";

	public static void main(String[] args) throws JAXBException,
			FileNotFoundException {
		Shelf politics = new Shelf();

		initShelf(politics);

		// create JAXB context and instantiate marshaller
		JAXBContext context = JAXBContext.newInstance(new Class[] {
				Shelf.class, AuthorPointer.class });
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
		PhoneNumber chinaNumber = new PhoneNumber();
		chinaNumber.setPrefix("(+86)");
		chinaNumber.setNumber("15893726132");

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

		Author mao = new Author();
		mao.setAge(99);
		mao.setName("Mao");
		mao.setPhoneNumber(chinaNumber);

		// set friends
		List<Author> celiaFriends = new ArrayList<Author>();
		celiaFriends.add(len);
		celiaFriends.add(mao);
		celia.setFriends(celiaFriends);

		List<Author> lenFriends = new ArrayList<Author>();
		lenFriends.add(celia);
		lenFriends.add(mao);
		len.setFriends(lenFriends);

		List<Author> maoFriends = new ArrayList<Author>();
		maoFriends.add(len);
		maoFriends.add(celia);
		maoFriends.add(mao);
		mao.setFriends(maoFriends);

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

		Book book4 = new Book();
		book4.setIsbn("448498-45485");
		book4.setName("Maoism");
		book4.setAuthor(mao);
		book4.setPublisher("China");
		bookList.add(book4);

		politics.setName("Politics");
		politics.setLocation("C-3");

		politics.setBookList(bookList);
	}
}
