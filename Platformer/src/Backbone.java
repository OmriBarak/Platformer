import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Backbone
{
	private static final int WIDTH = 300;
	private static final int HEIGHT = 300;
	
	private GLFWErrorCallback errorCallback;
	private GLFWKeyCallback keyCallback;

	private long windowUID;
	private long deltaRef;
	
	public void run() {
		init();
		
		GL.createCapabilities();
		glClearColor(0.0f, 0.0f, 1.0f, 0.0f);

		while (glfwWindowShouldClose(windowUID) == GLFW_FALSE ) {
			glfwPollEvents();
			draw();
		}
		
		glfwDestroyWindow(windowUID);
		keyCallback.release();
		glfwTerminate();
		errorCallback.release();
	}
	
	private void init()
	{
		deltaRef = System.currentTimeMillis();
		
		glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));

		if(glfwInit() != GLFW_TRUE)
		{
			System.err.println("Couldn't initialize GLFW");
			assert false;
		}

		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		windowUID = glfwCreateWindow(WIDTH, HEIGHT, "Mario meets Fez meets Journey", NULL, NULL);
		if (windowUID == NULL ) {
			System.err.println("Failed to create the GLFW window");
			assert false;
		}

		//TODO: implement keybinds in a separate class
		glfwSetKeyCallback(windowUID, keyCallback = new KeyboardHandler());

		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

		glfwSetWindowPos(windowUID, (vidmode.width()-WIDTH)/2, (vidmode.height()-HEIGHT)/2);
		
		glfwMakeContextCurrent(windowUID);
		glfwSwapInterval(1);
		
		glfwShowWindow(windowUID);
	}

	private void draw() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glfwSwapBuffers(windowUID);
	}
	
	private double delta() {
		long currentTime = System.currentTimeMillis();
		double delta = (currentTime - deltaRef)/1000.0f;
		deltaRef = currentTime;
		return delta;
	}
	
	public static void main(String[] arghhhhhh) {
		Backbone game = new Backbone();
		game.run();
	}
}
