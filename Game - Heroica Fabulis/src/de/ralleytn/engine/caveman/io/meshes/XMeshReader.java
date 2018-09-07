package de.ralleytn.engine.caveman.io.meshes;

import java.io.IOException;
import java.io.InputStream;

import de.ralleytn.engine.caveman.io.Reader;
import de.ralleytn.engine.caveman.rendering.geom3d.MeshData;
import de.ralleytn.engine.caveman.util.MeshUtil;
import de.ralleytn.engine.caveman.util.VectorUtil;

import static de.ralleytn.engine.caveman.io.meshes.XMeshFormat.*;
import static de.ralleytn.engine.caveman.util.BinaryUtil.*;

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
