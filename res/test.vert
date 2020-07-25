#version 400 core

uniform mat4 transformMat;
uniform mat4 proj_matrix;
in vec3 position;

out vec3 colour;

void main(void){
	gl_Position = transformMat * vec4(position,1.0);
	colour = vec3(position.x+0.5,position.y+0.5,1.0);
}