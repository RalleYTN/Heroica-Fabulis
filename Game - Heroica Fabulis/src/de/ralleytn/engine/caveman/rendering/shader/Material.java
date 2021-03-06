package de.ralleytn.engine.caveman.rendering.shader;

import javax.vecmath.Color4f;

import de.ralleytn.engine.caveman.rendering.Texture;

import static org.lwjgl.opengl.GL13.*;

/**
 * Represents a material. Materials are used to tell the shader <b>how</b> a mesh should be rendered.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 25.08.2018/0.3.0
 * @since 10.08.2018/0.1.0
 */
public class Material {
	
	private static final String UNIFORM_USE_OVERLAY1 = "matUseOverlay1";
	private static final String UNIFORM_USE_OVERLAY2 = "matUseOverlay2";
	private static final String UNIFORM_USE_OVERLAY3 = "matUseOverlay3";
	private static final String UNIFORM_USE_COLOR_MAP = "matUseColorMap";
	private static final String UNIFORM_USE_SPECULAR_MAP = "matUseSpecularMap";
	private static final String UNIFORM_USE_NORMAL_MAP = "matUseNormalMap";
	private static final String UNIFORM_USE_BLEND_MAP = "matUseBlendMap";
	
	private static final String UNIFORM_COLOR_MAP = "matColorMap";
	private static final String UNIFORM_NORMAL_MAP = "matNormalMap";
	private static final String UNIFORM_BLEND_MAP = "matBlendMap";
	private static final String UNIFORM_OVERLAY1 = "matOverlay1";
	private static final String UNIFORM_OVERLAY2 = "matOverlay2";
	private static final String UNIFORM_OVERLAY3 = "matOverlay3";
	private static final String UNIFORM_SPECULAR = "matSpecular";
	
	private static final String UNIFORM_COLOR_MAP_TILING = "matColorMapTiling";
	private static final String UNIFORM_NORMAL_MAP_TILING = "matNormalMapTiling";
	private static final String UNIFORM_BLEND_MAP_TILING = "matBlendMapTiling";
	private static final String UNIFORM_SPECULAR_MAP_TILING = "matSpecularMapTiling";
	private static final String UNIFORM_OVERLAY1_TILING = "matOverlay1Tiling";
	private static final String UNIFORM_OVERLAY2_TILING = "matOverlay2Tiling";
	private static final String UNIFORM_OVERLAY3_TILING = "matOverlay3Tiling";
	
	private static final String UNIFORM_MIN_BRIGHTNESS = "matMinBrightness";
	private static final String UNIFORM_SHINE_DAMPING = "matShineDamping";
	private static final String UNIFORM_SPECULAR_MAP = "matSpecularMap";
	private static final String UNIFORM_REFLECTIVITY = "matReflectivity";
	private static final String UNIFORM_ALLOW_TRANSPARENCY = "matAllowTransparency";
	private static final String UNIFORM_USE_UPWARDS_NORMALS = "matUseUpwardsNormals";
	private static final String UNIFORM_AFFECTED_BY_FOG = "matAffectedByFog";
	private static final String UNIFORM_AFFECTED_BY_LIGHT = "matAffectedByLight";
	private static final String UNIFORM_BRIGHTNESS = "matBrightness";
	private static final String UNIFORM_COLOR = "matColor";
	
	private Texture colorMap;		// 0
	private Texture specularMap;	// 1
	private Texture normalMap;		// 2
	private Texture blendMap;		// 3
	private Texture overlay1;		// 31
	private Texture overlay2;		// 30
	private Texture overlay3;		// 29
	private float colorMapTiling;
	private float specularMapTiling;
	private float normalMapTiling;
	private float blendMapTiling;
	private float overlay1Tiling;
	private float overlay2Tiling;
	private float overlay3Tiling;
	private Color4f color;
	private Fog fog;
	private float reflectivity;
	private float shineDamping;
	private float minBrightness;
	private float brightness;
	private boolean affectedByLight;
	private boolean specular;
	private boolean transparency;
	private boolean upwardsNormals;
	private boolean changed;
	
	/**
	 * @since 10.08.2018/0.1.0
	 */
	public Material() {
		
		this.shineDamping = 1.0F;
		this.brightness = 1.0F;
		this.color = new Color4f(1.0F, 0.0F, 1.0F, 1.0F);
		this.colorMapTiling = 1.0F;
		this.specularMapTiling = 1.0F;
		this.normalMapTiling = 1.0F;
		this.blendMapTiling = 1.0F;
		this.overlay1Tiling = 1.0F;
		this.overlay2Tiling = 1.0F;
		this.overlay3Tiling = 1.0F;
	}
	
	/**
	 * Sets the color map.
	 * @param colorMap the color map
	 * @since 10.08.2018/0.1.0
	 */
	public void setColorMap(Texture colorMap) {
		
		this.colorMap = colorMap;
		this.changed = true;
	}
	
	/**
	 * Sets the specular map.
	 * @param specularMap the specular map
	 * @since 10.08.2018/0.1.0
	 */
	public void setSpecularMap(Texture specularMap) {
		
		this.specularMap = specularMap;
		this.changed = true;
	}
	
	/**
	 * Sets the normal map.
	 * @param normalMap the normal map
	 * @since 10.08.2018/0.1.0
	 */
	public void setNormalMap(Texture normalMap) {
		
		this.normalMap = normalMap;
		this.changed = true;
	}
	
	/**
	 * Sets the blend map.
	 * @param blendMap the blend map
	 * @since 25.08.2018/0.3.0
	 */
	public void setBlendMap(Texture blendMap) {
		
		this.blendMap = blendMap;
		this.changed = true;
	}
	
	/**
	 * Sets the overlay on layer 1.
	 * @param overlay1 overlay texture
	 * @since 10.08.2018/0.1.0
	 */
	public void setOverlay1(Texture overlay1) {
		
		this.overlay1 = overlay1;
		this.changed = true;
	}
	
	/**
	 * Sets the overlay on layer 2.
	 * @param overlay2 overlay texture
	 * @since 10.08.2018/0.1.0
	 */
	public void setOverlay2(Texture overlay2) {
		
		this.overlay2 = overlay2;
		this.changed = true;
	}
	
	/**
	 * Sets the overlay on layer 3.
	 * @param overlay3 overlay texture
	 * @since 10.08.2018/0.1.0
	 */
	public void setOverlay3(Texture overlay3) {
		
		this.overlay3 = overlay3;
		this.changed = true;
	}
	
	/**
	 * Sets the tiling for the color map.
	 * @param tiling the tiling
	 * @since 25.08.2018/0.3.0
	 */
	public void setColorMapTiling(float tiling) {
		
		this.colorMapTiling = tiling;
		this.changed = true;
	}
	
	/**
	 * Sets the tiling for the specular map.
	 * @param tiling the tiling
	 * @since 25.08.2018/0.3.0
	 */
	public void setSpecularMapTiling(float tiling) {
		
		this.specularMapTiling = tiling;
		this.changed = true;
	}
	
	/**
	 * Sets the tiling for the normal map.
	 * @param tiling the tiling
	 * @since 25.08.2018/0.3.0
	 */
	public void setNormalMapTiling(float tiling) {
		
		this.normalMapTiling = tiling;
		this.changed = true;
	}
	
	/**
	 * Sets the tiling for the blend map.
	 * @param tiling the tiling
	 * @since 25.08.2018/0.3.0
	 */
	public void setBlendMapTiling(float tiling) {
		
		this.blendMapTiling = tiling;
		this.changed = true;
	}
	
	/**
	 * Sets the tiling for the first overlay.
	 * @param tiling the tiling
	 * @since 25.08.2018/0.3.0
	 */
	public void setOverlay1Tiling(float tiling) {
		
		this.overlay1Tiling = tiling;
		this.changed = true;
	}
	
	/**
	 * Sets the tiling for the second overlay.
	 * @param tiling the tiling
	 * @since 25.08.2018/0.3.0
	 */
	public void setOverlay2Tiling(float tiling) {
		
		this.overlay2Tiling = tiling;
		this.changed = true;
	}
	
	/**
	 * Sets the tiling for the third overlay.
	 * @param tiling the tiling
	 * @since 25.08.2018/0.3.0
	 */
	public void setOverlay3Tiling(float tiling) {
		
		this.overlay3Tiling = tiling;
		this.changed = true;
	}
	
	/**
	 * Sets the default color for the object.
	 * @param color default color (default = [{@code 1.0F}, {@code 0.0F}, {@code 1.0F}, {@code 1.0F}])
	 * @since 10.08.2018/0.1.0
	 */
	public void setColor(Color4f color) {
		
		this.color.set(color);
		this.changed = true;
	}
	
	/**
	 * Sets the fog.
	 * @param fog the fog
	 * @since 25.08.2018/0.3.0
	 */
	public void setFog(Fog fog) {
		
		this.fog = fog;
		this.changed = true;
	}
	
	/**
	 * Sets the reflectivity of the object.
	 * @param reflectivity the reflectivity (default = {@code 0.0F})
	 * @since 10.08.2018/0.1.0
	 */
	public void setReflectivity(float reflectivity) {
		
		this.reflectivity = reflectivity;
		this.changed = true;
	}
	
	/**
	 * Sets the shine damping.
	 * @param shineDamping the shine damping (default = {@code 1.0F})
	 * @since 10.08.2018/0.1.0
	 */
	public void setShineDamping(float shineDamping) {
		
		this.shineDamping = shineDamping;
		this.changed = true;
	}
	
	/**
	 * Sets the minimum brightness of the object.
	 * @param minBrightness the minimum brightness (default = {@code 0.0F})
	 * @since 10.08.2018/0.1.0
	 */
	public void setMinBrightness(float minBrightness) {
		
		this.minBrightness = minBrightness;
		this.changed = true;
	}
	
	/**
	 * Sets the brightness of the object.
	 * @param brightness the brightness (default = {@code 1.0F})
	 * @since 10.08.2018/0.1.0
	 */
	public void setBrightness(float brightness) {
		
		this.brightness = brightness;
		this.changed = true;
	}
	
	/**
	 * Sets if the object is affected by any of the lightning effects.
	 * @param affectedByLight {@code true} to make the object be affected by light, else {@code false} (default = {@code false})
	 * @since 10.08.2018/0.1.0
	 */
	public void setAffectedByLight(boolean affectedByLight) {
		
		this.affectedByLight = affectedByLight;
		this.changed = true;
	}
	
	/**
	 * Sets whether or not the object is specular. In order for a specular map to work this flag has to be set to {@code true}.
	 * @param specular {@code true} to make the object specular, else {@code false} (default = {@code false})
	 * @since 10.08.2018/0.1.0
	 */
	public void setSpecular(boolean specular) {
		
		this.specular = specular;
		this.changed = true;
	}
	
	/**
	 * Sets if transparency is allowed.
	 * @param transparency {@code true} allow transparency, {@code false} always opaque
	 * @since 24.08.2018/0.3.0
	 */
	public void setTransparent(boolean transparency) {
		
		this.transparency = transparency;
		this.changed = true;
	}
	
	/**
	 * Sets if the model uses upwards normals.
	 * @param upwardsNormals {@code true} upwards normals, {@code false} actual normals
	 * @since 24.08.2018/0.3.0
	 */
	public void setUpwardsNormals(boolean upwardsNormals) {
		
		this.upwardsNormals = upwardsNormals;
		this.changed = true;
	}
	
	/**
	 * @return the color map
	 * @since 10.08.2018/0.1.0
	 */
	public Texture getColorMap() {
		
		return this.colorMap;
	}
	
	/**
	 * @return the specular map
	 * @since 10.08.2018/0.1.0
	 */
	public Texture getSpecularMap() {
		
		return this.specularMap;
	}
	
	/**
	 * @return the normal map
	 * @since 10.08.2018/0.1.0
	 */
	public Texture getNormalMap() {
		
		return this.normalMap;
	}
	
	/**
	 * @return the blend map
	 * @since 25.08.2018/0.3.0
	 */
	public Texture getBlendMap() {
		
		return this.blendMap;
	}
	
	/**
	 * @return the overlay texture for layer 1
	 * @since 10.08.2018/0.1.0
	 */
	public Texture getOverlay1() {
		
		return this.overlay1;
	}
	
	/**
	 * @return the overlay texture for layer 2
	 * @since 10.08.2018/0.1.0
	 */
	public Texture getOverlay2() {
		
		return this.overlay2;
	}
	
	/**
	 * @return the overlay texture for layer 3
	 * @since 10.08.2018/0.1.0
	 */
	public Texture getOverlay3() {
		
		return this.overlay3;
	}
	
	/**
	 * @return the tiling for the color map
	 * @since 25.08.2018/0.3.0
	 */
	public float getColorMapTiling() {
		
		return this.colorMapTiling;
	}
	
	/**
	 * @return the tiling for the specular map
	 * @since 25.08.2018/0.3.0
	 */
	public float getSpecularMapTiling() {
		
		return this.specularMapTiling;
	}
	
	/**
	 * @return the tiling for the normal map
	 * @since 25.08.2018/0.3.0
	 */
	public float getNormalMapTiling() {
		
		return this.normalMapTiling;
	}
	
	/**
	 * @return the tiling for the blend map
	 * @since 25.08.2018/0.3.0
	 */
	public float getBlendMapTiling() {
		
		return this.blendMapTiling;
	}
	
	/**
	 * @return the tiling for the first overlay
	 * @since 25.08.2018/0.3.0
	 */
	public float getOverlay1Tiling() {
		
		return this.overlay1Tiling;
	}
	
	/**
	 * @return the tiling for the second overlay
	 * @since 25.08.2018/0.3.0
	 */
	public float getOverlay2Tiling() {
		
		return this.overlay2Tiling;
	}
	
	/**
	 * @return the tiling for the third overlay
	 * @since 25.08.2018/0.3.0
	 */
	public float getOverlay3Tiling() {
		
		return this.overlay3Tiling;
	}
	
	/**
	 * @return the object color
	 * @since 10.08.2018/0.1.0
	 */
	public Color4f getColor() {
		
		return this.color;
	}
	
	/**
	 * @return the fog
	 * @since 25.08.2018/0.3.0
	 */
	public Fog getFog() {
		
		return this.fog;
	}
	
	/**
	 * @return the reflectivity of the object
	 * @since 10.08.2018/0.1.0
	 */
	public float getReflectivity() {
		
		return this.reflectivity;
	}

	/**
	 * @return the shine damping of the object
	 * @since 10.08.2018/0.1.0
	 */
	public float getShineDamping() {
		
		return this.shineDamping;
	}
	
	/**
	 * @return the minimum brightness of the object
	 * @since 10.08.2018/0.1.0
	 */
	public float getMinBrightness() {
		
		return this.minBrightness;
	}
	
	/**
	 * @return the brightness of the object
	 * @since 10.08.2018/0.1.0
	 */
	public float getBrightness() {
		
		return this.brightness;
	}
	
	/**
	 * @return {@code true} if the object is affected by lightning effects, else {@code false}
	 * @since 10.08.2018/0.1.0
	 */
	public boolean isAffectedByLight() {
		
		return this.affectedByLight;
	}
	
	/**
	 * @return {@code true} if the object is specular, else {@code false}
	 * @since 10.08.2018/0.1.0
	 */
	public boolean isSpecular() {
		
		return this.specular;
	}
	
	/**
	 * @return {@code true} if transparency is allowed, else {@code false}
	 * @since 24.08.2018/0.3.0
	 */
	public boolean isTransparent() {
		
		return this.transparency;
	}
	
	/**
	 * @return {@code true} if upwards normals are used, else {@code false}
	 * @since 24.08.2018/0.3.0
	 */
	public boolean usesUpwardsNormals() {
		
		return this.upwardsNormals;
	}
	
	/**
	 * Applies this material to a shader pipeline.
	 * @param pipeline the shader pipeline
	 * @since 12.08.2018/0.1.0
	 */
	public void applyToShader(ShaderPipeline pipeline) {

		pipeline.setUniform(UNIFORM_AFFECTED_BY_LIGHT, this.affectedByLight);
		pipeline.setUniform(UNIFORM_BRIGHTNESS, this.brightness);
		pipeline.setUniform(UNIFORM_COLOR, this.color);
		pipeline.setUniform(UNIFORM_ALLOW_TRANSPARENCY, this.transparency);

		this.applyTexture(pipeline, this.colorMap,  UNIFORM_USE_COLOR_MAP,  UNIFORM_COLOR_MAP,  GL_TEXTURE0,   0, UNIFORM_COLOR_MAP_TILING,  this.colorMapTiling);
		this.applyTexture(pipeline, this.normalMap, UNIFORM_USE_NORMAL_MAP, UNIFORM_NORMAL_MAP, GL_TEXTURE2,   2, UNIFORM_NORMAL_MAP_TILING, this.normalMapTiling);
		this.applyTexture(pipeline, this.blendMap,  UNIFORM_USE_BLEND_MAP,  UNIFORM_BLEND_MAP,  GL_TEXTURE3,   3, UNIFORM_BLEND_MAP_TILING,  this.blendMapTiling);
		this.applyTexture(pipeline, this.overlay1,  UNIFORM_USE_OVERLAY1,   UNIFORM_OVERLAY1,   GL_TEXTURE31, 31, UNIFORM_OVERLAY1_TILING,   this.overlay1Tiling);
		this.applyTexture(pipeline, this.overlay2,  UNIFORM_USE_OVERLAY2,   UNIFORM_OVERLAY2,   GL_TEXTURE30, 30, UNIFORM_OVERLAY2_TILING,   this.overlay2Tiling);
		this.applyTexture(pipeline, this.overlay3,  UNIFORM_USE_OVERLAY3,   UNIFORM_OVERLAY3,   GL_TEXTURE29, 29, UNIFORM_OVERLAY3_TILING,   this.overlay3Tiling);

		if(this.affectedByLight) {
			
			pipeline.setUniform(UNIFORM_SPECULAR, this.specular);
			pipeline.setUniform(UNIFORM_MIN_BRIGHTNESS, this.minBrightness);
			pipeline.setUniform(UNIFORM_USE_UPWARDS_NORMALS, this.upwardsNormals);
			
			if(this.specular) {
				
				pipeline.setUniform(UNIFORM_SHINE_DAMPING, this.shineDamping);
				this.applyTexture(pipeline, this.specularMap, UNIFORM_USE_SPECULAR_MAP, UNIFORM_SPECULAR_MAP, GL_TEXTURE1, 1, UNIFORM_SPECULAR_MAP_TILING, this.specularMapTiling);

				if(this.specularMap == null) {
					
					pipeline.setUniform(UNIFORM_REFLECTIVITY, this.reflectivity);
				}
			}
		}

		boolean affectedByFog = this.fog != null;
		pipeline.setUniform(UNIFORM_AFFECTED_BY_FOG, affectedByFog);
		
		if(affectedByFog) {
			
			this.fog.applyToShader(pipeline);
		}
		
		this.changed = false;
	}
	
	/**
	 * Applies a single texture to the shader pipeline. Should not be called outside of {@link #applyToShader(ShaderPipeline)}.
	 * @param pipeline the shader pipeline
	 * @param texture the texture
	 * @param uniformUse name of the uniform variable that contains the information if the texture is used or not
	 * @param uniformname of the uniform variable that contains the texture
	 * @param slot the OpenGL texture slot
	 * @param position the slot as number
	 * @param tilingUniform
	 * @param tiling
	 * @since 15.08.2018/0.1.0
	 */
	private final void applyTexture(ShaderPipeline pipeline, Texture texture, String uniformUse, String uniform, int slot, int position, String tilingUniform, float tiling) {
		
		boolean use = (texture != null);
		pipeline.setUniform(uniformUse, use);
		
		if(use) {
			
			glActiveTexture(slot);
			texture.bind();
			pipeline.setUniform(uniform, position);
			pipeline.setUniform(tilingUniform, tiling);
		}
	}
	
	/**
	 * @return {@code true} if the material was changed after applying it to the shader pipeline, else {@code false}
	 * @since 16.08.2018/0.1.0
	 */
	public boolean hasChanged() {
		
		return this.changed;
	}

	/**
	 * 
	 * @return
	 * @since 04.09.2018/0.4.0
	 */
	public Material copy() {
		
		Material material = new Material();
		material.changed = true;
		material.affectedByLight = this.affectedByLight;
		material.blendMap = this.blendMap;
		material.blendMapTiling = this.blendMapTiling;
		material.brightness = this.brightness;
		material.color = this.color;
		material.colorMap = this.colorMap;
		material.colorMapTiling = this.colorMapTiling;
		material.fog = this.fog;
		material.minBrightness = this.minBrightness;
		material.normalMap = this.normalMap;
		material.normalMapTiling = this.normalMapTiling;
		material.overlay1 = this.overlay1;
		material.overlay2 = this.overlay2;
		material.overlay3 = this.overlay3;
		material.overlay1Tiling = this.overlay1Tiling;
		material.overlay2Tiling = this.overlay2Tiling;
		material.overlay3Tiling = this.overlay3Tiling;
		material.reflectivity = this.reflectivity;
		material.shineDamping = this.shineDamping;
		material.specular = this.specular;
		material.specularMap = this.specularMap;
		material.specularMapTiling = this.specularMapTiling;
		material.transparency = this.transparency;
		material.upwardsNormals = this.upwardsNormals;
		
		return material;
	}
}
