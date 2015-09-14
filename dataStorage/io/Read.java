package dataStorage.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Provides some static methods for reading a file.
 *
 * @author Shenbo Xuan 300259386
 *
 */
/*
 * These methods are mainly borrowed from the open source library Apache Commons
 * https://commons.apache.org/
 *
 * The reason why I use these methods rather than the Java standard library ways
 * to read Strings from files is becasuse after a bunch of testing, it turns out
 * that when reading a huge XML file into a String, BufferedReader cannot do the
 * job properly, i.e. it loses information.
 */
public class Read {

	/**
	 * The default buffer size to use.
	 */
	private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

	/**
	 * Reads the contents of a file into a String. The file is always closed.
	 *
	 * @param file
	 *            the file to read, must not be null
	 * @param encoding
	 *            the encoding to use, null means platform default
	 * @return the file contents, never null
	 * @throws IOException
	 *             in case of an I/O error
	 * @throws java.io.UnsupportedEncodingException
	 *             if the encoding is not supported by the VM
	 */
	public static String readFileToString(File file) throws IOException {
		InputStream in = null;
		try {
			in = openInputStream(file);
			return toString(in);
		} finally {
			closeQuietly(in);
		}
	}

	/**
	 * Opens a {@link FileInputStream} for the specified file, providing better
	 * error messages than simply calling new FileInputStream(file). At the end
	 * of the method either the stream will be successfully opened, or an
	 * exception will have been thrown. An exception is thrown if the file does
	 * not exist. An exception is thrown if the file object exists but is a
	 * directory. An exception is thrown if the file exists but cannot be read.
	 *
	 * @param file
	 *            the file to open for input, must not be null
	 * @return a new {@link FileInputStream} for the specified file
	 * @throws FileNotFoundException
	 *             if the file does not exist
	 * @throws IOException
	 *             if the file object is a directory
	 * @throws IOException
	 *             if the file cannot be read
	 */
	public static FileInputStream openInputStream(File file) throws IOException {
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new IOException("File '" + file
						+ "' exists but is a directory");
			}
			if (file.canRead() == false) {
				throw new IOException("File '" + file + "' cannot be read");
			}
		} else {
			throw new FileNotFoundException("File '" + file
					+ "' does not exist");
		}
		return new FileInputStream(file);
	}

	/**
	 * Get the contents of an InputStream as a String using the default
	 * character encoding of the platform. This method buffers the input
	 * internally, so there is no need to use a BufferedInputStream.
	 *
	 * @param input
	 *            the InputStream to read from
	 * @return the requested String
	 * @throws NullPointerException
	 *             if the input is null
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	public static String toString(InputStream input) throws IOException {
		StringWriter sw = new StringWriter();
		copy(input, sw);
		return sw.toString();
	}

	/**
	 * Copy bytes from an InputStream to chars on a Writer using the default
	 * character encoding of the platform. This method buffers the input
	 * internally, so there is no need to use a BufferedInputStream. This method
	 * uses {@link InputStreamReader}.
	 *
	 * @param input
	 *            the InputStream to read from
	 * @param output
	 *            the Writer to write to
	 * @throws NullPointerException
	 *             if the input or output is null
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	public static void copy(InputStream input, Writer output)
			throws IOException {
		InputStreamReader in = new InputStreamReader(input);
		copy(in, output);
	}

	/**
	 * Copy chars from a Reader to a Writer. This method buffers the input
	 * internally, so there is no need to use a BufferedReader. Large streams
	 * (over 2GB) will return a chars copied value of -1 after the copy has
	 * completed since the correct number of chars cannot be returned as an int.
	 * For large streams use the copyLarge(Reader, Writer) method.
	 *
	 * @param input
	 *            the Reader to read from
	 * @param output
	 *            the Writer to write to
	 * @return the number of characters copied
	 * @throws NullPointerException
	 *             if the input or output is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @throws ArithmeticException
	 *             if the character count is too large
	 */
	public static int copy(Reader input, Writer output) throws IOException {
		long count = copyLarge(input, output);
		if (count > Integer.MAX_VALUE) {
			return -1;
		}
		return (int) count;
	}

	/**
	 * Copy chars from a large (over 2GB) Reader to a Writer. This method
	 * buffers the input internally, so there is no need to use a
	 * BufferedReader.
	 *
	 * @param input
	 *            the Reader to read from
	 * @param output
	 *            the Writer to write to
	 * @return the number of characters copied
	 * @throws NullPointerException
	 *             if the input or output is null
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	public static long copyLarge(Reader input, Writer output)
			throws IOException {
		char[] buffer = new char[DEFAULT_BUFFER_SIZE];
		long count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}

	/**
	 * Unconditionally close an InputStream. This is typically used in finally
	 * blocks.
	 *
	 * @param input
	 *            the InputStream to close, may be null or already closed
	 */
	public static void closeQuietly(InputStream input) {
		try {
			if (input != null) {
				input.close();
			}
		} catch (IOException ioe) {
			// ignore
		}
	}
}
