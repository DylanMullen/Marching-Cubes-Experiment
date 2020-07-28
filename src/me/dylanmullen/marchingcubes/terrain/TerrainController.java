package me.dylanmullen.marchingcubes.terrain;

import org.joml.Vector3f;

import me.dylanmullen.marchingcubes.graphics.Camera;

public class TerrainController
{

	private Terrain terrain;
	private Camera camera;

	public TerrainController(Camera camera)
	{
		this.terrain = new Terrain();
		this.camera = camera;
	}

	public void loadSurroundingChunks()
	{
		int xCord = (int) (camera.getPosition().x / 16f) * 16 - (camera.getPosition().x < 0 ? 16 : 0);
		int zCode = (int) (camera.getPosition().z / 16f) * 16 - (camera.getPosition().z < 0 ? 16 : 0);
		terrain.generateChunk(new Vector3f(xCord, 0, zCode));
	}

	public Camera getCamera()
	{
		return camera;
	}

	public Terrain getTerrain()
	{
		return terrain;
	}

}
