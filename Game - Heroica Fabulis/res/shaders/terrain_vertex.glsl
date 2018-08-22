// Author: Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
// Version: 11.08.2018/0.1.0
// Since: 11.08.2018/0.1.0

#version 460 core

in vec3 inVertex;
in vec2 inTexCoord;
in vec3 inNormal;

out vec2 texCoord;
out vec3 surfaceNormal;
out vec3 toLightVector;
out vec3 toCameraVector;

uniform mat4 transformation;
uniform mat4 projection;
uniform mat4 view;
uniform vec3 lightPos;

void main(void) {

	vec4 world = transformation * vec4(inVertex, 1.0);
	gl_Position = projection * view * world;
	texCoord = inTexCoord;
	surfaceNormal = (transformation * vec4(inNormal, 0.0)).xyz;
	toLightVector = lightPos - world.xyz;
	toCameraVector = (inverse(view) * vec4(0.0, 0.0, 0.0, 1.0)).xyz - world.xyz;
}