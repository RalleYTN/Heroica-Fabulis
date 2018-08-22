package de.ralleytn.games.heroicafabulis.engine.io.textures;

import java.io.IOException;
import java.io.InputStream;

import de.ralleytn.games.heroicafabulis.engine.io.Reader;

import static de.ralleytn.games.heroicafabulis.engine.io.textures.XImgFormat.*;

/**
 * Reader for XIMG image files.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 20.08.2018/0.2.0
 * @since 19.08.2018/0.2.0
 */
public class XImgTextureReader extends Reader<TextureData> {

	@Override
	public TextureData read(InputStream inputStream) throws IOException {
		
		try(InputStream imageStream = inputStream) {
			
			readSignature(imageStream);
			int flags = readFlags(imageStream);
			int width = readWidth(imageStream);
			int height = readHeight(imageStream);
			int[] pixels = readPixels(imageStream, flags, width, height);
			
			TextureData data = new TextureData();
			data.setSize(width, height);
			data.setPixels(pixels);
			
			return data;
		}
	}
}
