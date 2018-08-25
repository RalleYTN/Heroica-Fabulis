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
uniform sampler2D matOverlay1;
uniform sampler2D matOverlay2;
uniform sampler2D matOverlay3;

uniform vec4 matColor;

uniform vec3 lightColor;
uniform vec3 fogColor;

uniform bool matAffectedByLight;
uniform bool matSpecular;
uniform bool matUseSpecularMap;
uniform bool matUseColorMap;
uniform bool matUseNormalMap;
uniform bool matUseOverlay1;
uniform bool matUseOverlay2;
uniform bool matUseOverlay3;

uniform float matShineDamping;
uniform float matReflectivity;
uniform float matMinBrightness;
uniform float matBrightness;

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
	color = (matUseColorMap ? texture2D(matColorMap, texCoord) : matColor);
	
	if(matUseOverlay1) color = blend(color, texture2D(matOverlay1, texCoord));
	if(matUseOverlay2) color = blend(color, texture2D(matOverlay2, texCoord));
	if(matUseOverlay3) color = blend(color, texture2D(matOverlay3, texCoord));

	if(matAffectedByLight) {
	
		vec3 normalizedSurfaceNormal = normalize(surfaceNormal);
		vec3 normalizedLightVector = normalize(toLightVector);
		vec3 diffuse = max(dot(normalizedSurfaceNormal, normalizedLightVector), matMinBrightness) * lightColor;
		vec4 diffuseLight = vec4(diffuse, 1.0) * color;
	
		if(matSpecular) {
		
			vec3 normalizedCameraVector = normalize(toCameraVector);
			vec3 lightDirection = -normalizedLightVector;
			vec3 reflectedLightDirection = reflect(lightDirection, normalizedSurfaceNormal);
			vec4 specularLight = vec4(pow(max(dot(reflectedLightDirection, normalizedCameraVector), 0.0), matShineDamping) * (matUseSpecularMap ? texture2D(matSpecularMap, texCoord).r : matReflectivity) * lightColor, 1.0);
			
			color = diffuseLight + specularLight;
		
		} else {
		
			color = diffuseLight;
		}
	}
	
	color *= brightness;
	color = mix(vec4(fogColor, 1.0), color, fogVisibility);
}