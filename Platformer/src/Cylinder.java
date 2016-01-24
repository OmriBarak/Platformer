import java.util.ArrayList;

public class Cylinder {
	private ArrayList<Float> vertices;
	private ArrayList<Integer> indices;
	
	private final static int HEIGHT = 5; 
	private int n;
	
	public Cylinder(){
	
	}
	
	public void genPrism(int n){
		this.n = n;
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

	public Mesh toMesh() {
		//pos[]
		float pos[] = new float[vertices.size()];		
		for(int i=0; i<vertices.size(); i++) {
			pos[i] = vertices.get(i).floatValue();
		}
		
		//tex[]
		float tex[] = new float[2*vertices.size()/3];
		for(int i=0; i<vertices.size()/3; i++) {
			tex[2*i] = 0.0f;
			tex[2*i+1] = 0.0f;
		}
		
		//norm[]
		float norm[] = new float[vertices.size()];
		for(int i=0; i<vertices.size()/3; i++) {
			norm[3*i] = vertices.get(i*3);
			norm[3*i+1] = 0.0f;
			norm[3*i+2] = vertices.get(i*3+2);
		}
		
		//col[]
		float colorIncrement = 1.0f/((float) vertices.size());
		float col[] = new float[4*vertices.size()/3];
		for(int i=0; i<HEIGHT; i++) {
			for(int j=0;j<n;j++) {
				col[4*(n*i+j)] = i*colorIncrement;
				col[4*(n*i+j)+1] = i*colorIncrement;
				col[4*(n*i+j)+2] = i*colorIncrement;
				col[4*(n*i+j)+3] = 0.0f;
			}
		}
		
		//ind[]
		int ind[] = new int[indices.size()];
		for(int i=0; i<indices.size();i++) {
			ind[i] = indices.get(i);
		}
		
		return new Mesh(pos, tex, norm, col, ind);
	}
	
	public ArrayList<Float> getVertices(){
		return vertices;
	}
	
	public ArrayList<Integer> getIndices(){
		return indices;
	}
}
