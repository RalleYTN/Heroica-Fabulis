package de.ralleytn.games.heroicafabulis.engine.io.audio;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;

import de.ralleytn.games.heroicafabulis.engine.audio.AudioData;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Reader for AIFF audio files.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 28.08.2018/0.3.0
 * @since 17.08.2018/0.2.0
 */
public class AiffAudioReader extends AudioReader {

	private Encoding encoding;
	
	/**
	 * 
	 * @since 28.08.2028/0.3.0
	 */
	public AiffAudioReader() {
		
		super();
	}
	
	/**
	 * 
	 * @param inputStream
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 * @since 28.08.2018/0.3.0
	 */
	public AiffAudioReader(InputStream inputStream) throws IOException, UnsupportedAudioFileException {

		this.inputStream = inputStream instanceof AudioInputStream ? (AudioInputStream)inputStream : AudioSystem.getAudioInputStream(new BufferedInputStream(inputStream));
		AudioFormat audioFormat = ((AudioInputStream)this.inputStream).getFormat();
		this.encoding = audioFormat.getEncoding();
		this.sampleRate = (int)audioFormat.getSampleRate();
		this.channels = audioFormat.getChannels();
		this.sampleSizeInBits = audioFormat.getSampleSizeInBits();
		this.sampleSizeInBytes = this.sampleSizeInBits / 8;
		this.format = getFormat(this.channels, this.sampleSizeInBits);
		this.totalFrames = ((AudioInputStream)this.inputStream).getFrameLength();
		this.bufferSize = this.sampleSizeInBytes == 2 ? 64 : 128;
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
			data.setFormat(format);
			data.setFrequency(sampleRate);
			data.setData(createByteBuffer(buffer, sampleSizeInBits == 16, audioFormat.getEncoding()));
			
			return data;
			
		} catch(UnsupportedAudioFileException exception) {
			
			throw new IOException(exception);
		}
	}
	
	/**
	 * Wraps the audio data in a {@linkplain ByteBuffer}.
	 * @param bytes the audio data
	 * @param twoByteData
	 * @param encoding the encoding
	 * @return the created {@linkplain ByteBuffer}
	 * @since 17.08.2018/0.2.0
	 */
	private static final ByteBuffer createByteBuffer(byte[] bytes, boolean twoByteData, Encoding encoding) {
		
		ByteBuffer buffer = ByteBuffer.allocateDirect(bytes.length);
		buffer.order(ByteOrder.nativeOrder());
		
		ByteBuffer source = ByteBuffer.wrap(bytes);
		source.order(ByteOrder.BIG_ENDIAN);
		
		if(twoByteData) {
			
			ShortBuffer sBuffer = buffer.asShortBuffer();
			ShortBuffer sSource = source.asShortBuffer();
			
			while(sSource.hasRemaining()) {
				
				sBuffer.put(sSource.get());
			}
			
		} else {
			
			while(source.hasRemaining()) {
				
				byte nextByte = source.get();
				
				if(encoding == Encoding.PCM_SIGNED) {
					
					nextByte = (byte)(nextByte + 127);
				}
				
				buffer.put(nextByte);
			}
		}
		
		buffer.rewind();
		return buffer;
	}

	@Override
	public AudioData nextChunk() throws IOException {
		
		if(this.currentFrame < this.totalFrames) {

			byte[] buffer = new byte[(this.channels * this.sampleSizeInBytes) * this.bufferSize];
			this.inputStream.read(buffer, 0, buffer.length);
			AudioData data = new AudioData();
			data.setChannels(this.channels);
			data.setData(createByteBuffer(buffer, this.sampleSizeInBits == 16, this.encoding));
			data.setFormat(this.format);
			data.setFrequency(this.sampleRate);
			
			this.currentFrame += this.bufferSize;
			return data;
		}
		
		return null;
	}
}
