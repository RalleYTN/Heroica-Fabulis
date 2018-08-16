package de.ralleytn.games.heroicafabulis.engine.rendering.shader;

import java.util.HashMap;
import java.util.Map;

import javax.vecmath.Matrix4f;
import javax.vecmath.Tuple2f;
import javax.vecmath.Tuple3f;
import javax.vecmath.Tuple4f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import de.ralleytn.games.heroicafabulis.engine.Bindable;
import de.ralleytn.games.heroicafabulis.engine.EngineException;
import de.ralleytn.games.heroicafabulis.engine.LWJGLObject;
import de.ralleytn.games.heroicafabulis.engine.util.MatrixUtil;

/**
 * Represents a shader pipeline.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 14.08.2018/0.1.0
 * @since 10.08.2018/0.1.0
 */
public class ShaderPipeline extends LWJGLObject implements Bindable {

	private Shader vertexShader;
	private Shader geometryShader;
	private Shader fragmentShader;
	private Map<Integer, String> inputVarBinding;
	
	/**
	 * @since 11.08.2018/0.1.0
	 */
	public ShaderPipeline() {
		
		this.inputVarBinding = new HashMap<>();
		this.id = glCreateProgram();
	}
	
	@Override
	public void dispose() {
		
		glDeleteProgram(this.id);
	}
	
	@Override
	public void bind() {
		
		glUseProgram(this.id);
	}

	@Override
	public void unbind() {
		
		glUseProgram(0);
	}
	
	/**
	 * Sets the vertex shader.
	 * @param vertexShader the vertex shader
	 * @since 11.08.2018/0.1.0
	 */
	public void setVertexShader(Shader vertexShader) {
		
		this.vertexShader = vertexShader;
	}
	
	/**
	 * Sets the geometry shader.
	 * @param geometryShader the geometry shader
	 * @since 11.08.2018/0.1.0
	 */
	public void setGeometryShader(Shader geometryShader) {
		
		this.geometryShader = geometryShader;
	}
	
	/**
	 * Sets the fragment shader.
	 * @param fragmentShader the fragment shader
	 * @since 11.08.2018/0.1.0
	 */
	public void setFragmentShader(Shader fragmentShader) {
		
		this.fragmentShader = fragmentShader;
	}
	
	/**
	 * Sets a uniform variable.
	 * @param uniform variable name
	 * @param value the variable value
	 * @since 11.08.2018/0.1.0
	 */
	public void setUniform(String uniform, Matrix4f value) {
		
		glUniformMatrix4fv(this.getUniformLocation(uniform), false, MatrixUtil.toArray4f(value));
	}

	/**
	 * Sets a uniform variable.
	 * @param uniform variable name
	 * @param value the variable value
	 * @since 11.08.2018/0.1.0
	 */
	public void setUniform(String uniform, float value) {

		glUniform1f(this.getUniformLocation(uniform), value);
	}

	/**
	 * Sets a uniform variable.
	 * @param uniform variable name
	 * @param value the variable value
	 * @since 11.08.2018/0.1.0
	 */
	public void setUniform(String uniform, Tuple2f value) {
		
		glUniform2f(this.getUniformLocation(uniform), value.x, value.y);
	}

	/**
	 * Sets a uniform variable.
	 * @param uniform variable name
	 * @param x X component of the variable value
	 * @param y Y component of the variable value
	 * @since 11.08.2018/0.1.0
	 */
	public void setUniform(String uniform, float x, float y) {
		
		glUniform2f(this.getUniformLocation(uniform), x, y);
	}

	/**
	 * Sets a uniform variable.
	 * @param uniform variable name
	 * @param value the variable value
	 * @since 11.08.2018/0.1.0
	 */
	public void setUniform(String uniform, Tuple3f value) {
		
		glUniform3f(this.getUniformLocation(uniform), value.x, value.y, value.z);
	}

	/**
	 * Sets a uniform variable.
	 * @param uniform variable name
	 * @param x X component of the variable value
	 * @param y Y component of the variable value
	 * @param z Z component of the variable value
	 * @since 11.08.2018/0.1.0
	 */
	public void setUniform(String uniform, float x, float y, float z) {
		
		glUniform3f(this.getUniformLocation(uniform), x, y, z);
	}

	/**
	 * Sets a uniform variable.
	 * @param uniform variable name
	 * @param value the variable value
	 * @since 11.08.2018/0.1.0
	 */
	public void setUniform(String uniform, Tuple4f value) {
		
		glUniform4f(this.getUniformLocation(uniform), value.x, value.y, value.z, value.w);
	}

	/**
	 * Sets a uniform variable.
	 * @param uniform variable name
	 * @param x X component of the variable value
	 * @param y Y component of the variable value
	 * @param z Z component of the variable value
	 * @param w W component of the variable value
	 * @since 11.08.2018/0.1.0
	 */
	public void setUniform(String uniform, float x, float y, float z, float w) {
		
		glUniform4f(this.getUniformLocation(uniform), x, y, z, w);
	}

	/**
	 * Sets a uniform variable.
	 * @param uniform variable name
	 * @param value the variable value
	 * @since 11.08.2018/0.1.0
	 */
	public void setUniform(String uniform, int value) {
		
		glUniform1i(this.getUniformLocation(uniform), value);
	}
	
	/**
	 * Sets a uniform variable.
	 * @param uniform variable name
	 * @param value the variable value
	 * @since 11.08.2018/0.1.0
	 */
	public void setUniform(String uniform, boolean value) {
		
		glUniform1i(this.getUniformLocation(uniform), value ? GL_TRUE : GL_FALSE);
	}
	
	/**
	 * Binds the attribute at the given index to the given variable name
	 * @param index attribute index
	 * @param variable variable name
	 * @since 11.08.2018/0.1.0
	 */
	public void bindAttribute(int index, String variable) {
		
		this.inputVarBinding.put(index, variable);
	}
	
	/**
	 * Links the shader pipeline
	 * @throws EngineException if the shader pipeline could not be linked
	 * @since 11.08.2018/0.1.0
	 */
	public void link() throws EngineException {
		
		glAttachShader(this.id, this.vertexShader.getID());										// Vertex
		if(this.geometryShader != null) glAttachShader(this.id, this.geometryShader.getID());	// Geometry
		glAttachShader(this.id, this.fragmentShader.getID());									// Fragment
		
		this.inputVarBinding.forEach((index, variable) -> glBindAttribLocation(this.id, index, variable));
		
		glLinkProgram(this.id);
		
		if(glGetProgrami(this.id, GL_LINK_STATUS) == GL_FALSE) {
			
			// Has to be done in this order!
			EngineException exception = new EngineException("Could not link shader program! " + glGetProgramInfoLog(this.id));
			this.dispose();
			throw exception;
		}
		
		glValidateProgram(this.id);
	}
	
	/**
	 * @return the vertex shader
	 * @since 11.08.2018/0.1.0
	 */
	public Shader getVertexShader() {
		
		return this.vertexShader;
	}
	
	/**
	 * @return the geometry shader
	 * @since 11.08.2018/0.1.0
	 */
	public Shader getGeometryShader() {
		
		return this.geometryShader;
	}
	
	/**
	 * @return the fragment shader
	 * @since 11.08.2018/0.1.0
	 */
	public Shader getFragmentShader() {
		
		return this.fragmentShader;
	}
	
	/**
	 * @param uniform name of the uniform variable
	 * @return the location of the uniform variable
	 * @since 11.08.2018/0.1.0
	 */
	protected int getUniformLocation(String uniform) {
		
		return glGetUniformLocation(this.id, uniform);
	}
}
