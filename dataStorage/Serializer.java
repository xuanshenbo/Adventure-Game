package dataStorage;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import dataStorage.pointers.AreaPointer;
import model.state.GameState;

/**
 *
 * @author Shenbo Xuan 300259386
 *
 */
public class Serializer {

	private static final String GAME_STATE_XML = "./myGame.xml";

	// So no one can accidently create a Serializer class
	private Serializer() {}

	public static void serialize(GameState game) throws JAXBException {
			JAXBContext context = JAXBContext.newInstance(new Class[] {
					GameState.class, AreaPointer.class});
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// Write to System.out
			m.marshal(game, System.out);

			// Write to File
			m.marshal(game, new File(GAME_STATE_XML));
	}
}
