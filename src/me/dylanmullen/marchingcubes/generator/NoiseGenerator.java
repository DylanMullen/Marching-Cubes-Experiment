package me.dylanmullen.marchingcubes.generator;

import java.util.Random;

public class NoiseGenerator
{

	private Random random;
	private long seed;

	public NoiseGenerator()
	{
		this.random = new Random();
		this.seed = System.currentTimeMillis();
	}

	public float generateSmoothNoise(int x, int y)
	{
		float corners = (generateRoughNoise(x - 1, y - 1) + generateRoughNoise(x + 1, y - 1)
				+ generateRoughNoise(x - 1, y + 1) + generateRoughNoise(x + 1, y + 1)) * 16f;
		float mids = (generateRoughNoise(x - 1, y) + generateRoughNoise(x + 1, y) + generateRoughNoise(x, y + 1)
				+ generateRoughNoise(x, y - 1)) / 8f;
		float center = generateRoughNoise(x, y) / 4f;
		return corners + mids + center;
	}

	public float generateRoughNoise(int x, int y)
	{
		random.setSeed(x * 1024 + y * 512 + seed);
		return random.nextFloat();
	}

}
