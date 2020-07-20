package me.dylanmullen.marchingcubes.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class BufferUtil
{

	public static FloatBuffer toFloatBuffer(float[] data)
	{
		FloatBuffer buffer = ByteBuffer.allocateDirect(data.length << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
		buffer.put(data).flip();
		return buffer;
	}
	
}
