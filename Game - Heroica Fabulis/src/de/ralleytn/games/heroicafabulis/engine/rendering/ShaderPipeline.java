package de.ralleytn.games.heroicafabulis.engine.rendering;

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
 * 
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 12.08.2018/0.1.0
 * @since 10.08.2018/0.1.0
 */
public class ShaderPipeline extends LWJGLObject implements Bindable {

	private Shader vertexShader;
	// private Shader tesselationControlShader;
	// private Shader tesselationEvaluationShader;
	private Shader geometryShader;
	private Shader fragmentShader;
	// private Shader computeShader;
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
	 * 
	 * @param vertexShader
	 * @since 11.08.2018/0.1.0
	 */
	public void setVertexShader(Shader vertexShader) {
		
		this.vertexShader = vertexShader;
	}
	
	/**
	 * 
	 * @param geometryShader
	 * @since 11.08.2018/0.1.0
	 */
	public void setGeometryShader(Shader geometryShader) {
		
		this.geometryShader = geometryShader;
	}
	
	/**
	 * 
	 * @param fragmentShader
	 * @since 11.08.2018/0.1.0
	 */
	public void setFragmentShader(Shader fragmentShader) {
		
		this.fragmentShader = fragmentShader;
	}
	
	/**
	 * 
	 * @param uniform
	 * @param value
	 * @since 11.08.2018/0.1.0
	 */
	public void setUniform(String uniform, Matrix4f value) {
		
		glUniformMatrix4fv(this.getUniformLocation(uniform), false, MatrixUtil.toArray(value));
	}

	/**
	 * 
	 * @param uniform
	 * @param value
	 * @since 11.08.2018/0.1.0
	 */
	public void setUniform(String uniform, float value) {

		glUniform1f(this.getUniformLocation(uniform), value);
	}

	/**
	 * 
	 * @param uniform
	 * @param value
	 * @since 11.08.2018/0.1.0
	 */
	public void setUniform(String uniform, Tuple2f value) {
		
		glUniform2f(this.getUniformLocation(uniform), value.x, value.y);
	}

	/**
	 * 
	 * @param uniform
	 * @param x
	 * @param y
	 * @since 11.08.2018/0.1.0
	 */
	public void setUniform(String uniform, float x, float y) {
		
		glUniform2f(this.getUniformLocation(uniform), x, y);
	}

	/**
	 * 
	 * @param uniform
	 * @param value
	 * @since 11.08.2018/0.1.0
	 */
	public void setUniform(String uniform, Tuple3f value) {
		
		glUniform3f(this.getUniformLocation(uniform), value.x, value.y, value.z);
	}

	/**
	 * 
	 * @param uniform
	 * @param x
	 * @param y
	 * @param z
	 * @since 11.08.2018/0.1.0
	 */
	public void setUniform(String uniform, float x, float y, float z) {
		
		glUniform3f(this.getUniformLocation(uniform), x, y, z);
	}

	/**
	 * 
	 * @param uniform
	 * @param value
	 * @since 11.08.2018/0.1.0
	 */
	public void setUniform(String uniform, Tuple4f value) {
		
		glUniform4f(this.getUniformLocation(uniform), value.x, value.y, value.z, value.w);
	}

	/**
	 * 
	 * @param uniform
	 * @param x
	 * @param y
	 * @param z
	 * @param w
	 * @since 11.08.2018/0.1.0
	 */
	public void setUniform(String uniform, float x, float y, float z, float w) {
		
		glUniform4f(this.getUniformLocation(uniform), x, y, z, w);
	}

	/**
	 * 
	 * @param uniform
	 * @param value
	 * @since 11.08.2018/0.1.0
	 */
	public void setUniform(String uniform, int value) {
		
		glUniform1i(this.getUniformLocation(uniform), value);
	}
	
	/**
	 * 
	 * @param uniform
	 * @param value
	 * @since 11.08.2018/0.1.0
	 */
	public void setUniform(String uniform, boolean value) {
		
		glUniform1i(this.getUniformLocation(uniform), value ? GL_TRUE : GL_FALSE);
	}
	
	/**
	 * 
	 * @param index
	 * @param variable
	 * @since 11.08.2018/0.1.0
	 */
	public void bindAttribute(int index, String variable) {
		
		this.inputVarBinding.put(index, variable);
	}
	
	/**
	 * 
	 * @throws EngineException
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
	 * 
	 * @return
	 * @since 11.08.2018/0.1.0
	 */
	public Shader getVertexShader() {
		
		return this.vertexShader;
	}
	
	/**
	 * 
	 * @return
	 * @since 11.08.2018/0.1.0
	 */
	public Shader getGeometryShader() {
		
		return this.geometryShader;
	}
	
	/**
	 * 
	 * @return
	 * @since 11.08.2018/0.1.0
	 */
	public Shader getFragmentShader() {
		
		return this.fragmentShader;
	}
	
	/**
	 * 
	 * @param uniform
	 * @return
	 * @since 11.08.2018/0.1.0
	 */
	protected int getUniformLocation(String uniform) {
		
		return glGetUniformLocation(this.id, uniform);
	}
}
