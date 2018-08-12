package de.ralleytn.games.heroicafabulis.engine.rendering;

import javax.vecmath.Color4f;

import static org.lwjgl.opengl.GL13.*;

/**
 * Represents a material. Materials are used to tell the shader <b>how</b> a mesh should be rendered.
 * Any changes that were made to the material after it was given to the shader will be ignored.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 12.08.2018/0.1.0
 * @since 10.08.2018/0.1.0
 */
public class Material {

	private Texture colorMap;		// 0
	private Texture specularMap;	// 1
	private Texture normalMap;		// 2
	private Texture overlay1;		// 31
	private Texture overlay2;		// 30
	private Texture overlay3;		// 29
	private Color4f color;
	private float reflectivity;
	private float shineDamping;
	private float minBrightness;
	private float brightness;
	private boolean affectedByLight;
	private boolean specular;
	
	/**
	 * @since 10.08.2018/0.1.0
	 */
	public Material() {
		
		this.shineDamping = 1.0F;
		this.brightness = 1.0F;
		this.color = new Color4f(1.0F, 0.0F, 1.0F, 1.0F);	// Purple like in any other game where a mesh has no texture (so it stands out and is easier found)
	}
	
	/**
	 * 
	 * @param colorMap
	 * @since 10.08.2018/0.1.0
	 */
	public void setColorMap(Texture colorMap) {
		
		this.colorMap = colorMap;
	}
	
	/**
	 * 
	 * @param specularMap
	 * @since 10.08.2018/0.1.0
	 */
	public void setSpecularMap(Texture specularMap) {
		
		this.specularMap = specularMap;
	}
	
	/**
	 * 
	 * @param normalMap
	 * @since 10.08.2018/0.1.0
	 */
	public void setNormalMap(Texture normalMap) {
		
		this.normalMap = normalMap;
	}
	
	/**
	 * 
	 * @param overlay1
	 * @since 10.08.2018/0.1.0
	 */
	public void setOverlay1(Texture overlay1) {
		
		this.overlay1 = overlay1;
	}
	
	/**
	 * 
	 * @param overlay2
	 * @since 10.08.2018/0.1.0
	 */
	public void setOverlay2(Texture overlay2) {
		
		this.overlay2 = overlay2;
	}
	
	/**
	 * 
	 * @param overlay3
	 * @since 10.08.2018/0.1.0
	 */
	public void setOverlay3(Texture overlay3) {
		
		this.overlay3 = overlay3;
	}
	
	/**
	 * 
	 * @param color
	 * @since 10.08.2018/0.1.0
	 */
	public void setColor(Color4f color) {
		
		this.color.set(color);;
	}
	
	/**
	 * 
	 * @param reflectivity
	 * @since 10.08.2018/0.1.0
	 */
	public void setReflectivity(float reflectivity) {
		
		this.reflectivity = reflectivity;
	}
	
	/**
	 * 
	 * @param shineDamping
	 * @since 10.08.2018/0.1.0
	 */
	public void setShineDamping(float shineDamping) {
		
		this.shineDamping = shineDamping;
	}
	
	/**
	 * 
	 * @param minBrightness
	 * @since 10.08.2018/0.1.0
	 */
	public void setMinBrightness(float minBrightness) {
		
		this.minBrightness = minBrightness;
	}
	
	/**
	 * 
	 * @param brightness
	 * @since 10.08.2018/0.1.0
	 */
	public void setBrightness(float brightness) {
		
		this.brightness = brightness;
	}
	
	/**
	 * 
	 * @param affectedByLight
	 * @since 10.08.2018/0.1.0
	 */
	public void setAffectedByLight(boolean affectedByLight) {
		
		this.affectedByLight = affectedByLight;
	}
	
	/**
	 * 
	 * @param specular
	 * @since 10.08.2018/0.1.0
	 */
	public void setSpecular(boolean specular) {
		
		this.specular = specular;
	}
	
	/**
	 * 
	 * @return
	 * @since 10.08.2018/0.1.0
	 */
	public Texture getColorMap() {
		
		return this.colorMap;
	}
	
	/**
	 * 
	 * @return
	 * @since 10.08.2018/0.1.0
	 */
	public Texture getSpecularMap() {
		
		return this.specularMap;
	}
	
	/**
	 * 
	 * @return
	 * @since 10.08.2018/0.1.0
	 */
	public Texture getNormalMap() {
		
		return this.normalMap;
	}
	
	/**
	 * 
	 * @return
	 * @since 10.08.2018/0.1.0
	 */
	public Texture getOverlay1() {
		
		return this.overlay1;
	}
	
	/**
	 * 
	 * @return
	 * @since 10.08.2018/0.1.0
	 */
	public Texture getOverlay2() {
		
		return this.overlay2;
	}
	
	/**
	 * 
	 * @return
	 * @since 10.08.2018/0.1.0
	 */
	public Texture getOverlay3() {
		
		return this.overlay3;
	}
	
	/**
	 * 
	 * @return
	 * @since 10.08.2018/0.1.0
	 */
	public Color4f getColor() {
		
		return this.color;
	}
	
	/**
	 * 
	 * @return
	 * @since 10.08.2018/0.1.0
	 */
	public float getReflectivity() {
		
		return this.reflectivity;
	}

	/**
	 * 
	 * @return
	 * @since 10.08.2018/0.1.0
	 */
	public float getShineDamping() {
		
		return this.shineDamping;
	}
	
	/**
	 * 
	 * @return
	 * @since 10.08.2018/0.1.0
	 */
	public float getMinBrightness() {
		
		return this.minBrightness;
	}
	
	/**
	 * 
	 * @return
	 * @since 10.08.2018/0.1.0
	 */
	public float getBrightness() {
		
		return this.brightness;
	}
	
	/**
	 * 
	 * @return
	 * @since 10.08.2018/0.1.0
	 */
	public boolean isAffectedByLight() {
		
		return this.affectedByLight;
	}
	
	/**
	 * 
	 * @return
	 * @since 10.08.2018/0.1.0
	 */
	public boolean isSpecular() {
		
		return this.specular;
	}
	
	/**
	 * 
	 * @param shaderPipeline
	 * @since 12.08.2018/0.1.0
	 */
	final void applyToShader(ShaderPipeline shaderPipeline) {

		shaderPipeline.setUniform("matAffectedByLight", this.affectedByLight);
		shaderPipeline.setUniform("matBrightness", this.brightness);
		shaderPipeline.setUniform("matColor", this.color);
		
		boolean useColorMap = this.colorMap != null;
		boolean useNormalMap = this.normalMap != null;
		boolean useOverlay1 = this.overlay1 != null;
		boolean useOverlay2 = this.overlay2 != null;
		boolean useOverlay3 = this.overlay3 != null;
		
		shaderPipeline.setUniform("matUseColorMap", useColorMap);
		shaderPipeline.setUniform("matUseNormalMap", useNormalMap);
		shaderPipeline.setUniform("matUseOverlay1", useOverlay1);
		shaderPipeline.setUniform("matUseOverlay2", useOverlay2);
		shaderPipeline.setUniform("matUseOverlay3", useOverlay3);
		
		if(useColorMap) {
			
			glActiveTexture(GL_TEXTURE0);
			this.colorMap.bind();
			shaderPipeline.setUniform("matColorMap", 0);
		}
		
		if(useNormalMap) {

			glActiveTexture(GL_TEXTURE2);
			this.normalMap.bind();
			shaderPipeline.setUniform("matNormalMap", 2);
		}
		
		if(useOverlay1) {
			
			glActiveTexture(GL_TEXTURE31);
			this.overlay1.bind();
			shaderPipeline.setUniform("matOverlay1", 31);
		}
		
		if(useOverlay2) {
			
			glActiveTexture(GL_TEXTURE30);
			this.overlay2.bind();
			shaderPipeline.setUniform("matOverlay2", 30);
		}
		
		if(useOverlay3) {
			
			glActiveTexture(GL_TEXTURE29);
			this.overlay3.bind();
			shaderPipeline.setUniform("matOverlay3", 29);
		}
		
		if(this.affectedByLight) {
			
			shaderPipeline.setUniform("matSpecular", this.specular);
			shaderPipeline.setUniform("matMinBrightness", this.minBrightness);
			
			if(this.specular) {
				
				boolean useSpecularMap = this.specularMap != null;
				shaderPipeline.setUniform("matUseSpecularMap", useSpecularMap);
				shaderPipeline.setUniform("matShineDamping", this.shineDamping);
				
				if(useSpecularMap) {
					
					glActiveTexture(GL_TEXTURE1);
					this.specularMap.bind();
					shaderPipeline.setUniform("matSpecularMap", 1);
					
				} else {
					
					shaderPipeline.setUniform("matReflectivity", this.reflectivity);
				}
			}
		}
	}
}
