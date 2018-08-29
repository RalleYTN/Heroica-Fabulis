package de.ralleytn.games.heroicafabulis.engine.io.meshes;

import java.io.IOException;
import java.io.InputStream;

import de.ralleytn.games.heroicafabulis.engine.io.Reader;
import de.ralleytn.games.heroicafabulis.engine.rendering.geom.MeshData;
import de.ralleytn.games.heroicafabulis.engine.util.MeshUtil;
import de.ralleytn.games.heroicafabulis.engine.util.VectorUtil;

import static de.ralleytn.games.heroicafabulis.engine.io.meshes.XMeshFormat.*;
import static de.ralleytn.games.heroicafabulis.engine.util.BinaryUtil.*;

/**
 * Reads XMESH model files.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 18.08.2018/0.2.0
 * @since 18.08.2018/0.2.0
 */
public class XMeshReader extends Reader<MeshData> {
	
	@Override
	public MeshData read(InputStream inputStream) throws IOException {
		
		try(InputStream meshStream = inputStream) {
			
			readSignature(meshStream);
			int flags = readFlags(meshStream);
			float[] vertices = readVertices(meshStream);
			int[] indices = readIndices(meshStream);
			float[] texCoords = getBit(flags, 0) ? readTexCoords(meshStream) : null;
			float[] normals = getBit(flags, 1) ? readNormals(meshStream) : null;
			
			if(normals == null && getBit(flags, 7)) {
				
				normals = VectorUtil.toArray3f(MeshUtil.generateNormals(VectorUtil.toList3f(vertices), indices));
			}
			
			MeshData data = new MeshData();
			data.setIndices(indices);
			data.setNormals(normals);
			data.setTextureCoordinates(texCoords);
			data.setVertices(vertices);
			
			return data;
		}
	}
}
