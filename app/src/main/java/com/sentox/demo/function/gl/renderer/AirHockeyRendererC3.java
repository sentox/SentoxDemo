package com.sentox.demo.function.gl.renderer;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

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
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;

/**
 * 描述：测试用渲染器
 * 说明：第3章
 * Created by Sentox
 * Created on 2018/11/13
 */
public class AirHockeyRendererC3 implements GLSurfaceView.Renderer {

    private final String TAG = "AirHockeyRendererC3";

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

    private int mProgram;
    private static final String A_POSITION = "a_Position";

    private static final String A_COLOR = "a_Color";
    //单个颜色值数据个数
    private static final int COLOR_COMPONENT_COUNT = 3;
    //跨距，由于现在一个顶点有位置和颜色属性，因此OpenGL需要知道stride才能知道每个位置间有多少个字节，才能正确读取数据
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;

    //顶点着色器位置信息存储位置
    private int mAPositionLocation;
    //顶点着色器颜色储存位置
    private int mAColorLocation;

    private static final String U_MATRIX = "u_Matrix";

    //矩阵存储
    private final float[] mProjectionMatrix = new float[16];
    //矩阵位置
    private int mUMatrixLocation;


    public AirHockeyRendererC3() {

        float[] tableVerticesTriangles = {

                //数据结构：x（x坐标）,y（y坐标）,R（红色通道）,G（绿色通道）,B（蓝色通道）
                //颜色值在这里只有3位，单事实上OpenGL中颜色值有4个分量，在这里会使用默认值1替换没有被复制的alpha分量
                //扇形三角形
                0f, 0f, 1f, 1f, 1f,
                -0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
                0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
                0.5f, 0.8f, 0.7f, 0.7f, 0.7f,
                -0.5f, 0.8f, 0.7f, 0.7f, 0.7f,
                -0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
                //line
                -0.5f, 0f, 1f, 0f, 0f,
                0.5f, 0f, 1f, 0f, 0f,
                //两个点
                0f, -0.25f, 0f, 0f, 1f,
                0f, 0.25f, 1f, 0f, 0f,
                //边框1
                -0.6f, -0.9f, 1f, 1f, 0f,
                0.6f, -0.9f, 1f, 0f, 0f,
                //边框2
                0.6f, 0.9f, 1f, 1f, 0f,
                -0.6f, 0.9f, 1f, 0f, 0f,
                //边框3
                -0.6f, -0.9f, 1f, 1f, 0f,
                -0.6f, 0.9f, 1f, 0f, 0f,
                //边框4
                0.6f, 0.9f, 1f, 1f, 0f,
                0.6f, -0.9f, 1f, 0f, 0f
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
        glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
        Loger.i(TAG, "surface被创建");
        String vertexShaderSource = TextResourceReader.readTextFileFromResource(R.raw.simple_vertex_shader_c3);
        String fragmentShaderSource = TextResourceReader.readTextFileFromResource(R.raw.simple_fragment_shader_c2);

        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);
        mProgram = ShaderHelper.linkProgram(vertexShader, fragmentShader);
        if (BuildConfig.LOG_DEBUG) {
            ShaderHelper.validateProgram(mProgram);
        }
        //将对象传入OpenGL
        glUseProgram(mProgram);

        mAColorLocation = glGetAttribLocation(mProgram, A_COLOR);

        mAPositionLocation = glGetAttribLocation(mProgram, A_POSITION);
        //将内存空间的指针指向0处，从头开始读取数据
        mTableData.position(0);
        /**
         * 将顶点的位置数据与着色器中的A_POSITION关联起来
         * index：属性位置
         * size：顶点分量数量，此处为之前定义的2，
         *      但是在着色器中a_Position被定义为vec4，
         *      所以有4个分量，在有分量没被指定值的情况下，
         *      默认会把前三个分量设为0，最后一个分量设为1
         *
         * type:顶点数据类型
         * normalized:只有使用整型数时才有意义，忽略
         * stride：只有当一个数组存储多于一个属性时才有意义（例如不只是顶点坐标属性）
         * ptr：读取顶点的内存buffer对象
         * **/
        glVertexAttribPointer(mAPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT,
                false, STRIDE, mTableData);

        //使能顶点位置数组（激活顶点数组）
        glEnableVertexAttribArray(mAPositionLocation);

        //定位到POSITION_COMPONENT_COUNT，第一个顶点的颜色值由这里开始
        mTableData.position(POSITION_COMPONENT_COUNT);
        /**
         *  将顶点的颜色数据与着色器中的A_COLOR关联起来
         * **/
        glVertexAttribPointer(mAColorLocation, COLOR_COMPONENT_COUNT, GL_FLOAT, false, STRIDE, mTableData);
        //使能顶点颜色数组
        glEnableVertexAttribArray(mAColorLocation);

        //获取矩阵在着色器中存储的位置
        mUMatrixLocation = glGetUniformLocation(mProgram, U_MATRIX);

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        Loger.i(TAG, "surface被改变");
        //通知OpenGL可视区域改变
        GLES20.glViewport(0, 0, width, height);
        //获得实际长宽比，以较长的边为分子，较短的边为分母
        final float aspectRatio = width > height ? (float) width / (float) height :
                (float) height / (float) width;

        //获取矩阵坐标空间
        if(width>height){
            //横屏，在此状态下坐标的宽度扩展
            Matrix.orthoM(mProjectionMatrix,0,-aspectRatio,aspectRatio,-1f,1f,-1f,1f);
        }else{
            //正方形或者竖屏，在此状态下坐标的高度扩展
            Matrix.orthoM(mProjectionMatrix,0,-1f,1f,-aspectRatio,aspectRatio,-1f,1f);
        }
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
//        Loger.i(TAG, "surface渲染一帧");
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        //将计算好的矩阵传递给顶点着色器
        glUniformMatrix4fv(mUMatrixLocation,1,false,mProjectionMatrix,0);

        //绘制桌子，参数1代表要画三角形扇，参数2说明从顶点数组开始处读取顶点，参数3代表读入6个顶点
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
        //画中线
        glDrawArrays(GL_LINES, 6, 2);
        //画点
        glDrawArrays(GL_POINTS, 8, 1);
        glDrawArrays(GL_POINTS, 9, 1);

        //画边框
        glDrawArrays(GL_LINES, 10, 8);

    }
}
