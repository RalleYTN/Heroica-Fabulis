package de.ralleytn.games.heroicafabulis.engine.io.meshes;

import static de.ralleytn.games.heroicafabulis.engine.io.meshes.XMeshFormat.*;

import java.io.IOException;
import java.io.OutputStream;

import de.ralleytn.games.heroicafabulis.engine.io.Writer;

/**
 * Writes XMESH model files.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 22.08.2018/0.2.0
 * @since 18.08.2018/0.2.0
 */
public class XMeshWriter extends Writer<MeshData> {

	@Override
	public void write(OutputStream outputStream, MeshData data) throws IOException {
		
		try(OutputStream meshStream = outputStream) {
			
			writeSignature(meshStream);
			writeFlags(meshStream, data);
			writeData(meshStream, data);
		}
	}
}
