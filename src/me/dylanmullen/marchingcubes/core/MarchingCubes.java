package me.dylanmullen.marchingcubes.core;

import java.awt.Dimension;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import me.dylanmullen.marchingcubes.generator.MarchingCubeGenerator;
import me.dylanmullen.marchingcubes.graphics.Camera;
import me.dylanmullen.marchingcubes.graphics.Shader;
import me.dylanmullen.marchingcubes.graphics.VAO;
import me.dylanmullen.marchingcubes.util.BufferUtil;
import me.dylanmullen.marchingcubes.util.GameObject;
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

	private GameObject object;
	private Shader shader;

	public void run()
	{
		init();
		GL.createCapabilities();

		this.shader = new Shader("test.vert", "test.frag");
		this.shader.bindAttrib(0, "position");

		MarchingCubeGenerator gen = new MarchingCubeGenerator(16, 16);
		gen.generate();

		VAO vao = gen.generateMesh();

		this.object = new GameObject(vao, new Vector3f(0, 0, -1), new Vector3f(0, 0, 0));
		GL11.glClearColor(0f, 0f, 0f, 1f);

		Matrix4f projMat = getProjectionMatrix();
		shader.start();
		shader.setProjectionMatrix(projMat);
		shader.stop();

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

			render();

			GLFW.glfwSwapBuffers(this.window.getWindowReference());
			GLFW.glfwPollEvents();
		}
		running = false;
		stop();
	}

	private void update()
	{
		camera.update();
	}

	private void render()
	{
		Matrix4f trans = translation(object.getPosition(), object.getRotation(), new Vector3f(1, 1, 1));
		shader.start();
		shader.setTransformationMatrix(trans);
		shader.setViewMatrix(camera.getViewMatrix());
		drawVAO(object.getVAO().getCount());
		shader.stop();
	}

	private Matrix4f translation(Vector3f position, Vector3f rotation, Vector3f scale)
	{
		Matrix4f result = new Matrix4f();
		result.identity();
		result.translate(position);
		result.rotate((float) Math.toRadians(rotation.x), new Vector3f(1, 0, 0));
		result.rotate((float) Math.toRadians(rotation.y), new Vector3f(0, 1, 0));
		result.rotate((float) Math.toRadians(rotation.z), new Vector3f(0, 0, 1));
		result.scale(scale);
		return result;
	}

	private Matrix4f getProjectionMatrix()
	{
		Matrix4f matrix = new Matrix4f();

		float FOV = 60f;
		float aspect = (float) WIDTH / (float) HEIGHT;
		float near = 0.1f;
		float far = 100f;

		float yScale = coTangent(toRadians(FOV / 2f));
		float xScale = yScale / aspect;
		float fustrum = far - near;

		matrix.m00(xScale);
		matrix.m11(yScale);
		matrix.m22(-((far + near) / fustrum));
		matrix.m23(-1);
		matrix.m32(-((2 * near * far) / fustrum));
		matrix.m33(0);

		return matrix;
	}

	private float coTangent(float x)
	{
		return (float) (1 / Math.tan(x));
	}

	private float toRadians(float x)
	{
		return (float) Math.toRadians(x);
	}

	private void drawVAO(int length)
	{
		if(window.getKeyboardHandler().isPressed(GLFW.GLFW_KEY_G))
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		else
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);

		GL30.glBindVertexArray(object.getVAO().getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glDrawElements(GL11.GL_TRIANGLES, length, GL11.GL_UNSIGNED_INT, 0);
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
		this.window = new Window("Marching Cubes", new Dimension((int) WIDTH, (int) HEIGHT));
		window.createWindow();

		this.camera = new Camera(window.getKeyboardHandler());
	}

	public boolean isRunning()
	{
		return running;
	}

}
