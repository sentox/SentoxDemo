package com.sentox.demo.utils.opengl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import com.sentox.demo.function.base.application.GOApplication;
import com.sentox.demo.function.base.log.Loger;

import static android.opengl.GLES20.GL_LINEAR;
import static android.opengl.GLES20.GL_LINEAR_MIPMAP_LINEAR;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_MIN_FILTER;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDeleteTextures;
import static android.opengl.GLES20.glGenTextures;
import static android.opengl.GLES20.glGenerateMipmap;
import static android.opengl.GLES20.glTexParameteri;

/**
 * 描述：纹理帮助类
 * 说明：
 * Created by Sentox
 * Created on 2019/6/13
 */
public class TextureHelper {

    public static final String TAG = "TextureHelper";

    /**
     * 加载图片资源进入新建的纹理对象
     *
     * @return 纹理id（0则代表创建失败）
     **/
    public static int loadTexture(int resoureId) {
        final int[] textureObjectIds = new int[1];
        glGenTextures(1, textureObjectIds, 0);

        if (textureObjectIds[0] == 0) {
            Loger.i(TAG, "无法生成新的OpenGL纹理对象");
            return 0;
        }
        //解码位图
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        final Bitmap bitmap = BitmapFactory.decodeResource(GOApplication.getAppContext().getResources(), resoureId, options);
        if (bitmap == null) {
            Loger.i(TAG, "ResId:" + resoureId + ",图片解码失败,删除纹理对象");
            glDeleteTextures(1, textureObjectIds, 0);
            return 0;
        }

        //OpenGL绑定这个2D纹理的id
        glBindTexture(GL_TEXTURE_2D, textureObjectIds[0]);

        //设置图片缩小时使用三线性过滤
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        //设置图片放大时使用双线性过滤
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        //OpenGL读入图片数据
        GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();

        //生成mip贴图
        glGenerateMipmap(GL_TEXTURE_2D);
        //完成纹理加载后,解除绑定的纹理
        glBindTexture(GL_TEXTURE_2D,0);

        return textureObjectIds[0];
    }

}
