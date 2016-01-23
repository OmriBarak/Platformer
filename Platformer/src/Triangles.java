import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.glfw.GLFW.*;

public class Triangles {
	private static float[] vertices;
	private static int[] indices;
	
	public Triangles(){
		createVertices();
		createIndices();
	}
	
	public void createVertices(){
		vertices = new float[10];
		
		for(int i = 0; i < vertices.length; i++){
			Vertex vert = new Vertex();
			vert.setPosition(new float[]{0.0f, 0.0f, 0.0f});
			vert.setTextureCoords(new float[]{0.0f, 0.0f});
			vert.setNormal(new float[]{0.0f, 0.0f, 0.0f});
		}
	}
	
	public void createIndices(){
		indices = new int[10];
		
		for(int i = 0; i < indices.length; i++){
			indices[i] = i;
		}
	}
}
