package de.ralleytn.games.heroicafabulis.engine.io.textures;

import java.io.IOException;
import java.io.OutputStream;

import de.ralleytn.games.heroicafabulis.engine.io.Writer;
import static de.ralleytn.games.heroicafabulis.engine.io.textures.XImgFormat.*;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 20.08.2018/0.2.0
 * @since 19.08.2018/0.2.0
 */
public class XImgWriter extends Writer<TextureData> {
	
	@Override
	public void write(OutputStream outputStream, TextureData data) throws IOException {
		
		try(OutputStream imageStream = outputStream) {
			
			writeSignature(imageStream);
			writeFlags(imageStream, data);
			writeSize(imageStream, data);
			writePixels(imageStream, data);
		}
	}
}
