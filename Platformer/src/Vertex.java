
public class Vertex {	
	private float[] position;
	private float[] texUV;
	private float[] normal;
	
	public Vertex() {
		position = new float[3];
		texUV = new float[2];
		normal = new float[3];
	}
	
	public void setPosition(float[] pos)		{ position = pos; }
	public void setTextureCoords(float[] tex)	{ texUV = tex; }
	public void setNormal(float[] norm)			{ normal = norm; }
	
	public float[] getPosition()		{ return position; }
	public float[] getTextureCoords()	{ return texUV; }
	public float[] getNormal()			{ return normal; }
}
