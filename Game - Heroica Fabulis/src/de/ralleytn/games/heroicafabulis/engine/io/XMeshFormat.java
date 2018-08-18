package de.ralleytn.games.heroicafabulis.engine.io;

import static de.ralleytn.games.heroicafabulis.engine.util.BinaryUtil.*;
import static de.ralleytn.games.heroicafabulis.engine.util.IOUtil.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import de.ralleytn.games.heroicafabulis.engine.rendering.geom.Mesh;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 18.08.2018/0.2.0
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
		
		String header = readString(meshStream, 5, StandardCharsets.UTF_8);
		
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
		
		float[] vertices = new float[readSignedInt(meshStream, true) * 3];
		
		for(int index = 0; index < vertices.length; index++) {
			
			vertices[index] = readFloat(meshStream, true);
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
		
		int[] indices = new int[readSignedInt(meshStream, true)];
		
		for(int index = 0; index < indices.length; index++) {
			
			indices[index] = readSignedInt(meshStream, true);
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
		
		float[] texCoords = new float[readSignedInt(meshStream, true) * 2];
		
		for(int index = 0; index < texCoords.length; index++) {
			
			texCoords[index] = readFloat(meshStream, true);
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
		
		float[] normals = new float[readSignedInt(meshStream, true) * 3];
		
		for(int index = 0; index < normals.length; index++) {
			
			normals[index] = readFloat(meshStream, true);
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
	public static final void writeSignature(OutputStream meshStream, Mesh data) throws IOException {
		
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
	public static final void writeFlags(OutputStream meshStream, Mesh data) throws IOException {
		
		int flags = 0b00000000;
		flags = setBit(flags, 0, data.hasTextureCoordinates());
		flags = setBit(flags, 1, data.hasNormals());
		flags = setBit(flags, 7, !data.hasNormals());
		meshStream.write(flags);
	}
	
	/**
	 * 
	 * @param meshStream
	 * @param data
	 * @throws IOException
	 * @since 18.08.2018/0.2.0
	 */
	public static final void writeVertices(OutputStream meshStream, Mesh data) throws IOException {
		
		writeInt(meshStream, data.getVertexCount(), true);
		float[] vertices = data.getVertexArray().getBuffer(0).getDataAsFloats();
		
		for(float vertex : vertices) {
			
			writeFloat(meshStream, vertex, true);
		}
	}
	
	/**
	 * 
	 * @param meshStream
	 * @param data
	 * @throws IOException
	 * @since 18.08.2018/0.2.0
	 */
	public static final void writeIndices(OutputStream meshStream, Mesh data) throws IOException {
		
		writeInt(meshStream, data.getIndexCount(), true);
		int[] indices = data.getIndexBuffer().getDataAsInts();
		
		for(int index : indices) {
			
			writeInt(meshStream, index, true);
		}
	}
	
	/**
	 * 
	 * @param meshStream
	 * @param data
	 * @throws IOException
	 * @since 18.08.2018/0.2.0
	 */
	public static final void writeTexCoords(OutputStream meshStream, Mesh data) throws IOException {
		
		if(data.hasTextureCoordinates()) {

			float[] texCoords = data.getVertexArray().getBuffer(1).getDataAsFloats();
			writeInt(meshStream, texCoords.length / 2, true);
				
			for(float texCoord : texCoords) {
					
				writeFloat(meshStream, texCoord, true);
			}
		}
	}
	
	/**
	 * 
	 * @param meshStream
	 * @param data
	 * @throws IOException
	 * @since 18.08.2018/0.2.0
	 */
	public static final void writeNormals(OutputStream meshStream, Mesh data) throws IOException {
		
		if(data.hasNormals()) {
			
			writeInt(meshStream, data.getFaceCount(), true);
			float[] normals = data.getVertexArray().getBuffer(2).getDataAsFloats();
			
			for(float normal : normals) {
				
				writeFloat(meshStream, normal, true);
			}
		}
	}
}
