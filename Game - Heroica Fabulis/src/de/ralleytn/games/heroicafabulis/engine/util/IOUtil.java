package de.ralleytn.games.heroicafabulis.engine.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import static de.ralleytn.games.heroicafabulis.engine.util.BinaryUtil.*;

/**
 * Utility class containing methods for working with input and output streams.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 24.08.2018/0.2.0
 * @since 11.08.2018/0.1.0
 */
public final class IOUtil {

	/**
	 * Private because no instances of this class should exist.
	 * @since 11.08.2018/0.1.0
	 */
	private IOUtil() {}
	
	/**
	 * Reads a string from the given input stream.
	 * @param stream the input stream
	 * @param length the byte length
	 * @param charset the charset used to decode the bytes
	 * @return the read string
	 * @throws IOException if an I/O error occurred
	 * @since 11.08.2018/0.1.0
	 */
	public static final String readString(InputStream stream, int length, Charset charset) throws IOException {

		return new String(readBytes(stream, length), charset.name());
	}
	
	/**
	 * Reads a string from the given input stream.
	 * @param stream the input stream
	 * @param length the byte length
	 * @return the read string
	 * @throws IOException if an I/O error occurred
	 * @since 23.08.2018/0.2.0
	 */
	public static final String readString(InputStream stream, int length) throws IOException {
		
		return new String(readBytes(stream, length));
	}
	
	/**
	 * Reads a float from the given input stream.
	 * @param inputStream the input stream
	 * @param bigEndian {@code true} for big endian byte order, {@code false} for small endian byte order
	 * @return the read float
	 * @throws IOException if an I/O error occurred
	 * @since 11.08.2018/0.1.0
	 */
	public static final float readFloat(InputStream inputStream, boolean bigEndian) throws IOException {
		
		return Float.intBitsToFloat(readSignedInt(inputStream, bigEndian));
	}
	
	/**
	 * Reads a double from the given input stream.
	 * @param inputStream the input stream
	 * @param bigEndian {@code true} for big endian byte order, {@code false} for small endian byte order
	 * @return the read double
	 * @throws IOException if an I/O error occurred
	 * @since 11.08.2018/0.1.0
	 */
	public static final double readDouble(InputStream inputStream, boolean bigEndian) throws IOException {
		
		return Double.longBitsToDouble(readSignedLong(inputStream, bigEndian));
	}
	
	/**
	 * Reads an unsigned short from the given input stream.
	 * @param inputStream the input stream
	 * @param bigEndian {@code true} for big endian byte order, {@code false} for small endian byte order
	 * @return the read short
	 * @throws IOException if an I/O error occurred
	 * @since 11.08.2018/0.1.0
	 */
	public static final int readUnsignedShort(InputStream inputStream, boolean bigEndian) throws IOException {
		
		return readSignedShort(inputStream, bigEndian) & 0xFFFF;
	}
	
	/**
	 * Reads an unsigned int from the given input stream.
	 * @param inputStream the input stream
	 * @param bigEndian {@code true} for big endian byte order, {@code false} for small endian byte order
	 * @return the read int
	 * @throws IOException if an I/O error occurred
	 * @since 11.08.2018/0.1.0
	 */
	public static final long readUnsignedInt(InputStream inputStream, boolean bigEndian) throws IOException {
		
		return readSignedInt(inputStream, bigEndian) & 0xFFFFFFFFL;
	}
	
	/**
	 * Reads a signed short from the given input stream.
	 * @param inputStream the input stream
	 * @param bigEndian {@code true} for big endian byte order, {@code false} for small endian byte order
	 * @return the read short
	 * @throws IOException if an I/O error occurred
	 * @since 11.08.2018/0.1.0
	 */
	public static final short readSignedShort(InputStream inputStream, boolean bigEndian) throws IOException {
		
		byte[] bytes = new byte[Short.BYTES];
		return (short)(bigEndian ?
				((bytes[0] & 0xFF) << 8) | (bytes[1] & 0xFF) :	// Big Endian
				((bytes[1] & 0xFF) << 8) | (bytes[0] & 0xFF));	// Small Endian
	}
	
	/**
	 * Reads a signed integer from the given input stream.
	 * @param inputStream the input stream
	 * @param bigEndian {@code true} for big endian byte order, {@code false} for small endian byte order
	 * @return the read int
	 * @throws IOException if an I/O error occurred
	 * @since 11.08.2018/0.1.0
	 */
	public static final int readSignedInt(InputStream inputStream, boolean bigEndian) throws IOException {
		
		byte[] bytes = readBytes(inputStream, Integer.BYTES);
		return bigEndian ?
				((bytes[0] & 0xFF) << 24) | ((bytes[1] & 0xFF) << 16) | ((bytes[2] & 0xFF) << 8) | (bytes[3] & 0xFF) :	// Big Endian
				((bytes[3] & 0xFF) << 24) | ((bytes[2] & 0xFF) << 16) | ((bytes[1] & 0xFF) << 8) | (bytes[0] & 0xFF);	// Small Endian
	}
	
	/**
	 * Reads a signed long from the given input stream.
	 * @param inputStream the input stream
	 * @param bigEndian {@code true} for big endian byte order, {@code false} for small endian byte order
	 * @return the read long
	 * @throws IOException if an I/O error occurred
	 * @since 11.08.2018/0.1.0
	 */
	public static final long readSignedLong(InputStream inputStream, boolean bigEndian) throws IOException {
		
		byte[] bytes = readBytes(inputStream, Long.BYTES);
		return bigEndian ?
				((bytes[0] & 0xFFL) << 56L) | ((bytes[1] & 0xFFL) << 48L) | ((bytes[2] & 0xFFL) << 40L) | ((bytes[3] & 0xFFL) << 32L) | ((bytes[4] & 0xFFL) << 24L) | ((bytes[5] & 0xFFL) << 16L) | ((bytes[6] & 0xFFL) << 8L) | (bytes[7] | 0xFFL) :	// Big Endian
				((bytes[7] & 0xFFL) << 56L) | ((bytes[6] & 0xFFL) << 48L) | ((bytes[5] & 0xFFL) << 40L) | ((bytes[4] & 0xFFL) << 32L) | ((bytes[3] & 0xFFL) << 24L) | ((bytes[2] & 0xFFL) << 16L) | ((bytes[1] & 0xFFL) << 8L) | (bytes[0] | 0xFFL);	// Small Endian
	}
	
	/**
	 * Reads a certain amount of bytes from the given input stream.
	 * @param stream the input stream
	 * @param length the amount of bytes that should be read
	 * @return the read bytes (the array length can be smaller than the given {@code length} parameter, that means the end of file was reached)
	 * @throws IOException if an I/O error occurred
	 * @since 19.08.2018/0.2.0
	 */
	public static final byte[] readBytes(InputStream stream, int length) throws IOException {
		
		byte[] buffer = new byte[length];
		int readByteCount = stream.read(buffer);
		
		if(readByteCount == length) {
			
			return buffer;
		}
		
		byte[] data = new byte[readByteCount];
		
		for(int index = 0; index < readByteCount; index++) {
			
			data[index] = buffer[index];
		}
		
		return data;
	}
	
	/**
	 * Reads the given input stream until it reaches its end.
	 * @param inputStream the input stream
	 * @return the read data
	 * @throws IOException if an I/O error occurred
	 * @since 11.08.2018/0.1.0
	 */
	public static final byte[] read(InputStream inputStream) throws IOException {
		
		try(ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
			
			write(inputStream, buffer);
			return buffer.toByteArray();
		}
	}
	
	/**
	 * Reads the content from the input stream and writes it on the output stream.
	 * @param inStream the input stream
	 * @param outStream the output stream
	 * @throws IOException if an I/O error occurred
	 * @since 11.08.2018/0.1.0
	 */
	public static final void write(InputStream inStream, OutputStream outStream) throws IOException {
		
		byte[] buffer = new byte[4096];
		int read = 0;
				
		while((read = inStream.read(buffer)) > 0) {
			            
			outStream.write(buffer, 0, read);
		}
		
		outStream.flush();
	}

	/**
	 * Writes a {@linkplain String} on the given output stream.
	 * @param stream the output stream
	 * @param data the data
	 * @param charset the charset with which the bytes should be encoded
	 * @throws IOException if an I/O error occurred
	 * @since 11.08.2018/0.1.0
	 */
	public static final void writeString(OutputStream stream, String data, Charset charset) throws IOException {
		
		stream.write(data.getBytes(charset));
	}
	
	/**
	 * Writes a {@linkplain String} on the given output stream.
	 * @param stream the output stream
	 * @param data the data
	 * @throws IOException if an I/O error occurred
	 * @since 23.08.2018/0.2.0
	 */
	public static final void writeString(OutputStream stream, String data) throws IOException {
		
		stream.write(data.getBytes());
	}
	
	/**
	 * Writes a {@code int} on the given output stream.
	 * @param stream the output stream
	 * @param data the data
	 * @param bigEndian {@code true} for big endian byte order, {@code false} for small endian byte order
	 * @throws IOException if an I/O error occurred
	 * @since 11.08.2018/0.1.0
	 */
	public static final void writeShort(OutputStream stream, short data, boolean bigEndian) throws IOException {

		stream.write(toBytes(data, Short.BYTES, bigEndian));
	}
	
	/**
	 * Writes an {@code int} on the given output stream.
	 * @param stream the output stream
	 * @param data the data
	 * @param bigEndian {@code true} for big endian byte order, {@code false} for small endian byte order
	 * @throws IOException if an I/O error occurred
	 * @since 11.08.2018/0.1.0
	 */
	public static final void writeInt(OutputStream stream, int data, boolean bigEndian) throws IOException {
		
		stream.write(toBytes(data, Integer.BYTES, bigEndian));
	}
	
	/**
	 * Writes a long on the given output stream.
	 * @param stream the output stream
	 * @param data the data
	 * @param bigEndian {@code true} for big endian byte order, {@code false} for small endian byte order
	 * @throws IOException if an I/O error occurred
	 * @since 11.08.2018/0.1.0
	 */
	public static final void writeLong(OutputStream stream, long data, boolean bigEndian) throws IOException {
		
		stream.write(toBytes(data, Long.BYTES, bigEndian));
	}
	
	/**
	 * Writes a float on the given output stream.
	 * @param stream the output stream
	 * @param data the data
	 * @param bigEndian {@code true} for big endian byte order, {@code false} for small endian byte order
	 * @throws IOException if an I/O error occurred
	 * @since 11.08.2018/0.1.0
	 */
	public static final void writeFloat(OutputStream stream, float data, boolean bigEndian) throws IOException {

		stream.write(toBytes(Float.floatToRawIntBits(data), Float.BYTES, bigEndian));
	}
	
	/**
	 * Writes a double on the given output stream.
	 * @param stream the output stream
	 * @param data the data
	 * @param bigEndian {@code true} for big endian byte order, {@code false} for small endian byte order
	 * @throws IOException if an I/O error occurred
	 * @since 11.08.2018/0.1.0
	 */
	public static final void writeDouble(OutputStream stream, double data, boolean bigEndian) throws IOException {
		
		stream.write(toBytes(Double.doubleToRawLongBits(data), Double.BYTES, bigEndian));
	}
}
