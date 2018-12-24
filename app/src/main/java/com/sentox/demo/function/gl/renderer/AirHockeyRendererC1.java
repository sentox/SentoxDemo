package com.sentox.demo.function.gl.renderer;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.sentox.demo.BuildConfig;
import com.sentox.demo.R;
import com.sentox.demo.function.base.log.Loger;
import com.sentox.demo.utils.opengl.ShaderHelper;
import com.sentox.demo.utils.opengl.TextResourceReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;

/**
 * 描述：测试用渲染器
 * 说明：第一章
 * Created by Sentox
 * Created on 2018/10/29
 */
public class AirHockeyRendererC1 implements GLSurfaceView.Renderer {

    private final String TAG = "AirHockeyRendererC1";

    //每个点的位置参数个数
    private static final int POSITION_COMPONENT_COUNT = 2;
    /**
     * java浮点数（float)有32位（bit）精度,
     * 一个字节（byte）只有8位精度，
     * 因此每个浮点数占用4个字节
     **/
    private static final int BYTES_PER_FLOAT = 4;
    //本地内存块
    private final FloatBuffer mTableData;
//    private final FloatBuffer mMalletsData;
//    private final FloatBuffer mLineData;

    private int mProgram;
    private static final String A_POSITION = "a_Position";
    private static final String U_COLOR = "u_Color";
    //片段着色器颜色储存位置
    private int mUColorLocation;
    //顶点着色器位置信息存储位置
    private int mAPositionLocation;


    public AirHockeyRendererC1() {

        float[] tableVerticesTriangles = {
                //三角形1
                -0.5f, -0.5f,
                0.5f, 0.5f,
                -0.5f, 0.5f,
                //三角形2
                -0.5f, -0.5f,
                0.5f, -0.5f,
                0.5f, 0.5f,
                //line
                -0.5f, 0f,
                0.5f, 0f,
                //两个点
                0f, -0.25f,
                0f, 0.25f,
                //边框1
                -0.6f, -0.6f,
                0.6f, -0.6f,
                //边框2
                0.6f, 0.6f,
                -0.6f, 0.6f,
                //边框3
                -0.6f, -0.6f,
                -0.6f, 0.6f,
                //边框4
                0.6f, 0.6f,
                0.6f, -0.6f
        };

        mTableData = ByteBuffer
                //分配内存空间大小
                .allocateDirect(tableVerticesTriangles.length * BYTES_PER_FLOAT)
                //按照本地字节顺序排序（例如浮点数就是从左往右）
                .order(ByteOrder.nativeOrder())
                //反应底层字节的FloatBuffer实例，可以统一使用而不是一个个字节使用
                .asFloatBuffer();
        //传入数据
        mTableData.put(tableVerticesTriangles);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        Loger.i(TAG, "surface被创建");
        glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
        String vertexShaderSource = TextResourceReader.readTextFileFromResource(R.raw.simple_vertex_shader);
        String fragmentShaderSource = TextResourceReader.readTextFileFromResource(R.raw.simple_fragment_shader);

        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);
        mProgram = ShaderHelper.linkProgram(vertexShader, fragmentShader);
        if (BuildConfig.LOG_DEBUG) {
            ShaderHelper.validateProgram(mProgram);
        }
        glUseProgram(mProgram);

        mUColorLocation = glGetUniformLocation(mProgram, U_COLOR);
        mAPositionLocation = glGetAttribLocation(mProgram, A_POSITION);
        //将内存空间的指针指向0处，从头开始读取数据
        mTableData.position(0);
        /**
         * index：属性位置
         * size：顶点分量数量，此处为之前定义的2，
         *      但是在着色器中a_Position被定义为vec4，
         *      所以有4个分量，在有分量没被指定值的情况下，
         *      默认会把前三个分量设为0，最后一个分量设为1
         *
         * type:顶点数据类型
         * normalized:只有使用整型数时才有意义，忽略
         * stride：只有当一个数组存储多余一个属性时才有意义（例如不只是顶点坐标属性）
         * ptr：读取顶点的内存buffer对象
         * **/
        glVertexAttribPointer(mAPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT,
                false, 0, mTableData);

        //使能顶点数组（激活顶点数组）
        glEnableVertexAttribArray(mAPositionLocation);

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        Loger.i(TAG, "surface被改变");
        GLES20.glViewport(0, 0, width, height);
//        glClearColor(0,0,0,0);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
//        Loger.i(TAG, "surface渲染一帧");
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        //调用代码更新着色器中u_Color的值，下面的值分别代表着红、绿、蓝的亮度，最后一个代表alpha值
        glUniform4f(mUColorLocation, 0.0f, 1.0f, 0.0f, 1.0f);
        //绘制桌子，参数1代表要画三角形，参数2说明从顶点数组开始处读取顶点，参数3代表读入6个顶点
        glDrawArrays(GL_TRIANGLES, 0, 6);
        //设颜色为红色
        glUniform4f(mUColorLocation, 1.0f, 0f, 0f, 1.0f);
        //画中线
        glDrawArrays(GL_LINES, 6, 2);
        //设颜色为蓝色
        glUniform4f(mUColorLocation, 0f, 0f, 1.0f, 1.0f);
        //画点
        glDrawArrays(GL_POINTS, 8, 1);
        glDrawArrays(GL_POINTS, 9, 1);

        //设颜色为红色
        glUniform4f(mUColorLocation, 1.0f, 0f, 0f, 1.0f);
        //画边框
        glDrawArrays(GL_LINES,10,8);

    }
}
