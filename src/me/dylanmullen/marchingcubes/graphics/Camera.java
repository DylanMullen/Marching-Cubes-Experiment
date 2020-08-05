package me.dylanmullen.marchingcubes.graphics;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import me.dylanmullen.marchingcubes.window.input.KeyboardHandler;

public class Camera
{

	private Vector3f position;
	private Vector3f chunkPosition;

	private Vector2f pitchYaw;
	private KeyboardHandler keyboard;

	private boolean moved;
	private boolean movedChunk;

	public Camera(KeyboardHandler keyboard)
	{
		this.position = new Vector3f(0, 0, 2);
		this.pitchYaw = new Vector2f(0, 0);
		this.chunkPosition = new Vector3f(getChunkCoord(position.x), 0, getChunkCoord(position.z));
		this.keyboard = keyboard;
	}

	public void move(Vector3f vec)
	{
		position.add(vec);
		moved = true;

		checkChunkMoved();
	}

	public void rotate()
	{
		this.pitchYaw.add(0, 0.1f);
	}

	public void update()
	{
		moved = false;
		movedChunk = false;
		handleInputs();
	}

	public void checkChunkMoved()
	{
		float xCoord = getChunkCoord(position.x);
		float zCoord = getChunkCoord(position.z);

		if (chunkPosition.x != xCoord || chunkPosition.y != zCoord)
		{
			chunkPosition.set(xCoord, 0, zCoord);
			movedChunk = true;
		}
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

	public boolean hasMovedChunk()
	{
		return movedChunk;
	}

	public Vector3f getChunkPosition()
	{
		return chunkPosition;
	}

	public int getChunkCoord(float input)
	{
		return (int) (input / 16f) * 16 - (input < 0 ? 16 : 0);
	}

}
