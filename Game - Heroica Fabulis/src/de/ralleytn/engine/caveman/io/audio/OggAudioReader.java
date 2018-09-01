package de.ralleytn.engine.caveman.io.audio;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import com.jcraft.jorbis.Info;

import de.ralleytn.engine.caveman.audio.AudioData;

import static org.lwjgl.openal.AL10.*;

/**
 * Reader for Vorbis audio files.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 28.08.2018/0.3.0
 * @since 17.08.2018/0.2.0
 */
public class OggAudioReader extends AudioReader {

	/**
	 * @since 28.08.2018/0.3.0
	 */
	public OggAudioReader() {
		
		super();
	}
	
	/**
	 * @param inputStream the input stream from which the audio should be streamed
	 * @throws IOException if an I/O error occurred
	 * @since 28.08.2018/0.3.0
	 */
	public OggAudioReader(InputStream inputStream) throws IOException {
		
		this.inputStream = inputStream instanceof OggInputStream ? (OggInputStream)inputStream : new OggInputStream(new BufferedInputStream(inputStream));
		Info info = ((OggInputStream)this.inputStream).getOggInfo();
		this.sampleRate = info.rate;
		this.channels = info.channels;
		this.sampleSizeInBits = 16;
		this.sampleSizeInBytes = 2;
		this.format = getFormat(this.channels, this.sampleSizeInBits);
		this.bufferSize = 64;
	}
	
	@Override
	public AudioData read(InputStream inputStream) throws IOException {
		
		try(OggInputStream oggStream = new OggInputStream(inputStream);
			ByteArrayOutputStream out = new ByteArrayOutputStream()) {
				
			while(!oggStream.atEnd()) {
				
				out.write(oggStream.read());
			}
			
			byte[] bytes = out.toByteArray();
			ByteBuffer buffer = ByteBuffer.allocateDirect(bytes.length);
			buffer.put(bytes);
			buffer.rewind();
			
			int channels = oggStream.getOggInfo().channels;
			int format = 0;
			int sampleRate = oggStream.getOggInfo().rate;
			
			if(channels == 2) format = AL_FORMAT_STEREO16;
			else if(channels == 1) format = AL_FORMAT_MONO16;
			else throw new IOException("Only mono and stereo sound is supported!");

			AudioData data = new AudioData();
			data.setChannels(channels);
			data.setFormat(format);
			data.setData(buffer);
			data.setFrequency(sampleRate);
			
			return data;
		}
	}

	@Override
	public AudioData nextChunk() throws IOException {
		
		if(!((OggInputStream)this.inputStream).atEnd()) {
			
			byte[] bytes = new byte[(this.channels * this.sampleSizeInBytes) * this.bufferSize];
			this.inputStream.read(bytes, 0, bytes.length);
			
			ByteBuffer buffer = ByteBuffer.allocateDirect(bytes.length);
			buffer.put(bytes);
			buffer.rewind();
			
			AudioData data = new AudioData();
			data.setChannels(this.channels);
			data.setData(buffer);
			data.setFormat(this.format);
			data.setFrequency(this.sampleRate);
			
			return data;
		}
		
		return null;
	}
}
