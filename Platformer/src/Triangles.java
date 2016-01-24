import java.util.ArrayList;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.glfw.GLFW.*;

public class Triangles {
	private ArrayList<Float> vertices;
	private ArrayList<Integer> indices;
	
	private final static int HEIGHT = 5; 
	
	public Triangles(){
	
	}
	
	public void genPrism(int n){
		
		double theta = 0;
		
		vertices = new ArrayList<Float>();
		for(int i = 0; i < HEIGHT; i++){
			for(int j = 0; j < n; j++) {
				vertices.add((float) Math.cos(theta));
				vertices.add((float)(i*2*Math.sin(Math.PI/n)));
				vertices.add((float) Math.sin(theta));
				
				theta += (2*Math.PI)/n;
			}
			theta = 0;
		}
		
		indices = new ArrayList<Integer>();
		
		for(int i = 0; i < (n*(HEIGHT-1)-1); i++){
			if( (i+1) % n == 0 ) {
				indices.add(i);
				indices.add(i-n+1);
				indices.add(i+n);
				
				indices.add(i-n+1);
				indices.add(i+n);
				indices.add(i+1);
			}
			else {
				indices.add(i);
				indices.add(i+1);
				indices.add(i+n);
				
				indices.add(i+1);
				indices.add(i+n);
				indices.add(i+n+1);
			}
		}
	}
	
	public ArrayList<Float> getVertices(){
		return vertices;
	}
	
	public ArrayList<Integer> getIndices(){
		return indices;
	}
}
