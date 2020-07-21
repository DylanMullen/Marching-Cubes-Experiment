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
				points[x + y * width] = (double) Math.round(generator.generateSmoothNoise(x, y) * 100) / 100;
			}
		print();
	}

	private void print()
	{
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
				System.out.print(points[x + y * width] + "\t" + ((width - 1 == x) ? "\n" : ""));
	}
}
