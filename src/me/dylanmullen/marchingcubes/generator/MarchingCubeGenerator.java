package me.dylanmullen.marchingcubes.generator;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

import me.dylanmullen.marchingcubes.graphics.VAO;
import me.dylanmullen.marchingcubes.square.MarchingCube;
import me.dylanmullen.marchingcubes.square.MarchingSquare;
import me.dylanmullen.marchingcubes.square.Node;
import me.dylanmullen.marchingcubes.util.BufferUtil;

public class MarchingCubeGenerator
{

	private NoiseGenerator generator;

	public MarchingCubeGenerator()
	{
		this.generator = new NoiseGenerator();
	}

	public VAO generateSquareMesh(ArrayList<MarchingSquare> squares)
	{
		VAO vao = new VAO();

		ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
		ArrayList<Integer> indices = new ArrayList<Integer>();

		for (MarchingSquare square : squares)
		{
			ArrayList<Node> nodes = (ArrayList<Node>) getNodes(square);
			createTriangles(vertices, indices, nodes);
		}

		vao.bind();
		vao.storeData(0, verticesToFloatArray(vertices));
		vao.storeIndicesBuffer(indicesToIntArray(indices));
		vao.unbind();

		return vao;
	}
	public VAO generateCubeMesh(ArrayList<MarchingCube> cubes)
	{
		VAO vao = new VAO();
		
		ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
		ArrayList<Integer> indices = new ArrayList<Integer>();
		
		for (MarchingCube square : cubes)
		{
			
		}
		
		vao.bind();
		vao.storeData(0, verticesToFloatArray(vertices));
		vao.storeIndicesBuffer(indicesToIntArray(indices));
		vao.unbind();
		
		return vao;
	}

	private List<Node> getNodes(MarchingSquare square)
	{
		List<Node> nodeList = new ArrayList<>();

		/* Credit to Sebastian Lague for doing this work for me. */
		switch (square.getConfiguration())
		{
			case 0:
				break;
			// 1 points:
			case 1:
				addNodes(nodeList, square.getBottomMiddle(), square.getBottomLeft(), square.getLeftMiddle());
				break;
			case 2:
				addNodes(nodeList, square.getRightMiddle(), square.getBottomRight(), square.getBottomMiddle());
				break;
			case 4:
				addNodes(nodeList, square.getTopMiddle(), square.getTopRight(), square.getRightMiddle());
				break;
			case 8:
				addNodes(nodeList, square.getTopLeft(), square.getTopMiddle(), square.getLeftMiddle());
				break;

			// 2 points:
			case 3:
				addNodes(nodeList, square.getRightMiddle(), square.getBottomRight(), square.getBottomLeft(),
						square.getLeftMiddle());
				break;
			case 6:
				addNodes(nodeList, square.getTopMiddle(), square.getTopRight(), square.getBottomRight(),
						square.getBottomMiddle());
				break;
			case 9:
				addNodes(nodeList, square.getTopLeft(), square.getTopMiddle(), square.getBottomMiddle(),
						square.getBottomLeft());
				break;
			case 12:
				addNodes(nodeList, square.getTopLeft(), square.getTopRight(), square.getRightMiddle(),
						square.getLeftMiddle());
				break;
			case 5:
				addNodes(nodeList, square.getTopMiddle(), square.getTopRight(), square.getRightMiddle(),
						square.getBottomMiddle(), square.getBottomLeft(), square.getLeftMiddle());
				break;
			case 10:
				addNodes(nodeList, square.getTopLeft(), square.getTopMiddle(), square.getRightMiddle(),
						square.getBottomRight(), square.getBottomMiddle(), square.getLeftMiddle());
				break;

			// 3 point:
			case 7:
				addNodes(nodeList, square.getTopMiddle(), square.getTopRight(), square.getBottomRight(),
						square.getBottomLeft(), square.getLeftMiddle());
				break;
			case 11:
				addNodes(nodeList, square.getTopLeft(), square.getTopMiddle(), square.getRightMiddle(),
						square.getBottomRight(), square.getBottomLeft());
				break;
			case 13:
				addNodes(nodeList, square.getTopLeft(), square.getTopRight(), square.getRightMiddle(),
						square.getBottomMiddle(), square.getBottomLeft());
				break;
			case 14:
				addNodes(nodeList, square.getTopLeft(), square.getTopRight(), square.getBottomRight(),
						square.getBottomMiddle(), square.getLeftMiddle());
				break;

			// 4 point:
			case 15:
				addNodes(nodeList, square.getTopLeft(), square.getTopRight(), square.getBottomRight(),
						square.getBottomLeft());
				break;
		}

		return nodeList;
	}

	private void addNodes(List<Node> nodeList, Node... nodes)
	{
		for (int i = 0; i < nodes.length; i++)
			nodeList.add(nodes[i]);
	}

	public void createTriangles(ArrayList<Vector3f> vertices, ArrayList<Integer> indices, ArrayList<Node> nodes)
	{
		addVertices(vertices, nodes);

		if (nodes.size() >= 3)
			addIndices(indices, nodes.get(0), nodes.get(1), nodes.get(2));
		if (nodes.size() >= 4)
			addIndices(indices, nodes.get(0), nodes.get(2), nodes.get(3));
		if (nodes.size() >= 5)
			addIndices(indices, nodes.get(0), nodes.get(3), nodes.get(4));
		if (nodes.size() >= 6)
			addIndices(indices, nodes.get(0), nodes.get(4), nodes.get(5));
	}

	public void addVertices(List<Vector3f> vertices, ArrayList<Node> nodes)
	{
		for (int i = 0; i < nodes.size(); i++)
		{
			if (nodes.get(i).getVertexID() != -1)
				continue;
			nodes.get(i).setVertexID(vertices.size());
			vertices.add(nodes.get(i).getPosition());
		}
	}

	public void addIndices(ArrayList<Integer> indices, Node... nodes)
	{
		indices.add(nodes[0].getVertexID());
		indices.add(nodes[1].getVertexID());
		indices.add(nodes[2].getVertexID());
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

	public int[] indicesToIntArray(ArrayList<Integer> indices)
	{
		int[] arr = new int[indices.size()];
		for (int i = 0; i < indices.size(); i++)
			arr[i] = indices.get(i);
		return arr;
	}

	public ArrayList<MarchingSquare> createSquares(Vector3f position, int width, int height)
	{
		ArrayList<MarchingSquare> squares = new ArrayList<MarchingSquare>();

		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
			{
				MarchingSquare square = new MarchingSquare(new Vector3f(x, 0, y));
				int xPos = x + (int) (position.x);
				int zPos = (int) (y + position.z);

				square.setValues(getValue(xPos, zPos), getValue(xPos + 1, zPos), getValue(xPos + 1, zPos + 1),
						getValue(xPos, zPos + 1));

				square.setConfiguration();
				squares.add(square);
			}

		return squares;
	}

	public float getValue(int x, int z)
	{
		return generator.generateNoise(x, z);
	}

}
