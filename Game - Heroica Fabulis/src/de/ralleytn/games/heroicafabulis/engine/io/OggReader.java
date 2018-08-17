package de.ralleytn.games.heroicafabulis.engine.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.openal.AL10.*;

import de.ralleytn.games.heroicafabulis.engine.audio.ALBuffer;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 17.08.2018/0.2.0
 * @since 17.08.2018/0.2.0
 */
public class OggReader extends AudioReader {

	@Override
	public ALBuffer read(InputStream inputStream) throws IOException {
		
		try(OggInputStream oggStream = new OggInputStream(inputStream);
			ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
				
			while(!oggStream.atEnd()) {
				
				buffer.write(oggStream.read());
			}
			
			byte[] bytes = buffer.toByteArray();
			ByteBuffer data = ByteBuffer.allocateDirect(bytes.length);
			data.put(bytes);
			data.rewind();
			
			int channels = oggStream.getOggInfo().channels;
			int format = 0;
			int sampleRate = oggStream.getOggInfo().rate;
			
			if(channels == 2) format = AL_FORMAT_STEREO16;
			else if(channels == 1) format = AL_FORMAT_MONO16;
			else throw new IOException("Only mono and stereo sound is supported!");
			
			return createBuffer(format, data, sampleRate);
		}
	}
}
