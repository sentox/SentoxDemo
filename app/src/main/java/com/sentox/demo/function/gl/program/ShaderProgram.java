package com.sentox.demo.function.gl.program;

import com.sentox.demo.utils.opengl.ShaderHelper;
import com.sentox.demo.utils.opengl.TextResourceReader;

import static android.opengl.GLES20.glUseProgram;

/**
 * 描述：OpenGL程序基类
 * 说明：
 * Created by Sentox
 * Created on 2019/6/16
 */
public class ShaderProgram {

    protected static final String U_MATRIX = "u_Matrix";
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";

    protected static final String A_POSITION = "a_Position";
    protected static final String A_COLOR = "a_Color";
    protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";

    protected final int program;

    protected ShaderProgram(int vertexShaderResId, int fragmentShaderResId) {
        program = ShaderHelper.buildProgram(TextResourceReader.readTextFileFromResource(vertexShaderResId),
                TextResourceReader.readTextFileFromResource(fragmentShaderResId));
    }

    /**
     *  设置OpenGL渲染程序为program
     * **/
    public void useProgram(){
        glUseProgram(program);
    }
}
