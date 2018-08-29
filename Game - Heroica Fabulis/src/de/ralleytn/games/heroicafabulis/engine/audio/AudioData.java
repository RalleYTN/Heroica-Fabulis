package de.ralleytn.games.heroicafabulis.engine.audio;

import java.nio.ByteBuffer;

import org.lwjgl.openal.AL10;

/**
 * Represents audio data.
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
	 * Sets the audio format.
	 * @param format the format
	 * @since 20.08.2018/0.2.0
	 * @see AL10#AL_FORMAT_MONO8
	 * @see AL10#AL_FORMAT_MONO16
	 * @see AL10#AL_FORMAT_STEREO8
	 * @see AL10#AL_FORMAT_STEREO16
	 */
	public void setFormat(int format) {
		
		this.format = format;
	}
	
	/**
	 * Sets the amount of channels.
	 * @param channels the amount of channels
	 * @since 20.08.2018/0.2.0
	 */
	public void setChannels(int channels) {
		
		this.channels = channels;
	}
	
	/**
	 * Sets the frequency.
	 * @param frequency the frequency
	 * @since 20.08.2018/0.2.0
	 */
	public void setFrequency(int frequency) {
		
		this.frequency = frequency;
	}
	
	/**
	 * Sets the actual audio data.
	 * @param data the audio data
	 * @since 20.08.2018/0.2.0
	 */
	public void setData(ByteBuffer data) {
		
		this.data = data;
	}
	
	/**
	 * @return the format
	 * @since 20.08.2018/0.2.0
	 */
	public int getFormat() {
		
		return this.format;
	}
	
	/**
	 * @return the channels
	 * @since 20.08.2018/0.2.0
	 */
	public int getChannels() {
		
		return this.channels;
	}
	
	/**
	 * @return the frequency
	 * @since 20.08.2018/0.2.0
	 */
	public int getFrequency() {
		
		return this.frequency;
	}
	
	/**
	 * @return the audio data
	 * @since 20.08.2018/0.2.0
	 */
	public ByteBuffer getData() {
		
		return this.data;
	}
}
