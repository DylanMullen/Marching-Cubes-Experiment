package me.dylanmullen.marchingcubes.graphics;

import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import me.dylanmullen.marchingcubes.square.MarchingCube;
import me.dylanmullen.marchingcubes.terrain.Chunk;
import me.dylanmullen.marchingcubes.terrain.Terrain;

public class Renderer
{

	private Camera camera;

	private Shader shader;
	private Matrix4f projection;

	public Renderer(Camera camera)
	{
		this.camera = camera;
		this.shader = new Shader("test.vert", "test.frag");
		this.projection = createProjectionMatrix();
		init();
	}

	private void init()
	{
		GL11.glClearColor(0f, 0f, 0f, 1f);

		shader.start();
		shader.setProjectionMatrix(projection);
		shader.stop();
	}

	/* TODO: Instance Rendering */
	public void renderTerrain(Terrain terrain)
	{
		shader.start();
		shader.setViewMatrix(camera.getViewMatrix());

		for (Chunk chunk : terrain.getLoadedChunks())
		{
			shader.setTransformationMatrix(chunk.getModelMatrix());

			shader.setVector3f("chunkColour", chunk.getColour());

			drawVAO(chunk.getModel());
		}
		shader.stop();
	}

	public void drawCube(MarchingCube vao)
	{
		if(vao.getVao() ==null)
			return;
		Matrix4f translate = new Matrix4f();
		translate.translate(vao.getPosition());
		shader.start();
		shader.setViewMatrix(camera.getViewMatrix());

		shader.setTransformationMatrix(translate);
		shader.setVector3f("chunkColour", vao.getColour());

		drawVAO(vao.getVao());

		shader.stop();
	}

	public void drawVAO(VAO vao)
	{
		GL30.glBindVertexArray(vao.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glDrawElements(GL11.GL_TRIANGLES, vao.getCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}

	private Matrix4f createProjectionMatrix()
	{
		Matrix4f matrix = new Matrix4f();

		float FOV = 60f;
		float aspect = (float) 1280 / (float) 720;
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

}
