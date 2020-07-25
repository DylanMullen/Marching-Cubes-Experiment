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

	public static Matrix4F orthographic(float left, float right, float bottom, float top, float near, float far)
	{
		Matrix4F matrix = new Matrix4F();

		matrix.matrix[0 + 0 * 4] = 2.0f / (right - left);
		matrix.matrix[1 + 1 * 4] = 2.0f / (top - bottom);
		matrix.matrix[2 + 2 * 4] = 2.0f / (near - far);
		matrix.matrix[0 + 3 * 4] = (left + right) / (left - right);
		matrix.matrix[1 + 3 * 4] = (bottom + top) / (bottom - top);
		matrix.matrix[2 + 3 * 4] = (far + near) / (far - near);

		return matrix;
	}

	public static Matrix4F transformation(Vector2F translate)
	{
		Matrix4F matrix = new Matrix4F();
		matrix.translate(translate);
		return matrix;
	}

	public static Matrix4F viewMatrix(float x, float y, float z)
	{
		return new Matrix4F().translate(-x, -y, -z);
	}

	public Matrix4F translate(Vector2F vector)
	{
		matrix[0 + 3 * 4] = vector.getX();
		matrix[1 + 3 * 4] = vector.getY();
		matrix[2 + 3 * 4] = 1f;
		return this;
	}

	public Matrix4F translate(float x, float y, float z)
	{
		matrix[0 + 3 * 4] = x;
		matrix[1 + 3 * 4] = y;
		matrix[2 + 3 * 4] = z;
		return this;
	}

	public Matrix4F rotate(float angle)
	{
		float r = (float) Math.toRadians(angle);
		float cos = (float) Math.cos(r);
		float sin = (float) Math.sin(r);

		matrix[0 + 0 * 4] = cos;
		matrix[1 + 0 * 4] = sin;

		matrix[0 + 1 * 4] = -sin;
		matrix[1 + 1 * 4] = cos;

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
