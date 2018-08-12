package de.ralleytn.games.heroicafabulis.engine.util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 11.08.2018/0.1.0
 * @since 11.08.2018/0.1.0
 */
public final class IOUtil {

	/**
	 * 
	 * @since 11.08.2018/0.1.0
	 */
	private IOUtil() {}
	
	/**
	 * 
	 * @param stream
	 * @param length
	 * @param charset
	 * @return
	 * @throws IOException
	 * @since 11.08.2018/0.1.0
	 */
	public static final String readString(InputStream stream, int length, Charset charset) throws IOException {

		return new String(BinaryUtil.toByteArray(read(stream, length)), charset.name());
	}
	
	/**
	 * 
	 * @param inputStream
	 * @param bigEndian
	 * @return
	 * @throws IOException
	 * @since 11.08.2018/0.1.0
	 */
	public static final float readFloat(InputStream inputStream, boolean bigEndian) throws IOException {
		
		int[] bytes = read(inputStream, 4);
		return Float.intBitsToFloat(BinaryUtil.getSignedInteger(bytes[3], bytes[2], bytes[1], bytes[0], bigEndian));
	}
	
	/**
	 * 
	 * @param inputStream
	 * @param bigEndian
	 * @return
	 * @throws IOException
	 * @since 11.08.2018/0.1.0
	 */
	public static final double readDouble(InputStream inputStream, boolean bigEndian) throws IOException {
		
		int[] bytes = read(inputStream, 8);
		return Double.longBitsToDouble(BinaryUtil.getSignedLong(bytes[7], bytes[6], bytes[5], bytes[4], bytes[3], bytes[2], bytes[1], bytes[0], bigEndian));
	}
	
	/**
	 * 
	 * @param inputStream
	 * @param bigEndian
	 * @return
	 * @throws IOException
	 * @since 11.08.2018/0.1.0
	 */
	public static final int readUnsignedShort(InputStream inputStream, boolean bigEndian) throws IOException {
		
		int[] bytes = read(inputStream, 2);
		return BinaryUtil.getUnsignedShort(bytes[1], bytes[0], bigEndian);
	}
	
	/**
	 * 
	 * @param inputStream
	 * @param bigEndian
	 * @return
	 * @throws IOException
	 * @since 11.08.2018/0.1.0
	 */
	public static final long readUnsignedInt(InputStream inputStream, boolean bigEndian) throws IOException {
		
		int[] bytes = read(inputStream, 4);
		return BinaryUtil.getUnsignedInteger(bytes[3], bytes[2], bytes[1], bytes[0], bigEndian);
	}
	
	/**
	 * 
	 * @param inputStream
	 * @param bigEndian
	 * @return
	 * @throws IOException
	 * @since 11.08.2018/0.1.0
	 */
	public static final short readSignedShort(InputStream inputStream, boolean bigEndian) throws IOException {
		
		int[] bytes = read(inputStream, 2);
		return BinaryUtil.getSignedShort(bytes[1], bytes[0], bigEndian);
	}
	
	/**
	 * 
	 * @param inputStream
	 * @param bigEndian
	 * @return
	 * @throws IOException
	 * @since 11.08.2018/0.1.0
	 */
	public static final int readSignedInt(InputStream inputStream, boolean bigEndian) throws IOException {
		
		int[] bytes = read(inputStream, 4);
		return BinaryUtil.getSignedInteger(bytes[3], bytes[2], bytes[1], bytes[0], bigEndian);
	}
	
	/**
	 * 
	 * @param inputStream
	 * @param bigEndian
	 * @return
	 * @throws IOException
	 * @since 11.08.2018/0.1.0
	 */
	public static final long readSignedLong(InputStream inputStream, boolean bigEndian) throws IOException {
		
		int[] bytes = read(inputStream, 8);
		return BinaryUtil.getSignedLong(bytes[7], bytes[6], bytes[5], bytes[4], bytes[3], bytes[2], bytes[1], bytes[0], bigEndian);
	}
	
	/**
	 * 
	 * @param stream
	 * @param length
	 * @return
	 * @throws IOException
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
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 * @since 11.08.2018/0.1.0
	 */
	public static final byte[] read(InputStream inputStream) throws IOException {
		
		try(ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
			
			write(inputStream, buffer);
			return buffer.toByteArray();
		}
	}
	
	/**
	 * 
	 * @param inStream
	 * @param outStream
	 * @throws IOException
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
	 * 
	 * @param stream
	 * @param binary64BitSequence
	 * @param length
	 * @param bigEndian
	 * @throws IOException
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
	 * 
	 * @param stream
	 * @param data
	 * @param charset
	 * @throws IOException
	 * @since 11.08.2018/0.1.0
	 */
	public static final void writeString(OutputStream stream, String data, Charset charset) throws IOException {
		
		stream.write(data.getBytes(charset));
	}
	
	/**
	 * 
	 * @param stream
	 * @param data
	 * @param bigEndian
	 * @throws IOException
	 * @since 11.08.2018/0.1.0
	 */
	public static final void writeShort(OutputStream stream, short data, boolean bigEndian) throws IOException {
		
		writeBytes(stream, data, Short.BYTES, bigEndian);
	}
	
	/**
	 * 
	 * @param stream
	 * @param data
	 * @param bigEndian
	 * @throws IOException
	 * @since 11.08.2018/0.1.0
	 */
	public static final void writeInt(OutputStream stream, int data, boolean bigEndian) throws IOException {
		
		writeBytes(stream, data, Integer.BYTES, bigEndian);
	}
	
	/**
	 * 
	 * @param stream
	 * @param data
	 * @param bigEndian
	 * @throws IOException
	 * @since 11.08.2018/0.1.0
	 */
	public static final void writeLong(OutputStream stream, long data, boolean bigEndian) throws IOException {
		
		writeBytes(stream, data, Long.BYTES, bigEndian);
	}
	
	/**
	 * 
	 * @param stream
	 * @param data
	 * @param bigEndian
	 * @throws IOException
	 * @since 11.08.2018/0.1.0
	 */
	public static final void writeFloat(OutputStream stream, float data, boolean bigEndian) throws IOException {

		writeBytes(stream, Float.floatToRawIntBits(data), Integer.BYTES, bigEndian);
	}
	
	/**
	 * 
	 * @param stream
	 * @param data
	 * @param bigEndian
	 * @throws IOException
	 * @since 11.08.2018/0.1.0
	 */
	public static final void writeDouble(OutputStream stream, double data, boolean bigEndian) throws IOException {
		
		writeBytes(stream, Double.doubleToRawLongBits(data), Long.BYTES, bigEndian);
	}
	
	/**
	 * 
	 * @param closable
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
