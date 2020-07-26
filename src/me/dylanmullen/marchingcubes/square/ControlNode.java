package me.dylanmullen.marchingcubes.square;

import org.joml.Vector3f;

public class ControlNode extends Node
{

	private Node[] nodes;
	private boolean active;

	public ControlNode(Vector3f position, boolean right)
	{
		super(position);
		this.nodes = new Node[2];
		this.active = false;
		setNodes(right);
	}

	private void setNodes(boolean right)
	{
		nodes[0] = NodeManager.getInstance().createNode(getPosition().add(0, 0, -0.5f));
		nodes[1] = NodeManager.getInstance().createNode(getPosition().add((right ? -0.5f : 0.5f), 0, 0));
	}

	public Node getAbove()
	{
		return nodes[0];
	}

	public Node getRight()
	{
		return nodes[1];
	}

	public boolean isActive()
	{
		return active;
	}

	public void setActive()
	{
		this.active = true;
	}

}
