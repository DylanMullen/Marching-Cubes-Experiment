package me.dylanmullen.marchingcubes.square;

import org.joml.Vector3f;

public class ControlNode extends Node
{

	private Node[] nodes;
	private boolean active;
	private boolean right;

	public ControlNode(Vector3f position, boolean right)
	{
		super(position);
		this.nodes = new Node[2];
		this.active = false;
		this.right = right;
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

	public void setLength(float rawValue)
	{
		float distance = getDistance(rawValue);
		System.out.println(distance);
		nodes[0].getPosition().set(0, 0, -distance);
		nodes[1].getPosition().set((right ? -distance : distance), 0, 0);
	}

	private float getDistance(float rawValue)
	{
		return 0.5f * Math.abs(rawValue);
	}

}
