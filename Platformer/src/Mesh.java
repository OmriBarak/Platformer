import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Mesh {	
	private float[] position; //xyz (w added in shader)
	private float[] texUV;	  //uv
	private float[] normal;	  //ijkw
	private float[] color;	  //rgba
	private float[] trackingColor;

	private int[] indices;  //vvv

	private int vboPosition = 0, vboTexUV = 0, vboNormal = 0, vboColor = 0;
	private int eboId = 0;
	private int vaoId = 0;

	double time = 0;

	public Mesh(float[] pos, float[] tex, float[] norm, float[] col, int[] ind) {
		position = pos;
		texUV = tex;
		normal = norm;
		color = col;
		indices = ind;
		trackingColor = Arrays.copyOf(color, color.length);
	}

	void bufferData() {
		vaoId = glGenVertexArrays();
		glBindVertexArray(vaoId);

		vboPosition	= glGenBuffers();
		FloatBuffer positionBuffer = BufferUtils.createFloatBuffer(position.length);
		positionBuffer.put(position).flip();
		glBindBuffer(GL_ARRAY_BUFFER, vboPosition);
		glBufferData(GL_ARRAY_BUFFER, positionBuffer, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(0);

		vboTexUV = glGenBuffers();
		FloatBuffer textureUVBuffer = BufferUtils.createFloatBuffer(texUV.length);
		textureUVBuffer.put(texUV).flip();
		glBindBuffer(GL_ARRAY_BUFFER, vboTexUV);
		glBufferData(GL_ARRAY_BUFFER, textureUVBuffer, GL_STATIC_DRAW);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(1);

		vboNormal = glGenBuffers();
		FloatBuffer normalBuffer = BufferUtils.createFloatBuffer(normal.length);
		normalBuffer.put(normal).flip();
		glBindBuffer(GL_ARRAY_BUFFER, vboNormal);
		glBufferData(GL_ARRAY_BUFFER, normalBuffer, GL_STATIC_DRAW);
		glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(2);

		vboColor = glGenBuffers();
		FloatBuffer colorBuffer = BufferUtils.createFloatBuffer(color.length);
		colorBuffer.put(color).flip();
		glBindBuffer(GL_ARRAY_BUFFER, vboColor);
		glBufferData(GL_ARRAY_BUFFER, colorBuffer, GL_DYNAMIC_DRAW);
		glVertexAttribPointer(3, 4, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(3);

		eboId = glGenBuffers();
		IntBuffer indexBuffer = BufferUtils.createIntBuffer(indices.length);
		indexBuffer.put(indices).flip();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboId);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW);

		glBindBuffer(GL_ARRAY_BUFFER, 0);

		glBindVertexArray(0);
	}

	void draw(double deltaMillis) {
		double colorFactor = Math.abs(Math.sin(time))+0.5;
		double otherColorFactor = Math.abs(Math.cos(time))+0.5;
		double whatColorFactor = Math.abs(Math.tan(time))+0.5;
		glBindVertexArray(vaoId);

		for(int i=0; i<color.length/3; i++) {
			color[3*i] = (float)colorFactor;
			color[3*i+1] = (float)otherColorFactor;
			color[3*i+2] = (float)whatColorFactor;
		}
		FloatBuffer colorBuffer = BufferUtils.createFloatBuffer(color.length);
		colorBuffer.put(color).flip();

		glBindBuffer(GL_ARRAY_BUFFER, vboColor);
		glBufferData(GL_ARRAY_BUFFER, colorBuffer, GL_DYNAMIC_DRAW);
		glVertexAttribPointer(3, 4, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(3);
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
		glBindVertexArray(0);
	}

	void dispose() {
		glBindVertexArray(0);
		glDeleteVertexArrays(vaoId);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glDeleteBuffers(eboId);

		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glDeleteBuffers(vboPosition);
		glDeleteBuffers(vboTexUV);
		glDeleteBuffers(vboNormal);
		glDeleteBuffers(vboColor);
	}

	public void setPosition(float[] pos)		{ position = pos; }
	public void setTextureCoords(float[] tex)	{ texUV = tex; }
	public void setNormal(float[] norm)			{ normal = norm; }

	public float[] getPosition()		{ return position; }
	public float[] getTextureCoords()	{ return texUV; }
	public float[] getNormal()			{ return normal; }
}
