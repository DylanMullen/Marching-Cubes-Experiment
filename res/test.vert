#version 400 core

uniform mat4 projectionMatrix;
uniform mat4 viewMat;
uniform mat4 modelMat;
uniform vec3 chunkColour;

in vec3 position;

out vec3 colour;

float rand(vec2 co){
    return fract(sin(dot(co.xy ,vec2(12.9898,78.233))) * 43758.5453);
}

void main(void){
	gl_Position = projectionMatrix * viewMat * modelMat * vec4(position,1.0);
	float random = rand(vec2(position.xy));
	colour =chunkColour;
}

