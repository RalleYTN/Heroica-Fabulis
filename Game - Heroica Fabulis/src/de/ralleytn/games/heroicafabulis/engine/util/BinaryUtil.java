package de.ralleytn.games.heroicafabulis.engine.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Utility class containing methods for working with binary code.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 11.08.2018/0.1.0
 * @since 11.08.2018/0.1.0
 */
public final class BinaryUtil {

	/**
	 * Private because no instance of this class should exist.
	 * @since 11.08.2018/0.1.0
	 */
	private BinaryUtil() {}
	
	/**
	 * @param binary64BitSequence the byte sequence
	 * @param position the position of the byte (right to left)
	 * @return the byte at the position in the binary sequence
	 * @since 11.08.2018/0.1.0
	 */
	public static final int getByte(long binary64BitSequence, int position) {
		
		return (int)((binary64BitSequence >> (position * 8)) & 0xFF);
	}
	
	/**
	 * Sets a single bit in a binary sequence
	 * @param binary32BitSequence the binary sequence
	 * @param position the position of the bit that should be set (right to left)
	 * @param bit the bit
	 * @return the binary sequence with the set bit
	 * @since 11.08.2018/0.1.0
	 */
	public static final int setBit(int binary32BitSequence, int position, boolean bit) {
		
		return bit ? binary32BitSequence | (1 << position) : binary32BitSequence & ~(1 << position);
	}
	
	/**
	 * @param binary32BitSequence the binary sequence
	 * @param position the position of the bit (right to left)
	 * @return the bit at the given position in the given binary sequence
	 * @since 11.08.2018/0.1.0
	 */
	public static final boolean getBit(int binary32BitSequence, int position) {
		
		return ((binary32BitSequence >> position) & 1) == 1;
	}
	
	/**
	 * Makes an unsigned integer out of 4 bytes.
	 * @param byte1 the first byte
	 * @param byte2 the second byte
	 * @param byte3 the third byte
	 * @param byte4 the fourth byte
	 * @param bigEndian {@code true} if the order is big endian, {@code false} if it is small endian
	 * @return the unsigned integer
	 * @since 11.08.2018/0.1.0
	 */
	public static final long getUnsignedInteger(int byte1, int byte2, int byte3, int byte4, boolean bigEndian) {
		
		return getSignedInteger(byte1, byte2, byte3, byte4, bigEndian) & 0xFFFFFFFFL;
	}
	
	/**
	 * Makes an unsigned short out of 2 bytes.
	 * @param byte1 the first byte
	 * @param byte2 the second byte
	 * @param bigEndian {@code true} if the order is big endian, {@code false} if it is small endian
	 * @return the unsigned short
	 * @since 11.08.2018/0.1.0
	 */
	public static final int getUnsignedShort(int byte1, int byte2, boolean bigEndian) {
		
		return getSignedShort(byte1, byte2, bigEndian) & 0xFFFF;
	}
	
	/**
	 * Makes a signed short out of 2 bytes.
	 * @param byte1 the first byte
	 * @param byte2 the second byte
	 * @param bigEndian {@code true} if the order is big endian, {@code false} if it is small endian
	 * @return the signed short
	 * @since 11.08.2018/0.1.0
	 */
	public static final short getSignedShort(int byte1, int byte2, boolean bigEndian) {
		
		return (short)(bigEndian ?
				((byte1 & 0xFF) << 8) | (byte2 & 0xFF) :	// Big Endian
				((byte2 & 0xFF) << 8) | (byte1 & 0xFF));	// Small Endian
	}
	
	/**
	 * Makes a signed integer out of 4 bytes.
	 * @param byte1 the first byte
	 * @param byte2 the second byte
	 * @param byte3 the third byte
	 * @param byte4 the fourth byte
	 * @param bigEndian {@code true} if the order is big endian, {@code false} if it is small endian
	 * @return the signed integer
	 * @since 11.08.2018/0.1.0
	 */
	public static final int getSignedInteger(int byte1, int byte2, int byte3, int byte4, boolean bigEndian) {
		
		return bigEndian ?
				((byte1 & 0xFF) << 24) | ((byte2 & 0xFF) << 16) | ((byte3 & 0xFF) << 8) | (byte4 & 0xFF) :	// Big Endian
				((byte4 & 0xFF) << 24) | ((byte3 & 0xFF) << 16) | ((byte2 & 0xFF) << 8) | (byte1 & 0xFF);	// Small Endian
	}
	
	/**
	 * Makes a signed long out of 8 bytes.
	 * @param byte1 the first byte
	 * @param byte2 the second byte
	 * @param byte3 the third byte
	 * @param byte4 the fourth byte
	 * @param byte5 the fifth byte
	 * @param byte6 the sixth byte
	 * @param byte7 the seventh byte
	 * @param byte8 the eighth byte
	 * @param bigEndian {@code true} if the order is big endian, {@code false} if it is small endian
	 * @return the signed long
	 * @since 11.08.2018/0.1.0
	 */
	public static final long getSignedLong(int byte1, int byte2, int byte3, int byte4, int byte5, int byte6, int byte7, int byte8, boolean bigEndian) {
		
		return bigEndian ?
				((byte1 & 0xFFL) << 56L) | ((byte2 & 0xFFL) << 48L) | ((byte3 & 0xFFL) << 40L) | ((byte4 & 0xFFL) << 32L) | ((byte5 & 0xFFL) << 24L) | ((byte6 & 0xFFL) << 16L) | ((byte7 & 0xFFL) << 8L) | (byte8 | 0xFFL) :	// Big Endian
				((byte8 & 0xFFL) << 56L) | ((byte7 & 0xFFL) << 48L) | ((byte6 & 0xFFL) << 40L) | ((byte5 & 0xFFL) << 32L) | ((byte4 & 0xFFL) << 24L) | ((byte3 & 0xFFL) << 16L) | ((byte2 & 0xFFL) << 8L) | (byte1 | 0xFFL);	// Small Endian
	}
	
	/**
	 * Converts an {@code int} array of bytes into a {@code byte} array.
	 * @param bytes the {@code int} array
	 * @return the {@code byte} array
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
