package me.dylanmullen.marchingcubes.square;

import me.dylanmullen.marchingcubes.math.Vector2F;

public class ControlNode extends Node
{

	public static final float DISTANCE = 20f;

	private Node[] nodes;
	private boolean active;

	public ControlNode(Vector2F position)
	{
		super(position);
		this.nodes = new Node[2];
		this.active = false;
		setNodes();
	}

	private void setNodes()
	{
		nodes[0] = NodeManager.getInstance().createNode(getPosition().clone().add(0, DISTANCE / 2));
		nodes[1] = NodeManager.getInstance().createNode(getPosition().clone().add(DISTANCE / 2, 0));
	}

	public boolean isActive()
	{
		return active;
	}

	public void setActive()
	{
		this.active = !active;
	}

}
