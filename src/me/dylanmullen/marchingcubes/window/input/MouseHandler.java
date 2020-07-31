package me.dylanmullen.marchingcubes.window.input;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

public class MouseHandler
{

	private long windowRef;

	private List<Button> buttons;
	private Vector2f mousePosition;

	public MouseHandler(long windowRef)
	{
		this.windowRef = windowRef;
		this.mousePosition = new Vector2f();
		this.buttons = new ArrayList<Button>();
		init();
	}

	private void init()
	{
		GLFW.glfwSetMouseButtonCallback(windowRef, new GLFWMouseButtonCallbackI()
		{

			@Override
			public void invoke(long window, int button, int action, int mods)
			{
				if (!isButton(button))
				{
					buttons.add(new Button(button));
				}
				getMouseButton(button).setClicked(action);
			}
		});

		GLFW.glfwSetCursorPosCallback(windowRef, new GLFWCursorPosCallbackI()
		{
			@Override
			public void invoke(long window, double xpos, double ypos)
			{
				if (mousePosition.x != xpos || mousePosition.y != ypos)
					mousePosition.set(xpos, ypos);
			}
		});
	}

	private Button getMouseButton(int code)
	{
		return buttons.stream().filter(e -> e.getButtonCode() == code).findFirst().get();
	}

	private boolean isButton(int code)
	{
		return buttons.stream().filter(e -> e.getButtonCode() == code).findFirst().isPresent();
	}
}
