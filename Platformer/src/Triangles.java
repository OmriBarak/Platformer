import java.util.ArrayList;

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
	
	public void createVertices(int n){
		//n is the number of rectangles in cylinder
		
		vertices = new float[10]; //360 / 10 = 36 degrees per vertex
		
//		for(int i = 0; i < vertices.length; i++){
//			for(int j = 0; j < vertices.length; j++){
//				float[] pos = new float[]{ (float) Math.cos( (double) (36*i) ), (float) j, (float) Math.sin( (double) (36*i) ) };
//				float[] tex = new float[]{ 0.0f, 0.0f };
//				float[] norm = new float[]{ 0.0f, 0.0f, 0.0f };
//				
//				Vertex vert = new Vertex(pos, tex, norm);
//			}
//		}
		
		double theta = 0;
		
		ArrayList<Float> pos = new ArrayList<Float>();
		ArrayList<Float> tex = new ArrayList<Float>();
		ArrayList<Float> norm = new ArrayList<Float>();
		
		for(int i = 0; i < 5; i++){
			for(int j = 0; j < n; j++) {
				pos.add((float) Math.cos(theta));
				pos.add((float)(i*2*Math.sin(Math.PI/n)));
				pos.add((float) Math.sin(theta));
				
				tex.add(0.0f);
				tex.add(0.0f);
				
				norm.add((float) Math.cos(theta));
				norm.add(0.0f);
				norm.add((float) Math.sin(theta));
				
				theta += (2*Math.PI)/n;
			}
			theta = 0;
		}
		
		float aPos[] = {};
		float aTex[] = {};
		float aNorm[] = {};
		
		for(int i=0; i<pos.size(); i++) {
			aPos[i] = pos.get(i).floatValue();
			aTex[i] = tex.get(i).floatValue();
			aNorm[i] = norm.get(i).floatValue();
		}
		
		Vertex cylinder = new Vertex(aPos, aTex, aNorm);
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
