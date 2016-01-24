import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Vertex {	
	private float[] position; //xyzw
	private float[] texUV;	  //uv
	private float[] normal;	  //ijkw
	private float[] color;	  //rgba
	
	private int vboPosition = 0, vboTexUV = 0, vboNormal = 0, vboColor = 0;
	private int vaoId = 0;
	
	public Vertex(float[] pos, float[] tex, float[] norm, float[] col) {
		position = pos;
		texUV = tex;
		normal = norm;
		color = col;
	}
	
	void bufferData() {
		vaoId = glGenVertexArrays();
		glBindVertexArray(vaoId);
		
		vboPosition	= glGenBuffers();
		FloatBuffer positionBuffer = BufferUtils.createFloatBuffer(position.length);
        positionBuffer.put(position);
        positionBuffer.flip();
        glBindBuffer(GL_ARRAY_BUFFER, vboPosition);
        glBufferData(GL_ARRAY_BUFFER, positionBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 4, GL_FLOAT, false, 0, 0);
        
        vboTexUV	= glGenBuffers();
        FloatBuffer textureUVBuffer = BufferUtils.createFloatBuffer(texUV.length);
        textureUVBuffer.put(position);
        textureUVBuffer.flip();
        glBindBuffer(GL_ARRAY_BUFFER, vboTexUV);
        glBufferData(GL_ARRAY_BUFFER, textureUVBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
        
        vboNormal	= glGenBuffers();
        FloatBuffer normalBuffer = BufferUtils.createFloatBuffer(normal.length);
        normalBuffer.put(position);
        normalBuffer.flip();
        glBindBuffer(GL_ARRAY_BUFFER, vboNormal);
        glBufferData(GL_ARRAY_BUFFER, normalBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(2, 4, GL_FLOAT, false, 0, 0);
        
        vboColor	= glGenBuffers();
        FloatBuffer colorBuffer = BufferUtils.createFloatBuffer(color.length);
        colorBuffer.put(position);
        colorBuffer.flip();
        glBindBuffer(GL_ARRAY_BUFFER, vboColor);
        glBufferData(GL_ARRAY_BUFFER, colorBuffer, GL_STATIC_DRAW);
        glVertexAttribPointer(3, 4, GL_FLOAT, false, 0, 0);
        
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
	}
	
	public void setPosition(float[] pos)		{ position = pos; }
	public void setTextureCoords(float[] tex)	{ texUV = tex; }
	public void setNormal(float[] norm)			{ normal = norm; }
	
	public float[] getPosition()		{ return position; }
	public float[] getTextureCoords()	{ return texUV; }
	public float[] getNormal()			{ return normal; }
}
