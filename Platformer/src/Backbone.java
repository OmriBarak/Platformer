import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Backbone
{
	private static final int WIDTH = 1000;
	private static final int HEIGHT = 500;

	private float cameraPosition[];
	private float yDisplacement;
	Mesh mesh;
	
	private GLFWErrorCallback errorCallback;
	private GLFWKeyCallback keyCallback;

	private long windowUID;
	private long deltaRef;

	private ShaderProgram shaderProgram;
	
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
		
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(windowUID, (vidmode.width()-WIDTH)/2, (vidmode.height()-HEIGHT)/2);
		
		glfwMakeContextCurrent(windowUID);
		
		GL.createCapabilities();
		
		glfwSwapInterval(1);
		glfwSetKeyCallback(windowUID, keyCallback = new KeyboardHandler());
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LESS);
		
		shaderProgram = new ShaderProgram();
        shaderProgram.attachVertexShader("VertexShader.glsl");
        shaderProgram.attachFragmentShader("FragmentShader.glsl");
        shaderProgram.link();
        shaderProgram.bind();
        
        glClearColor(0.0f, 0.0f, 1.0f, 0.0f);
        
		System.out.println("Running OpenGL version" + glGetString(GL_VERSION) + ".");
		
		cameraPosition = new float[3];
		cameraPosition[0] = 0.0f;
		cameraPosition[1] = 0.7f;
		cameraPosition[2] = -1.0f;
		
		yDisplacement = -3.6f;
		
		Cylinder cylinder = new Cylinder();
		cylinder.genPrism(40);
		mesh = cylinder.toMesh();
		mesh.bufferData();
	}

	private void draw() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		//CAMERA STUFF
		/////////////////////////////////////////////
		/////////////////////////////////////////////
		/////////////////////////////////////////////
		int cameraLocation = glGetUniformLocation(shaderProgram.getID(), "cameraPosition"); //Find the cameraPosition variable in the shader file
		glUniform3f(cameraLocation, cameraPosition[0], cameraPosition[1], cameraPosition[2]); //Set the camera position

		float fovX = 1.0f;
		float fovY = 1.0f;
		int fovxLocation = glGetUniformLocation(shaderProgram.getID(), "fovX");
		glUniform1f(fovxLocation, fovX);
		int fovyLocation = glGetUniformLocation(shaderProgram.getID(), "fovY");
		glUniform1f(fovyLocation, fovY);

		//Set near and far Z for the projection matrix
		float nearZ = -1.0f;
		float farZ = 1.1f;
		int nearLocation = glGetUniformLocation(shaderProgram.getID(), "nearZ");
		glUniform1f(nearLocation, nearZ);
		int farLocation = glGetUniformLocation(shaderProgram.getID(), "farZ");
		glUniform1f(farLocation, farZ);
		
//		if(glfwGetKey(windowUID, GLFW_KEY_W) == GLFW_PRESS) cameraPosition[2] += 0.1f; //Move forward
//		else if(glfwGetKey(windowUID, GLFW_KEY_S) == GLFW_PRESS) cameraPosition[2] -= 0.1f; //Move backward
//		if(glfwGetKey(windowUID, GLFW_KEY_A) == GLFW_PRESS) cameraPosition[0] -= 0.1f; //Move left
//		else if(glfwGetKey(windowUID, GLFW_KEY_D) == GLFW_PRESS) cameraPosition[0] += 0.1f; //Move right
//		if(glfwGetKey(windowUID, GLFW_KEY_UP) == GLFW_PRESS) cameraPosition[1] += 0.1f; //Move up
//		else if(glfwGetKey(windowUID, GLFW_KEY_DOWN) == GLFW_PRESS) cameraPosition[1] -= 0.1f; //Move down
		int yDisp = glGetUniformLocation(shaderProgram.getID(), "yDisp");
		glUniform1f(yDisp, yDisplacement);
		if(glfwGetKey(windowUID, GLFW_KEY_LEFT) == GLFW_PRESS) yDisplacement -= 0.01f; //Move left
			if(yDisplacement<-3.6f) yDisplacement = -3.6f;
		if(glfwGetKey(windowUID, GLFW_KEY_RIGHT) == GLFW_PRESS) yDisplacement += 0.01f; //Move right
			if(yDisplacement>0.3f) yDisplacement = 0.3f;
		System.out.println(yDisplacement);
		/////////////////////////////////////////////
		/////////////////////////////////////////////
		/////////////////////////////////////////////
		mesh.draw(delta());
		
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
		
		keyCallback.release();
		shaderProgram.dispose();
		glfwDestroyWindow(windowUID);
		glfwTerminate();
		errorCallback.release();
	}

	public static void main(String[] arghhhhhh) {
		Backbone game = new Backbone();
		game.run();
	}
}
