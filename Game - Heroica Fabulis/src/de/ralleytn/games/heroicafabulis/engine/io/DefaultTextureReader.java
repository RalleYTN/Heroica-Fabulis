package de.ralleytn.games.heroicafabulis.engine.io;

import java.io.IOException;
import java.io.InputStream;

import de.ralleytn.games.heroicafabulis.engine.rendering.Texture;

/**
 * Reads a texture under the assumption that the given {@linkplain InputStream} contains either a PNG, JPEG, GIF or BMP.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 04.08.2018/0.1.0
 * @since 04.08.2018/0.1.0
 */
public class DefaultTextureReader extends Reader<Texture> {

	@Override
	public Texture read(InputStream inputStream) throws IOException {
		
		// TODO
		return null;
	}
}
