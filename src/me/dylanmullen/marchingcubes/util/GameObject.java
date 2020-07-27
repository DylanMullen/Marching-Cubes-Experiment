package me.dylanmullen.marchingcubes.util;

import org.joml.Vector3f;

import me.dylanmullen.marchingcubes.graphics.VAO;

public class GameObject
{

	private VAO vao;
	private Vector3f position;
	private Vector3f rotation;

	public GameObject(VAO vao, Vector3f position, Vector3f rotation)
	{
		this.vao = vao;
		this.position = position;
		this.rotation = rotation;
	}

	public void setVao(VAO vao)
	{
		this.vao = vao;
	}
	
	public void move(Vector3f pos)
	{
		this.position.add(pos);
	}

	public void rotation(Vector3f rot)
	{
		this.rotation.add(rot);
	}

	public VAO getVAO()
	{
		return vao;
	}

	public Vector3f getPosition()
	{
		return position;
	}

	public Vector3f getRotation()
	{
		return rotation;
	}

}
