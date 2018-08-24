import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

/**
 * Tool for converting between OBJ and XMESH models.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 23.08.2018/0.2.0
 * @since 22.08.2018/0.2.0
 */
public final class MeshConverter {

	private static final String XMESH_SIGNATURE = "XMESH";
	
	/**
	 * Private because no instances of this class should exist.
	 * @since 22.08.2018/0.2.0
	 */
	private MeshConverter() {}
	
	/**
	 * @param args 0 = source file, 1 = target file
	 * @throws IOException if an I/O error occurred
	 * @since 22.08.2018/0.2.0
	 */
	public static void main(String[] args) throws IOException {
		
		args = new String[] {
			"res/meshes/stall.xmesh",
			"res/meshes/stall2.obj"
		};
		
		File source = new File(args[0]);
		File target = new File(args[1]);
		
		try(FileOutputStream out = new FileOutputStream(target);
			FileInputStream in = new FileInputStream(source)) {
			
			if(source.getName().toLowerCase().endsWith(".xmesh")) {
				
				try(ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
					
					// Read the entire image into memory
					int readByteCount = 0;
					int offset = 0;
					byte[] data = new byte[4096];
					
					while((readByteCount = in.read(data)) != -1) {
						
						buffer.write(data, 0, readByteCount);
					}
					
					data = buffer.toByteArray();
					
					// Check the signature
					String signature = new String(new byte[] {data[offset++], data[offset++], data[offset++], data[offset++], data[offset++]});
					
					if(signature.equals(XMESH_SIGNATURE)) {
						
						int flags = data[offset++] & 0xFF;
						boolean hasTexCoords = (flags & 1) == 1;
						boolean hasNormals = ((flags >> 1) & 1) == 1;
						boolean generateNormals = ((flags >> 7) & 1) == 1;
						List<Vector3f> vertices = new ArrayList<>();
						List<Vector2f> texCoords = null;
						List<Vector3f> normals = null;
						
						int vertexCount = ((data[offset++] & 0xFF) << 24) | ((data[offset++] & 0xFF) << 16) | ((data[offset++] & 0xFF) << 8) | (data[offset++] & 0xFF);
						
						for(int v = 0; v < vertexCount; v++) {
							
							float x = Float.intBitsToFloat(((data[offset++] & 0xFF) << 24) | ((data[offset++] & 0xFF) << 16) | ((data[offset++] & 0xFF) << 8) | (data[offset++] & 0xFF));
							float y = Float.intBitsToFloat(((data[offset++] & 0xFF) << 24) | ((data[offset++] & 0xFF) << 16) | ((data[offset++] & 0xFF) << 8) | (data[offset++] & 0xFF));
							float z = Float.intBitsToFloat(((data[offset++] & 0xFF) << 24) | ((data[offset++] & 0xFF) << 16) | ((data[offset++] & 0xFF) << 8) | (data[offset++] & 0xFF));
							vertices.add(new Vector3f(x, y, z));
						}
						
						int indexCount = ((data[offset++] & 0xFF) << 24) | ((data[offset++] & 0xFF) << 16) | ((data[offset++] & 0xFF) << 8) | (data[offset++] & 0xFF);
						int[] indices = new int[indexCount];
						
						for(int i = 0; i < indexCount; i++) {
							
							indices[i] = ((data[offset++] & 0xFF) << 24) | ((data[offset++] & 0xFF) << 16) | ((data[offset++] & 0xFF) << 8) | (data[offset++] & 0xFF);
						}
						
						if(hasTexCoords) {
							
							texCoords = new ArrayList<>();
							int texCoordCount = ((data[offset++] & 0xFF) << 24) | ((data[offset++] & 0xFF) << 16) | ((data[offset++] & 0xFF) << 8) | (data[offset++] & 0xFF);
							
							for(int vt = 0; vt < texCoordCount; vt++) {
								
								float x = Float.intBitsToFloat(((data[offset++] & 0xFF) << 24) | ((data[offset++] & 0xFF) << 16) | ((data[offset++] & 0xFF) << 8) | (data[offset++] & 0xFF));
								float y = Float.intBitsToFloat(((data[offset++] & 0xFF) << 24) | ((data[offset++] & 0xFF) << 16) | ((data[offset++] & 0xFF) << 8) | (data[offset++] & 0xFF));
								texCoords.add(new Vector2f(x, y));
							}
						}
						
						if(hasNormals) {
							
							normals = new ArrayList<>();
							int normalCount = ((data[offset++] & 0xFF) << 24) | ((data[offset++] & 0xFF) << 16) | ((data[offset++] & 0xFF) << 8) | (data[offset++] & 0xFF);

							// FIXME
							// I just suddenly run out of bytes even though it doesn't make sense.
							// All of the counts are correct and nowhere is the offset incremented wrong.
							
							for(int vn = 0; vn < normalCount; vn++) {
								
								float x = Float.intBitsToFloat(((data[offset++] & 0xFF) << 24) | ((data[offset++] & 0xFF) << 16) | ((data[offset++] & 0xFF) << 8) | (data[offset++] & 0xFF));
								float y = Float.intBitsToFloat(((data[offset++] & 0xFF) << 24) | ((data[offset++] & 0xFF) << 16) | ((data[offset++] & 0xFF) << 8) | (data[offset++] & 0xFF));
								float z = Float.intBitsToFloat(((data[offset++] & 0xFF) << 24) | ((data[offset++] & 0xFF) << 16) | ((data[offset++] & 0xFF) << 8) | (data[offset++] & 0xFF));
								normals.add(new Vector3f(x, y, z));
							}
							
						} else if(generateNormals) {
							
							normals = new ArrayList<>();
							
							for(int index = 0; index < vertices.size(); index++) {
								
								normals.add(new Vector3f());
							}
							
							for(int index = 0; index < indexCount; index += 3) {
								
								Vector3f vert1 = vertices.get(indices[index]);
								Vector3f vert2 = vertices.get(indices[index + 1]);
								Vector3f vert3 = vertices.get(indices[index + 2]);
								
								Vector3f v1 = new Vector3f(vert2);
								v1.sub(vert1);
								
								Vector3f v2 = new Vector3f(vert3);
								v2.sub(vert1);
								v2.cross(v2, v1);
								v2.normalize();
								
								normals.get(indices[index]).add(v2);
								normals.get(indices[index + 1]).add(v2);
								normals.get(indices[index + 2]).add(v2);
							}
							
							for(Vector3f normal : normals) {
								
								normal.normalize();
								normal.negate();
							}
						}
						
						for(Vector3f vertex : vertices) {
							
							out.write(String.format("v %s %s %s\n", vertex.x, vertex.y, vertex.z).getBytes());
						}
						
						if(hasTexCoords) {
							
							for(Vector2f texCoord : texCoords) {
								
								out.write(String.format("vt %s %s\n", texCoord.x, texCoord.y).getBytes());
							}
						}
						
						if(hasNormals) {
							
							for(Vector3f normal : normals) {
								
								out.write(String.format("vn %s %s %s\n", normal.x, normal.y, normal.z).getBytes());
							}
						}
						
						if(!hasTexCoords && !hasNormals) {
							
							for(int index = 0; index < indices.length; index += 3) {

								out.write(String.format("f %s %s %s\n", index, index + 1, index + 2).getBytes());
							}
							
						} else if(!hasTexCoords && hasNormals) {
							
							for(int index = 0; index < indices.length; index += 3) {

								int i2 = index + 1;
								int i3 = index + 3;
								out.write(String.format("f %s//%s %s//%s %s//%s\n", index, index, i2, i2, i3, i3).getBytes());
							}
							
						} else if(hasTexCoords && !hasNormals) {
							
							for(int index = 0; index < indices.length; index += 3) {
								
								int i2 = index + 1;
								int i3 = index + 3;
								out.write(String.format("f %s/%s %s/%s %s/%s\n", index, index, i2, i2, i3, i3).getBytes());
							}
							
						} else if(hasTexCoords && hasNormals) {
							
							for(int index = 0; index < indices.length; index += 3) {
								
								int i2 = index + 1;
								int i3 = index + 3;
								out.write(String.format("f %s/%s/%s %s/%s/%s %s/%s/%s\n", index, index, index, i2, i2, i2, i3, i3, i3).getBytes());
							}
						}
						
					} else {
						
						throw new IOException("Not a XMESH file!");
					}
				}
				
			} else {
				
				// TODO
			}
		}
	}
}
