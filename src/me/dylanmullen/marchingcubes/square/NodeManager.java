package me.dylanmullen.marchingcubes.square;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class NodeManager
{

	private static NodeManager instance;

	private List<ControlNode> cachedControlNodes;
	private List<Node> cachedNodes;

	public static NodeManager getInstance()
	{
		if (instance == null)
			instance = new NodeManager();

		return instance;
	}

	public NodeManager()
	{
		this.cachedControlNodes = new ArrayList<ControlNode>();
		this.cachedNodes = new ArrayList<Node>();
	}

	public Node createNode(Vector3f position)
	{
		if (isNode(position))
			return getNode(position);
		Node node = new Node(position);
		cachedNodes.add(node);
		return node;
	}

	public boolean isNode(Vector3f position)
	{
		return getNode(position) != null;
	}

	public Node getNode(Vector3f position)
	{
		try
		{
			return cachedNodes.stream().filter(e -> e.getPosition().equals(position)).findFirst().get();
		} catch (NullPointerException | NoSuchElementException e)
		{
			return null;
		}
	}

	public ControlNode createControlNode(Vector3f position)
	{
		if (isControlNode(position))
			return getControlNode(position);
		ControlNode node = new ControlNode(position);
		cachedControlNodes.add(node);
		return node;
	}

	public boolean isControlNode(Vector3f position)
	{
		return getControlNode(position) != null;
	}

	public ControlNode getControlNode(Vector3f position)
	{
		try
		{
			return cachedControlNodes.stream().filter(e -> e.getPosition().equals(position)).findFirst().get();
		} catch (NullPointerException | NoSuchElementException e)
		{
			return null;
		}
	}
}
