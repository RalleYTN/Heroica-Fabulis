package de.ralleytn.games.heroicafabulis.engine.io.audio;

import java.nio.ByteBuffer;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 22.08.2018/0.2.0
 * @since 20.08.2018/0.2.0
 */
public class AudioData {

	private int format;
	private int channels;
	private int frequency;
	private ByteBuffer data;
	
	/**
	 * 
	 * @param format
	 * @since 20.08.2018/0.2.0
	 */
	public void setFormat(int format) {
		
		this.format = format;
	}
	
	/**
	 * 
	 * @param channels
	 * @since 20.08.2018/0.2.0
	 */
	public void setChannels(int channels) {
		
		this.channels = channels;
	}
	
	/**
	 * 
	 * @param frequency
	 * @since 20.08.2018/0.2.0
	 */
	public void setFrequency(int frequency) {
		
		this.frequency = frequency;
	}
	
	/**
	 * 
	 * @param data
	 * @since 20.08.2018/0.2.0
	 */
	public void setData(ByteBuffer data) {
		
		this.data = data;
	}
	
	/**
	 * 
	 * @return
	 * @since 20.08.2018/0.2.0
	 */
	public int getFormat() {
		
		return this.format;
	}
	
	/**
	 * 
	 * @return
	 * @since 20.08.2018/0.2.0
	 */
	public int getChannels() {
		
		return this.channels;
	}
	
	/**
	 * 
	 * @return
	 * @since 20.08.2018/0.2.0
	 */
	public int getFrequency() {
		
		return this.frequency;
	}
	
	/**
	 * 
	 * @return
	 * @since 20.08.2018/0.2.0
	 */
	public ByteBuffer getData() {
		
		return this.data;
	}
}
