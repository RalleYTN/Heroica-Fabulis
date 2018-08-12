package de.ralleytn.games.heroicafabulis.engine.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 11.08.2018/0.1.0
 * @since 11.08.2018/0.1.0
 */
public final class BinaryUtil {

	/**
	 * 
	 * @since 11.08.2018/0.1.0
	 */
	private BinaryUtil() {}
	
	/**
	 * 
	 * @param binary64BitSequence
	 * @param position
	 * @return
	 * @since 11.08.2018/0.1.0
	 */
	public static final int getByte(long binary64BitSequence, int position) {
		
		return (int)((binary64BitSequence >> (position * 8)) & 0xFF);
	}
	
	/**
	 * 
	 * @param binary32BitSequence
	 * @param position
	 * @param bit
	 * @return
	 * @since 11.08.2018/0.1.0
	 */
	public static final int setBit(int binary32BitSequence, int position, boolean bit) {
		
		return bit ? binary32BitSequence | (1 << position) : binary32BitSequence & ~(1 << position);
	}
	
	/**
	 * 
	 * @param binary32BitSequence
	 * @param position
	 * @return
	 * @since 11.08.2018/0.1.0
	 */
	public static final boolean getBit(int binary32BitSequence, int position) {
		
		return ((binary32BitSequence >> position) & 1) == 1;
	}
	
	/**
	 * 
	 * @param byte1
	 * @param byte2
	 * @param byte3
	 * @param byte4
	 * @param bigEndian
	 * @return
	 * @since 11.08.2018/0.1.0
	 */
	public static final long getUnsignedInteger(int byte1, int byte2, int byte3, int byte4, boolean bigEndian) {
		
		return getSignedInteger(byte1, byte2, byte3, byte4, bigEndian) & 0xFFFFFFFFL;
	}
	
	/**
	 * 
	 * @param byte1
	 * @param byte2
	 * @param bigEndian
	 * @return
	 * @since 11.08.2018/0.1.0
	 */
	public static final int getUnsignedShort(int byte1, int byte2, boolean bigEndian) {
		
		return getSignedShort(byte1, byte2, bigEndian) & 0xFFFF;
	}
	
	/**
	 * 
	 * @param byte1
	 * @param byte2
	 * @param bigEndian
	 * @return
	 * @since 11.08.2018/0.1.0
	 */
	public static final short getSignedShort(int byte1, int byte2, boolean bigEndian) {
		
		return (short)(bigEndian ?
				((byte1 & 0xFF) << 8) | (byte2 & 0xFF) :	// Big Endian
				((byte2 & 0xFF) << 8) | (byte1 & 0xFF));	// Small Endian
	}
	
	/**
	 * 
	 * @param byte1
	 * @param byte2
	 * @param byte3
	 * @param byte4
	 * @param bigEndian
	 * @return
	 * @since 11.08.2018/0.1.0
	 */
	public static final int getSignedInteger(int byte1, int byte2, int byte3, int byte4, boolean bigEndian) {
		
		return bigEndian ?
				((byte1 & 0xFF) << 24) | ((byte2 & 0xFF) << 16) | ((byte3 & 0xFF) << 8) | (byte4 & 0xFF) :	// Big Endian
				((byte4 & 0xFF) << 24) | ((byte3 & 0xFF) << 16) | ((byte2 & 0xFF) << 8) | (byte1 & 0xFF);	// Small Endian
	}
	
	/**
	 * 
	 * @param byte1
	 * @param byte2
	 * @param byte3
	 * @param byte4
	 * @param byte5
	 * @param byte6
	 * @param byte7
	 * @param byte8
	 * @param bigEndian
	 * @return
	 * @since 11.08.2018/0.1.0
	 */
	public static final long getSignedLong(int byte1, int byte2, int byte3, int byte4, int byte5, int byte6, int byte7, int byte8, boolean bigEndian) {
		
		return bigEndian ?
				((byte1 & 0xFFL) << 56L) | ((byte2 & 0xFFL) << 48L) | ((byte3 & 0xFFL) << 40L) | ((byte4 & 0xFFL) << 32L) | ((byte5 & 0xFFL) << 24L) | ((byte6 & 0xFFL) << 16L) | ((byte7 & 0xFFL) << 8L) | (byte8 | 0xFFL) :	// Big Endian
				((byte8 & 0xFFL) << 56L) | ((byte7 & 0xFFL) << 48L) | ((byte6 & 0xFFL) << 40L) | ((byte5 & 0xFFL) << 32L) | ((byte4 & 0xFFL) << 24L) | ((byte3 & 0xFFL) << 16L) | ((byte2 & 0xFFL) << 8L) | (byte1 | 0xFFL);	// Small Endian
	}
	
	/**
	 * 
	 * @param bytes
	 * @return
	 * @since 11.08.2018/0.1.0
	 */
	public static final byte[] toByteArray(int[] bytes) {
		
		try(ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
			
			for(int byteAsInt : bytes) {
				
				buffer.write(byteAsInt);
			}
			
			return buffer.toByteArray();
			
		} catch(IOException exception) {
			
			// SHOULD NEVER HAPPEN!
			throw new RuntimeException(exception.getMessage());
		}
	}
}
