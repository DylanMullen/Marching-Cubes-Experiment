package me.dylanmullen.marchingcubes.square;

import org.joml.Vector2f;

public class Node
{

	private Vector2f position;

	public Node(Vector2f position)
	{
		this.position = position;
	}

	public Vector2f getPosition()
	{
		return new Vector2f(position.x, position.y);
	}
}
