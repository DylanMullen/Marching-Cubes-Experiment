package me.dylanmullen.marchingcubes.square;

import org.joml.Vector3f;

public class ControlNode extends Node
{

	private Node[] nodes;
	private boolean active;

	public ControlNode(Vector3f position)
	{
		super(position);
		this.nodes = new Node[2];
		this.active = false;
		setNodes();
	}

	private void setNodes()
	{
		nodes[0] = NodeManager.getInstance().createNode(getPosition().add(0, 0.5f, 0));
		nodes[1] = NodeManager.getInstance().createNode(getPosition().add(0.5f, 0, 0));
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
