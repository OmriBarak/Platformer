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
		vertices = new float[10]; //360 / 10 = 36 degrees per vertex
		
		for(int i = 0; i < vertices.length; i++){
			for(int j = 0; j < vertices.length; j++){
				Vertex vert = new Vertex();
				vert.setPosition(new float[]{(float) Math.cos( (double) (36*i) ), (float) j, (float) Math.sin( (double) (36*i) )});
				vert.setTextureCoords(new float[]{0.0f, 0.0f});
				vert.setNormal(new float[]{0.0f, 0.0f, 0.0f});
			}
		}
	}
	
	public void createIndices(){
		indices = new int[10];
		
		for(int i = 0; i < indices.length; i++){
			indices[i] = i;
		}
	}
	
	public float[] getVertices(){
		return vertices;
	}
	
	public int[] getIndices(){
		return indices;
	}
}
