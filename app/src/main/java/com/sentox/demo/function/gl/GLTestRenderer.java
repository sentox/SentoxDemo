package com.sentox.demo.function.gl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.sentox.demo.function.base.log.Loger;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 描述：测试用渲染器
 * 说明：
 * Created by Sentox
 * Created on 2018/10/29
 */
public class GLTestRenderer implements GLSurfaceView.Renderer {

    private final String TAG = "GLTestRenderer";

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        Loger.i(TAG, "surface被创建");
        GLES20.glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        Loger.i(TAG, "surface被改变");
        GLES20.glViewport(0 ,0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
//        Loger.i(TAG, "surface渲染一帧");
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
    }
}
