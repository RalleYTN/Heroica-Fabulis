package de.ralleytn.games.heroicafabulis.engine.io.audio;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import static org.lwjgl.openal.AL10.*;

import de.ralleytn.games.heroicafabulis.engine.io.Reader;

/**
 * Reads Wave audio files.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 27.08.2018/0.3.0
 * @since 17.08.2018/0.2.0
 */
public class WavAudioReader extends Reader<AudioData> implements AutoCloseable {

	private AudioInputStream inputStream;
	private int sampleRate;
	private int channels;
	private int sampleSizeInBits;
	private int sampleSizeInBytes;
	private int format;
	private int bufferSize;
	private long totalFrames;
	private long currentFrame;
	
	/**
	 * 
	 * @since 17.08.2018/0.2.0
	 */
	public WavAudioReader() {}
	
	/**
	 * 
	 * @param inputStream
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @since 26.08.2018/0.3.0
	 */
	public WavAudioReader(InputStream inputStream) throws UnsupportedAudioFileException, IOException {

		this.inputStream = inputStream instanceof AudioInputStream ? (AudioInputStream)inputStream : AudioSystem.getAudioInputStream(new BufferedInputStream(inputStream));
		AudioFormat audioFormat = this.inputStream.getFormat();
		this.sampleRate = (int)audioFormat.getSampleRate();
		this.channels = audioFormat.getChannels();
		this.sampleSizeInBits = audioFormat.getSampleSizeInBits();
		this.sampleSizeInBytes = this.sampleSizeInBytes / 8;
		this.format = getFormat(this.channels, this.sampleSizeInBits);
		this.totalFrames = this.inputStream.getFrameLength();
		this.bufferSize = 32;
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
	 * @return
	 * @throws IOException
	 * @since 26.08.2018/0.3.0
	 */
	public AudioData nextChunk() throws IOException {
		
		if(this.currentFrame >= this.totalFrames) {
			
			byte[] buffer = new byte[(this.channels * this.sampleSizeInBytes) * this.bufferSize];
			this.inputStream.read(buffer);
			
			AudioData data = new AudioData();
			data.setChannels(this.channels);
			data.setData(createByteBuffer(buffer, this.sampleSizeInBits == 16));
			data.setFormat(this.format);
			data.setFrequency(this.sampleRate);
			
			this.currentFrame += this.bufferSize;
			return data;
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param bufferSize
	 * @since 26.08.2018/0.3.0
	 */
	public void setBufferSize(int bufferSize) {
		
		this.bufferSize = bufferSize;
	}
	
	@Override
	public AudioData read(InputStream inputStream) throws IOException {
		
		try(BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
			
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedInputStream);
			AudioFormat audioFormat = audioStream.getFormat();
			int sampleRate = (int)audioFormat.getSampleRate();
			int channels = audioFormat.getChannels();
			int sampleSizeInBits = audioFormat.getSampleSizeInBits();
			int format = getFormat(channels, sampleSizeInBits);
			
			byte[] buffer = new byte[channels * (int)audioStream.getFrameLength() * sampleSizeInBits / 8];
			int read = 0;
			int total = 0;
			
			while((read = audioStream.read(buffer, total, buffer.length - total)) != -1 && total < buffer.length) {
				
				total += read;
			}
			
			AudioData data = new AudioData();
			data.setChannels(channels);
			data.setData(createByteBuffer(buffer, sampleSizeInBits == 16));
			data.setFormat(format);
			data.setFrequency(sampleRate);
			
			return data;
			
		} catch(UnsupportedAudioFileException exception) {
			
			throw new IOException(exception);
		}
	}
	
	/**
	 * 
	 * @return
	 * @since 26.08.2018/0.3.0
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
	 * @since 26.08.2018/0.3.0
	 */
	private static final int getFormat(int channels, int sampleSizeInBits) throws IOException {
		
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
	
	/**
	 * Wraps the audio data in a {@linkplain ByteBuffer}.
	 * @param bytes the audio data
	 * @param twoByteData
	 * @return the created {@linkplain ByteBuffer}
	 * @since 17.08.2018/0.2.0
	 */
	private static final ByteBuffer createByteBuffer(byte[] bytes, boolean twoByteData) {
		
		ByteBuffer buffer = ByteBuffer.allocateDirect(bytes.length);
		buffer.order(ByteOrder.nativeOrder());
		
		ByteBuffer source = ByteBuffer.wrap(bytes);
		source.order(ByteOrder.LITTLE_ENDIAN);
		
		if(twoByteData) {
			
			ShortBuffer sBuffer = buffer.asShortBuffer();
			ShortBuffer sSource = source.asShortBuffer();
			
			while(sSource.hasRemaining()) {
				
				sBuffer.put(sSource.get());
			}
			
		} else {
			
			while(source.hasRemaining()) {
				
				buffer.put(source.get());
			}
		}
		
		buffer.rewind();
		return buffer;
	}

	@Override
	public void close() throws Exception {
		
		this.inputStream.close();
	}
}
