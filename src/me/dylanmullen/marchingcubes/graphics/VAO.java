package me.dylanmullen.marchingcubes.graphics;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class VAO
{

	private int vaoID;

	private List<Integer> vbos;

	public VAO()
	{
		this.vaoID = glGenVertexArrays();
		this.vbos = new ArrayList<Integer>();
	}

	public void bind()
	{
		glBindVertexArray(vaoID);
	}
	
	public void unbind()
	{
		glBindVertexArray(0);
	}

	public void storeData(int attrib, FloatBuffer buffer)
	{
		int vboID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, buffer,GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attrib, 3, GL11.GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		vbos.add(vboID);
	}

	public int getVaoID()
	{
		return vaoID;
	}
}
