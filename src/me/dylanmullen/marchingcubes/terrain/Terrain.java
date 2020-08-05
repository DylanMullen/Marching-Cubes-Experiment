package me.dylanmullen.marchingcubes.terrain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

	public void loadSurroundedChunks(Vector3f chunkCoords)
	{
		generateChunk(new Vector3f(chunkCoords.x, 0, chunkCoords.z));
		loadSides(chunkCoords);
		loadCorners(chunkCoords);
	}

	private void loadSides(Vector3f chunkCoords)
	{
		generateChunk(new Vector3f(chunkCoords.x + 16, 0, chunkCoords.z));
		generateChunk(new Vector3f(chunkCoords.x - 16, 0, chunkCoords.z));
		generateChunk(new Vector3f(chunkCoords.x, 0, chunkCoords.z + 16));
		generateChunk(new Vector3f(chunkCoords.x, 0, chunkCoords.z - 16));
	}

	private void loadCorners(Vector3f chunkCoords)
	{
		generateChunk(new Vector3f(chunkCoords.x + 16, 0, chunkCoords.z + 16));
		generateChunk(new Vector3f(chunkCoords.x + 16, 0, chunkCoords.z - 16));
		generateChunk(new Vector3f(chunkCoords.x - 16, 0, chunkCoords.z + 16));
		generateChunk(new Vector3f(chunkCoords.x - 16, 0, chunkCoords.z - 16));
	}

	private void generateChunk(Vector3f position)
	{
		if (isLoaded(position))
			return;

		ArrayList<MarchingSquare> squares = generator.createSquares(position, CHUNK_SIZE, CHUNK_SIZE);
		VAO model = generator.generateSquareMesh(squares);
		Chunk chunk = new Chunk(position, model);
		loadedChunks.add(chunk);
	}

	public boolean isLoaded(Vector3f position)
	{
		return loadedChunks.stream().filter(e -> e.getPosition().equals(position)).findAny().isPresent();
	}

	public List<Chunk> getLoadedChunks()
	{
		return loadedChunks;
	}

	public List<Chunk> getChunksOutside(Vector3f vector)
	{
		Vector3f high = new Vector3f(vector.x - 16, 0, vector.z - 16);
		Vector3f low = new Vector3f(vector.x + 16, 0, vector.z + 16);

		return loadedChunks.stream().filter(e -> !intersects(high, low, e.getPosition())).map(e -> e)
				.collect(Collectors.toList());
	}

	private boolean intersects(Vector3f high, Vector3f low, Vector3f position)
	{
		return (position.x >= high.x && position.x <= low.x) && (position.z >= high.z && position.z <= low.z);
	}

	public void unloadChunks(Vector3f chunkPosition)
	{
		List<Chunk> chunks = getChunksOutside(chunkPosition);
		for (Chunk chunk : chunks)
			loadedChunks.remove(chunk);

	}
}
