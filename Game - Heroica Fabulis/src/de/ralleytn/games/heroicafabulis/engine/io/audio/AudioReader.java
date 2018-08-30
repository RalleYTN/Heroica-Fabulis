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

import de.ralleytn.games.heroicafabulis.engine.audio.AudioData;
import de.ralleytn.games.heroicafabulis.engine.io.Reader;

/**
 * Represents an abstract audio reader with streaming capabilities.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 29.08.2018/0.3.0
 * @since 27.08.2018/0.3.0
 */
public abstract class AudioReader extends Reader<AudioData> {

	protected InputStream inputStream;
	protected int sampleRate;
	protected int channels;
	protected int sampleSizeInBits;
	protected int sampleSizeInBytes;
	protected int format;
	protected int bufferSize;
	protected long totalFrames;
	protected long currentFrame;
	
	/**
	 * @since 27.08.2018/0.3.0
	 */
	public AudioReader() {}
	
	/**
	 * @param inputStream the input stream from which audio should be streamed
	 * @throws IOException if an I/O error occurred
	 * @throws UnsupportedAudioFileException if the audio is not supported
	 * @since 27.08.2018/0.3.0
	 */
	public AudioReader(InputStream inputStream) throws IOException, UnsupportedAudioFileException {
		
		this.inputStream = inputStream instanceof AudioInputStream ? (AudioInputStream)inputStream : AudioSystem.getAudioInputStream(new BufferedInputStream(inputStream));
		AudioFormat audioFormat = ((AudioInputStream)this.inputStream).getFormat();
		this.sampleRate = (int)audioFormat.getSampleRate();
		this.channels = audioFormat.getChannels();
		this.sampleSizeInBits = audioFormat.getSampleSizeInBits();
		this.sampleSizeInBytes = this.sampleSizeInBits / 8;
		this.format = getFormat(this.channels, this.sampleSizeInBits);
		this.totalFrames = ((AudioInputStream)this.inputStream).getFrameLength();
		this.bufferSize = this.sampleSizeInBytes == 2 ? 64 : 128;
	}
	
	/**
	 * Reads a chunk from the stream. the size of the chunk is typically {@code channels * sampleSizeInBytes * bufferSize}.
	 * @return the read chunk or {@code null} if the end of the stream is reached
	 * @throws IOException if an I/O error occurred
	 * @since 27.08.2018/0.3.0
	 */
	public abstract AudioData nextChunk() throws IOException;
	
	/**
	 * Sets the buffer size.
	 * @param bufferSize the buffer size
	 * @since 27.08.2018/0.3.0
	 */
	public void setBufferSize(int bufferSize) {
		
		this.bufferSize = bufferSize;
	}
	
	/**
	 * Resets the underlying input stream.
	 * @throws IOException if an I/O error occurred
	 * @since 27.08.2018/0.3.0
	 */
	public void reset() throws IOException {
		
		this.inputStream.reset();
	}
	
	/**
	 * Closes the underlying input stream.
	 * @throws IOException if an I/O error occurred
	 * @since 27.08.2018/0.3.0
	 */
	public void close() throws IOException {
		
		this.inputStream.close();
	}
	
	/**
	 * @return the buffer size
	 * @since 27.08.2018/0.3.0
	 */
	public int getBufferSize() {
		
		return this.bufferSize;
	}
	
	/**
	 * Takes in the channel count and the sample size in bits to determine the OpenAL audio format.
	 * @param channels the channel count
	 * @param sampleSizeInBits the sample size in bits
	 * @return the OpenAL format
	 * @throws IOException if no supported format can be found
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
