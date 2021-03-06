package de.ralleytn.engine.caveman.rendering;

import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.*;

import javax.vecmath.Color4f;
import javax.vecmath.Vector3f;

import de.ralleytn.engine.caveman.Entity;
import de.ralleytn.engine.caveman.Game;
import de.ralleytn.engine.caveman.Terrain;
import de.ralleytn.engine.caveman.rendering.geom.Mesh;
import de.ralleytn.engine.caveman.rendering.light.Light;
import de.ralleytn.engine.caveman.rendering.shader.Material;
import de.ralleytn.engine.caveman.rendering.shader.ShaderPipeline;

/**
 * Manages the rendering of 3D graphics.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 30.08.2018/0.3.0
 * @since 30.07.2018/0.1.0
 */
public class Graphics3D {

	private final Game game;
	private ShaderPipeline shaderPipeline;
	private Material material;
	private Mesh lastRenderedMesh;
	private int cullMode;
	
	/**
	 * @param game the {@linkplain Game} instance this manager belongs to
	 * @since 12.08.2018/0.1.0
	 */
	public Graphics3D(Game game) {
		
		this.game = game;
	}
	
	/**
	 * Renders a line.
	 * @param start the start point
	 * @param end the end point
	 * @param lineWidth the line width (default = {@code 1.0F})
	 * @param color the line color
	 * @since 15.08.2018/0.1.0
	 */
	public void renderLine(Vector3f start, Vector3f end, float lineWidth, Color4f color) {
		
		glColor4f(color.x, color.y, color.z, color.w);
		glBegin(GL_LINE_STRIP);
		glLineWidth(lineWidth);
		glVertex3f(start.x, start.y, start.z);
		glVertex3f(end.x, end.y, end.z);
		glEnd();
	}
	
	/**
	 * Renders a mesh.
	 * @param mesh the mesh
	 * @since 10.08.2018/0.1.0
	 */
	public void renderMesh(Mesh mesh) {
		
		VertexArray array = mesh.getVertexArray();

		if(this.lastRenderedMesh != mesh) {
			
			// Is it really necessary to disable?
			// I can save a little bit of performance by reducing the native calls.
			/*if(this.lastRenderedMesh != null) {
				
				VertexArray lastMeshArray = this.lastRenderedMesh.getVertexArray();
				if(this.lastRenderedMesh.hasNormals()) lastMeshArray.disable(2);
				if(this.lastRenderedMesh.hasTextureCoordinates()) lastMeshArray.disable(1);
				lastMeshArray.disable(0);
			}*/
			
			if(this.cullMode != mesh.getCullMode()) {
				
				this.setFaceCulling(mesh.getCullMode());
			}
			
			glBindVertexArray(array.getID());
			glEnableVertexAttribArray(0);
			if(mesh.hasTextureCoordinates()) glEnableVertexAttribArray(1);
			if(mesh.hasNormals()) glEnableVertexAttribArray(2);
			this.lastRenderedMesh = mesh;
		}
		
		glDrawElements(GL_TRIANGLES, mesh.getIndexCount(), GL_UNSIGNED_INT, 0);
	}
	
	/**
	 * Renders a chunk of terrain.
	 * @param terrain the terrain chunk
	 * @since 26.08.2018/0.3.0
	 */
	public void renderTerrain(Terrain terrain) {
		
		Mesh mesh = terrain.getMesh();
		Material material = terrain.getMaterial();
		ShaderPipeline shaderPipeline = terrain.getShaderPipeline();
		
		if(this.shaderPipeline != shaderPipeline) {
			
			this.setShaderPipeline(shaderPipeline);
		}
		
		if(this.shaderPipeline != null) {
			
			this.shaderPipeline.setUniform("projection", this.game.getCamera().getProjectionMatrix());
			this.shaderPipeline.setUniform("transformation", terrain.getTransformation());
			this.shaderPipeline.setUniform("view", this.game.getCamera().getViewMatrix());
			
			if(material != this.material || this.material.hasChanged() || (this.material.getFog() != null && this.material.getFog().hasChanged())) {
				
				this.material = material;
				this.material.applyToShader(this.shaderPipeline);
			}
			
			Light light = this.game.getScene().getSun();
			
			if(light != null) {
				
				this.shaderPipeline.setUniform("lightPos", light.getTranslation());
				this.shaderPipeline.setUniform("lightColor", light.getColor());
			}
		}
		
		this.renderMesh(mesh);
	}
	
	/**
	 * Renders an instance of {@linkplain Entity}.
	 * @param entity the object
	 * @since 26.08.2018/0.3.0
	 */
	public void renderEntity(Entity entity) {
		
		Mesh mesh = entity.getMesh();
		Material material = entity.getMaterial();
		ShaderPipeline shaderPipeline = entity.getShaderPipeline();
		
		if(this.shaderPipeline != shaderPipeline) {
			
			this.setShaderPipeline(shaderPipeline);
		}
		
		if(this.shaderPipeline != null) {
			
			this.shaderPipeline.setUniform("projection", this.game.getCamera().getProjectionMatrix());
			this.shaderPipeline.setUniform("transformation", entity.getTransformation());
			this.shaderPipeline.setUniform("view", this.game.getCamera().getViewMatrix());
			
			if(material != this.material || this.material.hasChanged() || (this.material.getFog() != null && this.material.getFog().hasChanged())) {
				
				this.material = material;
				this.material.applyToShader(this.shaderPipeline);
			}
			
			Light light = this.game.getScene().getSun();
			
			if(light != null) {
				
				this.shaderPipeline.setUniform("lightPos", light.getTranslation());
				this.shaderPipeline.setUniform("lightColor", light.getColor());
			}
		}
		
		this.renderMesh(mesh);
	}
	
	/**
	 * Sets the shader pipeline that is used.
	 * @param shaderPipeline the shader pipeline
	 * @since 12.08.2018/0.1.0
	 */
	public void setShaderPipeline(ShaderPipeline shaderPipeline) {
		
		if(shaderPipeline != null) {
			
			shaderPipeline.bind();
			
		} else {
			
			glUseProgram(0);
		}
		
		this.shaderPipeline = shaderPipeline;
	}
	
	/**
	 * Sets the face culling mode.
	 * @param mode the face culling mode
	 * @since 10.08.2018/0.1.0
	 */
	public void setFaceCulling(int mode) {
		
		if(mode == Mesh.CULLING_BACK) {
			
			glEnable(GL_CULL_FACE);
			glCullFace(GL_BACK);
			
		} else if(mode == Mesh.CULLING_FRONT) {
			
			glEnable(GL_CULL_FACE);
			glCullFace(GL_FRONT);
			
		} else if(mode == Mesh.CULLING_DISABLED) {
			
			glDisable(GL_CULL_FACE);
		}
		
		this.cullMode = mode;
	}
}
