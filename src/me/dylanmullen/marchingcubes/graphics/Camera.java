package me.dylanmullen.marchingcubes.graphics;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import me.dylanmullen.marchingcubes.window.KeyboardHandler;

public class Camera
{

	private Vector3f position;
	private Vector2f pitchYaw;
	private KeyboardHandler keyboard;

	private boolean moved;
	
	public Camera(KeyboardHandler keyboard)
	{
		this.position = new Vector3f(-8, 50, -8);
		this.pitchYaw = new Vector2f(0, 90);
		this.keyboard = keyboard;
	}

	public void move(Vector3f vec)
	{
		position.add(vec);
		moved=true;
	}
	
	public void rotate()
	{
		this.pitchYaw.add(0, 0.1f);
	}

	public void update()
	{
		moved=false;
		handleInputs();
	}

	private void handleInputs()
	{
		if (keyboard.isPressed(GLFW.GLFW_KEY_SPACE))
			move(new Vector3f(0f, 0.5f, 0f));
		if (keyboard.isPressed(GLFW.GLFW_KEY_LEFT_SHIFT))
			move(new Vector3f(0f, -0.5f, 0f));
		if (keyboard.isPressed(GLFW.GLFW_KEY_D))
			move(new Vector3f(0.5f, 0f, 0f));
		if (keyboard.isPressed(GLFW.GLFW_KEY_A))
			move(new Vector3f(-0.5f, 0f, 0f));
		if (keyboard.isPressed(GLFW.GLFW_KEY_S))
			move(new Vector3f(0f, 0f, 0.5f));
		if (keyboard.isPressed(GLFW.GLFW_KEY_W))
			move(new Vector3f(0f, 0f, -0.5f));
		if (keyboard.isPressed(GLFW.GLFW_KEY_LEFT_CONTROL))
			rotate();
	}

	public Matrix4f getViewMatrix()
	{
		Matrix4f matrix = new Matrix4f();
		matrix.identity();
		matrix.rotate((float) Math.toRadians(pitchYaw.y), new Vector3f(1, 0, 0));
		matrix.translate(-position.x, -position.y, -position.z);
		return matrix;
	}
	
	public Vector3f getPosition()
	{
		return position;
	}
	
	public boolean hasMoved()
	{
		return moved;
	}

}
