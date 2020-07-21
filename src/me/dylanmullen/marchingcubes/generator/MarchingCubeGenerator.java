package me.dylanmullen.marchingcubes.generator;

public class MarchingCubeGenerator
{

	private int width;
	private int height;
	private double[] points;

	private NoiseGenerator generator;

	public MarchingCubeGenerator(int width, int height)
	{
		this.width = width;
		this.height = height;
		this.points = new double[width * height];
		this.generator = new NoiseGenerator();
	}

	public void generate()
	{
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
			{
				points[x + y * width] = generator.generateSmoothNoise(x, y);
			}
	}

	public boolean isActive(int x, int y)
	{
		return points[x + y * width] >= 0;
	}

}
