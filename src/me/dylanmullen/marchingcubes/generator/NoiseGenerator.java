package me.dylanmullen.marchingcubes.generator;

import java.util.Random;

public class NoiseGenerator
{

	private final int OCTAVES = 4;

	private Random random;
	private long seed;

	public NoiseGenerator()
	{
		this.random = new Random();
		this.seed = random.nextInt(100000);
	}

	public float generateSmoothNoise(int x, int y, int z)
	{
		float corners = (generateRoughNoise(x - 1, y, z - 1) + generateRoughNoise(x + 1, y, z - 1)
				+ generateRoughNoise(x - 1, y, z + 1) + generateRoughNoise(x + 1, y, z + 1)) * 16f;
		float mids = (generateRoughNoise(x - 1, y, z) + generateRoughNoise(x + 1, y, z)
				+ generateRoughNoise(x, y, z + 1) + generateRoughNoise(x, y, z - 1)) / 8f;
		float center = generateRoughNoise(x, y, z) / 4f;
		return (corners + mids + center) / 100f;
	}

	public float generateNoise(float x, float z)
	{
		float total = 0;
		float d = (float) Math.pow(2, 2);

		for (int i = 0; i < 3; i++)
		{
			float freq = (float) (Math.pow(2, i) / d);
			total += getInterpolatedNoise(x * freq, 0, z * freq);
		}

		return total;
	}

	public float generate3DNoise(float x, float y, float z)
	{
		float total = 0;
		float d = (float) Math.pow(2, OCTAVES - 1);

		for (int i = 0; i < OCTAVES; i++)
		{
			float freq = (float) (Math.pow(2, i) / d);
			total += getInterpolatedNoise(x * freq, y * freq, z * freq);
		}
		return total;
	}

	public float getInterpolatedNoise(float x, float y, float z)
	{
		int intX = (int) x;
		int intY = (int) y;
		int intZ = (int) z;

		float fractionX = x - intX;
		float fractionZ = z - intZ;

		float topLeft = generateSmoothNoise(intX, intY, intZ);
		float topRight = generateSmoothNoise(intX + 1, intY, intZ);
		float bottomLeft = generateSmoothNoise(intX, intY, intZ + 1);
		float bottomRight = generateSmoothNoise(intX + 1, intY, intZ + 1);

		float inter1 = interpolate(topLeft, topRight, fractionX);
		float inter2 = interpolate(bottomLeft, bottomRight, fractionX);

		return interpolate(inter1, inter2, fractionZ);
	}

	public float interpolate(float x, float y, float blend)
	{
		double theta = blend * Math.PI;
		float factor = (float) (1f - Math.cos(theta)) * 0.5f;
		return x * (1f - factor) + y * factor;
	}

	public float generateRoughNoise(int x, int y, int z)
	{
		random.setSeed((x * 49632) + (y * 325176) + (z * 55161) + seed);
		return random.nextFloat() * 2f - 1f;
	}
}
