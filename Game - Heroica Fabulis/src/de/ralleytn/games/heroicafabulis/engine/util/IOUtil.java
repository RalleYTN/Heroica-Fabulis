package de.ralleytn.games.heroicafabulis.engine.util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * Utility class containing methods for working with input and output streams.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 11.08.2018/0.1.0
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

		return new String(BinaryUtil.toByteArray(read(stream, length)), charset.name());
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
		
		int[] bytes = read(inputStream, 4);
		return Float.intBitsToFloat(BinaryUtil.getSignedInteger(bytes[3], bytes[2], bytes[1], bytes[0], bigEndian));
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
		
		int[] bytes = read(inputStream, 8);
		return Double.longBitsToDouble(BinaryUtil.getSignedLong(bytes[7], bytes[6], bytes[5], bytes[4], bytes[3], bytes[2], bytes[1], bytes[0], bigEndian));
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
		
		int[] bytes = read(inputStream, 2);
		return BinaryUtil.getUnsignedShort(bytes[1], bytes[0], bigEndian);
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
		
		int[] bytes = read(inputStream, 4);
		return BinaryUtil.getUnsignedInteger(bytes[3], bytes[2], bytes[1], bytes[0], bigEndian);
	}
	
	/**
	 * reads a signed short from the given input stream.
	 * @param inputStream the input stream
	 * @param bigEndian {@code true} for big endian byte order, {@code false} for small endian byte order
	 * @return the read short
	 * @throws IOException if an I/O error occurred
	 * @since 11.08.2018/0.1.0
	 */
	public static final short readSignedShort(InputStream inputStream, boolean bigEndian) throws IOException {
		
		int[] bytes = read(inputStream, 2);
		return BinaryUtil.getSignedShort(bytes[1], bytes[0], bigEndian);
	}
	
	/**
	 * reads a signed int from the given input stream.
	 * @param inputStream the input stream
	 * @param bigEndian {@code true} for big endian byte order, {@code false} for small endian byte order
	 * @return the read int
	 * @throws IOException if an I/O error occurred
	 * @since 11.08.2018/0.1.0
	 */
	public static final int readSignedInt(InputStream inputStream, boolean bigEndian) throws IOException {
		
		int[] bytes = read(inputStream, 4);
		return BinaryUtil.getSignedInteger(bytes[3], bytes[2], bytes[1], bytes[0], bigEndian);
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
		
		int[] bytes = read(inputStream, 8);
		return BinaryUtil.getSignedLong(bytes[7], bytes[6], bytes[5], bytes[4], bytes[3], bytes[2], bytes[1], bytes[0], bigEndian);
	}
	
	/**
	 * @param stream the input stream
	 * @param length the byte length
	 * @return {@code length} bytes from the input stream as an {@code int} array
	 * @throws IOException if an I/O error occurred
	 * @since 11.08.2018/0.1.0
	 */
	public static final int[] read(InputStream stream, int length) throws IOException {
		
		int[] data = new int[length];
		
		for(int index = 0; index < data.length; index++) {
			
			data[index] = stream.read();
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
	 * Writes up to 8 bytes on the given output stream.
	 * @param stream the output stream
	 * @param binary64BitSequence the bytes
	 * @param length the number of bytes to be written
	 * @param bigEndian {@code true} for big endian byte order, {@code false} for small endian byte order
	 * @throws IOException if an I/O error occurred
	 * @since 11.08.2018/0.1.0
	 */
	public static final void writeBytes(OutputStream stream, long binary64BitSequence, int length, boolean bigEndian) throws IOException {
		
		if(!bigEndian) {
			
			for(int position = length - 1; position >= 0; position--) {
				
				stream.write(BinaryUtil.getByte(binary64BitSequence, position));
			}
			
		} else {
			
			for(int position = 0; position < length; position++) {
				
				stream.write(BinaryUtil.getByte(binary64BitSequence, position));
			}
		}
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
	 * Writes a {@code int} on the given output stream.
	 * @param stream the output stream
	 * @param data the data
	 * @param bigEndian {@code true} for big endian byte order, {@code false} for small endian byte order
	 * @throws IOException if an I/O error occurred
	 * @since 11.08.2018/0.1.0
	 */
	public static final void writeShort(OutputStream stream, short data, boolean bigEndian) throws IOException {
		
		writeBytes(stream, data, Short.BYTES, bigEndian);
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
		
		writeBytes(stream, data, Integer.BYTES, bigEndian);
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
		
		writeBytes(stream, data, Long.BYTES, bigEndian);
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

		writeBytes(stream, Float.floatToRawIntBits(data), Integer.BYTES, bigEndian);
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
		
		writeBytes(stream, Double.doubleToRawLongBits(data), Long.BYTES, bigEndian);
	}
	
	/**
	 * Convenience method for closing instances of {@linkplain Closable} without needing to wrap another try-catch block arround it.
	 * @param closable the {@linkplain Closable}
	 * @since 11.08.2018/0.1.0
	 */
	public static final void close(Closeable closable) {
		
		try {
			
			closable.close();
			
		} catch(IOException exception) {
			
			// SHOULD NEVER HAPPEN!
			throw new RuntimeException(exception.getMessage());
		}
	}
}
