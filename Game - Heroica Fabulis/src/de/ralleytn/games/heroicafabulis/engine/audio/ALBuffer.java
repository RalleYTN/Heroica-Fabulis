package de.ralleytn.games.heroicafabulis.engine.audio;

import static org.lwjgl.openal.AL10.*;

import de.ralleytn.games.heroicafabulis.engine.LWJGLObject;
import de.ralleytn.games.heroicafabulis.engine.io.audio.AudioData;

/**
 * Represents a buffer from OpenAL.
 * An {@linkplain ALBuffer} stores audio data.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 22.08.2018/0.2.0
 * @since 17.08.2018/0.2.0
 */
public class ALBuffer extends LWJGLObject {
	
	/**
	 * @since 17.08.2018/0.2.0
	 */
	public ALBuffer() {
		
		this.id = alGenBuffers();
	}
	
	@Override
	public void dispose() {
		
		alDeleteBuffers(this.id);
	}
	
	/**
	 * Sets the data.
	 * @param data the audio data
	 * @since 17.08.2018/0.2.0
	 */
	public void setData(AudioData data) {
		
		alBufferData(this.id, data.getFormat(), data.getData(), data.getFrequency());
	}
	
	/**
	 * @return the frequency
	 * @since 17.08.2018/0.2.0
	 */
	public int getFrequency() {
		
		return alGetBufferi(this.id, AL_FREQUENCY);
	}
	
	/**
	 * @return the size of the buffer
	 * @since 17.08.2018/0.2.0
	 */
	public int getSize() {
		
		return alGetBufferi(this.id, AL_SIZE);
	}
	
	/**
	 * @return the channel count
	 * @since 17.08.2018/0.2.0
	 */
	public int getChannels() {
		
		return alGetBufferi(this.id, AL_CHANNELS);
	}
	
	/**
	 * @return the bit depth of the audio
	 * @since 17.08.2018/0.2.0
	 */
	public int getBitDepth() {
		
		return alGetBufferi(this.id, AL_BITS);
	}
}
