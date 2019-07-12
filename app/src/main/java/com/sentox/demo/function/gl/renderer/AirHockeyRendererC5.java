package com.sentox.demo.function.gl.renderer;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.sentox.demo.R;
import com.sentox.demo.function.base.log.Loger;
import com.sentox.demo.function.gl.objects.Table;
import com.sentox.demo.function.gl.program.TextureShaderProgram;
import com.sentox.demo.utils.opengl.TextureHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;

/**
 * 描述：测试用渲染器
 * 说明：纹理单元
 * Created by Sentox
 * Created on 2019/06/16
 */
public class AirHockeyRendererC5 implements GLSurfaceView.Renderer {

    private final String TAG = "AirHockeyRendererC5";

    private final float[] mProjectionMatrix = new float[16];
    private final float[] modelMatrix = new float[16];

    private Table mTable;

    private TextureShaderProgram mTextureShaderProgram;

    private int mTextureId;

    public AirHockeyRendererC5() {

    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

        glClearColor(0,0,0,0);
        mTable = new Table();

        mTextureShaderProgram = new TextureShaderProgram();

        mTextureId = TextureHelper.loadTexture(R.mipmap.bg_dragon);
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
        if (width > height) {
            //横屏，在此状态下坐标的宽度扩展
            Matrix.orthoM(mProjectionMatrix, 0, -aspectRatio, aspectRatio, -1f, 1f, -1f, 1f);
        } else {
            //正方形或者竖屏，在此状态下坐标的高度扩展
            Matrix.orthoM(mProjectionMatrix, 0, -1f, 1f, -aspectRatio, aspectRatio, -1f, 1f);
        }
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        glClear(GL_COLOR_BUFFER_BIT);

        mTextureShaderProgram.useProgram();
        mTextureShaderProgram.setUniforms(mProjectionMatrix,mTextureId);
        mTable.bindData(mTextureShaderProgram);
        mTable.draw();

    }
}
