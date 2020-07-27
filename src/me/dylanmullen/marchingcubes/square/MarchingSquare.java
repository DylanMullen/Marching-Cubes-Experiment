package me.dylanmullen.marchingcubes.square;

import org.joml.Vector3f;

public class MarchingSquare
{

	private Vector3f position;
	private ControlNode[] controls;
	private int configuration;

	public MarchingSquare(Vector3f position)
	{
//		System.out.println(position.x + "," + position.z);
		this.position = position;
		this.controls = new ControlNode[4];
		createControlNodes();
	}

	private void createControlNodes()
	{
		// TOP-LEFT
		controls[0] = NodeManager.getInstance().createControlNode(getPosition(), false);
		// TOP-RIGHT
		controls[1] = NodeManager.getInstance().createControlNode(getPosition().add(1, 0, 0), true);
		// BOTTOM-RIGHT
		controls[2] = NodeManager.getInstance().createControlNode(getPosition().add(1, 0, 1), true);
		// BOTTOM-LEFT
		controls[3] = NodeManager.getInstance().createControlNode(getPosition().add(0, 0, 1), false);

	}

	public Node getTopLeft()
	{
		return controls[0];
	}

	public Node getTopMiddle()
	{
		return controls[0].getRight();
	}

	public Node getTopRight()
	{
		return controls[1];
	}

	public Node getRightMiddle()
	{
		return controls[2].getAbove();
	}

	public Node getBottomRight()
	{
		return controls[2];
	}

	public Node getBottomMiddle()
	{
		return controls[3].getRight();
	}

	public Node getBottomLeft()
	{
		return controls[3];
	}

	public Node getLeftMiddle()
	{
		return controls[3].getAbove();
	}

	public void setValues(float tL, float tR, float bR, float bL)
	{
		if (tL > 0)
			setActive(0);
		if (tR > 0)
			setActive(1);
		if (bR > 0)
			setActive(2);
		if (bL > 0)
			setActive(3);
	}

	public void setConfiguration()
	{
		if (isActive(0))
			configuration += 8;
		if (isActive(1))
			configuration += 4;
		if (isActive(2))
			configuration += 2;
		if (isActive(3))
			configuration += 1;
	}

	public void setConfiguration(int config)
	{
		this.configuration = config;
	}

	public int getConfiguration()
	{
		return configuration;
	}

	public void setActive(int position)
	{
		controls[position].setActive();
	}

	public boolean isActive(int position)
	{
		return controls[position].isActive();
	}

	@Override
	public String toString()
	{
		return "MarchingSquare{0:" + controls[0].isActive() + ",1:" + controls[1].isActive() + ",2:"
				+ controls[2].isActive() + ",3:" + controls[3].isActive() + "}";
	}

	public Vector3f getPosition()
	{
		return new Vector3f(position);
	}

	public ControlNode[] getControlNodes()
	{
		return controls;
	}
}
