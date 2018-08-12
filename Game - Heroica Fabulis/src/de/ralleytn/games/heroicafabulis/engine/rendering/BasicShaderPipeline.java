package de.ralleytn.games.heroicafabulis.engine.rendering;

import java.io.File;
import java.io.IOException;

import org.lwjgl.opengl.GL20;

import de.ralleytn.games.heroicafabulis.engine.EngineException;

/**
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 12.08.2018/0.1.0
 * @since 11.08.2018/0.1.0
 */
public class BasicShaderPipeline extends ShaderPipeline {

	private static final File DIRECTORY_SHADERS = new File("res/shaders");
	
	protected final File vertexShaderFile;
	protected final File geometryShaderFile;
	protected final File fragmentShaderFile;
	
	/**
	 * 
	 * @param name
	 * @throws IOException
	 * @throws EngineException
	 * @since 11.08.2018/0.1.0
	 */
	public BasicShaderPipeline(String name) throws IOException, EngineException {

		this.vertexShaderFile = new File(DIRECTORY_SHADERS, String.format("%s_vertex.glsl", name));
		this.geometryShaderFile = new File(DIRECTORY_SHADERS, String.format("%s_geometry.glsl", name));
		this.fragmentShaderFile = new File(DIRECTORY_SHADERS, String.format("%s_fragment.glsl", name));

		createShaderPipelineFromScratch();
	}
	
	@Override
	public void dispose() {

		this.disposeShader(this.getVertexShader());
		this.disposeShader(this.getGeometryShader());
		this.disposeShader(this.getFragmentShader());
		
		super.dispose();
	}
	
	/**
	 * 
	 * @throws IOException
	 * @throws EngineException
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
	 * 
	 * @param type
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws EngineException
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
	 * 
	 * @param shader
	 * @since 11.08.2018/0.1.0
	 */
	private void disposeShader(Shader shader) {
		
		if(shader != null) {
			
			GL20.glDetachShader(this.id, shader.getID());
			shader.dispose();
		}
	}
}
