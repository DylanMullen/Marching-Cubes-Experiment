package me.dylanmullen.marchingcubes.square;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.joml.Vector3f;

import me.dylanmullen.marchingcubes.generator.NoiseGenerator;
import me.dylanmullen.marchingcubes.generator.OpenSimplexNoise;
import me.dylanmullen.marchingcubes.graphics.VAO;
import me.dylanmullen.marchingcubes.util.BufferUtil;
import me.dylanmullen.marchingcubes.util.Table;

public class MarchingCube
{

	private Vector3f position;

	private ControlNode[] nodes;
	private int configuration;

	private Vector3f colour;

	private VAO vao;

	public MarchingCube(Vector3f position)
	{
		this.position = position;
		this.nodes = new ControlNode[8];

		System.out.println(position.x + ":" + position.z);

		this.colour = new Vector3f((float) Math.random(), (float) Math.random(), (float) Math.random());

		init();
	}

	public void generateVAO()
	{
		this.vao = new VAO();
		ArrayList<Vector3f> verts = new ArrayList<Vector3f>();

		if (Table.lookupEdgeTable(configuration) == 0)
			return;

		for (int i = 0; Table.triTable[configuration][i] != -1; i++)
		{
			verts.add(getNode(Table.triTable[configuration][i]).getPosition());
		}

		vao.bind();
		vao.storeVertices(verticesToFloatArray(verts), verts.size());
//		vao.storeIndicesBuffer(indices);
		vao.unbind();
	}

	public FloatBuffer verticesToFloatArray(ArrayList<Vector3f> vertices)
	{
		float[] points = new float[vertices.size() * 3];
		int index = 0;
		for (int i = 0; i < vertices.size(); i++)
		{
			points[index] = vertices.get(i).x;
			points[index + 1] = vertices.get(i).y;
			points[index + 2] = vertices.get(i).z;
			index += 3;
		}
		return BufferUtil.toFloatBuffer(points);
	}

	private void init()
	{
		setupBottomFace();
		setupTopFace();
	}

	private void setupTopFace()
	{
		// TOP Face
		// Top Left
//		getNodePosition(-0.5f, 0.5f, -0.5f)
		nodes[0] = new ControlNode(getPosition().add(0, 1f, 0f), false, true);
		// Top Right
		nodes[1] = new ControlNode(getPosition().add(1f, 1f, 0f), true, true);
		// Bottom Left
		nodes[2] = new ControlNode(getPosition().add(0f, 1f, 1f), false, true);
		// Bottom Right
		nodes[3] = new ControlNode(getPosition().add(1f, 1f, 1f), true, true);
	}

	private void setupBottomFace()
	{
		// Bottom Face
		// Top Left
		nodes[4] = new ControlNode(getPosition().add(0f, 0f, 0f), false, true);
		// Top Right
		nodes[5] = new ControlNode(getPosition().add(1f, 0f, 0f), true, true);
		// Bottom Left
		nodes[6] = new ControlNode(getPosition().add(0f, 0f, 1f), false, true);
		// Bottom Right
		nodes[7] = new ControlNode(getPosition().add(1f, 0f, 1f), true, true);
	}

	// Getters
	public ControlNode getTopLeftTF()
	{
		return nodes[0];
	}

	public ControlNode getTopRightTF()
	{
		return nodes[1];
	}

	public ControlNode getBottomLeftTF()
	{
		return nodes[2];
	}

	public ControlNode getBottomRightTF()
	{
		return nodes[3];
	}

	public ControlNode getTopLeftBF()
	{
		return nodes[4];
	}

	public ControlNode getTopRightBF()
	{
		return nodes[5];
	}

	public ControlNode getBottomLeftBF()
	{
		return nodes[6];
	}

	public ControlNode getBottomRightBF()
	{
		return nodes[7];
	}

	public Node getNode(int pos)
	{
		switch (pos)
		{
			case 0:
				return getTopLeftBF().getRight();
			case 1:
				return getBottomRightBF().getAbove();
			case 2:
				return getBottomLeftBF().getRight();
			case 3:
				return getBottomLeftBF().getAbove();
			case 4:
				return getTopLeftTF().getRight();
			case 5:
				return getBottomRightTF().getAbove();
			case 6:
				return getBottomLeftTF().getRight();
			case 7:
				return getBottomLeftTF().getAbove();
			case 8:
				return getTopLeftBF().getAboveY();
			case 9:
				return getTopRightBF().getAboveY();
			case 10:
				return getBottomRightBF().getAboveY();
			case 11:
				return getBottomLeftBF().getAboveY();
			default:
				return null;
		}
	}

	public int getConfiguration()
	{
		return configuration;
	}

	public void setValues(NoiseGenerator generator)
	{
		for (int i = 0; i < nodes.length; i++)
		{
			ControlNode node = nodes[i];
			Vector3f pos = node.getPosition();
			float value = (float) generator.generateNoise((pos.x + position.x), (pos.z + position.z));
			nodes[i].setNodeValue(value);
		}
		setConfiguration();
		generateVAO();
	}

	public void setConfiguration()
	{
		configuration = calculateIndex(this);
//		configuration=4;
	}

	private int calculateIndex(MarchingCube cube)
	{
		int index = 0;
		if (cube.getTopLeftBF().isActive())
			index |= 1;
		if (cube.getTopRightBF().isActive())
			index |= 2;
		if (cube.getBottomRightBF().isActive())
			index |= 4;
		if (cube.getBottomLeftBF().isActive())
			index |= 8;

		if (cube.getTopLeftTF().isActive())
			index |= 16;
		if (cube.getTopRightTF().isActive())
			index |= 32;
		if (cube.getBottomRightTF().isActive())
			index |= 64;
		if (cube.getBottomLeftTF().isActive())
			index |= 128;
		return index;
	}

	public Vector3f getPosition()
	{
		return new Vector3f(position);
	}

	public Vector3f getNodePosition(float xOffset, float yOffset, float zOffset)
	{
		Vector3f vec = new Vector3f(xOffset + position.x, yOffset + position.y, zOffset + position.z);
		return vec;
	}

	public VAO getVao()
	{
		return vao;
	}

	public Vector3f getColour()
	{
		return colour;
	}
}
