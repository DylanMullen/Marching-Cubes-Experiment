package me.dylanmullen.marchingcubes.terrain;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import me.dylanmullen.marchingcubes.graphics.VAO;

public class Chunk
{

	private Vector3f position;
	private VAO model;

	public Chunk(Vector3f position, VAO model)
	{
		this.position = position;
		this.model = model;
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
}
