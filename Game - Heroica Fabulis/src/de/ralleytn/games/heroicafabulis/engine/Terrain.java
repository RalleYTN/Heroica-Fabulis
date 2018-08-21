package de.ralleytn.games.heroicafabulis.engine;

import javax.vecmath.Matrix4f;
import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;

import de.ralleytn.games.heroicafabulis.engine.io.meshes.MeshData;
import de.ralleytn.games.heroicafabulis.engine.rendering.geom.Mesh;
import de.ralleytn.games.heroicafabulis.engine.rendering.geom.StaticMesh;
import de.ralleytn.games.heroicafabulis.engine.rendering.shader.Material;
import de.ralleytn.games.heroicafabulis.engine.rendering.shader.ShaderPipeline;
import de.ralleytn.games.heroicafabulis.engine.util.MatrixUtil;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 21.08.2018/0.2.0
 * @since 21.08.2018/0.2.0
 */
public class Terrain {

	/**
	 * 
	 * @since 21.08.2018/0.2.0
	 */
	public static final float SIZE = 200;
	
	/**
	 * 
	 * @since 21.08.2018/0.2.0
	 */
	public static final int VERTEX_COUNT = 64;
	
	private Vector2f position;
	private Mesh mesh;
	private Material material;
	private ShaderPipeline shaderPipeline;
	private Matrix4f transformation;
	
	/**
	 * 
	 * @param gridX
	 * @param gridY
	 * @since 21.08.2018/0.2.0
	 */
	public Terrain(int gridX, int gridY) {
		
		this.position = new Vector2f(gridX * SIZE, gridY * SIZE);
		this.mesh = this.generateMesh();
		this.transformation = new Matrix4f();
		this.calcTransformationMatrix();
	}
	
	/**
	 * 
	 * @since 21.08.2018/0.2.0
	 */
	private final void calcTransformationMatrix() {
		
		this.transformation.setIdentity();
		MatrixUtil.translate(new Vector3f(this.position.x, 0, this.position.y), this.transformation);
		MatrixUtil.rotate(0, Engine.AXIS_X, this.transformation);
		MatrixUtil.rotate(0, Engine.AXIS_Y, this.transformation);
		MatrixUtil.rotate(0, Engine.AXIS_Z, this.transformation);
		MatrixUtil.scale(new Vector3f(1, 1, 1), this.transformation);
	}
	
	/**
	 * 
	 * @param shaderPipeline
	 * @since 21.08.2018/0.2.0
	 */
	public void setShaderPipeline(ShaderPipeline shaderPipeline) {
		
		this.shaderPipeline = shaderPipeline;
	}
	
	/**
	 * 
	 * @param material
	 * @since 21.08.2018/0.2.0
	 */
	public void setMaterial(Material material) {
		
		this.material = material;
	}
	
	/**
	 * 
	 * @return
	 * @since 21.08.2018/0.2.0
	 */
	private final StaticMesh generateMesh() {
		
		int count = VERTEX_COUNT * VERTEX_COUNT;
		int vcm1 = VERTEX_COUNT - 1;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] texCoords = new float[count * 2];
		int[] indices = new int[6 * vcm1 * vcm1];
		int pointer = 0;
		
		for(int i = 0; i < VERTEX_COUNT; i++) {
			
			for(int j = 0; j < VERTEX_COUNT; j++) {
				
				int vp3 = pointer * 3;
				int vp31 = vp3 + 1;
				int vp32 = vp3 + 2;
				int vp2 = pointer * 2;
				
				vertices[vp3] = (float)j / (float)vcm1 * SIZE;
				vertices[vp31] = 0;
				vertices[vp32] = (float)i / (float)vcm1 * SIZE;
				
				normals[vp3] = 0;
				normals[vp31] = 1;
				normals[vp32] = 0;
				
				texCoords[vp2] = (float)j / (float)vcm1;
				texCoords[vp2 + 1] = (float)i / (float)vcm1;
				
				pointer++;
			}
		}
		
		pointer = 0;
		
		for(int gz = 0; gz < vcm1; gz++) {
			
			for(int gx = 0; gx < vcm1; gx++) {
				
				int topLeft = (gz * VERTEX_COUNT) + gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz + 1) * VERTEX_COUNT) + gx;
				int bottomRight = bottomLeft + 1;
				
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		
		MeshData data = new MeshData();
		data.setIndices(indices);
		data.setNormals(normals);
		data.setTextureCoordinates(texCoords);
		data.setVertices(vertices);
		
		return new StaticMesh(data);
	}
	
	/**
	 * 
	 * @return
	 * @since 21.08.2018/0.2.0
	 */
	public Mesh getMesh() {
		
		return this.mesh;
	}
	
	/**
	 * 
	 * @return
	 * @since 21.08.2018/0.2.0
	 */
	public Matrix4f getTransformationMatrix() {
		
		return this.transformation;
	}
	
	/**
	 * 
	 * @return
	 * @since 21.08.2018/0.2.0
	 */
	public Material getMaterial() {
		
		return this.material;
	}
	
	/**
	 * 
	 * @return
	 * @since 21.08.2018/0.2.0
	 */
	public ShaderPipeline getShaderPipeline() {
		
		return this.shaderPipeline;
	}
}
