package me.dylanmullen.marchingcubes.square;

import org.joml.Vector2f;


public class MarchingSquare
{

	private Vector2f position;
	private ControlNode[] controls;
	private int configuration;

	public MarchingSquare(Vector2f position)
	{
		this.position = position;
		this.controls = new ControlNode[4];
		createControlNodes();
	}

	private void createControlNodes()
	{
		controls[0] = NodeManager.getInstance().createControlNode(position);
		controls[1] = NodeManager.getInstance().createControlNode(new Vector2f(position.x, position.y).add(ControlNode.DISTANCE, 0));
		controls[2] = NodeManager.getInstance()
				.createControlNode(new Vector2f(position.x, position.y).add(ControlNode.DISTANCE, ControlNode.DISTANCE));
		controls[3] = NodeManager.getInstance().createControlNode(new Vector2f(position.x, position.y).add(0, ControlNode.DISTANCE));
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
		if(isActive(0))
			configuration+=8;
		if(isActive(1))
			configuration+=4;	
		if(isActive(2))
			configuration+=2;	
		if(isActive(3))
			configuration+=1;	
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

	public Vector2f getPosition()
	{
		return position;
	}

}
