package me.dylanmullen.marchingcubes.terrain;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

import me.dylanmullen.marchingcubes.generator.MarchingCubeGenerator;
import me.dylanmullen.marchingcubes.graphics.VAO;
import me.dylanmullen.marchingcubes.square.MarchingSquare;

public class Terrain
{

	private final int CHUNK_SIZE = 16;

	private MarchingCubeGenerator generator;

	private List<Chunk> loadedChunks;

	public Terrain()
	{
		this.generator = new MarchingCubeGenerator();
		this.loadedChunks = new ArrayList<>();
	}

	public void debug()
	{
		generateChunk(new Vector3f(0, 0, CHUNK_SIZE));
		generateChunk(new Vector3f(0, 0, -CHUNK_SIZE));
		generateChunk(new Vector3f(0, 0, 0));
		generateChunk(new Vector3f(CHUNK_SIZE, 0, 0));
		generateChunk(new Vector3f(-CHUNK_SIZE, 0, 0));
	}

	public void generateChunk(Vector3f position)
	{
		ArrayList<MarchingSquare> squares = generator.createSquares(position, CHUNK_SIZE, CHUNK_SIZE);
		VAO model = generator.generateMesh(squares);
		Chunk chunk = new Chunk(position, model);
		loadedChunks.add(chunk);
	}

	public List<Chunk> getLoadedChunks()
	{
		return loadedChunks;
	}
}
