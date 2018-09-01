package de.ralleytn.engine.caveman.util;

/**
 * Utility class containing methods for working with binary code.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 23.08.2018/0.2.0
 * @since 11.08.2018/0.1.0
 */
public final class BinaryUtil {

	/**
	 * Private because no instance of this class should exist.
	 * @since 11.08.2018/0.1.0
	 */
	private BinaryUtil() {}
	
	/**
	 * @param binary the byte sequence
	 * @param position the position of the byte (right to left)
	 * @return the byte at the position in the binary sequence
	 * @since 11.08.2018/0.1.0
	 */
	public static final int getByte(long binary, int position) {
		
		return (int)((binary >> (position * 8)) & 0xFF);
	}
	
	/**
	 * Sets a single bit in a binary sequence
	 * @param binary the binary sequence
	 * @param position the position of the bit that should be set (right to left)
	 * @param bit the bit
	 * @return the binary sequence with the set bit
	 * @since 11.08.2018/0.1.0
	 */
	public static final int setBit(int binary, int position, boolean bit) {
		
		return bit ? binary | (1 << position) : binary & ~(1 << position);
	}
	
	/**
	 * @param binary the binary sequence
	 * @param position the position of the bit (right to left)
	 * @return the bit at the given position in the given binary sequence
	 * @since 11.08.2018/0.1.0
	 */
	public static final boolean getBit(int binary, int position) {
		
		return ((binary >> position) & 1) == 1;
	}
	
	/**
	 * Converts a number to a {@code byte} array.
	 * @param value the number
	 * @param length the byte count
	 * @param bigEndian {@code true} for big endian order, {@code false} for small endian
	 * @return the {@code byte} array
	 * @since 23.08.2018/0.2.0
	 */
	public static final byte[] toBytes(long value, int length, boolean bigEndian) {
		
		byte[] bytes = new byte[length];
		
		if(bigEndian) {
			
			int bitCount = bytes.length * 8;
			
			for(int index = 0; index < bytes.length; index++) {
				
				bytes[index] = (byte)(value >>> (bitCount - (8 * (index + 1))));
			}
			
		} else {
			
			for(int index = 0; index < bytes.length; index++) {
				
				bytes[index] = (byte)(value >>> (index * 8));
			}
		}
		
		return bytes;
	}
}
