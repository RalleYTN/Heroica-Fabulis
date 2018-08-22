package de.ralleytn.games.heroicafabulis.engine.io.audio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.openal.AL10.*;

import de.ralleytn.games.heroicafabulis.engine.io.Reader;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 20.08.2018/0.2.0
 * @since 17.08.2018/0.2.0
 */
public class OggAudioReader extends Reader<AudioData> {

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
}
