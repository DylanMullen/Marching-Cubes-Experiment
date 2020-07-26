package me.dylanmullen.marchingcubes.graphics;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import me.dylanmullen.marchingcubes.window.KeyboardHandler;

public class Camera
{

	private Vector3f position;
	private KeyboardHandler keyboard;

	public Camera(KeyboardHandler keyboard)
	{
		this.position = new Vector3f(0, 0, 0);
		this.keyboard = keyboard;
	}

	public void move(Vector3f vec)
	{
		position.add(vec);
	}

	public void update()
	{
		handleInputs();
	}

	private void handleInputs()
	{
		if (keyboard.isPressed(GLFW.GLFW_KEY_SPACE))
			move(new Vector3f(0f, 0.1f, 0f));
		if (keyboard.isPressed(GLFW.GLFW_KEY_LEFT_SHIFT))
			move(new Vector3f(0f, -0.1f, 0f));
		if (keyboard.isPressed(GLFW.GLFW_KEY_D))
			move(new Vector3f(0.1f, 0f, 0f));
		if (keyboard.isPressed(GLFW.GLFW_KEY_A))
			move(new Vector3f(-0.1f, 0f, 0f));
		if (keyboard.isPressed(GLFW.GLFW_KEY_S))
			move(new Vector3f(0f, 0f, 0.1f));
		if (keyboard.isPressed(GLFW.GLFW_KEY_W))
			move(new Vector3f(0f, 0f, -0.1f));
	}

	public Matrix4f getViewMatrix()
	{
		Matrix4f matrix = new Matrix4f();
		matrix.identity();
		matrix.translate(-position.x, -position.y, -position.z);
		return matrix;
	}

}
