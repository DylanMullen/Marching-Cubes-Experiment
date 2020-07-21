package me.dylanmullen.marchingcubes.square;

import me.dylanmullen.marchingcubes.math.Vector2F;

public class Node
{

	private Vector2F position;

	public Node(Vector2F position)
	{
		this.position = position;
	}

	public Vector2F getPosition()
	{
		return position;
	}
}
