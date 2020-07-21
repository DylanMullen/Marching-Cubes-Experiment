package me.dylanmullen.marchingcubes.core;

import me.dylanmullen.marchingcubes.generator.MarchingCubeGenerator;

public class Runner
{

	public static void main(String[] args)
	{
//		new MarchingCubes();
//		Matrix4F mat = new Matrix4F();
//		mat.identity();
//		mat.print();
		MarchingCubeGenerator gen = new MarchingCubeGenerator(5, 5);
		gen.generate();
	}

}
