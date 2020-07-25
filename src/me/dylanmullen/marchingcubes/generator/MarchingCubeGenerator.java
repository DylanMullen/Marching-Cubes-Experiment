package me.dylanmullen.marchingcubes.generator;

import java.util.ArrayList;
import java.util.List;

import me.dylanmullen.marchingcubes.graphics.VAO;
import me.dylanmullen.marchingcubes.math.Vector2F;
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
		this.points = new float[(width / 20) * (height / 20)];
		this.generator = new NoiseGenerator();
		this.squares = new ArrayList<MarchingSquare>();
	}

	public void generate()
	{
		for (int y = 0; y < height / 20; y++)
			for (int x = 0; x < width / 20; x++)
			{
				points[x + y * (width / 20)] = generator.generateSmoothNoise(x, y);
			}
		setupSquares();
	}

	public VAO generateMesh()
	{
		VAO vao = new VAO();
		float[] points = new float[squares.size() * 3];
		int index = 0;
		for (int i = 0; i < squares.size(); i++)
		{
			MarchingSquare square = squares.get(i);
			Vector2F position = square.getPosition();
			points[index] = position.getX();
			points[index + 1] = position.getY();
			points[index + 2] = 0;
			index += 3;
		}
		vao.bind();
		vao.storeData(0, BufferUtil.toFloatBuffer(points));
		vao.unbind();

		for (int i = 0; i < points.length; i += 3)
		{
			System.out.println(points[i] + ", " + points[i + 1] + ", " + points[i + 2]);
		}
		System.out.println(points.length);
		return vao;
	}

	public void setupSquares()
	{
		for (int y = 0; y < height / 20; y++)
			for (int x = 0; x < width / 20; x++)
			{
				MarchingSquare square = new MarchingSquare(new Vector2F(x * 20f, y * 20f));
				square.setValues(getValue(x, y), getValue(x + 1, y), getValue(x + 1, y + 1), getValue(x, y + 1));
				squares.add(square);
			}
	}

	public float getValue(int x, int y)
	{
		try
		{
			return points[x + y * (width / 20)];
		} catch (ArrayIndexOutOfBoundsException e)
		{
			return -1;
		}
	}

}
