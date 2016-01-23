import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Backbone implements Runnable
{
	private Thread thread;
	
	private GLFWErrorCallback errorCallback;
	private GLFWKeyCallback keyCallback;

	private long windowUID;
	
	public void begin()
	{
		try
		{
			init();
		} catch(Error e) {
			
		}
		thread = new Thread(this, "Backbone");
		thread.start();
	}

	private void init()
	{
		glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));

		if(glfwInit() != GLFW_TRUE)
		{
			System.err.println("Couldn't initialize GLFW");
			assert false;
		}

		//glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		int width = 300, height = 300;
		
		if ((windowUID = glfwCreateWindow(width, height, "Mario meets Fez meets Journey", NULL, NULL)) == NULL ) {
			System.err.println("Failed to create the GLFW window");
			assert false;
		}

		//TODO: implement keybinds in a separate class
		glfwSetKeyCallback(windowUID, keyCallback = new GLFWKeyCallback()
		{
			@Override
			public void invoke(long window, int key, int scancode, int action, int mods)
			{
				if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
					glfwSetWindowShouldClose(window, GLFW_TRUE);
			}
		});

		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

		glfwSetWindowPos(windowUID, (vidmode.width()-width)/2, (vidmode.height()-height)/2);
		
		glfwMakeContextCurrent(windowUID);
		glfwSwapInterval(1);
		
		glfwShowWindow(windowUID);

		GL.createCapabilities();
	}

	public void run() {
		//clear the color
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

	private void draw() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glfwSwapBuffers(windowUID);
	}
	
	public static void main(String[] arghhhhhh) {
		Backbone game = new Backbone();
		game.run();

	}
}
