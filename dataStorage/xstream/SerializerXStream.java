package dataStorage.xstream;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import model.state.GameState;

public class SerializerXStream {
	static XStream xstream = new XStream(new DomDriver());

	public static void serialize(GameState game) {

		String xml = xstream.toXML(game);

		try {
			BufferedWriter out = new BufferedWriter(
					new FileWriter("sample.txt"));
			out.write(xml);
			out.close();
		} catch (IOException e) {
			System.out.println("Exception ");
		}

		return;
	}

	public static GameState deserialize(String fileName) {
		return (GameState) xstream.fromXML(fileName);
	}
}
