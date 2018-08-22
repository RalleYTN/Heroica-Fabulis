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
 * @version 20.08.2018/0.2.0
 * @since 17.08.2018/0.2.0
 */
public class WavAudioReader extends Reader<AudioData> {

	@Override
	public AudioData read(InputStream inputStream) throws IOException {
		
		try(BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream)) {
			
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedInputStream);
			AudioFormat audioFormat = audioStream.getFormat();
			int sampleRate = (int)audioFormat.getSampleRate();
			int channels = audioFormat.getChannels();
			int sampleSizeInBits = audioFormat.getSampleSizeInBits();
			int format = 0;
			boolean bit16 = sampleSizeInBits == 16;

			if(channels == 1) {
				
				if(sampleSizeInBits == 8) format = AL_FORMAT_MONO8;
				else if(bit16) format = AL_FORMAT_MONO16;
				else throw new IOException("Illegal sample size!");
				
			} else if(channels == 2) {
				
				if(sampleSizeInBits == 8) format = AL_FORMAT_STEREO8;
				else if(bit16) format = AL_FORMAT_STEREO16;
				else throw new IOException("Illegal sample size!");
				
			} else {
				
				throw new IOException("Only mono and stereo sound is supported!");
			}
			
			byte[] buffer = new byte[channels * (int)audioStream.getFrameLength() * sampleSizeInBits / 8];
			int read = 0;
			int total = 0;
			
			while((read = audioStream.read(buffer, total, buffer.length - total)) != -1 && total < buffer.length) {
				
				total += read;
			}
			
			AudioData data = new AudioData();
			data.setChannels(channels);
			data.setData(createByteBuffer(buffer, bit16));
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
}
