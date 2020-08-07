package me.dylanmullen.marchingcubes.square;

import org.joml.Vector3f;

import me.dylanmullen.marchingcubes.generator.NoiseGenerator;

public class ControlNode extends Node
{

	private Node[] nodes;
	private boolean active;
	private boolean right;

	private float nodeValue;

	public ControlNode(Vector3f position, boolean right)
	{
		super(position);
		this.nodes = new Node[2];
		this.active = false;
		this.right = right;
		setNodes(right, false);
	}

	public ControlNode(Vector3f position, boolean right, boolean _3d)
	{
		super(position);
		this.nodes = new Node[(_3d ? 3 : 2)];
		this.active = false;
		this.right = right;
		setNodes(right, _3d);
	}

	private void setNodes(boolean right, boolean _3d)
	{
		nodes[0] = NodeManager.getInstance().createNode(getPosition().add(0, 0, -0.5f));
		nodes[1] = NodeManager.getInstance().createNode(getPosition().add((right ? -0.5f : 0.5f), 0, 0));
		if (_3d)
			nodes[2] = NodeManager.getInstance().createNode(getPosition().add(0, 0.5f, 0));
	}

	public Node getAbove()
	{
		return nodes[0];
	}

	public Node getRight()
	{
		return nodes[1];
	}

	public Node getAboveY()
	{
		return nodes[2];
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
		nodes[0].getPosition().set(0, 0, -distance);
		nodes[1].getPosition().set((right ? -distance : distance), 0, 0);
	}

	public void setNodeValue(float value)
	{
		this.nodeValue = value;
		
		if(nodeValue>=-0.02)
		{
			setActive();
		}
	}
	
	public float getNodeValue()
	{
		return nodeValue;
	}

	private float getDistance(float rawValue)
	{
		return 0.5f * Math.abs(rawValue);
	}

}
