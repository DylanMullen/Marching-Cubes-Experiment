package me.dylanmullen.marchingcubes.square;

import me.dylanmullen.marchingcubes.math.Vector2F;

public class MarchingSquare
{

	private Vector2F position;
	private ControlNode[] controls;

	public MarchingSquare(Vector2F position)
	{
		this.position = position;
		this.controls = new ControlNode[4];
		createControlNodes();
	}

	private void createControlNodes()
	{
		controls[0] = NodeManager.getInstance().createControlNode(position);
		controls[1] = NodeManager.getInstance().createControlNode(position.clone().add(ControlNode.DISTANCE, 0));
		controls[2] = NodeManager.getInstance()
				.createControlNode(position.clone().add(ControlNode.DISTANCE, ControlNode.DISTANCE));
		controls[3] = NodeManager.getInstance().createControlNode(position.clone().add(0, ControlNode.DISTANCE));
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
	
	public Vector2F getPosition()
	{
		return position;
	}

}
