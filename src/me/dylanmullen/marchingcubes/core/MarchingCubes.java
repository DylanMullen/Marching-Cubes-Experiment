package me.dylanmullen.marchingcubes.core;

import java.awt.Dimension;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import me.dylanmullen.marchingcubes.window.Window;

public class MarchingCubes implements Runnable
{

	private Window window;

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
		GL11.glClearColor(0f, 0f, 0f, 1f);

		while (!GLFW.glfwWindowShouldClose(this.window.getWindowReference()))
		{
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			GLFW.glfwSwapBuffers(this.window.getWindowReference());

			GLFW.glfwPollEvents();
		}
		running = false;
		stop();
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
