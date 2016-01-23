import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Backbone
{
	private static final int WIDTH = 1000;
	private static final int HEIGHT = 500;

	private GLFWErrorCallback errorCallback;
	private GLFWKeyCallback keyCallback;

	private long windowUID;
	private long deltaRef;

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
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
		
		windowUID = glfwCreateWindow(WIDTH, HEIGHT, "Mario meets Fez meets Journey", NULL, NULL);
		if (windowUID == NULL ) {
			System.err.println("Failed to create the GLFW window");
			assert false;
		}

		glfwSetKeyCallback(windowUID, keyCallback = new KeyboardHandler());

		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(windowUID, (vidmode.width()-WIDTH)/2, (vidmode.height()-HEIGHT)/2);
		
		glfwMakeContextCurrent(windowUID);
		glfwShowWindow(windowUID);
		
		GL.createCapabilities();
		glfwSwapInterval(1);
		glClearColor(0.0f, 0.0f, 1.0f, 0.0f);
		glEnable(GL_DEPTH_TEST);
		System.out.println("Running OpenGL version" + glGetString(GL_VERSION) + ".");
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

	public void run() {
		init();
	
		while (glfwWindowShouldClose(windowUID) == GLFW_FALSE ) {
			glfwPollEvents();
			draw();
		}
		
		glfwDestroyWindow(windowUID);
		keyCallback.release();
		glfwTerminate();
		errorCallback.release();
	}

	public static void main(String[] arghhhhhh) {
		Backbone game = new Backbone();
		game.run();
	}
}
