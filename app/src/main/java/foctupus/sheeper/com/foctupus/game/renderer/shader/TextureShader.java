package foctupus.sheeper.com.foctupus.game.renderer.shader;

import android.opengl.GLES20;

/**
 * Created by Jana on 22.04.2015.
 */
public class TextureShader {

    private int programID;
    private int vertexShaderID;
    private int fragmentShaderID;

    private static final String vertexShader =
            "uniform mat4 u_mvpMatrix;" +
                    "attribute vec4 a_position;" +
                    "attribute vec2 a_texCoord;" +
                    "varying vec2 v_texCoord;" +

                    "void main() {" +
                    "  gl_Position = u_mvpMatrix * a_position;" +
                    "  v_texCoord = a_texCoord;" +
                    "}";

    private static final String fragmentShader =
            "precision mediump float;" +
                    "uniform sampler2D u_texture;" +
                    "varying vec2 v_texCoord;" +

                    "void main() {" +

                    "  gl_FragColor = texture2D(u_texture, v_texCoord);" +
                    "}";


    private int location_mvpMatrix;
    private int location_position;
    private int location_textureCoords;
    private int location_textureUniform;

    public TextureShader()
    {
        vertexShaderID = loadShader(vertexShader, GLES20.GL_VERTEX_SHADER);
        fragmentShaderID = loadShader(fragmentShader, GLES20.GL_FRAGMENT_SHADER);
        programID = GLES20.glCreateProgram();
        GLES20.glAttachShader(programID, vertexShaderID);
        GLES20.glAttachShader(programID, fragmentShaderID);
        GLES20.glLinkProgram(programID);
        GLES20.glValidateProgram(programID);
        getAllUniformLocations();


    }

    public void loadMVPMatrix(float[] matrix)
    {
        loadMatrix(location_mvpMatrix, matrix);
    }

    public void start()
    {
        GLES20.glUseProgram(programID);
    }

    public void stop()
    {
        GLES20.glUseProgram(0);
    }

    public int getProgramID()
    {
        return programID;
    }

    public int getPositionHandle()
    {
        return location_position;
    }

    public int getTextureHandle()
    {
        return location_textureCoords;
    }

    public int getTextureUniform() { return location_textureUniform; }

    public void loadTextureUniform(int i)
    {
        GLES20.glUniform1i(i, 0);
    }

    private void getAllUniformLocations() {
        location_mvpMatrix= getUniformLocation("u_mvpMatrix");
        location_position = getAttribLocation("a_position");
        location_textureCoords = getAttribLocation("a_texCoord");
        location_textureUniform = getUniformLocation("u_texture");
    }

    private void loadMatrix(int location, float[] matrix)
    {
        GLES20.glUniformMatrix4fv(location, 1, false, matrix, 0);
    }

    private int getUniformLocation(String uniformName)
    {
        return GLES20.glGetUniformLocation(programID, uniformName);
    }

    private int getAttribLocation(String attribname)
    {
        return GLES20.glGetAttribLocation(programID, attribname);
    }

    private static int loadShader(String shaderSource, int type)
    {
        int shaderID = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shaderID, shaderSource);
        GLES20.glCompileShader(shaderID);


        final int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shaderID, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

        if (compileStatus[0] == 0)
        {
            System.err.println("Could not compile Shader: " + shaderSource);
            System.err.println(GLES20.glGetShaderInfoLog(shaderID));
            shaderID = 0;
        }
        return shaderID;
    }
}
