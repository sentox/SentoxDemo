package com.sentox.demo.function.gl.program;

import android.opengl.GLES20;

import com.sentox.demo.R;

import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glUniformMatrix4fv;

/**
 * 描述：纹理程序
 * 说明：
 * Created by Sentox
 * Created on 2019/6/16
 */
public class TextureShaderProgram extends ShaderProgram {

    private final int uMatrixLocation;
    private final int uTextureUnitLocation;

    private final int aPositionLocation;
    private final int aTextureCoordinatesLocation;

    public TextureShaderProgram(){
        super(R.raw.texture_vertex_shader,R.raw.texture_fragment_shader);

        uMatrixLocation = GLES20.glGetUniformLocation(program,U_MATRIX);
        uTextureUnitLocation = GLES20.glGetUniformLocation(program,U_TEXTURE_UNIT);

        aPositionLocation = GLES20.glGetAttribLocation(program,A_POSITION);
        aTextureCoordinatesLocation = GLES20.glGetAttribLocation(program,A_TEXTURE_COORDINATES);

    }

    /**
     *  设置矩阵并传入纹理单元数据
     * **/
    public void setUniforms(float[] matrix,int textureId){
        //传入矩阵
        glUniformMatrix4fv(uMatrixLocation,1,false,matrix,0);

        //设置活动单元为纹理单元0
        glActiveTexture(GL_TEXTURE0);

        //绑定纹理单元到活动单元
        glBindTexture(GL_TEXTURE_2D,textureId);

        //将绑定的纹理单元传入片段着色器的u_TextureUnit
        glUniform1i(uTextureUnitLocation,0);
    }

    /**
     *  返回a_position的引用id
     * **/
    public int getaPositionLocation(){
        return aPositionLocation;
    }

    /**
     *  返回纹理坐标的引用id
     * **/
    public int getaTextureCoordinatesLocation(){
        return aTextureCoordinatesLocation;
    }

}
