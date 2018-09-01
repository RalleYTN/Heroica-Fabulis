package de.ralleytn.engine.caveman.rendering.shader;

import java.io.File;
import java.io.IOException;

import org.lwjgl.opengl.GL20;

import de.ralleytn.engine.caveman.EngineException;

/**
 * A basic shader pipeline with vertex, geometry and fragment shader. the geometry shader is optional.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 16.08.2018/0.1.0
 * @since 11.08.2018/0.1.0
 */
public class BasicShaderPipeline extends ShaderPipeline {

	protected final File vertexShaderFile;
	protected final File geometryShaderFile;
	protected final File fragmentShaderFile;
	
	/**
	 * @param directory the directory with the shader source code; the directory has to contain at least the files 
	 * 					<code>"{name}_vertex.glsl"</code> and <code>"{name}_fragment.glsl"</code>, <code>"{name}_geometry.glsl"</code> is optional
	 * @param name the shader name (example: {@code "basic_vertex.glsl"} => {@code "basic"})
	 * @throws IOException if one of the shader files could not be read
	 * @throws EngineException if one of the shaders could not be compiled
	 * @since 11.08.2018/0.1.0
	 */
	public BasicShaderPipeline(File directory, String name) throws IOException, EngineException {

		this.vertexShaderFile = new File(directory, String.format("%s_vertex.glsl", name));
		this.geometryShaderFile = new File(directory, String.format("%s_geometry.glsl", name));
		this.fragmentShaderFile = new File(directory, String.format("%s_fragment.glsl", name));

		this.createShaderPipelineFromScratch();
	}
	
	@Override
	public void dispose() {

		this.disposeShader(this.getVertexShader());
		this.disposeShader(this.getGeometryShader());
		this.disposeShader(this.getFragmentShader());
		
		super.dispose();
	}
	
	/**
	 * Builds the shader pipeline from scratch.
	 * @throws IOException if one of the shader files could not be read
	 * @throws EngineException if one of the shaders could not be compiled
	 * @since 11.08.2018/0.1.0
	 */
	private final void createShaderPipelineFromScratch() throws IOException, EngineException {

		this.setVertexShader(createShaderFromFile(Shader.TYPE_VERTEX_SHADER, this.vertexShaderFile));
		this.setGeometryShader(createShaderFromFile(Shader.TYPE_GEOMETRY_SHADER, this.geometryShaderFile));
		this.setFragmentShader(createShaderFromFile(Shader.TYPE_FRAGMENT_SHADER, this.fragmentShaderFile));
		
		this.bindAttribute(0, "inVertex");
		this.bindAttribute(1, "inTexCoord");
		this.bindAttribute(2, "inNormal");
		
		this.link();
	}
	
	/**
	 * Builds a shader from a file.
	 * @param type the shader type
	 * @param file the file
	 * @return the built shader
	 * @throws IOException if the file could not be read
	 * @throws EngineException if the shader could not be compiled
	 * @since 11.08.2018/0.1.0
	 */
	private static final Shader createShaderFromFile(int type, File file) throws IOException, EngineException {
		
		if(file.exists() && file.isFile() && file.canRead()) {
			
			Shader shader = new Shader(type, file);
			shader.load();
			shader.compile();
			return shader;
		}
		
		return null;
	}
	
	/**
	 * Detaches a shader and disposes of it.
	 * @param shader the shader
	 * @since 11.08.2018/0.1.0
	 */
	private void disposeShader(Shader shader) {
		
		if(shader != null) {
			
			GL20.glDetachShader(this.id, shader.getID());
			shader.dispose();
		}
	}
}
