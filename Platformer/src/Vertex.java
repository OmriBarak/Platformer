import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

public class Vertex {	
	private float[] position;
	private float[] texUV;
	private float[] normal;
	
	private int vboPosition = 0, vboTexUV = 0, vboNormal = 0;
	private int vaoId = 0;
	
	public Vertex(float[] pos, float[] tex, float[] norm) {
		position = pos;
		texUV = tex;
		normal = norm;
	}
	
	void bufferData() {
		vaoId = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoId);
		
		vboPosition	= GL15.glGenBuffers();
		FloatBuffer positionBuffer = BufferUtils.createFloatBuffer(position.length);
        positionBuffer.put(position);
        positionBuffer.flip();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboPosition);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, positionBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
        
        vboTexUV	= GL15.glGenBuffers();
        FloatBuffer textureUVBuffer = BufferUtils.createFloatBuffer(position.length);
        textureUVBuffer.put(position);
        textureUVBuffer.flip();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboTexUV);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, textureUVBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);
        
        vboNormal	= GL15.glGenBuffers();
        FloatBuffer normalBuffer = BufferUtils.createFloatBuffer(position.length);
        normalBuffer.put(position);
        normalBuffer.flip();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboNormal);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, normalBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, 0, 0);
        
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
	}
	
	public void setPosition(float[] pos)		{ position = pos; }
	public void setTextureCoords(float[] tex)	{ texUV = tex; }
	public void setNormal(float[] norm)			{ normal = norm; }
	
	public float[] getPosition()		{ return position; }
	public float[] getTextureCoords()	{ return texUV; }
	public float[] getNormal()			{ return normal; }
}
