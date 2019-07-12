package com.sentox.demo.function.gl.data;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * 描述：顶点数据构建器
 * 说明：
 * Created by Sentox
 * Created on 2019/6/13
 */
public class VertexArray {

    /**
     * java浮点数（float)有32位（bit）精度,
     * 一个字节（byte）只有8位精度，
     * 因此每个浮点数占用4个字节
     **/
    private static final int BYTES_PER_FLOAT = 4;

    private final FloatBuffer mFloatBuffer;

    /**
     *  将顶点数据传入内存空间
     * **/
    public VertexArray(float[] vertexData){
        mFloatBuffer = ByteBuffer.allocateDirect(vertexData.length*BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertexData);
    }


    /**
     *  关联数据与着色器的变量
     * **/
    public void setVertexAttribPointer(int dataOffset,int attributeLocation,int componentCount,int stride){
        mFloatBuffer.position(dataOffset);
        GLES20.glVertexAttribPointer(attributeLocation,componentCount,GLES20.GL_FLOAT,false,stride,mFloatBuffer);
        GLES20.glEnableVertexAttribArray(attributeLocation);

        mFloatBuffer.position(0);
    }

}
