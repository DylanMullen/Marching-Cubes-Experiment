package me.dylanmullen.marchingcubes.core;

import java.awt.Dimension;
import java.util.ArrayList;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import me.dylanmullen.marchingcubes.generator.MarchingCubeGenerator;
import me.dylanmullen.marchingcubes.graphics.Camera;
import me.dylanmullen.marchingcubes.graphics.Renderer;
import me.dylanmullen.marchingcubes.graphics.Shader;
import me.dylanmullen.marchingcubes.graphics.VAO;
import me.dylanmullen.marchingcubes.square.MarchingCube;
import me.dylanmullen.marchingcubes.terrain.TerrainController;
import me.dylanmullen.marchingcubes.window.Window;

public class App implements Runnable
{

	private final float WIDTH = 1280f;
	private final float HEIGHT = 720f;

	private Window window;

	private Thread thread;
	private boolean running;

	private Camera camera;

	public App()
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

	VAO test;
	ArrayList<MarchingCube> cubes;

	public void run()
	{
		init();
		GL.createCapabilities();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);

		this.shader = new Shader("test.vert", "test.frag");
		this.shader.bindAttrib(0, "position");

		Renderer render = new Renderer(camera);
//		terrainController = new TerrainController(camera);
//
//		terrainController.handlePlayerMovement(camera);
//		;

//		MarchingCube cube = new MarchingCube(new Vector3f(0.5f, 0.5f, 0.5f));
//		cube.setConfiguration();
//
//		this.test = cube.createVAO();

		MarchingCubeGenerator gen = new MarchingCubeGenerator();
		this.cubes = gen.createCubes(new Vector3f(0, 0, 0), 8, 1);

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

			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

			if (window.getInputController().getKeyboard().isPressed(GLFW.GLFW_KEY_X))
				GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
			else
				GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);

//			render.renderTerrain(terrainController.getTerrain());
//			render.drawCube(test);

			render(render);

			GLFW.glfwSwapBuffers(this.window.getWindowReference());
			GLFW.glfwPollEvents();
		}
		running = false;
		stop();
	}

	private void update()
	{
		camera.update();
//		if (camera.hasMoved() && camera.hasMovedChunk())
//			terrainController.handlePlayerMovement(camera);
//		;

	}

	private void render(Renderer render)
	{
		int count =0;
		for(MarchingCube cube : cubes)
		{
			if(cube.getVao()==null)
			{
				count++;
				continue;
			}
			
			render.drawCube(cube);;
			
		}
//		System.out.println(count);
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
