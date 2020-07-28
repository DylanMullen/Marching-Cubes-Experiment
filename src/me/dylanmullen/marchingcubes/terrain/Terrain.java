package me.dylanmullen.marchingcubes.terrain;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
	}

	public void generateChunk(Vector3f position)
	{
		if(isLoaded(position))
			return;
		
		ArrayList<MarchingSquare> squares = generator.createSquares(position, CHUNK_SIZE, CHUNK_SIZE);
		VAO model = generator.generateMesh(squares);
		Chunk chunk = new Chunk(position, model);
		loadedChunks.add(chunk);
	}

	public boolean isLoaded(Vector3f position)
	{
		try
		{
			return loadedChunks.stream().filter(e -> e.getPosition().equals(position)).findAny().isPresent();
		} catch (NoSuchElementException e)
		{
			return false;
		}
	}

	public List<Chunk> getLoadedChunks()
	{
		return loadedChunks;
	}
}
