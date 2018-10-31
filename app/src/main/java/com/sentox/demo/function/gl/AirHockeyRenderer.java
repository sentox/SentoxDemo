package com.sentox.demo.function.gl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.sentox.demo.function.base.log.Loger;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 描述：测试用渲染器
 * 说明：
 * Created by Sentox
 * Created on 2018/10/29
 */
public class AirHockeyRenderer implements GLSurfaceView.Renderer {

    private final String TAG = "AirHockeyRenderer";

    //每个点的位置参数个数
    private static final int POSITION_COMPONENT_COUNT = 2;
    /**java浮点数（float)有32位（bit）精度,
     * 一个字节（byte）只有8位精度，
     * 因此每个浮点数占用4个字节**/
    private static final int BYTES_PER_FLOAT = 4;
    //本地内存块
    private final FloatBuffer mTrianglesData;
    private final FloatBuffer mMalletsData;
    private final FloatBuffer mLineData;

    public AirHockeyRenderer() {
        float[] tableVertices = {
                0f, 0f,
                0f, 14f,
                9f, 14f,
                9f, 0f
        };

        float[] tableVerticesTriangles = {
                //三角形1
                0f, 0f,
                9f, 14f,
                0f, 14f,
                //三角形2
                0f, 0f,
                9f, 0f,
                9f, 14f
        };

        float[] tableVerticesLine = {
                0f, 7f,
                9f, 7f
        };

        float[] tableVerticesMallets = {
                4.5f, 2f,
                4.5f, 12f
        };

        mTrianglesData = ByteBuffer
                //分配内存空间大小
                .allocateDirect(tableVerticesTriangles.length * BYTES_PER_FLOAT)
                //按照本地字节顺序排序（例如浮点数就是从左往右）
                .order(ByteOrder.nativeOrder())
                //反应底层字节的FloatBuffer实例，可以统一使用而不是一个个字节使用
                .asFloatBuffer();

        mLineData = ByteBuffer
                .allocateDirect(tableVerticesLine.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();

        mMalletsData = ByteBuffer
                .allocateDirect(tableVerticesMallets.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        Loger.i(TAG, "surface被创建");
        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        Loger.i(TAG, "surface被改变");
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
//        Loger.i(TAG, "surface渲染一帧");
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    }
}
