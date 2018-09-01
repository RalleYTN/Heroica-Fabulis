package de.ralleytn.engine.caveman.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.lwjgl.BufferUtils;

/**
 * Utility class containing methods for working with Buffers from the NIO package.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 04.08.2018/0.1.0
 * @since 04.08.2018/0.1.0
 */
public final class BufferUtil {

	/**
	 * Private because no instances of this class should exist.
	 * @since 04.08.2018/0.1.0
	 */
	private BufferUtil() {}
	
	/**
	 * Wraps a {@code byte} array in a {@linkplain ByteBuffer}.
	 * @param bytes the {@code byte} array
	 * @return the {@linkplain ByteBuffer}
	 * @since 04.08.2018/0.1.0
	 */
	public static final ByteBuffer toByteBuffer(byte[] bytes) {
		
		ByteBuffer buffer = BufferUtils.createByteBuffer(bytes.length);
		buffer.put(bytes);
		buffer.flip();
		
		return buffer;
	}

	/**
	 * Wraps an {@code int} array in an {@linkplain IntBuffer}.
	 * @param ints the {@code int} array
	 * @return the {@linkplain IntBuffer}
	 * @since 04.08.2018/0.1.0
	 */
	public static final IntBuffer toIntBuffer(int[] ints) {
		
		IntBuffer buffer = BufferUtils.createIntBuffer(ints.length);
		buffer.put(ints);
		buffer.flip();
		
		return buffer;
	}

	/**
	 * Wraps a {@code float} array in a {@linkplain FloatBuffer}.
	 * @param floats the {@code float} array
	 * @return the {@linkplain FloatBuffer}
	 * @since 04.08.2018/0.1.0
	 */
	public static final FloatBuffer toFloatBuffer(float[] floats) {
		
		FloatBuffer buffer = BufferUtils.createFloatBuffer(floats.length);
		buffer.put(floats);
		buffer.flip();
		
		return buffer;
	}
	
	/**
	 * Resizes a {@linkplain ByteBuffer}.
	 * @param buffer the {@linkplain ByteBuffer} that should be resized
	 * @param newCapacity the new capacity
	 * @return the resized {@linkplain ByteBuffer}
	 * @since 04.08.2018/0.1.0
	 */
	public static final ByteBuffer resizeByteBuffer(ByteBuffer buffer, int newCapacity) {
		
	    ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
	    buffer.flip();
	    newBuffer.put(buffer);
	    return newBuffer;
	}
	
	/**
	 * Loads a resource from tghe class path directly into a {@linkplain ByteBuffer}.
	 * @param resource the name of the resource on the class path
	 * @param bufferSize the buffer size
	 * @return the created {@linkplain ByteBuffer}
	 * @throws IOException if the resource could not be read
	 * @since 04.08.2018/0.1.0
	 */
	public static final ByteBuffer loadToByteBuffer(String resource, int bufferSize) throws IOException {
		
	    ByteBuffer buffer = null;
	    Path path = Paths.get(resource);
	    
	    if(Files.isReadable(path)) {
	    	
	        try(SeekableByteChannel channel = Files.newByteChannel(path)) {
	        	
	            buffer = BufferUtils.createByteBuffer((int)channel.size() + 1);
	            while(channel.read(buffer) != -1);
	        }
	        
	    } else {
	    	
	        try(InputStream source = BufferUtil.class.getClassLoader().getResourceAsStream(resource);
	        	ReadableByteChannel channel = Channels.newChannel(source)) {
	            
	        	buffer = BufferUtils.createByteBuffer(bufferSize);
	        	
	            while(channel.read(buffer) != -1) {

	                if(buffer.remaining() == 0) {
	                    
	                	buffer = resizeByteBuffer(buffer, buffer.capacity() * 2);
	                }
	            }
	        }
	    }

	    buffer.flip();
	    return buffer;
	}
}
