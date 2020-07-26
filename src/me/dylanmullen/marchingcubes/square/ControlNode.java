package me.dylanmullen.marchingcubes.square;

import org.joml.Vector2f;

public class ControlNode extends Node
{

	public static final float DISTANCE = 20f;

	private Node[] nodes;
	private boolean active;

	public ControlNode(Vector2f position)
	{
		super(position);
		this.nodes = new Node[2];
		this.active = false;
		setNodes();
	}

	private void setNodes()
	{
		nodes[0] = NodeManager.getInstance().createNode(getPosition().add(0, DISTANCE / 2));
		nodes[1] = NodeManager.getInstance().createNode(getPosition().add(DISTANCE / 2, 0));
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
