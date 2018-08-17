package de.ralleytn.games.heroicafabulis.engine.audio;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.openal.AL.*;
import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.AL11.*;
import static org.lwjgl.openal.ALC.*;
import static org.lwjgl.openal.ALC10.*;
import org.lwjgl.openal.ALCCapabilities;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 17.08.2018/0.2.0
 * @since 17.08.2018/0.2.0
 */
public final class OpenAL {

	/** @since 17.08.2018/0.2.0 */ public static final int DISTANCE_MODEL_NONE = AL_NONE;
	/** @since 17.08.2018/0.2.0 */ public static final int DISTANCE_MODEL_INVERSE = AL_INVERSE_DISTANCE;
	/** @since 17.08.2018/0.2.0 */ public static final int DISTANCE_MODEL_INVERSE_CLAMPED = AL_INVERSE_DISTANCE_CLAMPED;
	/** @since 17.08.2018/0.2.0 */ public static final int DISTANCE_MODEL_LINEAR = AL_LINEAR_DISTANCE;
	/** @since 17.08.2018/0.2.0 */ public static final int DISTANCE_MODEL_LINEAR_CLAMPED = AL_LINEAR_DISTANCE_CLAMPED;
	/** @since 17.08.2018/0.2.0 */ public static final int DISTANCE_MODEL_EXPONENT = AL_EXPONENT_DISTANCE;
	/** @since 17.08.2018/0.2.0 */ public static final int DISTANCE_MODEL_EXPONENT_CLAMPED = AL_EXPONENT_DISTANCE_CLAMPED;
	
	private static final Listener LISTENER = new Listener();
	private static long DEVICE_ID;
	private static long CONTEXT_ID;
	
	/**
	 * @since 17.08.2018/0.2.0
	 */
	private OpenAL() {}

	/**
	 * 
	 * @since 17.08.2018/0.2.0
	 */
	public static final void create() {
		
		OpenAL.DEVICE_ID = alcOpenDevice((ByteBuffer)null);
		ALCCapabilities deviceCaps = createCapabilities(OpenAL.DEVICE_ID);
		OpenAL.CONTEXT_ID = alcCreateContext(OpenAL.DEVICE_ID, (IntBuffer)null);
		alcMakeContextCurrent(OpenAL.CONTEXT_ID);
		createCapabilities(deviceCaps);
	}

	/**
	 * @since 17.08.2018/0.2.0
	 */
	public static final void destroy() {
		
		alcDestroyContext(OpenAL.CONTEXT_ID);
		alcCloseDevice(OpenAL.DEVICE_ID);
	}
	
	/**
	 * 
	 * @param model
	 * @since 17.08.2018/0.2.0
	 */
	public static final void setDistanceModel(int model) {
		
		alDistanceModel(model);
	}
	
	/**
	 * 
	 * @param factor
	 * @since 17.08.2018/0.2.0
	 */
	public static final void setDopplerFactor(float factor) {
		
		alDopplerFactor(factor);
	}
	
	/**
	 * 
	 * @param speed
	 * @since 17.08.2018/0.2.0
	 */
	public static final void setSpeedOfSound(float speed) {
		
		alSpeedOfSound(speed);
	}
	
	/**
	 * 
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public static final Listener getListener() {

		return LISTENER;
	}
	
	/**
	 * 
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public static final long getDeviceID() {
		
		return DEVICE_ID;
	}
	
	/**
	 * 
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public static final long getContextID() {
		
		return CONTEXT_ID;
	}
	
	/**
	 * 
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public static final int getDistanceModel() {
		
		return alGetInteger(AL_DISTANCE_MODEL);
	}
	
	/**
	 * 
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public static final float getDopplerFactor() {
		
		return alGetFloat(AL_DOPPLER_FACTOR);
	}
	
	/**
	 * 
	 * @return
	 * @since 17.08.2018/0.2.0
	 */
	public static final float getSpeedOfSound() {
		
		return alGetFloat(AL_SPEED_OF_SOUND);
	}
}
