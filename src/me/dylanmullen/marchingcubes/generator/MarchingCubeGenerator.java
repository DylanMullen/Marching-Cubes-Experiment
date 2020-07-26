package me.dylanmullen.marchingcubes.generator;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

import me.dylanmullen.marchingcubes.graphics.VAO;
import me.dylanmullen.marchingcubes.square.MarchingSquare;
import me.dylanmullen.marchingcubes.util.BufferUtil;

public class MarchingCubeGenerator
{

	private int width;
	private int height;
	private float[] points;

	private NoiseGenerator generator;

	private List<MarchingSquare> squares;

	public MarchingCubeGenerator(int width, int height)
	{
		this.width = width;
		this.height = height;
		this.points = new float[width * height];
		this.generator = new NoiseGenerator();
		this.squares = new ArrayList<MarchingSquare>();
	}

	public void generate()
	{
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
			{
				points[x + y * width] = generator.generateSmoothNoise(x, y);
			}
		setupSquares();
	}

	public VAO generateMesh()
	{
		VAO vao = new VAO();

		List<Vector3f> vertices = new ArrayList<Vector3f>();
		List<Integer> indices = new ArrayList<Integer>();

		for (MarchingSquare square : this.squares)
		{
			addVertices(vertices, square);
			addIndices(indices, square);
		}

		vao.bind();
		vao.storeData(0, verticesToFloatArray(vertices));
		vao.storeIndicesBuffer(indicesToIntArray(indices));
		vao.unbind();

		return vao;
	}

	public void addVertices(List<Vector3f> vertices, MarchingSquare square)
	{
		for (int i = 0; i < square.getControlNodes().length; i++)
		{
			if (square.getControlNodes()[i].getVertexID() == -1)
			{
				square.getControlNodes()[i].setVertexID(vertices.size());
				vertices.add(square.getControlNodes()[i].getPosition());
			}
		}
	}

	public void addIndices(List<Integer> indices, MarchingSquare square)
	{
		indices.add(square.getControlNodes()[0].getVertexID());
		indices.add(square.getControlNodes()[1].getVertexID());
		indices.add(square.getControlNodes()[3].getVertexID());

		indices.add(square.getControlNodes()[1].getVertexID());
		indices.add(square.getControlNodes()[2].getVertexID());
		indices.add(square.getControlNodes()[3].getVertexID());

	}

	public FloatBuffer verticesToFloatArray(List<Vector3f> vertices)
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

	public int[] indicesToIntArray(List<Integer> indices)
	{
		int[] arr = new int[indices.size()];
		for (int i = 0; i < indices.size(); i++)
			arr[i] = indices.get(i);
		return arr;
	}

	public void setupSquares()
	{
		for (int x = 0; x < width - 1; x++)
			for (int y = 0; y < height - 1; y++)
			{
				MarchingSquare square = new MarchingSquare(new Vector3f(x, 0, y));
				square.setValues(getValue(x, y), getValue(x + 1, y), getValue(x + 1, y + 1), getValue(x, y + 1));
				square.setConfiguration();
				squares.add(square);
			}
	}

	public float getValue(int x, int y)
	{
		try
		{
			return points[x + y * width];
		} catch (ArrayIndexOutOfBoundsException e)
		{
			return -1;
		}
	}

}
