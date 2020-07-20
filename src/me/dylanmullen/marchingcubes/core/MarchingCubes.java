package me.dylanmullen.marchingcubes.core;

import java.awt.Dimension;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import me.dylanmullen.marchingcubes.graphics.VAO;
import me.dylanmullen.marchingcubes.util.BufferUtil;
import me.dylanmullen.marchingcubes.window.Window;

public class MarchingCubes implements Runnable
{

	private Window window;
	private VAO test;

	private Thread thread;
	private boolean running;

	public MarchingCubes()
	{
		start();
	}

	private void start()
	{
		if (isRunning())
			return;
		this.thread = new Thread(this);
		running = true;
		thread.start();

	}

	public void run()
	{
		init();

		GL.createCapabilities();
		float[] vertices =
		{
				// Left bottom triangle
				-0.5f, 0.5f, 0f, -0.5f, -0.5f, 0f, 0.5f, -0.5f, 0f,
				// Right top triangle
				0.5f, -0.5f, 0f, 0.5f, 0.5f, 0f, -0.5f, 0.5f, 0f
		};

		this.test = new VAO();
		test.bind();
		test.storeData(0, BufferUtil.toFloatBuffer(vertices));
		test.unbind();

		GL11.glClearColor(0f, 0f, 0f, 1f);

		while (!GLFW.glfwWindowShouldClose(this.window.getWindowReference()))
		{
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

			drawVAO(vertices.length / 3);

			GLFW.glfwSwapBuffers(this.window.getWindowReference());
			GLFW.glfwPollEvents();
		}
		running = false;
		stop();
	}

	private void drawVAO(int length)
	{
		GL30.glBindVertexArray(test.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, length);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}

	private void stop()
	{
		if (!isRunning())
			return;
		try
		{
			thread.join();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	float[] pos = new float[]
	{
			-0.5f, 0.5f, -0.5f, 0.5f, -0.5f, 0.5f, -0.5f, 0.5f

	};

	private void init()
	{
		this.window = new Window("Marching Cubes", new Dimension(1280, 720));
		window.createWindow();
	}

	public boolean isRunning()
	{
		return running;
	}

}
