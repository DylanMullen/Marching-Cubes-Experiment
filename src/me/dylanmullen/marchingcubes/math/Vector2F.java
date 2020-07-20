package me.dylanmullen.marchingcubes.math;

public class Vector2F
{

	private float x;
	private float y;

	public Vector2F(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public void add(Vector2F vec)
	{
		this.x = this.x + vec.getX();
		this.y = this.y + vec.getY();
	}

	public void multiply(float mult)
	{
		this.x *= mult;
		this.y *= mult;
	}

	public void divide(float div)
	{
		this.x /= div;
		this.y /= div;
	}

	public float magnitude()
	{
		return (float) Math.sqrt((double) (this.x * this.x) + (double) (this.y * this.y));
	}

	public void normalize()
	{
		float mag = magnitude();
		divide(mag);
	}

	public float dot(Vector2F vec)
	{
		return this.x * vec.getX() + this.y * vec.getY();
	}

	public float getX()
	{
		return x;
	}

	public float getY()
	{
		return y;
	}

}
