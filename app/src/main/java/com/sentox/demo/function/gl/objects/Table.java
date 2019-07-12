package com.sentox.demo.function.gl.objects;

import com.sentox.demo.function.gl.data.VertexArray;
import com.sentox.demo.function.gl.program.TextureShaderProgram;

import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glDrawArrays;

/**
 * 描述：桌面数据
 * 说明：
 * Created by Sentox
 * Created on 2019/6/13
 */
public class Table {
    /**
     * 每个点的位置参数个数
     **/
    private static final int POSITION_COMPONENT_COUNT = 2;
    /**
     * java浮点数（float)有32位（bit）精度,
     * 一个字节（byte）只有8位精度，
     * 因此每个浮点数占用4个字节
     **/
    private static final int BYTES_PER_FLOAT = 4;
    //纹理坐标数据参数个数
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;

    //跨距
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT) * BYTES_PER_FLOAT;


    private static float[] VERTEX_DATA = {
            //坐标数据：X,Y,S,T
            //S为纹理横坐标，T为纹理纵坐标

            //三角扇坐标,我们这里想让图片跟桌子一样大，因此也要在每个数据上加上纹理坐标
            //注意，纹理坐标的0点与顶点坐标不同,且图片的朝向是朝右朝上的，因此需要T坐标与y坐标方向相反
            //这里的桌子是1单位宽，1.6单位高，而图片的比例是1：3，因此我们需要裁剪它的高度，并计算对应比例，则高度应为1.6/3,约为0.54，
            //则居中裁剪纹理后T坐标为0.23f，0.77f
            0f, 0f, 0.5f, 0.5f,
            -0.5f, -0.8f, 0f, 0.77f,
            0.5f, -0.8f, 1f, 0.77f,
            0.5f, 0.8f, 1f, 0.23f,
            -0.5f, 0.8f, 0f, 0.23f,
            -0.5f, -0.8f, 0f, 0.77f
    };

    private final VertexArray mVertexArray;

    public Table() {
        mVertexArray = new VertexArray(VERTEX_DATA);
    }

    public void bindData(TextureShaderProgram textureProgram) {
        //绑定顶点坐标
        mVertexArray.setVertexAttribPointer(0, textureProgram.getaPositionLocation(), POSITION_COMPONENT_COUNT, STRIDE);
        //绑定纹理坐标
        mVertexArray.setVertexAttribPointer(POSITION_COMPONENT_COUNT, textureProgram.getaTextureCoordinatesLocation(),
                TEXTURE_COORDINATES_COMPONENT_COUNT, STRIDE);
    }

    public void draw(){
        glDrawArrays(GL_TRIANGLE_FAN,0,6);
    }

}
