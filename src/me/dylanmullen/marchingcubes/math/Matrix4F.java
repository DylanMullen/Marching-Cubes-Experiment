package me.dylanmullen.marchingcubes.math;

import java.nio.FloatBuffer;

import me.dylanmullen.marchingcubes.util.BufferUtil;

public class Matrix4F
{

	private float[] matrix;

	public Matrix4F()
	{
		this.matrix = new float[4 * 4];
		identity();
	}

	public void identity()
	{
		matrix[0 + 0 * 4] = 1;
		matrix[1 + 1 * 4] = 1;
		matrix[2 + 2 * 4] = 1;
		matrix[3 + 3 * 4] = 1;
	}

	public Matrix4F orthographic(float left, float right, float bottom, float top, float near, float far)
	{
		matrix[0 + 0 * 4] = 2.0f / (right - left);
		matrix[1 + 1 * 4] = 2.0f / (top - bottom);
		matrix[2 + 2 * 4] = 2.0f / (near - far);
		matrix[0 + 3 * 4] = (left + right) / (left - right);
		matrix[1 + 3 * 4] = (bottom + top) / (bottom - top);
		matrix[2 + 3 * 4] = (far + near) / (far - near);
		
		return this;
	}

	public void print()
	{
		for (int y = 0; y < 4; y++)
		{
			for (int x = 0; x < 4; x++)
				System.out.print(matrix[x + y * 4] + "\t");
			System.out.print("\n");
		}
	}
	
	public FloatBuffer toBuffer()
	{
		return BufferUtil.toFloatBuffer(matrix);
	}
	
}
