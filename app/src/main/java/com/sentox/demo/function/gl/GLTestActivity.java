package com.sentox.demo.function.gl;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sentox.demo.R;
import com.sentox.demo.function.gl.renderer.AirHockeyRendererC5;

/**
 * 描述：GlSurfaceView测试activity
 * 说明：
 * Created by Sentox
 * Created on 2018/10/29
 */
public class GLTestActivity extends Activity {

    private GLSurfaceView mGLSurfaceView;
    private boolean mFlagRendererSet = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gl_test);
        init();
    }

    private void init(){
        mGLSurfaceView = (GLSurfaceView)findViewById(R.id.glsurfaceview);
        mGLSurfaceView.setEGLContextClientVersion(2);
        mGLSurfaceView.setRenderer(new AirHockeyRendererC5());
        mFlagRendererSet = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mFlagRendererSet) {
            mGLSurfaceView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mFlagRendererSet) {
            mGLSurfaceView.onPause();
        }
    }
}
