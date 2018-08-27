package de.ralleytn.games.heroicafabulis.engine.io.audio;

import static org.lwjgl.openal.AL10.AL_FORMAT_MONO16;
import static org.lwjgl.openal.AL10.AL_FORMAT_MONO8;
import static org.lwjgl.openal.AL10.AL_FORMAT_STEREO16;
import static org.lwjgl.openal.AL10.AL_FORMAT_STEREO8;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import de.ralleytn.games.heroicafabulis.engine.io.Reader;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 27.08.2018/0.3.0
 * @since 27.08.2018/0.3.0
 */
public abstract class AudioReader extends Reader<AudioData> {

	protected AudioInputStream inputStream;
	protected int sampleRate;
	protected int channels;
	protected int sampleSizeInBits;
	protected int sampleSizeInBytes;
	protected int format;
	protected int bufferSize;
	protected long totalFrames;
	protected long currentFrame;
	
	/**
	 * 
	 * @since 27.08.2018/0.3.0
	 */
	public AudioReader() {}
	
	/**
	 * 
	 * @param inputStream
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 * @since 27.08.2018/0.3.0
	 */
	public AudioReader(InputStream inputStream) throws IOException, UnsupportedAudioFileException {
		
		this.inputStream = inputStream instanceof AudioInputStream ? (AudioInputStream)inputStream : AudioSystem.getAudioInputStream(new BufferedInputStream(inputStream));
		AudioFormat audioFormat = this.inputStream.getFormat();
		this.sampleRate = (int)audioFormat.getSampleRate();
		this.channels = audioFormat.getChannels();
		this.sampleSizeInBits = audioFormat.getSampleSizeInBits();
		this.sampleSizeInBytes = this.sampleSizeInBits / 8;
		this.format = getFormat(this.channels, this.sampleSizeInBits);
		this.totalFrames = this.inputStream.getFrameLength();
		this.bufferSize = this.sampleSizeInBytes == 2 ? 64 : 128;
	}
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 * @since 27.08.2018/0.3.0
	 */
	public abstract AudioData nextChunk() throws IOException;
	
	/**
	 * 
	 * @param bufferSize
	 * @since 27.08.2018/0.3.0
	 */
	public void setBufferSize(int bufferSize) {
		
		this.bufferSize = bufferSize;
	}
	
	/**
	 * 
	 * @throws IOException
	 * @since 27.08.2018/0.3.0
	 */
	public void reset() throws IOException {
		
		this.inputStream.reset();
	}
	
	/**
	 * 
	 * @since 27.08.2018/0.3.0
	 */
	public void close() throws Exception {
		
		this.inputStream.close();
	}
	
	/**
	 * 
	 * @return
	 * @since 27.08.2018/0.3.0
	 */
	public int getBufferSize() {
		
		return this.bufferSize;
	}
	
	/**
	 * 
	 * @param channels
	 * @param sampleSizeInBits
	 * @return
	 * @throws IOException
	 * @since 27.08.2018/0.3.0
	 */
	protected static final int getFormat(int channels, int sampleSizeInBits) throws IOException {
		
		if(channels == 1) {
			
			if(sampleSizeInBits == 8) return AL_FORMAT_MONO8;
			else if(sampleSizeInBits == 16) return AL_FORMAT_MONO16;
			else throw new IOException("Illegal sample size!");
			
		} else if(channels == 2) {
			
			if(sampleSizeInBits == 8) return AL_FORMAT_STEREO8;
			else if(sampleSizeInBits == 16) return AL_FORMAT_STEREO16;
			else throw new IOException("Illegal sample size!");
			
		} else {
			
			throw new IOException("Only mono and stereo sound is supported!");
		}
	}
}
