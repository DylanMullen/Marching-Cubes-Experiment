package me.dylanmullen.marchingcubes.terrain;

import me.dylanmullen.marchingcubes.graphics.Camera;

public class TerrainController
{

	private Terrain terrain;

	public TerrainController(Camera camera)
	{
		this.terrain = new Terrain();
	}

	public void handlePlayerMovement(Camera camera)
	{
		terrain.loadSurroundedChunks(camera.getChunkPosition());
	}

	public Terrain getTerrain()
	{
		return terrain;
	}

}
