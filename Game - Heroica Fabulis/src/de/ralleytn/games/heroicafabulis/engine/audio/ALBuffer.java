package de.ralleytn.games.heroicafabulis.engine.audio;

import java.nio.ByteBuffer;

import static org.lwjgl.openal.AL10.*;

import de.ralleytn.games.heroicafabulis.engine.LWJGLObject;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 17.08.2018/0.2.0
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
	 * 
	 * @param format
	 * @param data
	 * @param frequency
	 * @since 17.08.2018/0.2.0
	 */
	public void setData(int format, ByteBuffer data, int frequency) {
		
		alBufferData(this.id, format, data, frequency);
	}
	
	/**
	 * 
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public int getFrequency() {
		
		return alGetBufferi(this.id, AL_FREQUENCY);
	}
	
	/**
	 * 
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public int getSize() {
		
		return alGetBufferi(this.id, AL_SIZE);
	}
	
	/**
	 * 
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public int getChannels() {
		
		return alGetBufferi(this.id, AL_CHANNELS);
	}
	
	/**
	 * 
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public int getBitDepth() {
		
		return alGetBufferi(this.id, AL_BITS);
	}
}
