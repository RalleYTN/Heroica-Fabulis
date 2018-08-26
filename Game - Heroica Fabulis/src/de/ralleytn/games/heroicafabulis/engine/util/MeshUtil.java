package de.ralleytn.games.heroicafabulis.engine.util;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

import de.ralleytn.games.heroicafabulis.engine.io.meshes.MeshData;

/**
 * Utility class containg methods for working with meshes.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 26.08.2018/0.3.0
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
	 * @since 26.08.2018/0.3.0
	 */
	public static final MeshData mergeLazy(List<MeshData> meshes, List<Matrix4f> transformations) {
		
		ArrayList<Float> vertices = new ArrayList<>();
		ArrayList<Float> texCoords = new ArrayList<>();
		ArrayList<Float> normals = new ArrayList<>();
		ArrayList<Integer> indices = new ArrayList<>();
		int offset = 0;
		int m = 0;
		
		for(MeshData mesh : meshes) {
			
			Matrix4f transformation = transformations.get(m);
			float[] mVertices = mesh.getVertices();
			float[] mNormals = mesh.getNormals();
			
			for(int index = 0; index < mesh.getVertices().length; index += 3) {
				
				Vector3f vertex = MatrixUtil.multiply(transformation, mVertices[index], mVertices[index + 1], mVertices[index + 2]);
				vertices.add(vertex.x);
				vertices.add(vertex.y);
				vertices.add(vertex.z);
				
				Vector3f normal = MatrixUtil.multiply(transformation, mNormals[index], mNormals[index + 1], mNormals[index + 2]);
				normals.add(normal.x);
				normals.add(normal.y);
				normals.add(normal.z);
			}
			
			ListUtil.addFloatArray(texCoords, mesh.getTextureCoordinates());
			int[] mIndices = mesh.getIndices();
			
			for(int index : mIndices) {
				
				indices.add(index + offset);
			}
			
			offset += mVertices.length / 3;
			m++;
		}
		
		MeshData mesh = new MeshData();
		mesh.setIndices(ListUtil.toPrimitiveIntArray(indices));
		mesh.setNormals(ListUtil.toPrimitiveFloatArray(normals));
		mesh.setTextureCoordinates(ListUtil.toPrimitiveFloatArray(texCoords));
		mesh.setVertices(ListUtil.toPrimitiveFloatArray(vertices));
		
		return mesh;
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
