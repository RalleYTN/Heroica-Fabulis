package de.ralleytn.games.heroicafabulis.engine.util;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import de.ralleytn.games.heroicafabulis.engine.io.meshes.MeshData;

/**
 * Utility class containg methods for working with meshes.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 11.08.2018/0.1.0
 * @since 11.08.2018/0.1.0
 */
public final class MeshUtil {

	/**
	 * Private because no instances of this class should be created.
	 * @since 11.08.2018/0.1.0
	 */
	private MeshUtil() {}
	
	/**
	 * 
	 * @param meshes
	 * @param transformations
	 * @return
	 * @since 25.08.2018/0.3.0
	 */
	public static final MeshData mergeLazy(List<MeshData> meshes, List<Matrix4f> transformations) {
		
		int lengthVertices = 0;
		int lengthNormals = 0;
		int lengthTexCoords = 0;
		int lengthIndices = 0;
		ArrayList<Integer> indexLengths = new ArrayList<>();
		
		for(MeshData mesh : meshes) {
			
			lengthVertices += mesh.getVertices().length;
			lengthNormals += mesh.getNormals().length;
			lengthTexCoords += mesh.getTextureCoordinates().length;
			int length = mesh.getIndices().length;
			lengthIndices += length;
			indexLengths.add(length);
		}
		
		float[] vertices = new float[lengthVertices];
		float[] texCoords = new float[lengthTexCoords];
		float[] normals = new float[lengthNormals];
		int[] indices = new int[lengthIndices];
		
		int iv = 0;
		int ivt = 0;
		int ivn = 0;
		int i = 0;
		int indexLength = 0;
		
		for(int im = 0; im < meshes.size(); im++) {
			
			MeshData mesh = meshes.get(im);
			float[] mVertices = mesh.getVertices();
			float[] mTexCoords = mesh.getTextureCoordinates();
			float[] mNormals = mesh.getNormals();
			int[] mIndices = mesh.getIndices();
			Matrix4f transformation = transformations.get(im);
			
			for(int index = 0; index < mVertices.length; index += 3) {
				
				Vector3f vertex = MatrixUtil.multiply(transformation, mVertices[index], mVertices[index + 1], mVertices[index + 2]);
				vertices[iv++] = vertex.x + transformation.m30;
				vertices[iv++] = vertex.y + transformation.m31;
				vertices[iv++] = vertex.z + transformation.m32;
				
				Vector3f normal = MatrixUtil.multiply(transformation, mNormals[index], mNormals[index + 1], mNormals[index + 2]);
				normals[ivn++] = normal.x;
				normals[ivn++] = normal.y;
				normals[ivn++] = normal.z;
			}
			
			for(int index = 0; index < mTexCoords.length; index++) {
				
				texCoords[ivt++] = mTexCoords[index];
			}
			
			for(int index = 0; index < mIndices.length; index++) {
				
				indices[i++] = indexLength + mIndices[index];
			}
			
			indexLength += indexLengths.get(im);
		}
		
		MeshData data = new MeshData();
		data.setIndices(indices);
		data.setNormals(normals);
		data.setTextureCoordinates(texCoords);
		data.setVertices(vertices);
		
		return data;
	}
	
	/**
	 * Generates the normals of a mesh based on the vertices and the indices.
	 * @param vertices the vertices
	 * @param indices the indices
	 * @return the generated normals
	 * @since 11.08.2018/0.1.0
	 */
	public static final List<Vector3f> generateNormals(List<Vector3f> vertices, int[] indices) {
		
		List<Vector3f> normals = new ArrayList<>();
		
		for(int index = 0; index < vertices.size(); index++) {
			
			normals.add(new Vector3f());
		}
		
		for(int index = 0; index < indices.length; index += 3) {
			
			Vector3f v1 = vertices.get(indices[index]);
			Vector3f v2 = vertices.get(indices[index + 1]);
			Vector3f v3 = vertices.get(indices[index + 2]);
			
			Vector3f faceNormal = generateFaceNormal(v1, v2, v3);
			
			normals.get(indices[index]).add(faceNormal);
			normals.get(indices[index + 1]).add(faceNormal);
			normals.get(indices[index + 2]).add(faceNormal);
		}
		
		normals.forEach(normal -> {
			
			normal.normalize();
			normal.negate();
		});
		
		return normals;
	}

	/**
	 * Generates a face normal.
	 * @param vert1 first vertex
	 * @param vert2 second vertex
	 * @param vert3 third vertex
	 * @return the generated face normal
	 * @since 11.08.2018/0.1.0
	 */
	public static final Vector3f generateFaceNormal(Vector3f vert1, Vector3f vert2, Vector3f vert3) {
		
		Vector3f v1 = new Vector3f(vert2);
		v1.sub(vert1);
		
		Vector3f v2 = new Vector3f(vert3);
		v2.sub(vert1);
		v2.cross(v2, v1);
		v2.normalize();
		
		return v2;
	}
}
