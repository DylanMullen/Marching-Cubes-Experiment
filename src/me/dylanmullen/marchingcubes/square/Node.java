package me.dylanmullen.marchingcubes.square;

import org.joml.Vector3f;

public class Node
{

	private Vector3f position;
	private int vertexID;

	public Node(Vector3f position)
	{
		this.position = position;
		this.vertexID = -1;
	}

	public Vector3f getPosition()
	{
		return new Vector3f(position.x, position.y, position.z);
	}
	
	public void setPosition(Vector3f pos)
	{
		this.position=pos;
	}

	public int getVertexID()
	{
		return vertexID;
	}

	public void setVertexID(int vertexID)
	{
		this.vertexID = vertexID;
	}
}
