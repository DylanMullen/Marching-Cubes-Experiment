package me.dylanmullen.marchingcubes.core;

import java.awt.Dimension;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import me.dylanmullen.marchingcubes.generator.MarchingCubeGenerator;
import me.dylanmullen.marchingcubes.graphics.Camera;
import me.dylanmullen.marchingcubes.graphics.Renderer;
import me.dylanmullen.marchingcubes.graphics.Shader;
import me.dylanmullen.marchingcubes.terrain.TerrainController;
import me.dylanmullen.marchingcubes.window.Window;

public class MarchingCubes implements Runnable
{

	private final float WIDTH = 1280f;
	private final float HEIGHT = 720f;

	private Window window;

	private Thread thread;
	private boolean running;

	private Camera camera;

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

	private Shader shader;

	MarchingCubeGenerator gen;
	TerrainController terrainController;

	public void run()
	{
		init();
		GL.createCapabilities();

		this.shader = new Shader("test.vert", "test.frag");
		this.shader.bindAttrib(0, "position");

		Renderer render = new Renderer(camera);
		terrainController = new TerrainController(camera);

		terrainController.handlePlayerMovement(camera);
		;

		long lastTime = System.nanoTime();
		double amountOfTicks = 30.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;

		while (!GLFW.glfwWindowShouldClose(this.window.getWindowReference()))
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			while (delta >= 1)
			{
				update();
				delta--;
			}

			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

			if (window.getInputController().getKeyboard().isPressed(GLFW.GLFW_KEY_X))
				GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
			else
				GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);

			render.renderTerrain(terrainController.getTerrain());

			GLFW.glfwSwapBuffers(this.window.getWindowReference());
			GLFW.glfwPollEvents();
		}
		running = false;
		stop();
	}

	private void update()
	{
		camera.update();
		if (camera.hasMoved() && camera.hasMovedChunk())
			terrainController.handlePlayerMovement(camera);
		;
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
		this.window = new Window("Marching Cubes", new Dimension((int) WIDTH, (int) HEIGHT));
		window.createWindow();

		this.camera = new Camera(window.getInputController().getKeyboard());
	}

	public boolean isRunning()
	{
		return running;
	}

}
