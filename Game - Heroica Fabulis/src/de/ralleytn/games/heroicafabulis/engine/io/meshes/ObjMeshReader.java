package de.ralleytn.games.heroicafabulis.engine.io.meshes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import de.ralleytn.games.heroicafabulis.engine.io.Reader;
import de.ralleytn.games.heroicafabulis.engine.rendering.geom.MeshData;
import de.ralleytn.games.heroicafabulis.engine.util.ListUtil;
import de.ralleytn.games.heroicafabulis.engine.util.VectorUtil;

/**
 * Reads Wavefront Object model files.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 18.08.2018/0.2.0
 * @since 18.08.2018/0.2.0
 */
public class ObjMeshReader extends Reader<MeshData> {

	private static final String TYPE_VERTEX = "v";
	private static final String TYPE_TEXCOORD = "vt";
	private static final String TYPE_NORMAL = "vn";
	private static final String TYPE_FACE = "f";
	
	@Override
	public MeshData read(InputStream inputStream) throws IOException {
		
		List<Vector3f> verticesList = new ArrayList<>();
		List<Vector2f> textureCoordinatesList = new ArrayList<>();
		List<Vector3f> normalsList = new ArrayList<>();
		List<Integer> indicesList = new ArrayList<>();
		
		float[] texCoords = null;
		float[] normals = null;
		
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
			
			String line = null;
			
			while((line = reader.readLine()) != null) {
				
				line = line.trim();
				
				if(!line.startsWith("#") && line.contains(" ")) {
					
					String[] parts = line.split(" ");
					String type = parts[0];
					
					if(type.equals(TYPE_VERTEX)) {

						verticesList.add(new Vector3f(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]), Float.parseFloat(parts[3])));
						
					} else if(type.equals(TYPE_TEXCOORD)) {

						textureCoordinatesList.add(new Vector2f(Float.parseFloat(parts[1]), Float.parseFloat(parts[2])));
						
					} else if(type.equals(TYPE_NORMAL)) {

						normalsList.add(new Vector3f(Float.parseFloat(parts[1]), Float.parseFloat(parts[2]), Float.parseFloat(parts[3])));
						
					} else if(type.equals(TYPE_FACE)) {
						
						if(texCoords == null) {

							texCoords = new float[verticesList.size() * 2];
							normals = new float[verticesList.size() * 3];
						}
						
						for(int face = 1; face <= 3; face++) {
							
							String[] faceParts = parts[face].split("/");
							int currentVertexPointer = Integer.parseInt(faceParts[0]) - 1;
							indicesList.add(currentVertexPointer);
							
							int pointer = currentVertexPointer * 2;
							Vector2f textureCoordinate = textureCoordinatesList.get(Integer.parseInt(faceParts[1]) - 1);
							texCoords[pointer] = textureCoordinate.x;
							texCoords[pointer + 1] = 1.0F - textureCoordinate.y;
							
							pointer = currentVertexPointer * 3;
							Vector3f normal = normalsList.get(Integer.parseInt(faceParts[2]) - 1);
							normals[pointer] = normal.x;
							normals[pointer + 1] = normal.y;
							normals[pointer + 2] = normal.z;
						}
					}
				}
			}
		}
		
		MeshData data = new MeshData();
		data.setVertices(VectorUtil.toArray3f(verticesList));
		data.setIndices(ListUtil.toPrimitiveIntArray(indicesList));
		data.setTextureCoordinates(texCoords);
		data.setNormals(normals);
		
		return data;
	}
}
