package me.dylanmullen.marchingcubes.generator;

import java.util.Random;

public class NoiseGenerator
{

	private Random random;
	private long seed;

	public NoiseGenerator()
	{
		this.random = new Random();
		this.seed = random.nextInt(100000);
	}

	public void setSeed()
	{
		this.seed = System.currentTimeMillis();
	}

	public float generateSmoothNoise(int x, int y)
	{
		float corners = (generateRoughNoise(x - 1, y - 1) + generateRoughNoise(x + 1, y - 1)
				+ generateRoughNoise(x - 1, y + 1) + generateRoughNoise(x + 1, y + 1)) * 16f;
		float mids = (generateRoughNoise(x - 1, y) + generateRoughNoise(x + 1, y) + generateRoughNoise(x, y + 1)
				+ generateRoughNoise(x, y - 1)) / 8f;
		float center = generateRoughNoise(x, y) / 4f;
		return (corners + mids + center) / 100f;
	}

	public float generateNoise(int x, int z)
	{
		return getInterpolatedNoise(x / 8f, z / 8f);
	}

	public float getInterpolatedNoise(float x, float y)
	{
		int intX = (int) x;
		int intY = (int) y;

		float fractionX = x - intX;
		float fractionY = y - intY;

		float topLeft = generateSmoothNoise(intX, intY);
		float topRight = generateSmoothNoise(intX + 1, intY);
		float bottomLeft = generateSmoothNoise(intX, intY + 1);
		float bottomRight = generateSmoothNoise(intX + 1, intY + 1);

		float inter1 = interpolate(topLeft, topRight, fractionX);
		float inter2 = interpolate(bottomLeft, bottomRight, fractionX);

		return interpolate(inter1, inter2, fractionY);
	}

	public float interpolate(float x, float y, float blend)
	{
		double theta = blend * Math.PI;
		float factor = (float) (1f - Math.cos(theta)) * 0.5f;
		return x * (1f - factor) + y * factor;
	}

	public float generateRoughNoise(int x, int y)
	{
		random.setSeed(x * 49632 + y * 325176 + seed);
		return random.nextFloat() * 2f - 1f;
	}
}
