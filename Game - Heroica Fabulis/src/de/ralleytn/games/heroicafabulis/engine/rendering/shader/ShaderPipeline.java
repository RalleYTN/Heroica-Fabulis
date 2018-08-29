package de.ralleytn.games.heroicafabulis.engine.rendering.shader;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

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
 * @version 29.08.2018/0.3.0
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
	 * Wrapper for setting uniform variables.
	 * This method just checks if the uniform variable exists and if it does, executes the given consumer (which is supposed to actually set the uniform variable).
	 * @param uniform the uniform variable name
	 * @param action the action to be executed when the uniform variable exists
	 * @return {@code true} if the variable exists, {@code false} if it doesn't
	 * @since 29.08.2018/0.3.0
	 */
	private final boolean setUniform(String uniform, Consumer<Integer> action) {
		
		int location = this.getUniformLocation(uniform);
		
		if(location != -1) {
			
			action.accept(location);
			return true;
		}
		
		return false;
	}
	
	/**
	 * Sets a uniform variable.
	 * @param uniform variable name
	 * @param value the variable value
	 * @return {@code true} if the variable exists, {@code false} if it doesn't
	 * @since 11.08.2018/0.1.0
	 */
	public boolean setUniform(String uniform, Matrix4f value) {
		
		return this.setUniform(uniform, location -> glUniformMatrix4fv(location, false, MatrixUtil.toArray4f(value)));
	}

	/**
	 * Sets a uniform variable.
	 * @param uniform variable name
	 * @param value the variable value
	 * @return {@code true} if the variable exists, {@code false} if it doesn't
	 * @since 11.08.2018/0.1.0
	 */
	public boolean setUniform(String uniform, float value) {

		return this.setUniform(uniform, location -> glUniform1f(location, value));
	}

	/**
	 * Sets a uniform variable.
	 * @param uniform variable name
	 * @param value the variable value
	 * @return {@code true} if the variable exists, {@code false} if it doesn't
	 * @since 11.08.2018/0.1.0
	 */
	public boolean setUniform(String uniform, Tuple2f value) {
		
		return this.setUniform(uniform, location -> glUniform2f(location, value.x, value.y));
	}

	/**
	 * Sets a uniform variable.
	 * @param uniform variable name
	 * @param x X component of the variable value
	 * @param y Y component of the variable value
	 * @return {@code true} if the variable exists, {@code false} if it doesn't
	 * @since 11.08.2018/0.1.0
	 */
	public boolean setUniform(String uniform, float x, float y) {
		
		return this.setUniform(uniform, location -> glUniform2f(location, x, y));
	}

	/**
	 * Sets a uniform variable.
	 * @param uniform variable name
	 * @param value the variable value
	 * @return {@code true} if the variable exists, {@code false} if it doesn't
	 * @since 11.08.2018/0.1.0
	 */
	public boolean setUniform(String uniform, Tuple3f value) {
		
		return this.setUniform(uniform, location -> glUniform3f(location, value.x, value.y, value.z));
	}

	/**
	 * Sets a uniform variable.
	 * @param uniform variable name
	 * @param x X component of the variable value
	 * @param y Y component of the variable value
	 * @param z Z component of the variable value
	 * @return {@code true} if the variable exists, {@code false} if it doesn't
	 * @since 11.08.2018/0.1.0
	 */
	public boolean setUniform(String uniform, float x, float y, float z) {
		
		return this.setUniform(uniform, location -> glUniform3f(location, x, y, z));
	}

	/**
	 * Sets a uniform variable.
	 * @param uniform variable name
	 * @param value the variable value
	 * @return {@code true} if the variable exists, {@code false} if it doesn't
	 * @since 11.08.2018/0.1.0
	 */
	public boolean setUniform(String uniform, Tuple4f value) {
		
		return this.setUniform(uniform, location -> glUniform4f(location, value.x, value.y, value.z, value.w));
	}

	/**
	 * Sets a uniform variable.
	 * @param uniform variable name
	 * @param x X component of the variable value
	 * @param y Y component of the variable value
	 * @param z Z component of the variable value
	 * @param w W component of the variable value
	 * @return {@code true} if the variable exists, {@code false} if it doesn't
	 * @since 11.08.2018/0.1.0
	 */
	public boolean setUniform(String uniform, float x, float y, float z, float w) {
		
		return this.setUniform(uniform, location -> glUniform4f(location, x, y, z, w));
	}

	/**
	 * Sets a uniform variable.
	 * @param uniform variable name
	 * @param value the variable value
	 * @return {@code true} if the variable exists, {@code false} if it doesn't
	 * @since 11.08.2018/0.1.0
	 */
	public boolean setUniform(String uniform, int value) {
		
		return this.setUniform(uniform, location -> glUniform1i(location, value));
	}
	
	/**
	 * Sets a uniform variable.
	 * @param uniform variable name
	 * @param value the variable value
	 * @return {@code true} if the variable exists, {@code false} if it doesn't
	 * @since 11.08.2018/0.1.0
	 */
	public boolean setUniform(String uniform, boolean value) {
		
		return this.setUniform(uniform, location -> glUniform1i(location, value ? GL_TRUE : GL_FALSE));
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
