package de.ralleytn.engine.caveman.rendering.shader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.lwjgl.opengl.GL20.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL40;
import org.lwjgl.opengl.GL43;

import de.ralleytn.engine.caveman.EngineException;
import de.ralleytn.engine.caveman.LWJGLObject;
import de.ralleytn.engine.caveman.io.Loadable;

/**
 * Represents a single shader in OpenGL.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 11.08.2018/0.1.0
 * @since 10.08.2018/0.1.0
 */
public class Shader extends LWJGLObject implements Loadable {

	/** @since 10.08.2018/0.1.0 */ public static final int TYPE_VERTEX_SHADER = GL_VERTEX_SHADER;
	/** @since 10.08.2018/0.1.0 */ public static final int TYPE_FRAGMENT_SHADER = GL_FRAGMENT_SHADER;
	/** @since 10.08.2018/0.1.0 */ public static final int TYPE_COMPUTE_SHADER = GL43.GL_COMPUTE_SHADER;
	/** @since 10.08.2018/0.1.0 */ public static final int TYPE_GEOMETRY_SHADER = GL32.GL_GEOMETRY_SHADER;
	/** @since 10.08.2018/0.1.0 */ public static final int TYPE_TESSELATION_CONTROL_SHADER = GL40.GL_TESS_CONTROL_SHADER;
	/** @since 10.08.2018/0.1.0 */ public static final int TYPE_TESSELATION_EVALUATION_SHADER = GL40.GL_TESS_EVALUATION_SHADER;
	
	private int type;
	private File file;
	
	/**
	 * @param type the type of shader
	 * @param file the file containing the shader source code
	 * @since 10.08.2018/0.1.0
	 */
	public Shader(int type, File file) {
		
		this.type = type;
		this.file = file;
		this.id = glCreateShader(type);
	}
	
	@Override
	public void dispose() {
		
		glDeleteShader(this.id);
		this.disposed = true;
	}
	
	@Override
	public void load() throws IOException {
		
		StringBuilder source = new StringBuilder();
		
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.file), StandardCharsets.UTF_8))) {
			
			String line = null;
			
			while((line = reader.readLine()) != null) {
				
				line = line.trim();
				
				if(!line.isEmpty()) {
					
					source.append(line);
					source.append('\n');
				}
			}
		}
		
		glShaderSource(this.id, source);
	}
	
	/**
	 * Compiles the shader.
	 * @throws EngineException if the shader could not be compiled
	 * @since 10.08.2018/0.1.0
	 */
	public void compile() throws EngineException {
		
		glCompileShader(this.id);
		
		if(glGetShaderi(this.id, GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			
			// Has to be done in this order, otherwise the info log is empty
			EngineException exception = new EngineException("Could not compile shader! " + glGetShaderInfoLog(this.id));
			this.dispose();
			throw exception;
		}
	}
	
	/**
	 * @return the file containing the shader source code
	 * @since 10.08.2018/0.1.0
	 */
	public File getFile() {
		
		return this.file;
	}
	
	/**
	 * @return the shader type
	 * @since 10.08.2018/0.1.0
	 */
	public int getType() {
		
		return this.type;
	}
}
