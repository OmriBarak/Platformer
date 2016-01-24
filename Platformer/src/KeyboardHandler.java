import org.lwjgl.glfw.*;
import static org.lwjgl.glfw.GLFW.*;

public class KeyboardHandler extends GLFWKeyCallback {
	 @Override
	 public void invoke(long window, int key, int scancode, int action, int mods)
		{
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, GLFW_TRUE);
		}
}
