package de.ralleytn.games.heroicafabulis.engine.io.xmesh;

import static de.ralleytn.games.heroicafabulis.engine.io.xmesh.XMeshFormat.*;

import java.io.IOException;
import java.io.OutputStream;

import de.ralleytn.games.heroicafabulis.engine.io.Writer;
import de.ralleytn.games.heroicafabulis.engine.rendering.geom.Mesh;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 18.08.2018/0.2.0
 * @since 18.08.2018/0.2.0
 */
public class XMeshWriter extends Writer<Mesh> {

	@Override
	public void write(OutputStream outputStream, Mesh data) throws IOException {
		
		try(OutputStream meshStream = outputStream) {
			
			writeSignature(meshStream, data);
			writeFlags(meshStream, data);
			writeVertices(meshStream, data);
			writeIndices(meshStream, data);
			writeTexCoords(meshStream, data);
			writeNormals(meshStream, data);
		}
	}
}
