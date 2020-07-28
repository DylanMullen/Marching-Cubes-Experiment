package me.dylanmullen.marchingcubes.terrain;

import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import me.dylanmullen.marchingcubes.graphics.VAO;

public class Chunk
{

	private Vector3f position;
	private Vector3f colour;
	private VAO model;

	public Chunk(Vector3f position, VAO model)
	{
		this.position = position;
		this.model = model;

		this.colour = new Vector3f((float) Math.random(), (float) Math.random(), (float) Math.random());
	}

	public Matrix4f getModelMatrix()
	{
		Matrix4f result = new Matrix4f();
		result.translate(position);
		return result;
	}

	public Vector3f getPosition()
	{
		return position;
	}

	public VAO getModel()
	{
		return model;
	}
	
	public Vector3f getColour()
	{
		return colour;
	}
}
