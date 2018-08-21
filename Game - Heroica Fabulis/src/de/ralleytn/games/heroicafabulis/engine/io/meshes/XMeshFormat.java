package de.ralleytn.games.heroicafabulis.engine.io.meshes;

import static de.ralleytn.games.heroicafabulis.engine.util.BinaryUtil.*;
import static de.ralleytn.games.heroicafabulis.engine.util.IOUtil.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 21.08.2018/0.2.0
 * @since 18.08.2018/0.2.0
 */
public final class XMeshFormat {
	
	// everything is big endian
	// first 5 bytes = XMESH signature
	// next byte = flags
	// 	- bit 1 = has texture coordinates
	//	- bit 2 = has normals
	// 	- bit 8 = generate normals
	// next 4 bytes = signed integer = vertex count
	// next 4 * vertex count * 3 bytes = vertices
	//	- each 4 byte block represents a signed float
	//	- each vertex has 3 floats
	//	- each vertex is ordered XYZ
	// next 4 bytes = signed integer = index count
	// next 4 * index count bytes = indices
	//	- each 4 byte block represents a signed integer
	// if has tex coords
	//	- next 4 bytes = signed integer = tex coord count
	//	- next 4 * tex coord count * 2 bytes = tex coords
	//		- each 4 byte block represents a signed float
	//		- each tex coord has 2 floats
	//		- each tex coord is orderd UV(XY)
	// if has normals
	//	- next 4 bytes = signed integer = normal count
	//	- next 4 * normal count * 3 bytes = normals
	//		- each 4 byte block represents a signed float
	//		- each normal has 3 floats
	//		- each normal is ordered XYZ
	
	/**
	 * @since 18.08.2018/0.2.0
	 */
	public static final String SIGNATURE = "XMESH";
	
	/**
	 * @since 18.08.2018/0.2.0
	 */
	private XMeshFormat() {}
	
	/**
	 * 
	 * @param meshStream
	 * @throws IOException
	 * @since 18.08.2018/0.2.0
	 */
	public static final void readSignature(InputStream meshStream) throws IOException {
		
		String header = readString(meshStream, SIGNATURE.length(), StandardCharsets.UTF_8);
		
		if(!header.equals(SIGNATURE)) {
			
			throw new IOException("Not a XMESH file!");
		}
	}
	
	/**
	 * 
	 * @param meshStream
	 * @return
	 * @throws IOException
	 * @since 18.08.2018/0.2.0
	 */
	public static final int readFlags(InputStream meshStream) throws IOException {
		
		return meshStream.read();
	}
	
	/**
	 * 
	 * @param meshStream
	 * @return
	 * @throws IOException
	 * @since 18.08.2018/0.2.0
	 */
	public static final float[] readVertices(InputStream meshStream) throws IOException {
		
		int length = readSignedInt(meshStream, true) * 3;
		float[] vertices = new float[length];
		byte[] data = new byte[length * Float.BYTES];
		int readBytes = meshStream.read(data);
		int vertexIndex = 0;
		
		for(int index = 0; index < readBytes; index += Float.BYTES) {
			
			vertices[vertexIndex++] = Float.intBitsToFloat(((data[index] & 0xFF) << 24) | ((data[index + 1] & 0xFF) << 16) | ((data[index + 2] & 0xFF) << 8) | (data[index + 3] & 0xFF));
		}
		
		return vertices;
	}
	
	/**
	 * 
	 * @param meshStream
	 * @return
	 * @throws IOException
	 * @since 18.08.2018/0.2.0
	 */
	public static final int[] readIndices(InputStream meshStream) throws IOException {
		
		int length = readSignedInt(meshStream, true);
		int[] indices = new int[length];
		byte[] data = new byte[length * Integer.BYTES];
		int readBytes = meshStream.read(data);
		int indexIndex = 0; // wat
		
		for(int index = 0; index < readBytes; index += Integer.BYTES) {
			
			indices[indexIndex++] = ((data[index] & 0xFF) << 24) | ((data[index + 1] & 0xFF) << 16) | ((data[index + 2] & 0xFF) << 8) | (data[index + 3] & 0xFF);
		}
		
		return indices;
	}
	
	/**
	 * 
	 * @param meshStream
	 * @return
	 * @throws IOException
	 * @since 18.08.2018/0.2.0
	 */
	public static final float[] readTexCoords(InputStream meshStream) throws IOException {
		
		int length = readSignedInt(meshStream, true) * 2;
		float[] texCoords = new float[length];
		byte[] data = new byte[length * Float.BYTES];
		int readBytes = meshStream.read(data);
		int texCoordIndex = 0;
		
		for(int index = 0; index < readBytes; index += Float.BYTES) {
			
			texCoords[texCoordIndex++] = Float.intBitsToFloat(((data[index] & 0xFF) << 24) | ((data[index + 1] & 0xFF) << 16) | ((data[index + 2] & 0xFF) << 8) | (data[index + 3] & 0xFF));
		}
		
		return texCoords;
	}
	
	/**
	 * 
	 * @param meshStream
	 * @return
	 * @throws IOException
	 * @since 18.08.2018/0.2.0
	 */
	public static final float[] readNormals(InputStream meshStream) throws IOException {
		
		int length = readSignedInt(meshStream, true) * 3;
		float[] normals = new float[length];
		byte[] data = new byte[length * Float.BYTES];
		int readBytes = meshStream.read(data);
		int normalIndex = 0;
		
		for(int index = 0; index < readBytes; index += Float.BYTES) {
			
			normals[normalIndex++] = Float.intBitsToFloat(((data[index] & 0xFF) << 24) | ((data[index + 1] & 0xFF) << 16) | ((data[index + 2] & 0xFF) << 8) | (data[index + 3] & 0xFF));
		}
		
		return normals;
	}
	
	/**
	 * 
	 * @param meshStream
	 * @param data
	 * @throws IOException
	 * @since 18.08.2018/0.2.0
	 */
	public static final void writeSignature(OutputStream meshStream, MeshData data) throws IOException {
		
		for(int index = 0; index < SIGNATURE.length(); index++) {
			
			meshStream.write(SIGNATURE.charAt(index));
		}
	}
	
	/**
	 * 
	 * @param meshStream
	 * @param data
	 * @throws IOException
	 * @since 18.08.2018/0.2.0
	 */
	public static final void writeFlags(OutputStream meshStream, MeshData data) throws IOException {
		
		int flags = 0b00000000;
		flags = setBit(flags, 0, data.getTextureCoordinates() != null);
		flags = setBit(flags, 1, data.getNormals() != null);
		flags = setBit(flags, 7, data.getNormals() == null);
		meshStream.write(flags);
	}
	
	/**
	 * 
	 * @param meshStream
	 * @param data
	 * @throws IOException
	 * @since 20.08.2018/0.2.0
	 */
	public static final void writeData(OutputStream meshStream, MeshData data) throws IOException {
		
		writeInt(meshStream, data.getVertices().length / 3, true);
		
		for(float vertex : data.getVertices()) {
			
			writeFloat(meshStream, vertex, true);
		}
		
		writeInt(meshStream, data.getIndices().length, true);
		
		for(int index : data.getIndices()) {
			
			writeInt(meshStream, index, true);
		}
		
		if(data.getTextureCoordinates() != null) {

			float[] texCoords = data.getTextureCoordinates();
			writeInt(meshStream, texCoords.length / 2, true);
				
			for(float texCoord : texCoords) {
					
				writeFloat(meshStream, texCoord, true);
			}
		}
		
		if(data.getNormals() != null) {
			
			writeInt(meshStream, data.getIndices().length / 3, true);
			float[] normals = data.getNormals();
			
			for(float normal : normals) {
				
				writeFloat(meshStream, normal, true);
			}
		}
	}
}
