package me.dylanmullen.marchingcubes.square;

import org.joml.Vector3f;

public class MarchingSquare
{

	private Vector3f position;
	private ControlNode[] controls;
	private int configuration;

	public MarchingSquare(Vector3f position)
	{
		this.position = position;
		this.controls = new ControlNode[4];
		createControlNodes();
	}

	private void createControlNodes()
	{
		// TOP-LEFT
		controls[0] = NodeManager.getInstance().createControlNode(getPosition());
		// TOP-RIGHT
		controls[1] = NodeManager.getInstance().createControlNode(getPosition().add(1, 0, 0));
		// BOTTOM-RIGHT
		controls[2] = NodeManager.getInstance().createControlNode(getPosition().add(1, 0, 1));
		// BOTTOM-LEFT
		controls[3] = NodeManager.getInstance().createControlNode(getPosition().add(0, 0, 1));
		
		for(ControlNode node : controls)
		{
			System.out.println(node.getPosition().x + ":"+node.getPosition().y);
		}
		System.out.println();
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

	public int getConfiguration()
	{
		return configuration;
	}

	public void setActive(int position)
	{
		controls[position].setActive();
		controls[position].setPosition(controls[position].getPosition().add(0,1,0));
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
