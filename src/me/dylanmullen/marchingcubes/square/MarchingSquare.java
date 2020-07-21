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

	public void setActive(int position)
	{
		controls[position].setActive();
	}

}
