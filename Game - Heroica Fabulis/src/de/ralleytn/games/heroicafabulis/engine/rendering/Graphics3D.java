package de.ralleytn.games.heroicafabulis.engine.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import de.ralleytn.games.heroicafabulis.engine.Entity;
import de.ralleytn.games.heroicafabulis.engine.Game;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 12.08.2018/0.1.0
 * @since 30.07.2018/0.1.0
 */
public class Graphics3D {

	private final Game game;
	private ShaderPipeline shaderPipeline;
	private Material material;
	
	public Graphics3D(Game game) {
		
		this.game = game;
	}
	
	/**
	 * 
	 * @param mesh
	 * @since 10.08.2018/0.1.0
	 */
	public void renderMesh(Mesh mesh) {
		
		this.setFaceCulling(mesh.getCullMode());
		VertexArray array = mesh.getVertexArray();
		array.bind();
		array.enable(0);									// Vertices
		if(mesh.hasTextureCoordinates()) array.enable(1);	// TexCoords
		if(mesh.hasNormals()) array.enable(2);				// Normals
		glDrawElements(GL_TRIANGLES, mesh.getIndexCount(), GL_UNSIGNED_INT, 0);
		if(mesh.hasNormals()) array.disable(2);				// Normals
		if(mesh.hasTextureCoordinates()) array.disable(1);	// TexCoords
		array.disable(0);									// Vertices
	}
	
	/**
	 * 
	 * @param entity
	 * @since 12.08.2018/0.1.0
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
			this.shaderPipeline.setUniform("transformation", entity.getTransformationMatrix());
			this.shaderPipeline.setUniform("view", this.game.getCamera().getViewMatrix());
			
			if(material != this.material) {
				
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
	 * 
	 * @param shaderPipeline
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
	 * 
	 * @param mode
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
	}
}
