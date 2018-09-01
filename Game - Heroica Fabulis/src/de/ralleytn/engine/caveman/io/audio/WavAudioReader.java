package de.ralleytn.engine.caveman.io.audio;

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

import de.ralleytn.engine.caveman.audio.AudioData;

/**
 * Reads Wave audio files.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 27.08.2018/0.3.0
 * @since 17.08.2018/0.2.0
 */
public class WavAudioReader extends AudioReader {
	
	/**
	 * @since 17.08.2018/0.2.0
	 */
	public WavAudioReader() {
		
		super();
	}
	
	/**
	 * @param inputStream the input stream from which the audio should be streamed
	 * @throws UnsupportedAudioFileException if the audio is not supported
	 * @throws IOException if an I/O error occurred
	 * @since 26.08.2018/0.3.0
	 */
	public WavAudioReader(InputStream inputStream) throws UnsupportedAudioFileException, IOException {

		super(inputStream);
	}
	
	@Override
	public AudioData nextChunk() throws IOException {
		
		if(this.currentFrame < this.totalFrames) {

			byte[] buffer = new byte[(this.channels * this.sampleSizeInBytes) * this.bufferSize];
			this.inputStream.read(buffer, 0, buffer.length);
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
	 * Wraps the audio data in a {@linkplain ByteBuffer}.
	 * @param bytes the audio data
	 * @param twoByteData the sample size in bits is 16
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
}
