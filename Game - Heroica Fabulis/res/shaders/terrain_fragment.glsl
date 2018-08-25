#version 460 core

in vec2 texCoord;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;
in float fogVisibility;

out vec4 color;

uniform sampler2D matColorMap;
uniform sampler2D matSpecularMap;
uniform sampler2D matNormalMap;
uniform sampler2D matBlendMap;
uniform sampler2D matOverlay1;
uniform sampler2D matOverlay2;
uniform sampler2D matOverlay3;

uniform float matColorMapTiling;
uniform float matSpecularMapTiling;
uniform float matNormalMapTiling;
uniform float matBlendMapTiling;
uniform float matOverlay1Tiling;
uniform float matOverlay2Tiling;
uniform float matOverlay3Tiling;

uniform vec4 matColor;

uniform vec3 lightColor;
uniform vec3 fogColor;

uniform bool matAffectedByLight;
uniform bool matSpecular;
uniform bool matUseSpecularMap;
uniform bool matUseColorMap;
uniform bool matUseNormalMap;
uniform bool matUseBlendMap;
uniform bool matUseOverlay1;
uniform bool matUseOverlay2;
uniform bool matUseOverlay3;

uniform float matShineDamping;
uniform float matReflectivity;
uniform float matMinBrightness;
uniform float matBrightness;

const vec4 NOCOLOR = vec4(0.0, 0.0, 0.0, 0.0);

vec4 blend(vec4 bgPixel, vec4 fgPixel) {

	vec3 bgRGB = bgPixel.rgb;
	vec3 fgRGB = fgPixel.rgb;
	
	float bgAlpha = bgPixel.a;
	float fgAlpha = fgPixel.a;
	float inverseFgAlpha = 1.0 - fgAlpha;
	
	if(fgAlpha == 1.0) {
	
		return fgPixel;
	
	} else if(bgAlpha == 1.0) {
	
		return vec4((fgRGB * fgAlpha) + (bgRGB * inverseFgAlpha), 1.0);
	
	} else {
	
		float alpha = fgAlpha + (bgAlpha * inverseFgAlpha);
		return vec4((((fgRGB * fgAlpha) + (bgRGB * bgAlpha * inverseFgAlpha)) / alpha), alpha);
	}
}

void main(void) {

	vec4 brightness = vec4(matBrightness, matBrightness, matBrightness, 1.0);
	color = (matUseColorMap ? texture2D(matColorMap, texCoord * matColorMapTiling) : matColor);
	
	if(matUseBlendMap) {
	
		vec4 blendMapColor = texture2D(matBlendMap, texCoord * matBlendMapTiling);
		float bgAmount = 1 - (blendMapColor.r + blendMapColor.g + blendMapColor.b);
		vec4 colorMapColor = color * bgAmount;
		vec4 overlay1Color = matUseOverlay1 ? texture2D(matOverlay1, texCoord * matOverlay1Tiling) * blendMapColor.r : NOCOLOR;
		vec4 overlay2Color = matUseOverlay2 ? texture2D(matOverlay2, texCoord * matOverlay2Tiling) * blendMapColor.g : NOCOLOR;
		vec4 overlay3Color = matUseOverlay3 ? texture2D(matOverlay3, texCoord * matOverlay3Tiling) * blendMapColor.b : NOCOLOR;
		color = colorMapColor + overlay1Color + overlay2Color + overlay3Color;
	
	} else {
	
		if(matUseOverlay1) color = blend(color, texture2D(matOverlay1, texCoord * matOverlay1Tiling));
		if(matUseOverlay2) color = blend(color, texture2D(matOverlay2, texCoord * matOverlay2Tiling));
		if(matUseOverlay3) color = blend(color, texture2D(matOverlay3, texCoord * matOverlay3Tiling));
	}

	if(matAffectedByLight) {
	
		vec3 normalizedSurfaceNormal = normalize(surfaceNormal);
		vec3 normalizedLightVector = normalize(toLightVector);
		vec3 diffuse = max(dot(normalizedSurfaceNormal, normalizedLightVector), matMinBrightness) * lightColor;
		vec4 diffuseLight = vec4(diffuse, 1.0) * color;
	
		if(matSpecular) {
		
			vec3 normalizedCameraVector = normalize(toCameraVector);
			vec3 lightDirection = -normalizedLightVector;
			vec3 reflectedLightDirection = reflect(lightDirection, normalizedSurfaceNormal);
			vec4 specularLight = vec4(pow(max(dot(reflectedLightDirection, normalizedCameraVector), 0.0), matShineDamping) * (matUseSpecularMap ? texture2D(matSpecularMap, texCoord * matSpecularMapTiling).r : matReflectivity) * lightColor, 1.0);
			
			color = diffuseLight + specularLight;
		
		} else {
		
			color = diffuseLight;
		}
	}
	
	color *= brightness;
	color = mix(vec4(fogColor, 1.0), color, fogVisibility);
}