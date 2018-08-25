#version 460 core

in vec3 inVertex;
in vec2 inTexCoord;
in vec3 inNormal;

out vec2 texCoord;
out vec3 surfaceNormal;
out vec3 toLightVector;
out vec3 toCameraVector;
out float fogVisibility;

uniform mat4 transformation;
uniform mat4 projection;
uniform mat4 view;
uniform vec3 lightPos;

uniform bool matUseUpwardsNormals;
uniform bool matAffectedByFog;

uniform float fogDensity;
uniform float fogGradient;

void main(void) {

	vec4 world = transformation * vec4(inVertex, 1.0);
	vec4 relativeCamDist = view * world;
	gl_Position = projection * relativeCamDist;
	texCoord = inTexCoord;
	surfaceNormal = !matUseUpwardsNormals ? (transformation * vec4(inNormal, 0.0)).xyz : vec3(0.0, 1.0, 0.0);
	toLightVector = lightPos - world.xyz;
	toCameraVector = (inverse(view) * vec4(0.0, 0.0, 0.0, 1.0)).xyz - world.xyz;
	fogVisibility = matAffectedByFog ? exp(-pow((length(relativeCamDist.xyz) * fogDensity), fogGradient)) : 1.0;
}