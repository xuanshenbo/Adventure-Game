package dataStorage.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Provides some static methods for writing things into a file.
 *
 * @author Shenbo Xuan 300259386
 *
 */

/*
 * These methods are mainly borrowed from the open source library Apache Commons
 * https://commons.apache.org/
 *
 * The reason why I use these methods rather than the Java standard library ways
 * to write things into files is becasuse after a bunch of testing, it turns out
 * that when writing a huge string into an XML file, neither BufferedWriter nor
 * PrintWriter can do the job properly, i.e. they lose information.
 */
public class Write {

	// Just so no one can accidently create a Write object
	private Write() {}

	/**
	 * Writes a String to a file creating the file if it does not exist.
	 *
	 * @param file
	 *            the file to write
	 * @param data
	 *            the content to write to the file
	 * @param encoding
	 *            the encoding to use, null means platform default
	 * @throws IOException
	 *             in case of an I/O error
	 * @throws java.io.UnsupportedEncodingException
	 *             if the encoding is not supported by the VM
	 */
	public static void writeStringToFile(File file, String data)
			throws IOException {
		OutputStream out = null;
		try {
			out = openOutputStream(file);
			write(data, out);
		} finally {
			closeQuietly(out);
		}
	}

	/**
	 * Opens a {@link FileOutputStream} for the specified file, checking and
	 * creating the parent directory if it does not exist. At the end of the
	 * method either the stream will be successfully opened, or an exception
	 * will have been thrown. The parent directory will be created if it does
	 * not exist. The file will be created if it does not exist. An exception is
	 * thrown if the file object exists but is a directory. An exception is
	 * thrown if the file exists but cannot be written to. An exception is
	 * thrown if the parent directory cannot be created.
	 *
	 * @param file
	 *            the file to open for output, must not be <code>null</code>
	 * @return a new {@link FileOutputStream} for the specified file
	 * @throws IOException
	 *             if the file object is a directory
	 * @throws IOException
	 *             if the file cannot be written to
	 * @throws IOException
	 *             if a parent directory needs creating but that fails
	 */
	public static FileOutputStream openOutputStream(File file)
			throws IOException {
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new IOException("File '" + file
						+ "' exists but is a directory");
			}
			if (file.canWrite() == false) {
				throw new IOException("File '" + file
						+ "' cannot be written to");
			}
		} else {
			File parent = file.getParentFile();
			if (parent != null && parent.exists() == false) {
				if (parent.mkdirs() == false) {
					throw new IOException("File '" + file
							+ "' could not be created");
				}
			}
		}
		return new FileOutputStream(file);
	}

	/**
	 * Writes chars from a String to bytes on an OutputStream using the default
	 * character encoding of the platform. This method uses
	 * {@link String#getBytes()}.
	 *
	 * @param data
	 *            the String to write, null ignored
	 * @param output
	 *            the <code>OutputStream</code> to write to
	 * @throws NullPointerException
	 *             if output is null
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	public static void write(String data, OutputStream output)
			throws IOException {
		if (data != null) {
			output.write(data.getBytes());
		}
	}

	/**
	 * Unconditionally close an OutputStream. This is typically used in finally
	 * blocks.
	 *
	 * @param output
	 *            the OutputStream to close, may be null or already closed
	 */
	public static void closeQuietly(OutputStream output) {
		try {
			if (output != null) {
				output.close();
			}
		} catch (IOException ioe) {
			// ignore
		}
	}
}
