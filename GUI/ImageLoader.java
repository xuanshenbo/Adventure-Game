package GUI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImageLoader {
	/*
	 * Loads an image from file and handles exceptions
	 */
	public static Image loadImage(String filename) {
		try {
			Image img = ImageIO.read(new File("src/images/"+filename));
			return img;
		} catch (IOException e) {
			// we've encountered an error loading the image. There's not much we
			// can actually do at this point, except to abort the game.
			throw new RuntimeException("Unable to load image: " + filename);
		}

	}

}
